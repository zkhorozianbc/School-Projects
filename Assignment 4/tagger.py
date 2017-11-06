import nltk

words = {}
state = {}

def get_penn_tags():
	f_penn = open('penntreebanktags.txt','r')

	for line in f_penn:

		tag = line.split()[1]
		state[tag] = {}

	# here we define the key "1" to indicate a break in sentence, i.e. punctuation, start/end of sentence,..
	state["1"] = {}

def training(filename):
	#open training file
	f_train = open(filename,'r')

	#fields needed to update dicts

	#initialized to "1" since we read from beginning of file
	prev_tag = "1"


	for line in f_train:


		#checks if line is blank, execute if not 
		if line.strip():

			tokens = line.split()

			#first check for punctuation

			if tokens[0].isalpha() == False and tokens[1].isalpha() == False:
					prev_tag = "1"
					continue
			# handles edge case where ,.$ is pos tag of word
			if len(tokens[1]) == 1 and tokens[1] in ".,$\'\"":
				prev_tag = "1"
				continue

			#make word lowercase
			word = tokens[0].lower()

			#tag already uppercase
			tag = tokens[1]


			#true if word has not been encountered
			#initialize first instance of tag given word, set value to 1
			if word not in words.keys():
				words[word] = {}
				words[word][tag] = [1]

			#true if tag has not been encountered
			# initialize first instance of tag given word, set value to 1
			if tag not in words[word].keys():
				words[word][tag] = [1]
			else:
				words[word][tag][0] += 1



			#true if tag has not been encountered
			# initialize first instance of tag given word, set value to 1
			if tag not in state[prev_tag].keys():
				state[prev_tag][tag] = [1]
			else:
				state[prev_tag][tag][0] += 1

			#update prev_tag
			prev_tag = tag

		else:
			prev_tag = "1"

	f_train.close()



def create_probabilities():

	for i in state.keys():

		pos_key_parent = state[i]
		#total num of occurences of prev pos
		total = float(sum(x[0] for x in pos_key_parent.values()))

		for j in pos_key_parent.keys():
			pos_key_child = pos_key_parent[j]

			probability = pos_key_child[0] / total

			pos_key_child.append(probability)


	for i in words.keys():

		#dict of pos tags and num of occurences
		word_key_parent = words[i]

		#total num of occurences of word
		total = float(sum(x[0] for x in word_key_parent.values()))

		for j in word_key_parent.keys():
			word_key_child = word_key_parent[j]

			probability = word_key_child[0] / total

			word_key_child.append(probability)




def viterbi_tagger(filename):
	f_test = open(filename,'r')
	f_out = open('zak261.pos', 'w')


	prev_tag = "1"

	for line in f_test:

		#checks if line is blank, execute if not 
		if line.strip():
			line = line.strip()
			

			if line[0].isalpha() == False and line[0].isdigit() == False:

				#handles periods and commas
				if len(line) == 1:
					f_out.write(line + "\t" + line + "\n")
					prev_tag = "1"
					continue

				#handles quotations
				if line[1].isalpha() == False:
					f_out.write(line + "\t" + line + "\n")
					prev_tag = "1"
					continue

			word = line.lower()
			#state transition probablities
			transitions = state[prev_tag]
			word_in = True

			#if word not encountered in training, set prob to constant
			if word not in words.keys():
				word_prob = 1 / 100000.0
				word_in = False
			

			V = {}

			if word_in == False:
				for i in transitions.keys():
					V[i] = transitions[i][1] * word_prob

			else:
				word_given_pos = words[word]

				for i in word_given_pos.keys():
					word_prob = word_given_pos[i][1]

					transition_prob = transitions.get(i,0)


					#if no transition exists, P(path) = 0
					if type(transition_prob) == type(0):
						V[i] = 0

					else:
						V[i] = transition_prob[1] * word_prob


			
			max_prob = 0
			max_tag = V.keys()[0]
			for i in V.keys():
				if V[i] > max_prob:
					max_prob = V[i]
					max_tag = i

			#write line which restores capitalization
			f_out.write(line + "\t" + max_tag)
			
			prev_tag = max_tag


		else:
			prev_tag = "1"
			

		f_out.write("\n")

	f_out.close()
	f_test.close()



get_penn_tags()

#training
training('WSJ_02-21.pos')
training('WSJ_24.pos')


create_probabilities()

#determine tags, output to zak261.pos
viterbi_tagger('WSJ_23.words')


