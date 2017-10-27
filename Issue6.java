package issues;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Issue6 extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Counter");
        yAxis.setLabel("Number");
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("STIW3054: Example of Chart for Displaying Random Numbers");
        XYChart.Series series = new XYChart.Series();
        series.setName("Issue 6");

        int a[] = new int[100];
        for (int i = 0; i < a.length; i++) {
            int num;
            num = (int) (Math.random() * 101);
            a[i] = num;
        }
        for (int i = 0; i < a.length; i++) {
            series.getData().add(new XYChart.Data(i, a[i]));
        }

        Scene scene = new Scene(lineChart, 800, 600);
        xAxis.setTickUnit(0.5);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
