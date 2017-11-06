"""
Run to print valid parse tree for sentence
"""


import nltk

grammar = nltk.CFG.fromstring("""
S -> S VP | NP VP
VP -> VBP PP
NP -> NP PP | DT Nom | NNS
PP -> IN NP 
IN -> "that" | "on" | "in"
Nom -> DT Nom | JJ Nom | NN Nom | NN | NNS
NN -> "planet" | "border" | "region"
VBP -> "think" | "are"
NNS -> "Scientists" | "areas"
DT -> "any" | "the"
JJ -> "habitable"
""")

sentence = "Scientists think that any habitable areas on the planet are in the border region".split()


parser = nltk.ChartParser(grammar)
for tree in parser.parse(sentence):
    print tree
