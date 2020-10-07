public class DistanceMeasure {
    private String funcId;
    private final String[] allFuncIds = {"cosine","euclidean"};
    public DistanceMeasure(String funcId) {
        this.setFuncId(funcId);
    }

    public DistanceMeasure() {
        this(null);
    }
    public double score(double[] a, double[] b) {
        return score(a,b,this.funcId);
    }
    public double score(double[] a, double[] b, String funcId) {
        return switch (funcId) {
            case "cosine" -> cosineSimilarity(a,b);
            case "euclidean" -> euclideanDist(a, b);
            default -> cosineSimilarity(a, b);
        };
    }

    private void setFuncId(String funcId) {
        String defaultFuncId = "cosine";

        if (funcId == null) {
            this.funcId = defaultFuncId;
            return;
        }

        boolean found = false;
        for (String allFuncId : this.allFuncIds) {
            if (allFuncId.equals(funcId)) {
                found = true;
                break;
            }
        }
        if (!found){
            this.funcId = defaultFuncId;
        }
        else {
            this.funcId = funcId;
        }


    }

    public double euclideanDist(double[] a, double[] b) {
        int H = a.length;
        double pS = 0;
        for (int i = 0; i < H; i++) {
            pS += Math.pow(b[i] - a[i],2);
        }
        return Math.sqrt(pS);
    }

    public double cosineSimilarity(double[] a, double[] b) {
        double dp = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < a.length; i++) {
            dp += a[i] * b[i];
            normA += Math.pow(a[i], 2);
            normB += Math.pow(b[i], 2);
        }
        return dp / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
