package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AreaChartSample extends Application {

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Area Chart Sample");
        final NumberAxis xAxis = new NumberAxis(1, 30, 1);
        final NumberAxis yAxis = new NumberAxis(-5, 27, 5);
        final AreaChart<Number, Number> ac = new AreaChart<>(xAxis, yAxis);
        xAxis.setForceZeroInRange(true);

        ac.setTitle("Temperature Monitoring (in Degrees C)");

        final XYChart.Series series1 = new XYChart.Series();
        series1.setName("March");
        series1.getData().add(new XYChart.Data(0, -2));
        series1.getData().add(new XYChart.Data(3, -4));
        series1.getData().add(new XYChart.Data(6, 0));
        series1.getData().add(new XYChart.Data(9, 5));
        series1.getData().add(new XYChart.Data(12, -4));
        series1.getData().add(new XYChart.Data(15, 6));
        series1.getData().add(new XYChart.Data(18, 8));
        series1.getData().add(new XYChart.Data(21, 14));
        series1.getData().add(new XYChart.Data(24, 4));
        series1.getData().add(new XYChart.Data(27, 6));
        series1.getData().add(new XYChart.Data(30, 6));

        final XYChart.Series series2 = new XYChart.Series();
        series2.setName("April");
        series2.getData().add(new XYChart.Data(0, 4));
        series2.getData().add(new XYChart.Data(3, 10));
        series2.getData().add(new XYChart.Data(6, 15));
        series2.getData().add(new XYChart.Data(9, 8));
        series2.getData().add(new XYChart.Data(12, 5));
        series2.getData().add(new XYChart.Data(15, 18));
        series2.getData().add(new XYChart.Data(18, 15));
        series2.getData().add(new XYChart.Data(21, 13));
        series2.getData().add(new XYChart.Data(24, 19));
        series2.getData().add(new XYChart.Data(27, 21));
        series2.getData().add(new XYChart.Data(30, 21));

        final XYChart.Series series3 = new XYChart.Series();
        series3.setName("May");
        series3.getData().add(new XYChart.Data(0, 20));
        series3.getData().add(new XYChart.Data(3, 15));
        series3.getData().add(new XYChart.Data(6, 13));
        series3.getData().add(new XYChart.Data(9, 12));
        series3.getData().add(new XYChart.Data(12, 14));
        series3.getData().add(new XYChart.Data(15, 18));
        series3.getData().add(new XYChart.Data(18, 25));
        series3.getData().add(new XYChart.Data(21, 25));
        series3.getData().add(new XYChart.Data(24, 23));
        series3.getData().add(new XYChart.Data(27, 26));
        series3.getData().add(new XYChart.Data(30, 26));

        final Scene scene = new Scene(ac, 800, 600);
        // scene.getStylesheets().add("areachartsample/Chart.css");
        ac.setHorizontalZeroLineVisible(true);
        ac.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
