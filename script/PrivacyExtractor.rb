require 'date'
require 'open-uri'
require 'open_uri_redirections'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'

require_relative 'ArchiveExtractor.rb'
require_relative 'PrivacyDiffer.rb'


module PrivacyExtractor
	
	class << self
		attr_accessor :mode, :url, :table_name
		attr_accessor :xpath, :plain_block
		attr_accessor :own_archive, :archive_xpath, :archive_url_block, :archive_date_block
	end
	
	self.mode = :fetch
	self.url = ""
	self.table_name = ""

	self.xpath = "//html"
	self.plain_block = -> (doc) do
		plain = doc.collect{|t| t.text.strip}.join
		if plain.nil?
			""
		else
			plain.strip
		end
	end

	self.own_archive = false
	self.archive_xpath = "//a"
	self.archive_url_block = -> (link) {self.url + link}
	self.archive_date_block = -> (date) {date}

	@links, @dates = [], []
	@db = SQLite3::Database.new "./../../script/policies.db"
	
	# LINKS UND DATES EXTRAHIEREN
	def self.getLinks
		if self.own_archive
			dom = Nokogiri::HTML open(self.url).read
			dom.xpath(self.archive_xpath).each do |el|
				@links.push(self.archive_url_block[el.attr("href").strip])
				@dates.push(self.archive_date_block[el.text.strip])
				if self.mode == :update
					break
				end
			end
		else
			stamps = ArchiveExtractor::stamps(self.url)
			@links = ArchiveExtractor::links(stamps, self.url)
			@dates = ArchiveExtractor::dates(stamps)
			if self.mode == :update
				@links = [@links[-1]]
				@dates = [@dates[-1]]
			end
		end
		if self.own_archive
			@links = @links.reverse
			@dates = @dates.reverse
		end
	end

	# PLAINTEXT ZUSAMMENSETZEN
	def self.build(link)
		#puts link
		plain = open(link).read
		dom = Nokogiri::HTML(plain)
		doc = dom.xpath(self.xpath)
		self.plain_block[doc]
	end

	# DATUM KONVERTIEREN
	def self.convert_date(date)
		d = Date.parse(date)
		[d.year, d.month, d.day].map{|i|i.to_s}.map{|s|s.length<2 ? "0"+s : s}.join("-")
	end

	# ALLGEMEINER PROZESS
	def self.extract	
		@db.execute("CREATE TABLE IF NOT EXISTS `#{self.table_name}` 
			(`ID` INTEGER PRIMARY KEY, `SYSTEM_DATE` TEXT, `DISPLAY_DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
		if self.mode == :fetch
			@db.execute("DELETE FROM `#{self.table_name}`;")
		end
		getLinks()
		range = (0..@links.size-1)
		newest_plain = ""
		distinct_versions = 1 
		range.each do |i|
			link = @links[i]
			display_date = @dates[i]
			system_date = convert_date(display_date)
			plain = build(link)
			if plain.empty?
				next
			end
			if i == range.first
				if self.mode == :update
					newest_id = @db.execute("SELECT COUNT(*) FROM #{self.table_name}")[0][0].to_i
					newest_plain = @db.execute("SELECT CONTENT FROM #{self.table_name} WHERE ID=?","#{newest_id}")[0][0]
				else
					newest_plain = plain
					@db.execute("INSERT INTO `#{self.table_name}` (SYSTEM_DATE, DISPLAY_DATE, LINK, CONTENT) VALUES(?,?,?,?);", 
					"#{system_date}", "#{display_date}", "#{link}", "#{plain}")
					next
				end
			end
			different = PrivacyDiffer::different?(newest_plain,plain)
			if different
				newest_plain = plain
				distinct_versions += 1
				puts "Found #{distinct_versions} distinct versions so far."
				@db.execute("INSERT INTO `#{self.table_name}` (SYSTEM_DATE, DISPLAY_DATE, LINK, CONTENT) VALUES(?,?,?,?);", 
					"#{system_date}", "#{display_date}", "#{link}", "#{plain}")
			end
		end
		puts "=> Successfully finished with #{distinct_versions} distinct versions."
	end

end
