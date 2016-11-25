require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'	

# Extraction
def extract single
	# ARCHIVE
	url = "https://wikimediafoundation.org/wiki/Privacy_policy"
	html = open(url).read
	dom = Nokogiri::HTML html
	links, dates = [], []
	dom.xpath('//p[contains(.,"This privacy policy was approved by the board")]/following::ul[1]//a').each do |el|
		links.push("https://wikimediafoundation.org" + el.attr("href"))
		dates.push(el.text.strip)
		break if single
	end
	# OPEN DB
	if !single
		@db.execute("DELETE FROM `WIKIMEDIA`;")
	end
	# WRITE DB
	for i in 1..links.size
		link = links[links.size-i]
		date = dates[dates.size-i]
		dom = Nokogiri::HTML open(link).read
		plain = dom.xpath('//div[@id="bodyContent"]//p|//h1|//h2|//h3|//h4').collect do |el|
			el.text.strip
		end.join "\n"
		@db.execute("INSERT INTO `WIKIMEDIA` (DATE, LINK, CONTENT) VALUES(?,?,?);", "#{date}", "#{link}", "#{plain}")
	end
end

# Main program
begin
	@db = SQLite3::Database.new "tmcrawl.db"
	@db.execute("CREATE TABLE IF NOT EXISTS `WIKIMEDIA` (`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
	if ARGV[0].to_i == 1
		extract true
	elsif ARGV[1].to_i == 0
		extract false
	end
end