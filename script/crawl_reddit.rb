require 'open-uri'
require 'nokogiri'
require 'rubygems'
require 'sqlite3'	

# Extraction
def extract single
	# ARCHIVE
	url = "https://www.reddit.com/help/privacypolicy"
	html = open(url).read
	dom = Nokogiri::HTML html
	links, dates = [], []
	dom.xpath('//ul[@class="revisions"]//a').each do |el|
		links.push(url + el.attr("href").strip)
		dates.push(el.text.strip)
		break if single
	end
	# OPEN DB
	if !single
		@db.execute("DELETE FROM `REDDIT`;")
	end
	# WRITE DB
	for i in 1..links.size
		#sleep(10.0+i*5.0)
		link = links[links.size-i]
		date = dates[dates.size-i]
		dom = Nokogiri::HTML open(link).read
		plain = dom.xpath('//div[@class="md wiki"]').collect do |el|
			el.text.strip
		end.join "\n"
		@db.execute("INSERT INTO `REDDIT` (DATE, LINK, CONTENT) VALUES(?,?,?);", "#{date}", "#{link}", "#{plain}")
	end
end

# Main program
begin
	@db = SQLite3::Database.new "tmcrawl.db"
	@db.execute("CREATE TABLE IF NOT EXISTS `REDDIT` (`ID` INTEGER PRIMARY KEY, `DATE` TEXT, `LINK` TEXT, `CONTENT` TEXT);")
	if ARGV[0].to_i == 1
		extract true
	elsif ARGV[1].to_i == 0
		extract false
	end
end