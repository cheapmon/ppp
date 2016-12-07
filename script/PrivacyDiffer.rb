require 'differ'

module PrivacyDiffer

	class << self
		attr_accessor :threshold
	end

	self.threshold = 2

	def self.different? s1, s2
		diff = Differ.diff_by_word(s1, s2).format_as(:ascii)
		changes = 0
		diff.scan(/\{[\+\-]\".*\"\}/).each do |m|
			changes += m.split.size
		end
		changes += diff.scan(/\{\"\w*\" >> \"\w*\"\}/).size
		words = s1.split.size
		percent_c = (changes.to_f)/(words.to_f)*100
		(percent_c >= self.threshold)
	end

end