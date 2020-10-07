### Instructions
    Run PreProcessor.java
    Results are printed to stdout
    
### Requirements
    StanfordCoreNLP
    
### Process
    Kmeans model is trained on Homework 1 document data.
    The centroids of this model at k=3 are used as the 
    reduced dataset on which the new data is classified. 
    I vectorize the input data using term-frequency from new data
    and inverse-document-frequency from trained document data.
    The resultant test data is inputted to knn with k=1 on the 
    centroids of the Kmeans model.
    