package org.gridsphere.services.core.charts;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.services.core.secdir.FileLocationID;
import java.io.IOException;
import java.io.File;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id: ChartService.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public interface ChartService extends PortletService {
    public FileLocationID createChartLocationID(String userID, String category, String fileName);
    public String getChartUrl(FileLocationID fileLocationID);
    public String getDownloadChartUrl(FileLocationID fileLocationID, String saveAs);
    public ChartDescriptor createPieChart(FileLocationID fileLocationID, org.jfree.data.DefaultPieDataset dataset) throws IOException, Exception;
    public ChartDescriptor createPie3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultPieDataset dataset) throws IOException, Exception;
    public ChartDescriptor createBarChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createBar3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createStackedBarChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createStackedBar3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createAreaChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createStackedAreaChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createLineChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception;
    public ChartDescriptor createGanttChart(FileLocationID fileLocationID, org.jfree.data.gantt.TaskSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createBarXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createAreaXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createLineXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createStepAreaXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createBarXYChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createTimeSeriesChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception;
    public ChartDescriptor createStepXYChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception;
    public void setChartDataset(FileLocationID fileLocationID, org.jfree.data.Dataset inDataset) throws IOException, Exception;
    public void setChartDataset(FileLocationID fileLocationID, org.jfree.data.Dataset inDataset, long datasetTimeStamp) throws IOException, Exception;
    public ChartDescriptor getChartDescriptor(FileLocationID fileLocationID) throws IOException, Exception;
    public void setChartDescriptor(FileLocationID fileLocationID, ChartDescriptor inChartDescriptor) throws IOException, Exception;
    public void setChartTitle(FileLocationID fileLocationID, String title) throws IOException, Exception;
    
    public String[] getChartList(FileLocationID fileLocationID);
    public File getChartImageFile(FileLocationID fileLocationID) throws IOException, Exception;
    public File getChartDataFile(FileLocationID fileLocationID) throws IOException, Exception;
    public boolean deleteChart(FileLocationID fileLocationID) throws IOException, Exception;
    public boolean deleteChart(FileLocationID fileLocationID, boolean deleteDataset) throws IOException, Exception;
    public boolean chartExists(FileLocationID fileLocationID);    
}
