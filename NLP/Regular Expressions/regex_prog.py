import re

"""
Extract phone numbers and dollar amounts written in all-OANC.txt

"""



f = open('test_dollar_phone_corpus.txt', 'r')

f_phone = open('output_phone.txt', 'r+')
f_money = open('output_money.txt', 'r+')


f_phone_list = open('phone_list.txt', 'r+')
f_money_list = open('money_list.txt', 'r+')



#regex for finding phone numbers

phone_pattern = '(((\+[0-9] ?)?\(?[0-9]{3}\)?)([ \-])?[0-9]{3}([ \-])?[0-9]{4})|([0-9]{10})'

#regex for finding dollar amounts
money_pattern = '(\$[0-9]+(\,[0-9]{3})*(\.[0-9]{2})?)|([0-9]+(\,[0-9]{3})* (trillion|billion|million|hundred|thousand|)((?=dollars))(dollars))'



def add_brackets_dollar(matchobj):
	token = "[" + matchobj.group(0) + "]"
	f_money_list.write(token + "\n")
	return token

def add_brackets_phone(matchobj):
	token = "[" + matchobj.group(0) + "]"
	f_phone_list.write(token + "\n")
	return token

for line in f:
	g = re.sub(money_pattern,add_brackets_dollar, line)
	f_money.write(g)

f.seek(0)

for line in f:
	h = re.sub(phone_pattern,add_brackets_phone, line)
	f_phone.write(h)




