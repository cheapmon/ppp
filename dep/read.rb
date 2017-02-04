require 'rubygems'
require 'sqlite3'

begin

	MODE = ARGV[0]
	ARG = ARGV[1..-1]

	case MODE.to_sym
		when :dates
			table_name = ARG[0].upcase
			db = SQLite3::Database.new "./../policies.db"
			results = db.execute("SELECT DATE FROM #{table_name}").collect do |a|
				a[0]
			end
			puts results
		when :content
			table_name = ARG[0].upcase
			date = ARG[1..-1].join(" ")
			db = SQLite3::Database.new "./../policies.db"
			result = db.execute("SELECT CONTENT FROM #{table_name} WHERE DATE='#{date}'")
			puts result
	end

end