package takred.weightcontrol;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import takred.weightcontrol.dto.WeightDto;

import java.awt.*;
import java.util.List;

public class ChartCreator
{
    public JFreeChart createChart(CategoryDataset dataset, int lowConfines, int highConfines ) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Линейный график веса.",
                null,
                "Вес(кг)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(new Range(lowConfines, highConfines));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        renderer.setSeriesStroke(
                0, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[] {10.0f, 6.0f}, 0.0f
                )
        );
        renderer.setSeriesStroke(
                1, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[] {6.0f, 6.0f}, 0.0f
                )
        );
        return chart;
    }
    public CategoryDataset createDataset(List<WeightDto> dtos) {
        String string = "Кривая веса.";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < dtos.size(); i++) {
            WeightDto weightDto = dtos.get(i);
            dataset.addValue(weightDto.getWeight().doubleValue(), string, weightDto.getDate().toString());
        }
        return dataset;
    }
}
