require 'date'
require_relative 'PrivacyExtractor.rb'

# Main script, containing all information neccessary for the extractor.
# @author Simon Kaleschke
module Crawler
	# The website to crawl, see below
	SITE = ARGV[0].to_sym
	# Mode of extraction, :update or :fetch
	MODE = ARGV[1].to_sym
	def self.start
		# Options for each site
		# @see PrivacyExtractor
		case SITE
			when :alternate
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "http://www.alternate.de/HILFE/ALTERNATE/Datenschutz"
				PrivacyExtractor.table_name = "ALTERNATE"
				PrivacyExtractor.xpath = '//div[@id="pageContent"]//div[@class="part"]'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.search(".//script").remove
					doc.collect do |part|
						part.text.gsub(/[^\S\n]+/,' ').strip + "\n\n"
					end.join.strip
				end
			when :amorelie
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.amorelie.de/datenschutz/"
				PrivacyExtractor.table_name = "AMORELIE"
				PrivacyExtractor.xpath = '//div[@class="container" or @class="std"]//p'
			when :apple
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "http://www.apple.com/privacy/privacy-policy/"
				PrivacyExtractor.table_name = "APPLE"
				PrivacyExtractor.xpath = '//div[@class="main"]//ul|//h1|//h5|//h6|//p'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.collect do |node|
						text = node.text.gsub(/[^\S\n]+/,' ').strip
						if node.name.include?("h")
							text + "\n"
						else
							text + "\n\n"
						end
					end.join.strip
				end
			when :burgerking
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.bk.com/privacy"
				PrivacyExtractor.table_name = "BURGERKING"
				PrivacyExtractor.xpath = '(//div[@class="row content"])[position()=1]//p'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.search(".//script").remove
					plain = doc.collect do |node|
						node.text.strip
					end
					plain.join("\n")
				end
			when :edeka
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.edeka.de/modulseiten/datenschutzbestimmungen/datenschutzbestimmungen.jsp"
				PrivacyExtractor.table_name = "EDEKA"
				PrivacyExtractor.xpath = '//div[@class="mainCl60"]//p|//h2'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect{|node|node.text.strip}.join("\n")
				end
			when :google
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.own_archive = true
				PrivacyExtractor.url = "https://www.google.com/intl/en/policies/privacy/archive/"
				PrivacyExtractor.table_name = "GOOGLE"
				PrivacyExtractor.xpath = '(//div[@role="article"]//h1)|(//div[@role="article"]//h2)|(//div[@role="article"]//p)|(//div[@role="article"]//ul)'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name == "p"
							t + "\n\n"
						else
							t + "\n"
						end
					end
					plain.join.strip
				end
				PrivacyExtractor.archive_xpath = '(//ul[@id="archives"]//a)[position() mod 2 = 1]'
				PrivacyExtractor.archive_date_block = -> (date) do
					if date == "Current version"
						Date::MONTHNAMES[Time.now.month] + " " + Time.now.day.to_s + ", " + Time.now.year.to_s
					else
						date
					end
				end
			when :microsoft
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://privacy.microsoft.com/en-us/privacystatement/"
				PrivacyExtractor.table_name = "MICROSOFT"
				PrivacyExtractor.xpath = '(//h1)|(//div[@class="div_content"]//p|//div[@class="div_heading_OnePSTemplete header-small"])'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name == "p"
							t + "\n\n"
						elsif node.name == "h1" or node.name == "div"
							t + "\n"
						end
					end
					plain.join.strip
				end
			when :payback
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.payback.de/pb/id/252686/"
				PrivacyExtractor.table_name = "PAYBACK"
				PrivacyExtractor.xpath = '//div[@class="con-w75"]//h1|//div[contains(@id,"A")]//p|//h4'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :paypal
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.paypal.com/yt/webapps/mpp/ua/privacy-full"
				PrivacyExtractor.table_name = "PAYPAL"
				PrivacyExtractor.xpath = '(//section[@id="main"]//h1|//h2|//h3|//p[not(@class="nonjsAlert")]|//ol)|(//section[@id="main"]//ul)'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.search("//script").remove
					plain = doc.collect do |node|
						t = node.text.gsub(/\u00A0/," ").strip
						if t.empty?
							t
						elsif node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :rocketbeans
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.rocketbeans.tv/datenschutz/"
				PrivacyExtractor.table_name = "ROCKETBEANS"
				PrivacyExtractor.xpath = '(//div[@class="row" and contains(.,"Datenschutzerklärung")]//h1)|(//div[@class="row" and contains(.,"Datenschutzerklärung")]//h2)|(//div[@class="row" and contains(.,"Datenschutzerklärung")]//h3)|(//div[@class="row" and contains(.,"Datenschutzerklärung")]//p)'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :steam
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "http://store.steampowered.com/privacy_agreement/english/"
				PrivacyExtractor.table_name = "STEAM"
				PrivacyExtractor.xpath = '//div[@id="newsColumn"]'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.text.gsub(/[\s]{2,}/,"\n").strip
				end
			when :subway
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "http://www.subway.com/en-us/legal/privacynotice"
				PrivacyExtractor.table_name = "SUBWAY"
				PrivacyExtractor.xpath = '//div[@class="container wrap-container"]//ul|//h1|//p'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.gsub(/[\s]{2,}/,"\n").strip
						if node.name == "h1"
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :sueddeutsche
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://service.sueddeutsche.de/lesermarkt/service/datenschutz.html"
				PrivacyExtractor.table_name = "SUEDDEUTSCHE"
				PrivacyExtractor.xpath = '(//div[@class="main clearfix"])//h2|//h3|//p'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.collect{|node|node.text.strip}.join
				end
			when :trivago
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "http://company.trivago.com/privacy-policy/"
				PrivacyExtractor.table_name = "TRIVAGO"
				PrivacyExtractor.xpath = '//h2|//article//h3|//article//p|//article//ul'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :twitter
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.own_archive = true
				PrivacyExtractor.url = "https://twitter.com/privacy/previous?lang=en"
				PrivacyExtractor.table_name = "TWITTER"
				PrivacyExtractor.xpath = '//div[@class="Field-items"]//p|//h2|//h3'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.search("//script").remove
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain[2..-1].join.strip
				end
				PrivacyExtractor.archive_xpath = '//ul[@class="UserPolicy--previousListing-links"]//a'
				PrivacyExtractor.archive_url_block = -> (link) do
					"https://twitter.com" + link + "?lang=en"
				end
				PrivacyExtractor.archive_date_block = -> (date) do
					if date == "Version 1"
						"May 14, 2007"
					else
						date.split(": ")[1]
					end
				end
			when :unileipzig
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.zv.uni-leipzig.de/service/datenschutz.html"
				PrivacyExtractor.table_name = "UNILEIPZIG"
				PrivacyExtractor.xpath = '//div[@class="inhalt"]//p'
			when :vine
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://vine.co/privacy"
				PrivacyExtractor.table_name = "VINE"
				PrivacyExtractor.xpath = '//div[@class="container legal"]//h2|//h3|//p'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
			when :whatsapp
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.own_archive = true
				PrivacyExtractor.url = "https://www.whatsapp.com/legal/?doc=privacy-policy&mode=revisions&l=en"
				PrivacyExtractor.table_name = "WHATSAPP"
				PrivacyExtractor.xpath = '((//div[@class="block__body"])[position() = 1])//h3|((//div[@class="block__body"])[position() = 1])//h4|((//div[@class="block__body"])[position() = 1])//p'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
				PrivacyExtractor.archive_xpath = '//ul[@class="linklist"]//a'
				PrivacyExtractor.archive_url_block = -> (link) do
					if link == "/legal/#privacy-policy"
						"https://www.whatsapp.com/legal/?doc=privacy-policy"
					else
						"https://www.whatsapp.com" + link
					end
				end
				PrivacyExtractor.archive_date_block = -> (date) do
					if date == "Current version"
						Date::MONTHNAMES[Time.now.month] + " " + Time.now.day.to_s + ", " + Time.now.year.to_s
					else
						date
					end
				end
			when :wikimedia
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.own_archive = true
				PrivacyExtractor.url = "https://wikimediafoundation.org/wiki/Privacy_policy"
				PrivacyExtractor.table_name = "WIKIMEDIA"
				PrivacyExtractor.xpath = '((//div[@id="bodyContent"])//dl|//ol|//h1|//h2|//h3|//h4|//h5|//p)[not(ancestor::div[@id="mw-panel" or @id="mw-navigation" or @id="toc"]) and not(ancestor::table)]'
				PrivacyExtractor.plain_block = -> (doc) do
					plain = doc.collect do |node|
						t = node.text.strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain.join.strip
				end
				PrivacyExtractor.archive_xpath = '//p[contains(.,"This privacy policy was approved by the board")]/following::ul[1]//a'
				PrivacyExtractor.archive_url_block = -> (link) do
					"https://wikimediafoundation.org" + link
				end
				PrivacyExtractor.archive_date_block = -> (date) do
					if (d = date.split(" - ")[1]).nil?
						date.split(" to ")[1][0..-2].split.join(" 01, ")
					else
						d.split.join(" 01, ")
					end
				end
			when :zalando
				PrivacyExtractor.mode = MODE
				PrivacyExtractor.url = "https://www.zalando.de/zalando-datenschutz/"
				PrivacyExtractor.table_name = "ZALANDO"
				PrivacyExtractor.xpath = '//div[@id="content"]//ul|//div[@id="content"]//p|//h2|//h4'
				PrivacyExtractor.plain_block = -> (doc) do
					doc.search("//script").remove
					plain = doc.collect do |node|
						t = node.text.gsub(/\s{2,}/," ").strip
						if node.name.include?("h")
							t + "\n"
						else
							t + "\n\n"
						end
					end
					plain[0..-3].join.strip
				end
		end
		# Call the extractor
		PrivacyExtractor.extract
	end
end

begin
	Crawler.start
end