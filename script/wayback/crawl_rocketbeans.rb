require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'
require './archive_extractor.rb'

# Extraction
def extract single
	# ARCHIVE
	url = "https://www.rocketbeans.tv/datenschutz/"
	stamps = ArchEx::stamps url
	links = ArchEx::links stamps, url
	dates = ArchEx::dates stamps
	# OPEN DB
	if !single
		@db.execute("DELETE FROM `ROCKETBEANS`;")
	end
	# WRITE DB
	for i in 0..links.size-1
		link = links[i]
		date = dates[i]
		dom = Nokogiri::HTML open(link).read
		plain = dom.xpath('//div[@class="row"]//h1|//h2|//h3|//p').collect do |el|
			el.text.strip
		end.join "\n"
		@db.execute("INSERT INTO `ROCKETBEANS` (DATE, LINK, CONTENT) VALUES(?,?,?);", "#{date}", "#{link}", "#{plain}")
		break if single
	end
end

# Main program
begin
	@db = SQLite3::Database.new "tmcrawl.db"
	@db.execute("CREATE TABLE IF NOT EXISTS `ROCKETBEANS` (`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
	if ARGV[0].to_i == 1
		extract true
	elsif ARGV[1].to_i == 0
		extract false
	end
end
