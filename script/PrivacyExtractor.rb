require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'

require './ArchiveExtractor.rb'
require './PrivacyDiffer.rb'

module PrivacyExtractor

	class << self
		attr_accessor :mode, :url, :table_name, :xpath, :plain_block
		attr_accessor :own_archive, :archive_xpath, :archive_url_block, :archive_date_block
		attr_accessor :delay
	end

	self.mode = :fetch
	self.url = ""
	self.table_name = ""
	self.xpath = "//html"
	self.plain_block = -> (plain) {plain}
	self.own_archive = false
	self.archive_xpath = "//a"
	self.archive_url_block = -> (link) {self.url + link}
	self.archive_date_block = -> (date) {date}
	self.delay = 0

	@links, @dates = [], []
	@db = SQLite3::Database.new "./../policies.db"

	def self.extract
		if self.own_archive
			dom = Nokogiri::HTML open(self.url).read
			dom.xpath(self.archive_xpath).each do |el|
				@links.push(self.archive_url_block[el.attr("href").strip])
				@dates.push(self.archive_date_block[el.text.strip])
				break if self.mode == :update
			end
		else
			stamps = ArchiveExtractor::stamps(self.url)
			@links = ArchiveExtractor::links(stamps, self.url)
			@dates = ArchiveExtractor::dates(stamps)
			if self.mode == :update
				@links = @links[-1..-1]
				@dates = @dates[-1..-1]
			end
		end
		@db.execute("CREATE TABLE IF NOT EXISTS `#{self.table_name}` 
			(`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
		@db.execute("DELETE FROM `#{self.table_name}`;") if self.mode == :fetch
		range = (self.own_archive) ? (1..@links.size) : (0..@links.size-1)
		newest_plain = ""
		distinct_versions = 1
		sleep(self.delay) 
		for i in range
			sleep(self.delay+i*self.delay)
			link = (self.own_archive) ? @links[@links.size-i] : @links[i]
			date = (self.own_archive) ? @dates[@dates.size-i] : @dates[i]
			dom = Nokogiri::HTML open(link).read
			plain = dom.xpath(self.xpath).collect do |el|
				self.plain_block[el.text.strip]
			end.join("\n")
			if i == range.first
				if self.mode == :update
					newest_id = @db.execute("SELECT COUNT(*) FROM #{self.table_name}")[0][0].to_i
					newest_plain = @db.execute("SELECT CONTENT FROM #{self.table_name} WHERE ID=?","#{newest_id}")[0][0]
				else
					newest_plain = plain
					@db.execute("INSERT INTO `#{self.table_name}` (DATE, LINK, CONTENT) VALUES(?,?,?);", 
					"#{date}", "#{link}", "#{plain}")
					next
				end
			end
			if plain.include?("Got an HTTP 302 response at crawl time")
				next
			end
			different = PrivacyDiffer::different?(newest_plain,plain)
			if different
				newest_plain = plain
				distinct_versions += 1
				puts "Found #{distinct_versions} distinct versions so far."
				@db.execute("INSERT INTO `#{self.table_name}` (DATE, LINK, CONTENT) VALUES(?,?,?);", 
					"#{date}", "#{link}", "#{plain}")
			end
		end
		puts "=> Successfully finished with #{distinct_versions} distinct versions."
	end

end
