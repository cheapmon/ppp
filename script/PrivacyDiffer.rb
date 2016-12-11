require 'differ'

# Module for difference testing between privacy policy versions.
# @author Simon Kaleschke
module PrivacyDiffer

	class << self
		attr_accessor :threshold
	end

	# "Percentage" at which two policies should be deemed different.
	self.threshold = 2

	# Check for differences.
	# @param s1 [String] The first string to compare.
	# @param s2 [String] The second string to compare.
	# @return [true,false] Whether the two strings are different (depending on a threshold).
	def self.different? s1, s2
		# Uses the ruby gem Differ, which encodes differences like this:
		# Deletions: {-"This text was deleted."}
		# Insertions: {+"This text was inserted."}
		# Single Words: {"WordBeforeChange" >> "WordAfterChange"}
		diff = Differ.diff_by_word(s1, s2).format_as(:ascii)
		changes = 0
		# Counts number of words deleted and inserted.
		diff.scan(/\{[\+\-]\".*\"\}/).each do |m|
			changes += m.split.size
		end
		# Counts number of single word changes.
		changes += diff.scan(/\{\"\w*\" >> \"\w*\"\}/).size
		words = s1.split.size
		# Calculates the "percentage" of words changed depending on the original number of words.
		# This is not a valid percentage as it can get up to values of 180.
		percent_c = (changes.to_f)/(words.to_f)*100
		# Return if the percentage is bigger than the threshold.
		(percent_c >= self.threshold)
	end

end