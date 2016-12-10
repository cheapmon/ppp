require 'open-uri'
require 'nokogiri'
require 'json'
require 'date'

module ArchiveExtractor

	def self.stamps url
		cdx = "http://web.archive.org/cdx/search/cdx?fl=timestamp&output=json&url=" + url
		html = open(URI.encode(cdx)).read
		stamps = JSON.parse(html)
		return stamps.collect do |s|
			s[0]
		end
	end
	
	def self.links stamps, url
		return (1..stamps.size-1).collect do |s|
			URI.encode("https://web.archive.org/web/" + stamps[s] + "/" + url)
		end
	end
	
	def self.dates stamps
		return (1..stamps.size-1).collect do |s|
			year = stamps[s][0..3]
			month = Date::MONTHNAMES[stamps[s][4..5].to_i]
			day = stamps[s][6..7]
			month + " " + day + ", " + year
		end	
	end
		
end