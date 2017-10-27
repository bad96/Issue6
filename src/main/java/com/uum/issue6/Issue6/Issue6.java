/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uum.issue6.Issue6;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a real-time chart using SwingWorker
 */
public class Issue6 {

    MySwingWorker mySwingWorker;
    SwingWrapper<XYChart> sw;
    XYChart chart;

    public static void main(String[] args) throws Exception {
        Issue6 swingWorkerRealTime = new Issue6();
        swingWorkerRealTime.go();
    }

    private void go() {
        // Create Chart
        chart = QuickChart.getChart("STIW3054: Example of Chart for Displaying Random Numbers", "Counter", "Number", "randomWalk", new double[]{0}, new double[]{0});
        chart.getStyler().setLegendVisible(false);
        //chart.getStyler().setXAxisTicksVisible(false);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        LinkedList<Double> fifo = new LinkedList<Double>();

        public MySwingWorker() {

            fifo.add(0.0);
        }

        @Override
        protected Boolean doInBackground() throws Exception {

            while (!isCancelled()) {
                fifo.add(fifo.get(fifo.size() - 1) + Math.random() - .5);
                if (fifo.size() > 500) {
                    fifo.removeFirst();
                }

                double[] array = new double[fifo.size()];
                for (int i = 0; i < fifo.size(); i++) {
                    array[i] = fifo.get(i);
                }
                /*int a[] = new int[1000000];
                int num;
                for (int i = 0; i < a.length; i++) {
                    num = (int) (Math.random() * 1000001);
                    a[i] = num;
                }*/
                publish(array);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("MySwingWorker shut down.");
                }

            }
            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {
            //System.out.println("number of chunks: " + chunks.size());

            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

            chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
            sw.repaintChart();

            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }
    }
}
