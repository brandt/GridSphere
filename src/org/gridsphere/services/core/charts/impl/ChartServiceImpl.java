package org.gridsphere.services.core.charts.impl;

import org.apache.oro.text.perl.Perl5Util;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.charts.*;
import org.gridsphere.services.core.secdir.FileLocationID;
import org.gridsphere.services.core.secdir.SecureDirectoryService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id$
 */

public class ChartServiceImpl implements ChartService, PortletServiceProvider {
    private boolean inited = false;
    private SecureDirectoryService secureDirectoryService;
    private static final String CHART_SERVICE_DIRECTORY = "_chart_service_directory_";
    private Perl5Util util = new Perl5Util();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        if (!inited) {
            System.setProperty("java.awt.headless", "true");
            try {
                secureDirectoryService = (SecureDirectoryService)PortletServiceFactory.createPortletService(SecureDirectoryService.class, true);
            } catch (PortletServiceException e) {
                throw new PortletServiceUnavailableException("Unable to get instance of SecureDirectoryService!", e);
            }
	    inited = true;
        }
    }

    public void destroy() {
    }

    public String getChartUrl(FileLocationID fileLocationID) {
        return getDownloadChartUrl(fileLocationID, null);
    }

    public String getDownloadChartUrl(FileLocationID fileLocationID, String saveAs) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        String chartUrl = null;
        try {
            refreshChart(fileLocationID);
            ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
            Image imageInfo = chartDescriptor.getFileInfo().getImage();
            chartUrl = (saveAs != null ? secureDirectoryService.getDownloadFileUrl(secureDirectoryService.createFileLocationID(userID,
                    imageInfo.getAppName(),
                    imageInfo.getFilename() + (imageInfo.getType().equals("JPEG") ? ".jpeg" : ".png")),
                    saveAs,
                    null) :
                    secureDirectoryService.getFileUrl(secureDirectoryService.createFileLocationID(userID,
                            imageInfo.getAppName(),
                            imageInfo.getFilename() + (imageInfo.getType().equals("JPEG") ? ".jpeg" : ".png"))));
        } catch (Exception e) {
        }
        return chartUrl;
    }

    public ChartDescriptor createPieChart(FileLocationID fileLocationID, org.jfree.data.DefaultPieDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Pie");
    }

    public ChartDescriptor createPie3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultPieDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Pie3D");
    }

    public ChartDescriptor createBarChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Bar");
    }

    public ChartDescriptor createBar3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Bar3D");
    }

    public ChartDescriptor createStackedBarChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "StackedBar");
    }

    public ChartDescriptor createStackedBar3DChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "StackedBar3D");
    }

    public ChartDescriptor createAreaChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Area");
    }

    public ChartDescriptor createStackedAreaChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "StackedArea");
    }

    public ChartDescriptor createLineChart(FileLocationID fileLocationID, org.jfree.data.DefaultCategoryDataset dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Line");
    }

    public ChartDescriptor createGanttChart(FileLocationID fileLocationID, org.jfree.data.gantt.TaskSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "Gantt");
    }

    public ChartDescriptor createBarXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "BarXY");
    }

    public ChartDescriptor createAreaXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "AreaXY");
    }

    public ChartDescriptor createLineXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "LineXY");
    }

    public ChartDescriptor createStepAreaXYChart(FileLocationID fileLocationID, org.jfree.data.XYSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "StepAreaXY");
    }

    public ChartDescriptor createBarXYChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "BarXY");
    }

    public ChartDescriptor createTimeSeriesChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "TimeSeries");
    }

    public ChartDescriptor createStepXYChart(FileLocationID fileLocationID, org.jfree.data.time.TimeSeriesCollection dataset) throws IOException, Exception {
        return createChart(fileLocationID, dataset, "StepXY");
    }

    private ChartDescriptor createChart(FileLocationID fileLocationID, org.jfree.data.Dataset dataset, String type) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        if (appName.equals(CHART_SERVICE_DIRECTORY))
            throw new IOException("AppName collides with internal chart service directory !!!");
        ChartDescriptor chartDescriptor = null;
        try {
            chartDescriptor = createChartDescriptor(userID, appName, resource, type, dataset);
            writeChartDescriptor(userID, appName, resource, chartDescriptor);
        } catch (MarshalException e) {
            throw new Exception("Unable to write create chart description file (MarshalException).", e);
        } catch (ValidationException e) {
            throw new Exception("Unable to write create chart description file (ValidationException).", e);
        }
        chartDescriptor.getChartInfo().setRefresh(true);
        return chartDescriptor;
    }

    public void setChartDataset(FileLocationID fileLocationID, org.jfree.data.Dataset inDataset, long datasetTimeStamp) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        if (chartDescriptor.getFileInfo().getDataset().getTimestamp() < datasetTimeStamp)
            setChartDataset(fileLocationID, inDataset);
    }

    public void setChartDataset(FileLocationID fileLocationID, org.jfree.data.Dataset inDataset) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        String datasetType = chartDescriptor.getFileInfo().getDataset().getType();
        if (!inDataset.getClass().getName().equals(datasetType))
            throw new Exception("Incompatibile dataset type (" + datasetType + ") expected.");
        writeDataset(userID, appName, resource, inDataset);
        chartDescriptor.getFileInfo().getDataset().setTimestamp(new Date().getTime());
        chartDescriptor.getChartInfo().setRefresh(true);
        writeChartDescriptor(userID, appName, resource, chartDescriptor);
    }

    public void setChartDescriptor(FileLocationID fileLocationID, ChartDescriptor inChartDescriptor) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = getChartDescriptor(fileLocationID);
        Image inChartImage = inChartDescriptor.getFileInfo().getImage();
        Image chartImage = chartDescriptor.getFileInfo().getImage();
        if (!inChartImage.getAppName().equals(chartImage.getAppName()) ||
                !inChartImage.getFilename().equals(chartImage.getFilename()) ||
                !inChartImage.getType().equals(chartImage.getType())) {
            try {
                getChartImageFile(fileLocationID).delete();
            } catch (Exception e) {
                getChartImageFile(fileLocationID).deleteOnExit();
            }
            inChartDescriptor.getChartInfo().setRefresh(true);
        }
        Dataset inDataset = inChartDescriptor.getFileInfo().getDataset();
        Dataset dataset = chartDescriptor.getFileInfo().getDataset();
        if (!inDataset.getAppName().equals(dataset.getAppName()) ||
                !inDataset.getFilename().equals(dataset.getFilename())) {
            inDataset.setTimestamp(new Date().getTime());
            inChartDescriptor.getChartInfo().setRefresh(true);
        }
//        inChartDescriptor.getChartInfo().setRefresh(true); - in getChartDescriptor(String userID, String appName, String resource) - allows to change chart settings, and refresh chart after first dataset change
        writeChartDescriptor(userID, appName, resource, inChartDescriptor);
    }

    public ChartDescriptor getChartDescriptor(FileLocationID fileLocationID) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        chartDescriptor.getChartInfo().setRefresh(true);
        return chartDescriptor;
    }

    public void setChartTitle(FileLocationID fileLocationID, String title) throws IOException, Exception {
        ChartDescriptor chartDescriptor = getChartDescriptor(fileLocationID);
        chartDescriptor.getChartInfo().setTitle(title);
        setChartDescriptor(fileLocationID, chartDescriptor);
    }

    public String[] getChartList(FileLocationID fileLocationID) {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String path = fileLocationID.getFilePath();
        org.gridsphere.services.core.secdir.FileInfo resourceLists[] = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, CHART_SERVICE_DIRECTORY, appName + "/" + path));
        int count = 0;
        if (resourceLists == null)
            return null;
        for (int i = 0; i < resourceLists.length; ++i) {
            if (!resourceLists[i].isDirectory())
                ++count;
        }
        String chartList[] = new String[count];
        count = 0;
        for (int i = 0; i < resourceLists.length; ++i) {
            if (!resourceLists[i].isDirectory()) {
                String resource = resourceLists[i].getResource();
                resource = util.substitute("s/\\.xml$//", resource);
                chartList[count++] = resource;
            }
        }
        return chartList;
    }

    public File getChartImageFile(FileLocationID fileLocationID) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        refreshChart(fileLocationID);
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        Image imageInfo = chartDescriptor.getFileInfo().getImage();
        return secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID,
                imageInfo.getAppName(),
                imageInfo.getFilename() + (imageInfo.getType().equals("JPEG") ? ".jpeg" : ".png")));
    }

    public File getChartDataFile(FileLocationID fileLocationID) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        Dataset datasetInfo = chartDescriptor.getFileInfo().getDataset();
        return secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID,
                datasetInfo.getAppName(),
                datasetInfo.getFilename() + ".xml"));
    }

    public boolean deleteChart(FileLocationID fileLocationID) throws IOException, Exception {
        return deleteChart(fileLocationID, false);
    }

    public boolean deleteChart(FileLocationID fileLocationID, boolean deleteDataset) throws IOException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        Image imageInfo = chartDescriptor.getFileInfo().getImage();
        secureDirectoryService.deleteFile(secureDirectoryService.createFileLocationID(userID,
                imageInfo.getAppName(),
                imageInfo.getFilename() + (imageInfo.getType().equals("JPEG") ? ".jpeg" : ".png")),
                true,
                true);
        if (deleteDataset) {
            Dataset dataset = chartDescriptor.getFileInfo().getDataset();
            secureDirectoryService.deleteFile(secureDirectoryService.createFileLocationID(userID,
                    dataset.getAppName(),
                    dataset.getFilename() + ".xml"),
                    true,
                    true);
        }
        return secureDirectoryService.deleteFile(secureDirectoryService.createFileLocationID(userID, CHART_SERVICE_DIRECTORY, appName + "/" + resource + ".xml"), true, true);
    }

    public FileLocationID createChartLocationID(String userID, String category, String fileName) {
        return new FileLocationID(userID, category, fileName);
    }

    public boolean chartExists(FileLocationID fileLocationID) {
        File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(fileLocationID.getUserID(), CHART_SERVICE_DIRECTORY, fileLocationID.getCategory() + "/" + fileLocationID.getFilePath() + ".xml"));
        return file.exists();
    }

    private boolean refreshChart(FileLocationID fileLocationID) throws IOException, MarshalException, ValidationException, Exception {
        String userID = fileLocationID.getUserID();
        String appName = fileLocationID.getCategory();
        String resource = fileLocationID.getFilePath();
        ChartDescriptor chartDescriptor = readChartDescriptor(userID, appName, resource);
        if (chartDescriptor.getChartInfo().getRefresh() == false) {
            Image imageInfo = chartDescriptor.getFileInfo().getImage();
//            File imageFile = getChartImageFile(userID, appName, resource);
            Dataset datasetInfo = chartDescriptor.getFileInfo().getDataset();
            File datasetFile = getChartDataFile(fileLocationID);
            if ((imageInfo.getTimestamp() > datasetInfo.getTimestamp()) && (imageInfo.getTimestamp() >= datasetFile.lastModified()))
                return false;
        }
        org.jfree.data.Dataset dataset = readDataset(userID, chartDescriptor.getFileInfo().getDataset().getAppName(), resource, chartDescriptor.getFileInfo().getDataset().getType());
        org.jfree.chart.JFreeChart chart = null;
        ChartInfo chartInfo = chartDescriptor.getChartInfo();
        try {
            if (chartInfo.getType().equals("Pie")) {
                chart = org.jfree.chart.ChartFactory.createPieChart(chartInfo.getTitle(),
                        (org.jfree.data.DefaultPieDataset) dataset,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("Pie3D")) {
                chart = org.jfree.chart.ChartFactory.createPieChart3D(chartInfo.getTitle(),
                        (org.jfree.data.DefaultPieDataset) dataset,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("Bar")) {
                chart = org.jfree.chart.ChartFactory.createBarChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("Bar3D")) {
                chart = org.jfree.chart.ChartFactory.createBarChart3D(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("StackedBar")) {
                chart = org.jfree.chart.ChartFactory.createStackedBarChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("StackedBar3D")) {
                chart = org.jfree.chart.ChartFactory.createStackedBarChart3D(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("BarXY")) {
                if (dataset instanceof org.jfree.data.XYSeriesCollection)
                    chart = org.jfree.chart.ChartFactory.createXYBarChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            false,
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.XYSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
                else
                    chart = org.jfree.chart.ChartFactory.createXYBarChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            true,
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.time.TimeSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
            } else if (chartInfo.getType().equals("Area")) {
                chart = org.jfree.chart.ChartFactory.createAreaChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("StackedArea")) {
                chart = org.jfree.chart.ChartFactory.createStackedAreaChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("AreaXY")) {
                chart = org.jfree.chart.ChartFactory.createXYAreaChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.XYSeriesCollection) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("Gantt")) {
                chart = org.jfree.chart.ChartFactory.createGanttChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.gantt.TaskSeriesCollection) dataset,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("Line")) {
                chart = org.jfree.chart.ChartFactory.createLineChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.DefaultCategoryDataset) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("LineXY")) {
                chart = org.jfree.chart.ChartFactory.createXYLineChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.XYSeriesCollection) dataset,
                        chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                        org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                        org.jfree.chart.plot.PlotOrientation.VERTICAL,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("TimeSeries")) {
                chart = org.jfree.chart.ChartFactory.createTimeSeriesChart(chartInfo.getTitle(),
                        chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                        chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                        (org.jfree.data.time.TimeSeriesCollection) dataset,
                        chartInfo.getLegend(),
                        false,
                        false);
            } else if (chartInfo.getType().equals("StepXY")) {
                if (dataset instanceof org.jfree.data.XYSeriesCollection)
                    chart = org.jfree.chart.ChartFactory.createXYStepChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.XYSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
                else
                    chart = org.jfree.chart.ChartFactory.createXYStepChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.time.TimeSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
            } else if (chartInfo.getType().equals("StepAreaXY")) {
                if (dataset instanceof org.jfree.data.XYSeriesCollection)
                    chart = org.jfree.chart.ChartFactory.createXYStepAreaChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.XYSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
                else
                    chart = org.jfree.chart.ChartFactory.createXYStepAreaChart(chartInfo.getTitle(),
                            chartInfo.getPlot().getSettings().getCategory().getDomainAxisLabel(),
                            chartInfo.getPlot().getSettings().getCategory().getRangeAxisLabel(),
                            (org.jfree.data.time.TimeSeriesCollection) dataset,
                            chartInfo.getPlot().getSettings().getCategory().getPlotOrientation().equals("PlotOrientation.HORIZONTAL") ?
                            org.jfree.chart.plot.PlotOrientation.HORIZONTAL :
                            org.jfree.chart.plot.PlotOrientation.VERTICAL,
                            chartInfo.getLegend(),
                            false,
                            false);
            }
        } catch (ClassCastException e) {
            throw new Exception("Incorrect dataset type.");
        }
        if (chartInfo.getBackgroundPaint() != null)
            chart.setBackgroundPaint(createPaint(chartInfo.getBackgroundPaint()));
        if (chartInfo.getSubtitle() != null)
            chart.addSubtitle(new org.jfree.chart.title.TextTitle(chartInfo.getSubtitle().getText(),
                    chartInfo.getSubtitle().getFont() != null ? createFont(chartInfo.getSubtitle().getFont()) : new java.awt.Font("Serif", java.awt.Font.PLAIN, 10)));
        Plot plotInfo = chartInfo.getPlot();
        org.jfree.chart.plot.Plot plot = chart.getPlot();
        if (plotInfo.getBackgroundPaint() != null)
            plot.setBackgroundPaint(createPaint(plotInfo.getBackgroundPaint()));
        plot.setNoDataMessage(plotInfo.getNoDataMessage().getValue());
        plot.setNoDataMessageFont(createFont(plotInfo.getNoDataMessage().getFont()));
        plot.setNoDataMessagePaint(createPaint(plotInfo.getNoDataMessage().getPaint()));
        plot.setForegroundAlpha(plotInfo.getForegroundAlpha());
        try {
            for (int i = 0; i < plotInfo.getSeriesPaintCount(); ++i) {
                if (chartDescriptor.getFileInfo().getDataset().getType().equals("org.jfree.data.DefaultPieDataset"))
                    ((org.jfree.chart.plot.PiePlot) chart.getPlot()).setSectionPaint(i, createPaint(plotInfo.getSeriesPaint(i)));
                else if (chartDescriptor.getFileInfo().getDataset().getType().equals("org.jfree.data.DefaultCategoryDataset") || chartDescriptor.getFileInfo().getDataset().getType().equals("org.jfree.data.gantt.TaskSeriesCollection"))
                    chart.getCategoryPlot().getRenderer().setSeriesPaint(i, createPaint(plotInfo.getSeriesPaint(i)));
                else if (chartDescriptor.getFileInfo().getDataset().getType().equals("org.jfree.data.XYSeriesCollection") || chartDescriptor.getFileInfo().getDataset().getType().equals("org.jfree.data.time.TimeSeriesCollection"))
                    chart.getXYPlot().getRenderer().setSeriesPaint(i, createPaint(plotInfo.getSeriesPaint(i)));
            }
        } catch (ClassCastException e) {
        }
        if (plotInfo.getSettings().getPie() != null)
            try {
                ((org.jfree.chart.plot.PiePlot) plot).setLabelGenerator(new org.jfree.chart.labels.StandardPieItemLabelGenerator(plotInfo.getSettings().getPie().getLabelGenerator()));
            } catch (ClassCastException e) {
            }
        Image image = chartDescriptor.getFileInfo().getImage();
        if (image.getType().equals("JPEG")) {
            File chartFile = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, image.getAppName(), image.getFilename() + ".jpeg"));
            if (chartFile == null) throw new IOException("Cannot create chartfile");
            org.jfree.chart.ChartUtilities.saveChartAsJPEG(chartFile, image.getQuality(), chart, image.getWidth(), image.getHeight());
        } else if (image.getType().equals("PNG")) {
            File chartFile = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, image.getAppName(), image.getFilename() + ".png"));
            if (chartFile == null) throw new IOException("Cannot create chartfile");
            org.jfree.chart.ChartUtilities.saveChartAsPNG(chartFile, chart, image.getWidth(), image.getHeight());
        } else
            throw new IOException("Image type (" + image.getType() + ") is not supported.");
        chartDescriptor.getFileInfo().getImage().setTimestamp(new Date().getTime());
        chartDescriptor.getChartInfo().setRefresh(false);
        writeChartDescriptor(userID, appName, resource, chartDescriptor);
        return true;
    }

    private java.awt.Paint createPaint(PaintType paint) {
        if (paint.getColor() != null)
            return new java.awt.Color(paint.getColor().getRed(), paint.getColor().getGreen(), paint.getColor().getBlue());
        GradientPoint gradientPoint0 = paint.getGradient().getGradientPoint(0);
        GradientPoint gradientPoint1 = paint.getGradient().getGradientPoint(1);
        return new java.awt.GradientPaint(gradientPoint0.getX(), gradientPoint0.getY(), new java.awt.Color(gradientPoint0.getColor().getRed(), gradientPoint0.getColor().getGreen(), gradientPoint0.getColor().getBlue()),
                gradientPoint1.getX(), gradientPoint1.getY(), new java.awt.Color(gradientPoint1.getColor().getRed(), gradientPoint1.getColor().getGreen(), gradientPoint1.getColor().getBlue()));
    }

    private java.awt.Font createFont(FontType font) {
        return new java.awt.Font(font.getFamily(), font.getStyle(), font.getSize());
    }

    private ChartDescriptor createChartDescriptor(String userID, String appName, String resource, String type, org.jfree.data.Dataset inDataset) throws IOException, Exception {
        writeDataset(userID, appName, resource, inDataset);
        Image image = new Image();
        image.setAppName(appName);
        image.setFilename(resource);
        image.setTimestamp(0);
        Dataset dataset = new Dataset();
        dataset.setAppName(appName);
        dataset.setFilename(resource);
        dataset.setType(inDataset.getClass().getName());
        dataset.setTimestamp(new Date().getTime());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setImage(image);
        fileInfo.setDataset(dataset);
        NoDataMessage noDataMessage = new NoDataMessage();
        Color color = new Color();
        color.setRed(255);
        Paint paint = new Paint();
        paint.setColor(color);
        noDataMessage.setFont(new Font());
        noDataMessage.setPaint(paint);
        Plot plot = new Plot();
        plot.setNoDataMessage(noDataMessage);
        Settings settings = new Settings();
        if (type.equals("Pie") || type.equals("Pie3D"))
            settings.setPie(new Pie());
        else
            settings.setCategory(new Category());
        plot.setSettings(settings);
        ChartInfo chartInfo = new ChartInfo();
        chartInfo.setType(type);
        chartInfo.setPlot(plot);
        Color backgroundColor = new Color();
        backgroundColor.setRed(238);
        backgroundColor.setGreen(238);
        backgroundColor.setBlue(238);
        BackgroundPaint backgroundPaint = new BackgroundPaint();
        backgroundPaint.setColor(backgroundColor);
        chartInfo.setBackgroundPaint(backgroundPaint);
        ChartDescriptor chartDescriptor = new ChartDescriptor();
        chartDescriptor.setFileInfo(fileInfo);
        chartDescriptor.setChartInfo(chartInfo);
        return chartDescriptor;
    }

    private ChartDescriptor readChartDescriptor(String userID, String appName, String resource) throws IOException, MarshalException, ValidationException {
        File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, CHART_SERVICE_DIRECTORY, appName + "/" + resource + ".xml"));
        FileReader reader = new FileReader(file);
        ChartDescriptor chartDescriptor = (ChartDescriptor) ChartDescriptor.unmarshal(reader);
        reader.close();
        return chartDescriptor;
    }

    private void writeChartDescriptor(String userID, String appName, String resource, ChartDescriptor chartDescriptor) throws IOException, MarshalException, ValidationException {
        File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, CHART_SERVICE_DIRECTORY, appName + "/" + resource + ".xml"));
        FileWriter writer = new FileWriter(file);
        chartDescriptor.marshal(writer);
        writer.flush();
        writer.close();
    }

    private void writeDataset(String userID, String appName, String resource, org.jfree.data.Dataset inDataset) throws IOException, Exception {
        File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, appName, resource + ".xml"));
        try {
            if (inDataset.getClass().getName().equals("org.jfree.data.DefaultPieDataset"))
                DatasetUtilities.writeDefaultPieDataset(file, (org.jfree.data.DefaultPieDataset) inDataset);
            else if (inDataset.getClass().getName().equals("org.jfree.data.DefaultCategoryDataset"))
                DatasetUtilities.writeDefaultCategoryDataset(file, (org.jfree.data.DefaultCategoryDataset) inDataset);
            else if (inDataset.getClass().getName().equals("org.jfree.data.gantt.TaskSeriesCollection"))
                DatasetUtilities.writeTaskSeriesCollection(file, (org.jfree.data.gantt.TaskSeriesCollection) inDataset);
            else if (inDataset.getClass().getName().equals("org.jfree.data.time.TimeSeriesCollection"))
                DatasetUtilities.writeTimeSeriesCollection(file, (org.jfree.data.time.TimeSeriesCollection) inDataset);
            else if (inDataset.getClass().getName().equals("org.jfree.data.XYSeriesCollection"))
                DatasetUtilities.writeXYSeriesCollection(file, (org.jfree.data.XYSeriesCollection) inDataset);
        } catch (MarshalException e) {
            file.delete();
            throw new Exception("Unable to write dataset file (MarshalException).", e);
        } catch (ValidationException e) {
            file.delete();
            throw new Exception("Unable to write dataset file (ValidationException).", e);
        }
    }

    private org.jfree.data.Dataset readDataset(String userID, String appName, String resource, String type) throws IOException, MarshalException, ValidationException, Exception {
        File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, appName, resource + ".xml"));
        org.jfree.data.Dataset dataset = null;
        if (type.equals("org.jfree.data.DefaultPieDataset"))
            dataset = DatasetUtilities.readDefaultPieDataset(file);
        else if (type.equals("org.jfree.data.DefaultCategoryDataset"))
            dataset = DatasetUtilities.readDefaultCategoryDataset(file);
        else if (type.equals("org.jfree.data.gantt.TaskSeriesCollection"))
            dataset = DatasetUtilities.readTaskSeriesCollection(file);
        else if (type.equals("org.jfree.data.time.TimeSeriesCollection"))
            dataset = DatasetUtilities.readTimeSeriesCollection(file);
        else if (type.equals("org.jfree.data.XYSeriesCollection"))
            dataset = DatasetUtilities.readXYSeriesCollection(file);
        else
            throw new Exception("Unsupported dataset type.");
        return dataset;
    }
}
