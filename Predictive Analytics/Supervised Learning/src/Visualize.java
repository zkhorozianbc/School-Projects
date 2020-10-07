import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
public class Visualize extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;

    public Visualize(String title) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Boys VS Girls weight comparison chart",
                "X-Axis", "Y-Axis", dataset);


        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
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
//    private XYDataset createDataset() {
//        XYSeriesCollection dataset = new XYSeriesCollection();
//
//        //Boys (Age,weight) series
//        XYSeries series1 = new XYSeries("Boys");
//        series1.add(1, 72.9);
//        series1.add(2, 81.6);
//        series1.add(3, 88.9);
//        series1.add(4, 96);
//        series1.add(5, 102.1);
//        series1.add(6, 108.5);
//        series1.add(7, 113.9);
//        series1.add(8, 119.3);
//        series1.add(9, 123.8);
//        series1.add(10, 124.4);
//
//        dataset.addSeries(series1);
//
//        //Girls (Age,weight) series
//        XYSeries series2 = new XYSeries("Girls");
//        series2.add(1, 72.5);
//        series2.add(2, 80.1);
//        series2.add(3, 87.2);
//        series2.add(4, 94.5);
//        series2.add(5, 101.4);
//        series2.add(6, 107.4);
//        series2.add(7, 112.8);
//        series2.add(8, 118.2);
//        series2.add(9, 122.9);
//        series2.add(10, 123.4);
//
//        dataset.addSeries(series2);
//
//        return dataset;
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Visualize example = new Visualize("Scatter Chart Example");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
