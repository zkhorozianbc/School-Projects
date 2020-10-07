import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
public class KMeans {

    private final boolean DEBUG = true;

    private double[][] M,centroids;
    public int K,xDim,yDim,numEpochs;
    public int[] clusterAssignments;
    private int[] prevAssignments;
    private DistanceMeasure distanceMeasure;
    public KMeans(double[][] M, int K) {
        this.M = M;
        this.K = K;
        this.xDim = this.M.length;
        this.yDim = this.M[0].length;
        this.numEpochs = 500;

        this.distanceMeasure = new DistanceMeasure();

    }

    public void cluster(int maxRestarts) {
        for (int i = 0; i < maxRestarts; i++) {
            System.out.println("Iteration " + i);
            System.out.println(Arrays.toString(this.clusterIter()));
            double purity = getPurity();
            System.out.println(purity);
            if (purity == 1.0) {
                break;
            }
        }
    }
    public void cluster() {
        this.cluster(100);
    }
    public int[] clusterIter() {

        this.centroids = this.getRandomCentroids();
        this.clusterAssignments = new int[this.xDim];
        this.prevAssignments = new int[this.xDim];

        for (int epoch = 0; epoch < this.numEpochs; epoch++) {

            //assign documents to closest cluster
            for (int i = 0; i < this.xDim; i++) {
                this.clusterAssignments[i] = this.getClosestCluster(this.M[i]);
            }

            //update centroids
            this.updateClusters();

            if (DEBUG) {if (epoch > this.numEpochs-2) System.out.println(Arrays.toString(clusterAssignments)); }

            if (this.shouldTerminateKMeans())
                break;

        }


        return this.clusterAssignments;

    }


    public double getPurity() {
        double purity = 0;
        HashSet<Integer> seen = new HashSet<>();
        for (int k = 0; k < this.K; k++) {
            HashMap<Integer,Integer> counts = new HashMap<Integer,Integer>();
            for (int i = 8*k; i < 8*(k+1); i++) {
                counts.put(this.clusterAssignments[i],counts.getOrDefault(this.clusterAssignments[i],0)+1);
            }
            int maxC = -1;
            int maxV = -1;
            for (Integer c: counts.keySet()) {
                if (counts.get(c) > maxV) {
                    maxC = c;
                    maxV = counts.get(c);
                }
            }
            if (seen.contains(maxC))
                continue;
            seen.add(maxC);
            purity += maxV;
        }

        return purity / 24.0;

    }



    public boolean shouldTerminateKMeans() {
        int numDiffs = 0;
        for (int i = 0; i < this.xDim; i++) {
            if (this.clusterAssignments[i] != this.prevAssignments[i]) {
                numDiffs += 1;
            }
        }
        if (numDiffs == 0)
            return true;

        for (int i = 0; i < this.xDim; i++)
            this.prevAssignments[i] = this.clusterAssignments[i];

        return false;

    }



    public void updateClusters() {
        for (int k = 0; k < this.K; k++) {
            double clusterSize = 0;
            double[] w = new double[this.yDim];

            for (int i = 0; i < this.xDim; i++) {
                if (k != this.clusterAssignments[i])
                    continue;
                clusterSize += 1;
                for (int j = 0; j < this.yDim; j++)
                    w[j] += this.M[i][j];
            }
            if (clusterSize == 0)
                continue;

            for (int j = 0; j < this.yDim; j++)
                this.centroids[k][j] = w[j] / clusterSize;
        }

    }

    public int getClosestCluster(double[] x) {
        double minDist = Double.POSITIVE_INFINITY;
        int minCluster = 0;
        for (int j = 0; j < this.K; j++) {
            double dist = this.distanceMeasure.score(x, this.centroids[j]);
            if (dist < minDist) {
                minCluster = j;
                minDist = dist;
            }
        }
        return minCluster;
    }

    public double[][] getSmarterCentroids() {
        double[][] centroids = new double[this.K][this.yDim];
        double[] dists =new double[this.xDim];
        for (int k = 0; k < this.K; k++) {
            for (int i = 0; i < this.xDim; i++) {
                if (dists[i] < 0)
                    continue;
                for (int j = 0; j < this.xDim; j++) {

                    dists[i] += this.distanceMeasure.score(this.M[i], this.M[j]);
                }
            }
            int maxIndex = 0;
            double maxSum = 0;
            for (int i = 0; i < this.xDim; i++) {
                if (dists[i] > maxSum) {
                    maxIndex = i;
                    maxSum = dists[i];
                }
            }

            for (int j = 0; j < this.yDim; j++)
                centroids[k][j] = this.M[maxIndex][j];

            dists[maxIndex] = -1;
        }

        return centroids;
    }

    public double[][] getRandomCentroids() {
        HashSet<Integer> indices = new HashSet<Integer>();
        while (indices.size() < this.K) {
            int rnd = new Random().nextInt(this.xDim);
            indices.add(rnd);
        }
        double[][] randomCentroids = new double[this.K][this.yDim];
        int k = 0;
        for (int doc_index: indices) {
            for (int term_index = 0; term_index < this.yDim; term_index++)
                randomCentroids[k][term_index] = this.M[doc_index][term_index];
            k += 1;
        }

        return randomCentroids;
    }

    public double[][] generateRandomCentroids() {
        double minV = Double.POSITIVE_INFINITY;
        double maxV = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < this.xDim; i++) {
            for (int j = 0; j < this.yDim; j++) {
                minV = Math.min(minV,this.M[i][j]);
                maxV = Math.max(maxV,this.M[i][j]);
            }
        }

        double[][] randomCentroids = new double[this.K][this.yDim];
        for (int i = 0; i < this.K; i++) {
            for (int j = 0; j < this.yDim; j++) {
                randomCentroids[i][j] = minV + (maxV-minV)*Math.random();

            }
        }


        return randomCentroids;
    }


}
