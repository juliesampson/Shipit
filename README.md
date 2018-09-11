# Shipit
coding audition
I was given a coding problem that involved 5 digit zip codes, interval notation, sorting, storing, retrieveing, testing, timing and thinking. See the problem below. Being out of the industry for a awhile (years actually), it was a good crash refresher corse on a bunch of things java, the tools, the community, the mind set. Regardless of what happens here with this particular job, it feels good to be cranking out some code again.

A few notes on content, and how to run it. 

Things you need to know:

1) in root project folder (Shipit) there is a src directory with 5 java files, a property file, and 2 csv files. One for input data and one for out.

2) Locate Driver.java. This is your entry point as it contains a main function. There's not any cmmdline args to fret about. Just one driver.properties one in the parent directory.

3) I got a jump on some junit tests, but I'm not that happy with them. I wish they were better.There's a lot of Junit stuff that I don't know which is cool. I went a little crazy with parameterized classes, so please forgive me that. The tests can be run with my TSRangeRunner class. Thanks for the glance and Cheers!

Julie


PROBLEM
____________________________________

BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

[94133,94133] [94200,94299] [94600,94699]

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]
Then the output should be = [94133,94133] [94200,94299] [94600,94699]

If the input = [94133,94133] [94200,94299] [94226,94399] 
Then the output should be = [94133,94133] [94200,94399]
