require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'	

# Extraction
def extract single
	# ARCHIVE
	url = "https://twitter.com/privacy/previous?lang=en"
	html = open(url).read
	dom = Nokogiri::HTML html
	links, dates = [], []
	dom.xpath('//ul[@class="UserPolicy--previousListing-links"]//a').each do |el|
		links.push("https://twitter.com" + el.attr("href").strip + "?lang=en")
		dates.push(el.text.strip)
		break if single
	end
	# OPEN DB
	if !single
		@db.execute("DELETE FROM `TWITTER`;")
	end
	# WRITE DB
	for i in 1..links.size
		link = links[links.size-i]
		date = dates[dates.size-i]
		dom = Nokogiri::HTML open(link).read
		elements = dom.xpath('//div[@class="Field-items"]//p|//h2|//h3').collect do |el|
			el.text.strip
		end
		elements = elements[2..elements.size-1]
		plain = elements.join "\n"
		@db.execute("INSERT INTO `TWITTER` (DATE, LINK, CONTENT) VALUES(?,?,?);", "#{date}", "#{link}", "#{plain}")
	end
end

# Main program
begin
	@db = SQLite3::Database.new "tmcrawl.db"
	@db.execute("CREATE TABLE IF NOT EXISTS `TWITTER` (`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
	if ARGV[0].to_i == 1
		extract true
	elsif ARGV[1].to_i == 0
		extract false
	end
end