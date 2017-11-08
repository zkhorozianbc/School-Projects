f_output = open('training.chunk','w')

def read(filename):

	f_read = open(filename,'r')
	line_num = 0
	sentence = []
	for line in f_read:

		if line == '\n':
			if line_num == 0:
				line_num += 1
				f_output.write('\n')
				continue

			feature(sentence)
			f_output.write('\n')
			sentence = []

		else:
			line = line.split()

			word = line[0]
			POS = line[1]
			BIO = line[2]

			attributes = {}
			attributes['word'] = word
			attributes['POS'] = POS
			attributes['BIO'] = BIO


			sentence.append(attributes)

	f_read.close()
	f_output.close()


def feature(sentence):

	for i in sentence:

		output = '{0}\tPOS={1}'.format(i['word'],i['POS'])

		index = sentence.index(i)
		j = 1
		#prev fields
		
		while index - j >= 0 and j <= 3:
			key = 'prev_'
			for field in sorted(i.keys()):
	
					output += '\t' + j*key + field + '=' + sentence[index-j][field]

					output += '\t' + j*key + field + '+{0}'.format((j-1)*key + field) + "={0}+{1}".format(sentence[index-j][field],sentence[index-(j-1)][field])
			j += 1

		#next fields

		j = 1
		while index + j < len(sentence) and j <= 3:
			key = 'next_'
			for field in sorted(i.keys()):
				if field != 'word':
					output += '\t' + j*key + field + '=' + sentence[index+j][field]

					output += '\t' + '{0}+'.format((j-1)*key + field) + j*key + field + "={0}+{1}".format(sentence[index+(j-1)][field],sentence[index+j][field])

			j += 1

		tags = set()
		for attr in sentence[:index]:
			if attr['POS'] == 'DT':
				tags = set()
			else:
				tags.add(attr['POS'])

		output += '\ttags-since-DT: {0}'.format('+'.join(sorted(tags)))


		output += '\t{0}'.format(i['BIO'])

		f_output.write(output + '\n')


read('WSJ_02-21.pos-chunk')

