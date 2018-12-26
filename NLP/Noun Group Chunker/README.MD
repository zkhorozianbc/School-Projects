NounGroupTagger.py writeup...

-- Scoring for WSJ_24 --

31653 out of 32853 tags correct
  accuracy: 96.35
8378 groups in key
8581 groups in response
7671 correct groups
  precision: 89.40
  recall:    91.56
  F1:        90.47


Features for each word:
1. POS
2. POS of previous 3 words (or however many words till beginning of sentence)
3. POS of following 3 words (or however many words till end of sentence)
4. POS given previous POS for the previous 3 words (i.e. prev_prev_prev_POS+prev_prev_POS=CD+NN)
5. POS given following POS for the following 3 words  (i.e. next_POS+next_next_POS=NN+NNS)
6. list of tags since last determiner (i.e. tags-since-DT: NN CD NN VBD)

