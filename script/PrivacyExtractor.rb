require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'

require './ArchiveExtractor.rb'
require './PrivacyDiffer.rb'

# Module for extracting privacy policies
# @author Simon Kaleschke
module PrivacyExtractor

	# Accessors for crawl.rb
	class << self
		attr_accessor :mode, :url, :table_name, :xpath, :plain_block
		attr_accessor :own_archive, :archive_xpath, :archive_url_block, :archive_date_block
	end

	# Mode of extraction, :update or fetch
	self.mode = :fetch
	# Url to extract from OR url to get archive links from
	self.url = ""
	# Name for the table in which the information is stored
	self.table_name = ""
	# XPath used for extracting the policy
	self.xpath = "//html"
	# Lambda for manipulating extracted text (e. g. removing whitespace)
	self.plain_block = -> (plain) {plain}
	# Whether the website has its own archive or not
	self.own_archive = false
	# XPath for extracting the archive links from the archive url
	self.archive_xpath = "//a"
	# Lambda for manipulating the extracted urls (e. g. checking if the link is relative)
	self.archive_url_block = -> (link) {self.url + link}
	# Lambda for manipulating the extracted date (default format is "MONTHNAME DAY, YEAR")
	self.archive_date_block = -> (date) {date}

	# Array for storing link and date information
	@links, @dates = [], []
	# Database to store the information in
	@db = SQLite3::Database.new "./../policies.db"

	# The main extraction process.
	def self.extract
		# Get links to each policy over time from the own archive OR Archive.org.
		# Choose only one link if you're in update mode.
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
		# Create the table in the database.
		@db.execute("CREATE TABLE IF NOT EXISTS `#{self.table_name}` 
			(`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
		# Drop the table if you want to fetch all information anew.
		@db.execute("DELETE FROM `#{self.table_name}`;") if self.mode == :fetch
		# Policy extraction from each link starts here.
		range = (self.own_archive) ? (1..@links.size) : (0..@links.size-1)
		newest_plain = ""
		distinct_versions = 1 
		for i in range
			# Get the link and date from the array.
			link = (self.own_archive) ? @links[@links.size-i] : @links[i]
			date = (self.own_archive) ? @dates[@dates.size-i] : @dates[i]
			dom = Nokogiri::HTML open(link).read
			# Extract plain text and modify it, join by newlines
			plain = dom.xpath(self.xpath).collect do |el|
				self.plain_block[el.text.strip]
			end.join("\n")
			# If this entry is the first, put it in the database as base for difference checking.
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
			# Check whether the plaintext was extracted properly
			if plain.include?("Got an HTTP 302 response at crawl time")
				next
			end
			# Check for differences between entries
			different = PrivacyDiffer::different?(newest_plain,plain)
			# If the difference is big enough, add it to the data base
			# Set this entry as the new base for difference checking
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
