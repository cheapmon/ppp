require 'date'

require './PrivacyExtractor.rb'

begin

	SITE = ARGV[0].to_sym
	MODE = ARGV[1].to_sym

	case SITE
		when :alternate
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "http://www.alternate.de/HILFE/ALTERNATE/Datenschutz"
			PrivacyExtractor.table_name = "ALTERNATE"
			PrivacyExtractor.xpath = '//div[@id="pageContent"]//div[@class="title" or @class="part" or @class="text"]//text()[not(ancestor::script)]'
		when :amorelie
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.amorelie.de/datenschutz/"
			PrivacyExtractor.table_name = "AMORELIE"
			PrivacyExtractor.xpath = '//div[@class="container"]//h1|//p'
		when :apple
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "http://www.apple.com/privacy/privacy-policy/"
			PrivacyExtractor.table_name = "APPLE"
			PrivacyExtractor.xpath = '//div[@class="main"]//h1|//h5|//h6|//p'
		when :burgerking
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.bk.com/privacy"
			PrivacyExtractor.table_name = "BURGERKING"
			PrivacyExtractor.xpath = '//div[@class="row content"]//h3|//p'
		when :edeka
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.edeka.de/modulseiten/datenschutzbestimmungen/datenschutzbestimmungen.jsp"
			PrivacyExtractor.table_name = "EDEKA"
			PrivacyExtractor.xpath = '//div[@class="mainCl60"]//p|//h2'
		when :google
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.own_archive = true
			PrivacyExtractor.url = "https://www.google.com/intl/en/policies/privacy/archive/"
			PrivacyExtractor.table_name = "GOOGLE"
			PrivacyExtractor.xpath = '//div[@id="pp-wrapper" or @role="article"]/*[not(self::div)]'
			PrivacyExtractor.archive_xpath = '(//ul[@id="archives"]//a)[position() mod 2 = 1]'
			PrivacyExtractor.archive_date_block = -> (date) {
				if date == "Current version"
					Date::MONTHNAMES[Time.now.month] + " " + Time.now.day.to_s + ", " + Time.now.year.to_s
				else
					date
				end
			}
		when :microsoft
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://privacy.microsoft.com/en-us/privacystatement/"
			PrivacyExtractor.table_name = "MICROSOFT"
			PrivacyExtractor.xpath = '(//h1)|(//div[@class="div_content"]//p|//label)'
		when :payback
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.payback.de/pb/id/252686/"
			PrivacyExtractor.table_name = "PAYBACK"
			PrivacyExtractor.xpath = '//div[@class="con-w75"]//h1|//h4|//p'
		when :paypal
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.paypal.com/yt/webapps/mpp/ua/privacy-full"
			PrivacyExtractor.table_name = "PAYPAL"
			PrivacyExtractor.xpath = '//section[@id="main"]//h1|//h2|//h3|//p[not(@class="nonjsAlert")]'
		when :rocketbeans
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.rocketbeans.tv/datenschutz/"
			PrivacyExtractor.table_name = "ROCKETBEANS"
			PrivacyExtractor.xpath = '//div[@class="row"]//h1|//h2|//h3|//p'
		when :steam
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "http://store.steampowered.com/privacy_agreement/english/"
			PrivacyExtractor.table_name = "STEAM"
			PrivacyExtractor.xpath = '//div[@id="newsColumn"]'
			PrivacyExtractor.plain_block = -> (plain) {
				plain.gsub(/[\s]{2,}/,"\n")
			}
		when :subway
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "http://www.subway.com/en-us/legal/privacynotice"
			PrivacyExtractor.table_name = "SUBWAY"
			PrivacyExtractor.xpath = '//div[@class="container wrap-container"]//h1|//p'
		when :sueddeutsche
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://service.sueddeutsche.de/lesermarkt/service/datenschutz.html"
			PrivacyExtractor.table_name = "SUEDDEUTSCHE"
			PrivacyExtractor.xpath = '(//div[@class="main clearfix"]/*)[not(position() = 1)]'
		when :trivago
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "http://company.trivago.com/privacy-policy/"
			PrivacyExtractor.table_name = "TRIVAGO"
			PrivacyExtractor.xpath = '//h2|//article//h3|//article//p'
		when :twitter
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.own_archive = true
			PrivacyExtractor.url = "https://twitter.com/privacy/previous?lang=en"
			PrivacyExtractor.table_name = "TWITTER"
			PrivacyExtractor.xpath = '(//div[@class="Field-items"]//p|//h2|//h3)//text()[not(ancestor::noscript) and not(ancestor::div[@class="Message Message--warning Message--cookies"])]'
			PrivacyExtractor.archive_xpath = '//ul[@class="UserPolicy--previousListing-links"]//a'
			PrivacyExtractor.archive_url_block = -> (link) {
				"https://twitter.com" + link + "?lang=en"
			}
			PrivacyExtractor.archive_date_block = -> (date) {
				if date == "Version 1"
					"May 14, 2007"
				else
					date.split(": ")[1]
				end
			}
		when :unileipzig
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.zv.uni-leipzig.de/service/datenschutz.html"
			PrivacyExtractor.table_name = "UNILEIPZIG"
			PrivacyExtractor.xpath = '//div[@class="inhalt"]//p[position() <= 2]|//h1[position() = 1]'
		when :vine
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://vine.co/privacy"
			PrivacyExtractor.table_name = "VINE"
			PrivacyExtractor.xpath = '//div[@class="container legal"]//h2|//h3|//p'
		when :whatsapp
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.own_archive = true
			PrivacyExtractor.url = "https://www.whatsapp.com/legal/?doc=privacy-policy&mode=revisions&l=en"
			PrivacyExtractor.table_name = "WHATSAPP"
			PrivacyExtractor.xpath = '(//div[@class="block__body"])[position() = 1]'
			PrivacyExtractor.archive_xpath = '//ul[@class="linklist"]//a'
			PrivacyExtractor.archive_url_block = -> (link) {
				if link == "/legal/#privacy-policy"
					"https://www.whatsapp.com/legal/?doc=privacy-policy"
				else
					"https://www.whatsapp.com" + link
				end
			}
			PrivacyExtractor.archive_date_block = -> (date) {
				if date == "Current version"
					Date::MONTHNAMES[Time.now.month] + " " + Time.now.day.to_s + ", " + Time.now.year.to_s
				else
					date
				end
			}
		when :wikimedia
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.own_archive = true
			PrivacyExtractor.url = "https://wikimediafoundation.org/wiki/Privacy_policy"
			PrivacyExtractor.table_name = "WIKIMEDIA"
			PrivacyExtractor.xpath = '(//div[@id="bodyContent"]//p|//h1|//h2|//h3|//h4)//text()[not(ancestor::div[@id="mw-navigation"])]'
			PrivacyExtractor.archive_xpath = '//p[contains(.,"This privacy policy was approved by the board")]/following::ul[1]//a'
			PrivacyExtractor.archive_url_block = -> (link) {
				"https://wikimediafoundation.org" + link
			}
			PrivacyExtractor.archive_date_block = -> (date) {
				if (d = date.split(" - ")[1]).nil?
					date.split(" to ")[1][0..-2]
				else
					d[0..-2]
				end
			}
		when :zalando
			PrivacyExtractor.mode = MODE
			PrivacyExtractor.url = "https://www.zalando.de/zalando-datenschutz/"
			PrivacyExtractor.table_name = "ZALANDO"
			PrivacyExtractor.xpath = '//div[@id="content"]/*[not(self::script)]'

			
	end
	
	PrivacyExtractor.extract

end