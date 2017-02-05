# Import gems
require 'date'
require 'open-uri'
require 'open_uri_redirections'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'

# Import local classes
require_relative 'ArchiveExtractor.rb'
require_relative 'PrivacyDiffer.rb'

# Module for plaintext extraction
# @author Simon Kaleschke
module PrivacyExtractor
	
	# Options for the extraction
	class << self
		# Extract all versions from scratch (:fetch) or just look for a new version (:update)
		attr_accessor :mode
		# The url to extract the plaintext from, or, if it has its own archive, the urls to older versions
		attr_accessor :url
		# The name of the table in which the information is stored in the database
		attr_accessor :table_name
		# Xpath for the main html containers in which the Policy is found
		attr_accessor :xpath
		# Lambda block to manipulate the found html containers
		attr_accessor :plain_block
		# Whether the website has its own archive for Policies (true) or not (false)
		attr_accessor :own_archive
		# Xpath to find links to older versions
		attr_accessor :archive_xpath
		# Lambda block to manipulate said found urls
		attr_accessor :archive_url_block
		# Lambda block to manipulate the date format found in the archive
		attr_accessor :archive_date_block
	end
	
	# Default options for base information
	self.mode = :fetch
	self.url = ""
	self.table_name = ""

	# Default options for the way text is extracted from the html nodes
	self.xpath = "//html"
	self.plain_block = -> (doc) do
		plain = doc.collect{|t| t.text.strip}.join
		if plain.nil?
			""
		else
			plain.strip
		end
	end

	# Default options if own archive is present
	self.own_archive = false
	self.archive_xpath = "//a"
	self.archive_url_block = -> (link) {self.url + link}
	self.archive_date_block = -> (date) {date}

	# Init local variables
	@links, @dates = [], []
	@db_path = ""
	File.open("./../config.ppp", "r") do |line|
		option = line.split(": ")
		if option[0] == "DB-PATH"
			@db_path = option[1]
		end
	end
	@db = SQLite3::Database.new @db_path
	
	# Extract links to older versions from own archive or from archive.org
	def self.getLinks
		if self.own_archive
			# Open the archive website and parse it into a nodeset
			dom = Nokogiri::HTML open(self.url).read
			# Extract link nodes
			dom.xpath(self.archive_xpath).each do |el|
				# Modify found links
				@links.push(self.archive_url_block[el.attr("href").strip])
				# Modify found dates
				@dates.push(self.archive_date_block[el.text.strip])
				# If only the newest version is required, stop here
				if self.mode == :update
					break
				end
			end
		else
			# Get timestamps of old versions from archive.org
			stamps = ArchiveExtractor::stamps(self.url)
			# Using these stamps, generate links to those versions
			@links = ArchiveExtractor::links(stamps, self.url)
			# Using these stamps, generate a proper date format
			@dates = ArchiveExtractor::dates(stamps)
			# If only the newest version is required, stop here
			if self.mode == :update
				@links = [@links[-1]]
				@dates = [@dates[-1]]
			end
		end
		# Ensure the older versions are further down the list
		if self.own_archive
			@links = @links.reverse
			@dates = @dates.reverse
		end
	end

	# Create the final plaintext
	def self.build(link)
		# Open the link and parse a nodeset
		plain = open(link).read
		dom = Nokogiri::HTML(plain)
		# Extract relevant nodes
		doc = dom.xpath(self.xpath)
		# Modify found nodes and return the result
		self.plain_block[doc]
	end

	# Convert display date (Month Day, Year) >> system date (YYYY-MM-DD)
	# One is for the user and the other for the website
	def self.convert_date(date)
		d = Date.parse(date)
		[d.year, d.month, d.day].map{|i|i.to_s}.map{|s|s.length<2 ? "0"+s : s}.join("-")
	end

	# Main extraction process, glues everything together
	def self.extract	
		# If no table for this website exists yet, create it
		@db.execute("CREATE TABLE IF NOT EXISTS `#{self.table_name}` 
			(`ID` INTEGER PRIMARY KEY, `SYSTEM_DATE` TEXT, `DISPLAY_DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
		# If data is written anew from scratch, delete everything in the table
		if self.mode == :fetch
			@db.execute("DELETE FROM `#{self.table_name}`;")
		end
		# Create the urls to extract from
		getLinks()
		# Prepare iteration
		range = (0..@links.size-1)
		newest_plain = ""
		distinct_versions = 1 
		# Iterate over all links, generate plaintext and compare them, save only distinct versions
		range.each do |i|
			# Get link and dates
			link = @links[i]
			display_date = @dates[i]
			system_date = convert_date(display_date)
			# Build the plaintext
			plain = build(link)
			# Skip if nothing was extracted
			if plain.empty?
				next
			end
			# If it's the first iteration, set the first entry as the first distinct version, otherwise take the latest entry
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
			# Compare the newest and the current plaintext
			different = PrivacyDiffer::different?(newest_plain,plain)
			# If they're different, push the new one to the database and set it as newest
			if different
				newest_plain = plain
				distinct_versions += 1
				# Notify new version
				puts "Found #{distinct_versions} distinct versions so far."
				# Save to database
				@db.execute("INSERT INTO `#{self.table_name}` (SYSTEM_DATE, DISPLAY_DATE, LINK, CONTENT) VALUES(?,?,?,?);", 
					"#{system_date}", "#{display_date}", "#{link}", "#{plain}")
			end
		end
		# Notify success
		puts "=> Successfully finished with #{distinct_versions} distinct versions."
	end

end
