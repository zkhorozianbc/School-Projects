from nltk.stem.porter import PorterStemmer
import nltk
import scipy as sp
import numpy as np
from stop_list import closed_class_stop_words
import tensorflow as tf
import itertools
import math
import operator

documents = {}
queries = {}
d_words = {}
q_words = {}
stemmer = PorterStemmer()
train = {}
test = {}
docs_to_train = {}


def read_cranqrel():
		
	f_cranqrel = open('cranqrel','r')

	for k in sorted(docs_to_train.keys()):
		if k <= 150:
			train[k] = {}
			train[k]["X"] = docs_to_train[k]
			train[k]["Y"] = []
		else:
			test[k] = {}
			test[k]["X"] = docs_to_train[k]
			test[k]["Y"] = []


	for line in f_cranqrel:
		line = line.split()
		q_id = int(line[0])
		d_id = int(line[1])
		score = int(line[2])

		#handles missing query responses
		if q_id not in docs_to_train.keys():
			continue

		#create dict of ground truth labels, list of tuples with key=d_id value=relevance score
		if q_id <= 150:
			if score == -1:
				train[q_id]["Y"].insert(0,[d_id,1])
			else:
				train[q_id]["Y"].append([d_id,score])

		else:
			if score == -1:
				test[q_id]["Y"].insert(0,[d_id,1])
			else:
				test[q_id]["Y"].append([d_id,score])


	#initialize relevance levels of 5 to irrelevant docs as determined by cranqrel in test set
	for i in train.keys():
		marked = [x[0] for x in train[i]["Y"]]

		# for j in range(1,1401):
		for j in range(1,50):
			if j not in marked:
				train[i]["Y"].append([j,5])

		#create d_id, score map
		# train[i]["X_map"] = dict(train[i]["X"])
		# train[i]["Y_map"] = dict(train[i]["Y"])


	for i in test.keys():
		marked = [x[0] for x in test[i]["Y"]]

		# for j in range(1,1401):
		for j in range(1,50):
			if j not in marked:
				test[i]["Y"].append((j,5))

	print train[1]

		# test[i]["X_map"] = dict(test[i]["X"])
		# test[i]["Y_map"] = dict(test[i]["Y"])


	
# def ListMLE():

# 	y = tf.reduce_sum(tf.mul(x,W))







# 	X = tf.placeholder(tf.float32, [None, 30])

# 	W = tf.Variable(tf.random_normal([30, 5]))
# 	y = tf.matmul(x, W)
# 	y_ids
# 	y_ = tf.placeholder(tf.float32, [None, 5])

# 	n = tf.Constant(30)
# 	phi = tf.reduce_prod(tf.convert_to_tensor([z[y_to_x[i]] / sum([z[y_to_x[k]] for k in range(i,n)]) for i in range(n)]))




# 	init = tf.initialize_all_variables()
# 	sess = tf.Session()
# 	sess.run(init)
# 	for i in range(len(train)*10):
#   		batch_xs = [tf.convert_to_tensor(train[k]["X"]) for k in range(len(train))]
# 		batch_ys = [tf.convert_to_tensor(train[k]["Y"]) for k in range(len(train))]
#   		sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})





# 	f_cranqrel.close()

			

# def likelihood_loss():
	

# #scoring function: sigmoid(f-measure)

# def learn():
# 	sample = [x,y for ]




	# nltk.metrics.scores.f_measure(X,Y)

	# classifier = nltk.classify.NaiveBayesClassifier.train(train)
	# classifier.classify_many(test)
	# for pdist in classifier.prob_classify_many(test):
	#     print('%.4f %.4f' % (pdist.prob('x'), pdist.prob('y')))

def read_text(filename,occur_dict,text_dict):
	f = open(filename,'r')

	id_to_update = 0
	read = False
	for line in f:
		line = line.split()

		if line[0] == ".I":
			
			#update occur dict, except on first iteration
			if id_to_update != 0:
				for word in list(set(text_dict[id_to_update])):

					word_count = text_dict[id_to_update].count(word)

					if word not in occur_dict.keys():
						occur_dict[word] = {}

					occur_dict[word][id_to_update] = word_count
						

			id_to_update = line[1]
			text_dict[id_to_update] = []
			read = False

		elif line[0] == ".W":
			read = True

		elif line[0] == ".T":
			read = True

		elif line[0] == ".A":
			read = False

		else:
			if read == True:

				#stem words exclude stop words, periods and commas
				line = [str(stemmer.stem(word)) for word in line if word not in closed_class_stop_words and len(word) > 1]

				for word in line:
					for letter in word:
						if letter.isalpha() == False:
							word = word.replace(letter,"")

				text_dict[id_to_update].extend(line)

	#handle last query
	for word in list(set(text_dict[id_to_update])):

		word_count = text_dict[id_to_update].count(word)

		if word not in occur_dict.keys():
			occur_dict[word] = {}

		occur_dict[word][id_to_update] = word_count

	f.close()



def vectorize():
	f_output = open('f_output','w')

	num_queries = float(len(queries))
	num_documents = float(len(documents))
	q_index = 1
	for q_id in sorted(queries.keys()):

		query = queries[q_id]

		query_vector = {}
		query_length = float(len(query))

		#term frequency
		for word in list(set(query)):
			query_vector[word] = query.count(word) 

		#tf-idf
		for word in query_vector.keys():
			query_vector[word] *= np.log(num_queries / len(q_words[word]))


		document_vector_dict = {}

		#vectorize documents
		for d_id in documents.keys():

			document = documents[d_id]
			document_vector = {}
			doc_length = float(len(document))

			#handles empty documents
			if doc_length == 0:
				document_vector_dict[d_id] = 0
				continue

			#term frequency 
			for word in query_vector.keys():

				document_vector[word] = document.count(word)

			#tf-idf
			for word in document_vector.keys():
				if document_vector[word] > 0:

					document_vector[word] *= np.log(num_documents / len(d_words[word]))



			#features: cosine similarity, distance between non-stop words in doc
			d = []
			q = []
			for word in sorted(query_vector.keys()):
				d.append(document_vector[word])
				q.append(query_vector[word])

			if sum(d) > 0:
				document_vector_dict[d_id] = 1 - sp.spatial.distance.cosine(d,q)
				
			else:
				document_vector_dict[d_id] = 0

		


		



		# ranking = sorted((k for k,v in document_vector_dict.iteritems()), reverse=True)

		ranking = [list(x) for x in sorted(document_vector_dict.items(), key=operator.itemgetter(1), reverse=True)]


		#sort documents by cosine similarity score
		# ranking = sorted(document_vector_dict,key=document_vector_dict.get,reverse=True)


		#create instance of training data
		docs_to_train[q_index] = ranking

		#remove unrelated documents where score = 0
		# ranking = [rank for rank in ranking if document_vector_dict[rank] != 0]

		# #write to output file
		# for d_id in ranking:
		# 	f_output.write(str(q_index) + " " + str(d_id) + " " + str(document_vector_dict[d_id]) + "\n")

		q_index += 1

	f_output.close()



# class ListMLE():
# 	def __init__(self):

# 		self.weights = tf.Variable(tf.random_normal([784, 200], mean=0.0), name="weights")


read_text('cran.qry',q_words,queries)
read_text('cran.all.1400',d_words,documents)
vectorize()
read_cranqrel()
# learn()







