package org.gridsphere.services.core.charts;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id$
 */

public class DatasetUtilities {

    public static org.jfree.data.DefaultPieDataset readDefaultPieDataset(File file) throws IOException, MarshalException, ValidationException {
        FileReader reader = new FileReader(file);
        DefaultPieDataset dataset = (DefaultPieDataset) DefaultPieDataset.unmarshal(reader);
        reader.close();
        org.jfree.data.DefaultPieDataset outDataset = new org.jfree.data.DefaultPieDataset();
        for (int i = 0; i < dataset.getItemCount(); ++i) {
            Item item = dataset.getItem(i);
            if (item.getValue() != null)
                outDataset.setValue(item.getKey(), Double.parseDouble(item.getValue()));
            else
                outDataset.setValue(item.getKey(), null);
        }
        return outDataset;
    }

    public static void writeDefaultPieDataset(File file, org.jfree.data.DefaultPieDataset inDataset) throws IOException, MarshalException, ValidationException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List keys = inDataset.getKeys();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            Number value = inDataset.getValue(key);
            Item item = new Item();
            item.setKey(key);
            if(value!=null)
                item.setValue(value.toString());
            dataset.addItem(item);
        }
        FileWriter output = new FileWriter(file);
        dataset.marshal(output);
        output.flush();
        output.close();
    }

    public static org.jfree.data.DefaultCategoryDataset readDefaultCategoryDataset(File file) throws IOException, MarshalException, ValidationException {
        FileReader reader = new FileReader(file);
        DefaultCategoryDataset dataset = (DefaultCategoryDataset) DefaultCategoryDataset.unmarshal(reader);
        reader.close();
        org.jfree.data.DefaultCategoryDataset outDataset = new org.jfree.data.DefaultCategoryDataset();
        for (int i = 0; i < dataset.getSeriesCount(); ++i) {
            Series serie = dataset.getSeries(i);
            for (int j = 0; j < serie.getItemCount(); ++j) {
                Item item = serie.getItem(j);
                if (item.getValue() != null)
                    outDataset.addValue(Double.parseDouble(item.getValue()), serie.getName(), item.getKey());
                else
                    outDataset.addValue(null, serie.getName(), item.getKey());
            }
        }
        return outDataset;
    }

    public static void writeDefaultCategoryDataset(File file, org.jfree.data.DefaultCategoryDataset inDataset) throws IOException, MarshalException, ValidationException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List rowKeys = inDataset.getRowKeys();
        List columnKeys = inDataset.getColumnKeys();
        for (int i = 0; i < rowKeys.size(); i++) {
            String rowKey = (String) rowKeys.get(i);
            Series series = new Series();
            series.setName(rowKey);
            for (int j = 0; j < columnKeys.size(); j++) {
                String columnKey = (String) columnKeys.get(j);
                Number value = inDataset.getValue(rowKey, columnKey);
                Item item = new Item();
                item.setKey(columnKey);
                if(value!=null)
                    item.setValue(value.toString());
                series.addItem(item);
            }
            dataset.addSeries(series);
        }
        FileWriter output = new FileWriter(file);
        dataset.marshal(output);
        output.flush();
        output.close();
    }

    public static org.jfree.data.XYSeriesCollection readXYSeriesCollection(File file) throws IOException, ValidationException, MarshalException {
        FileReader reader = new FileReader(file);
        XYSeriesCollection dataset = (XYSeriesCollection) XYSeriesCollection.unmarshal(reader);
        reader.close();
        org.jfree.data.XYSeriesCollection outDataset = new org.jfree.data.XYSeriesCollection();
        if (dataset.hasIntervalWidth())
            outDataset.setIntervalWidth(dataset.getIntervalWidth());
        if (dataset.hasIntervalPositionFactor())
            outDataset.setIntervalPositionFactor(dataset.getIntervalPositionFactor());
        if (dataset.hasAutoWidth())
            outDataset.setAutoWidth(dataset.getAutoWidth());
        Enumeration series = dataset.enumerateXYSeries();
        while (series.hasMoreElements()) {
            XYSeries serie = (XYSeries) series.nextElement();
            org.jfree.data.XYSeries outSeries = new org.jfree.data.XYSeries(serie.getName());
            if (serie.hasMaximumItemCount())
                outSeries.setMaximumItemCount(serie.getMaximumItemCount());
            if (serie.getDescription() != null)
                outSeries.setDescription(serie.getDescription());
            for (int i = 0; i < serie.getXYSeriesItemCount(); ++i) {
                XYSeriesItem item = serie.getXYSeriesItem(i);
                if (item.hasYValue())
                    outSeries.add(item.getXValue(), item.getYValue());
                else
                    outSeries.add(item.getXValue(), null);
            }
            outDataset.addSeries(outSeries);
        }
        return outDataset;
    }

    public static void writeXYSeriesCollection(File file, org.jfree.data.XYSeriesCollection inDataset) throws IOException, MarshalException, ValidationException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.setIntervalWidth(inDataset.getIntervalWidth());
        dataset.setIntervalPositionFactor(inDataset.getIntervalPositionFactor());
        dataset.setAutoWidth(inDataset.isAutoWidth());
        for (int i = 0; i < inDataset.getSeriesCount(); ++i) {
            String name = inDataset.getSeriesName(i);
            String description = inDataset.getSeries(i).getDescription();
            int maximumItemCount = inDataset.getSeries(i).getMaximumItemCount();
            XYSeries series = new XYSeries();
            if (description != null)
                series.setDescription(description);
            if (maximumItemCount != Integer.MAX_VALUE)
                series.setMaximumItemCount(maximumItemCount);
            series.setName(name);
            for (int j = 0; j < inDataset.getItemCount(i); ++j) {
                XYSeriesItem item = new XYSeriesItem();
                item.setXValue(inDataset.getXValue(i, j).doubleValue());
                if (inDataset.getYValue(i, j) != null)
                    item.setYValue(inDataset.getYValue(i, j).doubleValue());
                series.addXYSeriesItem(item);
            }
            dataset.addXYSeries(series);
        }
        FileWriter output = new FileWriter(file);
        dataset.marshal(output);
        output.flush();
        output.close();
    }

    public static org.jfree.data.time.TimeSeriesCollection readTimeSeriesCollection(File file) throws IOException, MarshalException, ValidationException, org.jfree.data.SeriesException {
        FileReader reader = new FileReader(file);
        TimeSeriesCollection dataset = (TimeSeriesCollection) TimeSeriesCollection.unmarshal(reader);
        reader.close();
        org.jfree.data.time.TimeSeriesCollection outDataset = new org.jfree.data.time.TimeSeriesCollection();
        String xPosition = dataset.getXPosition();
        if (xPosition != null)
            outDataset.setXPosition(xPosition.equals("TimePeriodAnchor.START") ?
                    org.jfree.data.time.TimePeriodAnchor.START : (
                    xPosition.equals("TimePeriodAnchor.END") ?
                    org.jfree.data.time.TimePeriodAnchor.END :
                    org.jfree.data.time.TimePeriodAnchor.MIDDLE
                    ));
        if (dataset.hasDomainIsPointsInTime())
            outDataset.setDomainIsPointsInTime(dataset.getDomainIsPointsInTime());
        Enumeration series = dataset.enumerateTimeSeries();
        while (series.hasMoreElements()) {
            TimeSeries serie = (TimeSeries) series.nextElement();
            org.jfree.data.time.TimeSeries outSeries = null;
            long milliseconds = serie.getTimeSeriesItem(0).getEnd() - serie.getTimeSeriesItem(0).getStart();
            String className = "org.jfree.data.time.";
            if (milliseconds == (long) 999) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Second.class);
                className += "Second";
            } else if (milliseconds == (long) 60 * 1000 - 1) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Minute.class);
                className += "Minute";
            } else if (milliseconds == (long) 60 * 60 * 1000 - 1) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Hour.class);
                className += "Hour";
            } else if (milliseconds == (long) 24 * 60 * 60 * 1000 - 1) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Day.class);
                className += "Day";
            } else if (milliseconds == (long) 7 * 24 * 60 * 60 * 1000 - 1) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Week.class);
                className += "Week";
            } else if ((long) 27 * 24 * 60 * 60 * 1000 < milliseconds && milliseconds < (long) 32 * 24 * 60 * 60 * 1000) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Month.class);
                className += "Month";
            } else if ((long) 88 * 24 * 60 * 60 * 1000 < milliseconds && milliseconds < (long) 93 * 24 * 60 * 60 * 1000) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Quarter.class);
                className += "Quarter";
            } else if ((long) 363 * 24 * 60 * 60 * 1000 < milliseconds && milliseconds < (long) 366 * 24 * 60 * 60 * 1000) {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.Year.class);
                className += "Year";
            } else {
                outSeries = new org.jfree.data.time.TimeSeries(serie.getName(), org.jfree.data.time.FixedMillisecond.class);
                className += "FixedMillisecond";
            }
            if (serie.hasMaximumItemCount())
                outSeries.setMaximumItemCount(serie.getMaximumItemCount());
            if (serie.hasHistoryCount())
                outSeries.setHistoryCount(serie.getHistoryCount());
            if (serie.getDescription() != null)
                outSeries.setDescription(serie.getDescription());
            if (serie.getDomainDescription() != null)
                outSeries.setDomainDescription(serie.getDomainDescription());
            if (serie.getRangeDescription() != null)
                outSeries.setRangeDescription(serie.getRangeDescription());
            for (int i = 0; i < serie.getTimeSeriesItemCount(); ++i) {
                TimeSeriesItem item = serie.getTimeSeriesItem(i);
                try {
                    if (item.hasValue())
                        outSeries.add((org.jfree.data.time.RegularTimePeriod) Class.forName(className).getConstructor(new Class[]{Date.class}).newInstance(new Object[]{new Date(item.getStart())}),
                                item.getValue());
                    else
                        outSeries.add((org.jfree.data.time.RegularTimePeriod) Class.forName(className).getConstructor(new Class[]{Date.class}).newInstance(new Object[]{new Date(item.getStart())}),
                                null);
                } catch (NoSuchMethodException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                } catch (ClassNotFoundException e) {
                } catch (InstantiationException e) {
                }
            }
            outDataset.addSeries(outSeries);
        }
        return outDataset;
    }

    public static void writeTimeSeriesCollection(File file, org.jfree.data.time.TimeSeriesCollection inDataset) throws IOException, MarshalException, ValidationException {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.setXPosition(inDataset.getXPosition().toString());
        dataset.setDomainIsPointsInTime(inDataset.getDomainIsPointsInTime());
        for (int i = 0; i < inDataset.getSeriesCount(); ++i) {
            org.jfree.data.time.TimeSeries serie = inDataset.getSeries(i);
            String name = serie.getName();
            TimeSeries series = new TimeSeries();
            series.setName(name);
            String description = serie.getDescription();
            String rangeDescription = serie.getRangeDescription();
            String domainDescription = serie.getDomainDescription();
            int maximumItemCount = serie.getMaximumItemCount();
            int historyCount = serie.getHistoryCount();
            if (description != null)
                series.setDescription(description);
            if (rangeDescription != null)
                series.setRangeDescription(rangeDescription);
            if (domainDescription != null)
                series.setDomainDescription(domainDescription);
            if (maximumItemCount != Integer.MAX_VALUE)
                series.setMaximumItemCount(maximumItemCount);
            if (historyCount != 0)
                series.setHistoryCount(historyCount);
            for (int j = 0; j < inDataset.getItemCount(i); ++j) {
                org.jfree.data.time.TimeSeriesDataItem inItem = serie.getDataItem(j);
                TimeSeriesItem item = new TimeSeriesItem();
                item.setStart(inItem.getPeriod().getStart().getTime());
                item.setEnd(inItem.getPeriod().getEnd().getTime());
                if (inItem.getValue() != null)
                    item.setValue(inItem.getValue().doubleValue());
                series.addTimeSeriesItem(item);
            }
            dataset.addTimeSeries(series);
        }
        FileWriter output = new FileWriter(file);
        dataset.marshal(output);
        output.flush();
        output.close();
    }

    public static org.jfree.data.gantt.TaskSeriesCollection readTaskSeriesCollection(File file) throws IOException, MarshalException, ValidationException{
        FileReader reader = new FileReader(file);
        TaskSeriesCollection dataset = (TaskSeriesCollection) TaskSeriesCollection.unmarshal(reader);
        reader.close();
        org.jfree.data.gantt.TaskSeriesCollection outDataset = new org.jfree.data.gantt.TaskSeriesCollection();
        Enumeration series=dataset.enumerateTaskSeries();
        while (series.hasMoreElements()) {
            TaskSeries serie =  (TaskSeries) series.nextElement();
            org.jfree.data.gantt.TaskSeries outSeries=new org.jfree.data.gantt.TaskSeries(serie.getName());
            for(int j=0; j<serie.getTaskCount();++j){
                Task task=serie.getTask(j);
                org.jfree.data.gantt.Task outTask=new org.jfree.data.gantt.Task(task.getDescription(),
                        new Date(task.getDuration().getStart()),
                        new Date(task.getDuration().getEnd())
                );
                if(task.hasPercentComplete())
                    outTask.setPercentComplete(task.getPercentComplete());
                for(int k=0; k<task.getSubtaskCount();++k){
                    Subtask subtask=task.getSubtask(k);
                    org.jfree.data.gantt.Task outSubtask=new org.jfree.data.gantt.Task(task.getDescription(),
                        new Date(subtask.getDuration().getStart()),
                        new Date(subtask.getDuration().getEnd())
                    );
                    if(subtask.hasPercentComplete())
                        outSubtask.setPercentComplete(subtask.getPercentComplete());
                    outTask.addSubtask(outSubtask);
                }
                outSeries.add(outTask);
            }
            outDataset.add(outSeries);
        }
        return outDataset;
    }

    public static void writeTaskSeriesCollection(File file, org.jfree.data.gantt.TaskSeriesCollection inDataset) throws IOException, MarshalException, ValidationException{
        TaskSeriesCollection dataset=new TaskSeriesCollection();
        for(int i=0; i<inDataset.getRowCount();++i){
            String serieName=inDataset.getSeriesName(i);
            TaskSeries series=new TaskSeries();
            series.setName(serieName);
            for(int j=0; j<inDataset.getColumnCount();++j){
                Task task=new Task();
                task.setDescription((String) inDataset.getColumnKey(j));
                for(int k=0;k<inDataset.getSubIntervalCount(i,j);++k){
                    Subtask subtask=new Subtask();
                    subtask.setDescription((String) inDataset.getColumnKey(j));
                    if(inDataset.getPercentComplete(i,j,k)!=null)
                        subtask.setPercentComplete(inDataset.getPercentComplete(i,j,k).doubleValue());
                    Duration duration=new Duration();
                    duration.setStart(inDataset.getStartValue(i,j,k).longValue());
                    duration.setEnd(inDataset.getEndValue(i,j,k).longValue());
                    subtask.setDuration(duration);
                    task.addSubtask(subtask);
                }
                if(inDataset.getPercentComplete(i,j)!=null)
                    task.setPercentComplete(inDataset.getPercentComplete(i,j).doubleValue());
                Duration duration=new Duration();
                duration.setStart(inDataset.getStartValue(i,j).longValue());
                duration.setEnd(inDataset.getEndValue(i,j).longValue());
                task.setDuration(duration);
                series.addTask(task);
            }
            dataset.addTaskSeries(series);
        }
        FileWriter output = new FileWriter(file);
        dataset.marshal(output);
        output.flush();
        output.close();
    }
}
