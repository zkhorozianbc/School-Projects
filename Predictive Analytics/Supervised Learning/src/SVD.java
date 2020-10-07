import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SVD {
    public static void main(String[] args) {

        // create a chart...
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot", // chart title
                "X", // x axis label
                "Y", // y axis label
                createDataset(), // data  ***-----PROBLEM------***
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        // create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        frame.setVisible(true);
    }



    private static XYDataset createDataset() {
        DoubleMatrix2D y = new DenseDoubleMatrix2D(3,3).assign(0.5);
        SingularValueDecomposition s = new SingularValueDecomposition(y);
        System.out.println(s.getS());
        System.out.println(s.rank());
        DoubleMatrix2D x = s.getS().viewPart(0,0,3,2);
        System.out.println(x.toString());



        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series = new XYSeries("Random");
        for (int i = 0; i < x.rows(); i++) {
            series.add(x.get(i,0), x.get(i,1));
        }
        result.addSeries(series);
        return result;
    }
}
