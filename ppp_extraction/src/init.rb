# Init database and fetch everything
begin
	sites = ["alternate", "amorelie", "apple", "burgerking", "edeka", "google",
    "microsoft", "payback", "paypal", "rocketbeans", "steam", "subway",
    "sueddeutsche", "trivago", "twitter", "unileipzig", "vine",
    "whatsapp", "wikimedia", "zalando"]
	sites.each do |site|
		system "ruby Crawler.rb #{site} fetch"
	end
end