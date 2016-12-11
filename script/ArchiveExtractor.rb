require 'open-uri'
require 'nokogiri'
require 'json'
require 'date'

# Module for extracting links to old histories of websites, using the wayback machine by archive.org.
# @author Simon Kaleschke
module ArchiveExtractor

	# Get all the timestamps for saved versions.
	# @param url [String] The url to hand over to the wayback machine.
	# @return [Array<String] All timestamps.
	def self.stamps url
		cdx = "http://web.archive.org/cdx/search/cdx?fl=timestamp&output=json&url=" + url
		html = open(URI.encode(cdx)).read
		stamps = JSON.parse(html)
		return stamps.collect do |s|
			s[0]
		end
	end
	
	# Get all links to saved versions.
	# @param stamps [Array<String>] Timestamps of previous versions.
	# @param url [String] The url to hand over to the wayback machine.
	# @return [Array<String>] Full links of the old versions.
	def self.links stamps, url
		return (1..stamps.size-1).collect do |s|
			URI.encode("https://web.archive.org/web/" + stamps[s] + "/" + url)
		end
	end
	
	# Convert the waybackmachine timestamps to default format. ("MONTHNAME DAY, YEAR")
	# @param stamps [Array<String>] Timestamps of previous versions.
	# @return [Array<String>] The converted dates.
	def self.dates stamps
		return (1..stamps.size-1).collect do |s|
			year = stamps[s][0..3]
			month = Date::MONTHNAMES[stamps[s][4..5].to_i]
			day = stamps[s][6..7]
			month + " " + day + ", " + year
		end	
	end
		
end