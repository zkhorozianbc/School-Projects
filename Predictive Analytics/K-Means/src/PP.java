import edu.stanford.nlp.pipeline.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.io.FileWriter;
import java.lang.Math;
public class PP {
    public static void main(String[] args)  throws Exception {

        List<String> documentStrings = ingestDocuments();

        List<List<String>> X = vectorize(documentStrings);

        Map<String,Integer> vocabulary = createVocabulary(X);

        double[][] M = getDocumentTermMatrix(X,vocabulary);

        double[][] _M = tfIdfTransform(M);


        KMeans model = new KMeans(_M,3);
        model.cluster();

        getTopTerms(model,_M,vocabulary);

    }


    public static void getTopTerms(KMeans model, double[][] _M, Map<String,Integer> vocabulary) throws IOException {

        File myObj = new File("topics.txt");

        FileWriter myWriter = new FileWriter("topics.txt");

        for (int k = 0; k < model.K; k++) {
            double[] counts = new double[vocabulary.size()];
            for (int term_index = 0; term_index < vocabulary.size(); term_index++) {
                for (int doc_index = 0; doc_index < _M.length; doc_index++) {
                    if (model.clusterAssignments[doc_index] == k)
                        counts[term_index] += _M[doc_index][term_index];
                }
            }
            List<String> opa = new ArrayList<String>(vocabulary.keySet());
            opa.sort(Comparator.comparingDouble(o -> counts[vocabulary.get(o)]));
            System.out.println(opa.subList(opa.size()-10,opa.size()));
            System.out.println("\n\n\n");

            myWriter.write(opa.subList(opa.size()-10,opa.size()).toString());
            myWriter.write("\n\n");

        }

        myWriter.close();
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
        Scanner in = new Scanner(new File("/Users/zachary/Documents/PredictiveAnalytics/project_one/src/stopWords.txt"));
        Set<String> s = new HashSet<String>();
        while (in.hasNext()){
            s.add(in.next());
        }
        in.close();
        return s;
    }

    public static List<String> ingestDocuments() throws Exception {
        List<String> documents = new ArrayList<String>();

        String dirPathPrefix = "/Users/zachary/Documents/PredictiveAnalytics/project_one/data/C";
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
