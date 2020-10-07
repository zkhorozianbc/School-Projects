import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class PreProcesser {
    public static void main(String[] args)  throws Exception {

        List<String> documentStrings = ingestDocuments();
        List<List<String>> X = vectorize(documentStrings);

        Map<String,Integer> vocabulary = createVocabulary(X);
        double[][] M = getDocumentTermMatrix(X,vocabulary);
        double[][] _M = tfIdfTransform(M);

        KMeans model = new KMeans(_M,3);
        model.cluster();

        getTopTerms(model.K,model.clusterAssignments,_M,vocabulary);


        List<String> examples =  ingestAllExamplesFromTestDir();
        double[][] _A = createTestVectors(M,vocabulary, examples);
        knnCentroids(model.centroids,_A);

    }
    public static void knnCentroids(double[][] centroids,double[][] _A) {
        DistanceMeasure dm = new DistanceMeasure();

        for (int i = 0; i < _A.length; i++) {

            Map<Integer,Double> scores = new HashMap<Integer, Double>(centroids.length);
            for (int j = 0; j < centroids.length; j++)
                scores.put(j,dm.score(_A[i],centroids[j]));
            // Create a list from elements of HashMap
            List<Map.Entry<Integer, Double> > opa =
                    new LinkedList<Map.Entry<Integer, Double> >(scores.entrySet());

            // Sort the list
            Collections.sort(opa, new Comparator<Map.Entry<Integer, Double> >() {
                public int compare(Map.Entry<Integer, Double> o1,
                                   Map.Entry<Integer, Double> o2)
                {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
            System.out.println("Document " + (i+1) + ": Closest Cluster: " + opa.get(0).getKey());


        }
    }

    public static void knn(int k, double[][] _M,double[][] _A, int[] clusterAssignments) {
        DistanceMeasure dm = new DistanceMeasure("euclidean");

        for (int i = 0; i < _A.length; i++) {

            Map<Integer,Double> scores = new HashMap<Integer, Double>(_M.length);
            for (int j = 0; j < _M.length; j++)
                scores.put(j,dm.score(_A[i],_M[j]));
            // Create a list from elements of HashMap
            List<Map.Entry<Integer, Double> > opa =
                    new LinkedList<Map.Entry<Integer, Double> >(scores.entrySet());

            // Sort the list
            Collections.sort(opa, new Comparator<Map.Entry<Integer, Double> >() {
                public int compare(Map.Entry<Integer, Double> o1,
                                   Map.Entry<Integer, Double> o2)
                {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
            System.out.println("Document " + i);
            System.out.println(opa.subList(0,opa.size()-k));
            System.out.println("Closest Cluster: " + clusterAssignments[opa.get(0).getKey()]);
            System.out.println();
        }
    }


    public static double[][] createTestVectors(double[][] M, Map<String,Integer> vocabulary, List<String> examples) throws Exception{
        List<List<String>> tokenizedExamples = vectorize(examples);
        int numExamples = tokenizedExamples.size();
        //create raw document term-matrix with term counts for each example document
        double[][] A = new double[numExamples][vocabulary.size()];
        for (int doc_index = 0; doc_index < numExamples; doc_index++) {

            for (String word: tokenizedExamples.get(doc_index)) {
                if (vocabulary.containsKey(word)) {
                    A[doc_index][vocabulary.get(word)] += 1;
                }
            }
        }
        //use idf from training set to transform tf from example set
        double[][] _A = tfIdfTransformExamples(M,A);

        return _A;
    }

    public static List<String> ingestAllExamplesFromTestDir() throws Exception {
        final File folder = new File("/Users/zachary/Documents/PredictiveAnalytics/project_two/data/test");
        List<String> documents = new ArrayList<>();
        String text;
        for (final File fileEntry : folder.listFiles()) {
            text = new Scanner(new File(fileEntry.getAbsolutePath()), Charset.defaultCharset()).useDelimiter("\\Z").next();
            documents.add(text);
        }
        return documents;
    }

    public static List<String> ingestExamples() throws Exception {
        List<String> documents = new ArrayList<String>();

        String dirPathPrefix = "/Users/zachary/Documents/PredictiveAnalytics/project_two/data/test/unknown";
        String[] fileSuffixes = {"01","02","03","04","05","06","07","08","09","10"};
        String path,text;

        for (String fS : fileSuffixes) {
            path = dirPathPrefix + fS + ".txt";
            text = new Scanner(new File(path), Charset.defaultCharset()).useDelimiter("\\Z").next();
            documents.add(text);
        }

        return documents;

    }
    public static void getTopTerms(int K, int[] clusterAssignments, double[][] _M, Map<String,Integer> vocabulary) throws IOException {

        System.out.println("******* TOPICS *******");

        File myObj = new File("topics.txt");

        FileWriter myWriter = new FileWriter("topics.txt");

        for (int k = 0; k < K; k++) {
            double[] counts = new double[vocabulary.size()];
            for (int term_index = 0; term_index < vocabulary.size(); term_index++) {
                for (int doc_index = 0; doc_index < _M.length; doc_index++) {
                    if (clusterAssignments[doc_index] == k)
                        counts[term_index] += _M[doc_index][term_index];
                }
            }
            List<String> opa = new ArrayList<String>(vocabulary.keySet());
            opa.sort(Comparator.comparingDouble(o -> counts[vocabulary.get(o)]));
            System.out.println("Cluster " + k);
            System.out.println(opa.subList(opa.size()-10,opa.size()));
            System.out.println("\n\n\n");

            myWriter.write(opa.subList(opa.size()-10,opa.size()).toString());
            myWriter.write("\n\n");

        }

        myWriter.close();
    }

    public static double[][] tfIdfTransformExamples(double[][] M, double[][] A) {
        double[][] _A =  new double[A.length][A[0].length];
        for (int term_index = 0; term_index < M[0].length; term_index++) {
            double count = 0;
            for (int doc_index = 0; doc_index < M.length; doc_index++)
                count += M[doc_index][term_index] > 0 ? 1: 0;


            for (int doc_index = 0; doc_index < A.length; doc_index++) {

                _A[doc_index][term_index] = A[doc_index][term_index] * Math.log(M.length / count);
            }

        }

        return _A;
    }

    public static double[][] tfIdfTransform(double[][] M) {
        double[][] _M =  new double[M.length][M[0].length];
        for (int term_index = 0; term_index < M[0].length; term_index++) {
            double count = 0;
            for (int doc_index = 0; doc_index < M.length; doc_index++)
                count += M[doc_index][term_index] > 0 ? 1: 0;

            for (int doc_index = 0; doc_index < M.length; doc_index++)
                _M[doc_index][term_index] = M[doc_index][term_index] * Math.log(M.length / count);
        }

        return _M;
    }


    public static double[][] getDocumentTermMatrix(List<List<String>> X, Map<String,Integer> vocabulary) {
        double[][] M = new double[X.size()][vocabulary.size()];

        for (int doc_index = 0; doc_index < X.size(); doc_index++) {

            for (String word: X.get(doc_index)) {
                int term_index = vocabulary.get(word);
                M[doc_index][term_index] += 1;

            }

            for (int term_index = 0; term_index < vocabulary.size(); term_index++) {
                M[doc_index][term_index] = (M[doc_index][term_index] / X.get(doc_index).size());
            }

        }

        return M;
    }

    public static Map<String,Integer> createVocabulary(List<List<String>> X) {
        Map<String,Integer> s = new HashMap<String,Integer>();
        int term_index = 0;
        for (List<String> x: X) {
            for (String word: x) {
                if (!s.containsKey(word)) {
                    s.put(word,term_index);
                    term_index += 1;
                }
            }
        }
        return s;
    }




    public static List<List<String>> vectorize(List<String> documentStrings) throws Exception {

        StanfordCoreNLP pipeline = initPipeline();

        //get stop words from text file
        Set<String> stopWords = ingestStopWords();

        List<List<String>> X = new ArrayList<List<String>>();

        for (String docString: documentStrings) {

            //replace all whitespace with single space
            docString = docString.trim().replaceAll("\\s+", " ");

            // create a document object
            CoreDocument document = new CoreDocument(docString);

            // annotate the document
            pipeline.annotate(document);

            //list of tokens
            List<String> x = new ArrayList<String>();

            //remove stop words and punctuation
            for (CoreSentence sent: document.sentences()) {
                for (String l: sent.lemmas()) {
                    if (!stopWords.contains(l.toLowerCase())) {
                        if (l.trim().replaceAll("[^a-zA-Z0-9]+","").length() > 1 && !l.toLowerCase().equals("mr."))
                            x.add(l.toLowerCase());
                    }
                }
            }

            //get named entities using StandordCoreNLP lib
            Set<String> entities = new HashSet<String>();
            for (CoreEntityMention em: document.entityMentions()) {
                entities.add(em.text().toLowerCase());
            }

            //merged named entities
            List<String> merged = mergedNamedEntities(x,entities);

            X.add(merged);


        }

        return X;
    }

    public static List<String> mergedNamedEntities(List<String> x, Set<String> entities) {
        List<String> merged = new ArrayList<>();

        for (int i = 0; i < x.size(); i++) {
            boolean found = false;
            for (int n = 2; n < 4; n++) {
                StringBuilder gram = new StringBuilder();
                for (int j = i; j < i + n && j < x.size(); j++) {
                    if (j > i)
                        gram.append(" ");
                    gram.append(x.get(j));
                }

                if (entities.contains(gram.toString())) {
                    merged.add(gram.toString());
                    i += n;
                    found = true;
                    break;
                }
            }

            if (!found)
                merged.add(x.get(i));

        }
        return merged;
    }


    public static StanfordCoreNLP initPipeline() {
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        return new StanfordCoreNLP(props);
    }

    public static Set<String> ingestStopWords() throws Exception {
        Scanner in = new Scanner(new File("/Users/zachary/Documents/PredictiveAnalytics/project_two/data/stopwords.txt"));
        Set<String> s = new HashSet<String>();
        while (in.hasNext()){
            s.add(in.next());
        }
        in.close();
        return s;
    }

    public static List<String> ingestDocuments() throws Exception {
        List<String> documents = new ArrayList<String>();

        String dirPathPrefix = "/Users/zachary/Documents/PredictiveAnalytics/project_two/data/train/C";
        String[] dirSuffixes = {"1","4","7"};
        String[] fileSuffixes = {"01","02","03","04","05","06","07","08"};
        String path,text;
        for (String dS: dirSuffixes) {
            for (String fS : fileSuffixes) {
                path = dirPathPrefix + dS + "/article" + fS + ".txt";
                text = new Scanner(new File(path)).useDelimiter("\\Z").next();
                documents.add(text);
            }
        }
        return documents;

    }

}
