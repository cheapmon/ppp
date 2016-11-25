require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'	

# Extraction
def extract single
	# ARCHIVE
	url = "https://www.whatsapp.com/legal/?doc=privacy-policy&mode=revisions&l=en"
	html = open(url).read
	dom = Nokogiri::HTML html
	links, dates = [], []
	links.push("https://www.whatsapp.com/legal/?doc=privacy-policy&l=en")
	dates.push("Current Version")
	dom.xpath('(//ul[@class="linklist"]//a)[not(position() = 1)]').each do |el|
		links.push("https://www.whatsapp.com" + el.attr("href").strip)
		dates.push(el.text.strip)
	end if !single
	# OPEN DB
	if !single
		@db.execute("DELETE FROM `WHATSAPP`;")
	end
	# WRITE DB
	for i in 1..links.size
		link = links[links.size-i]
		date = dates[dates.size-i]
		dom = Nokogiri::HTML open(link).read
		plain = dom.xpath('(//div[@class="block__body"])[position() = 1]').collect do |el|
			el.text.strip
		end.join "\n"
		@db.execute("INSERT INTO `WHATSAPP` (DATE, LINK, CONTENT) VALUES(?,?,?);", "#{date}", "#{link}", "#{plain}")
	end
end

# Main program
begin
	@db = SQLite3::Database.new "tmcrawl.db"
	@db.execute("CREATE TABLE IF NOT EXISTS `WHATSAPP` (`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
	if ARGV[0].to_i == 1
		extract true
	elsif ARGV[1].to_i == 0
		extract false
	end
end