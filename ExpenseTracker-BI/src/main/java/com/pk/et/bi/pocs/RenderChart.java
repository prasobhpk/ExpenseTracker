package com.pk.et.bi.pocs;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.image.PngRendererImpl;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IDataExtractionTask;
import org.eclipse.birt.report.engine.api.IDataIterator;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IResultSetItem;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.w3c.dom.DOMException;
import org.xml.sax.InputSource;

import com.ibm.icu.util.ULocale;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;

public class RenderChart {
	private static String BIRT_HOME = "D:/DEV_TOOLS/installed/birt-runtime-4_3_1/ReportEngine";

	/**
	 * Creates the X series for the Chart
	 * 
	 * @param uniqueMap
	 * @return
	 */
	public static TextDataSet createCategoriesDataSet(final Map uniqueMap) {
		final String[] categories = new String[uniqueMap.keySet().size()];
		int x = 0;
		for (final Iterator keyIt = uniqueMap.keySet().iterator(); keyIt
				.hasNext();) {
			categories[x] = (String) keyIt.next();
			x++;
		}
		return TextDataSetImpl.create(categories);
	}

	/**
	 * Creates the Y Series for a chart
	 * 
	 * @param uniqueMap
	 * @return
	 */
	public static NumberDataSet createValueDataSet(final Map uniqueMap) {
		final double[] values = new double[uniqueMap.values().size()];
		int x = 0;
		for (final Iterator valIt = uniqueMap.values().iterator(); valIt
				.hasNext();) {
			values[x] = Math.round(((Double) valIt.next()).doubleValue());
			x++;
		}

		return NumberDataSetImpl.create(values);
	}

	/**
	 * Will open a report design and return the Document instance
	 * 
	 * @param reportName
	 * @return
	 */
	public static IReportDocument executeReport(final String reportName)
			throws EngineException {
		// create a new report engine factory
		final IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

		// create a new report engine
		final EngineConfig engineConfig = new EngineConfig();
		engineConfig.setBIRTHome(BIRT_HOME); // will replace with
												// configuration file
		final IReportEngine engine = factory.createReportEngine(engineConfig);

		// create the report task and open the report design file
		final IReportRunnable design = engine.openReportDesign(reportName);
		final IRunTask runTask = engine.createRunTask(design);

		// use the default locale
		runTask.setLocale(Locale.getDefault());

		// run and close the report run task
		final File newTempFile = new File("C:/TEMP/birtRenderTemp",
				"test.rptDocument");
		final String tempFileLocation = newTempFile.getAbsolutePath();

		// delete the temp file, we just needed the name and path
		newTempFile.delete();

		runTask.run(tempFileLocation);
		runTask.close();

		final IReportDocument ird = engine
				.openReportDocument("C:/TEMP/birtRenderTemp/test.rptDocument");

		return ird;
	}

	/**
	 * Creates an input source from the XMLProperties string
	 * 
	 * @param xmlProperties
	 * @return
	 */
	public static InputSource getInputSourceFromString(
			final String xmlProperties) {
		final StringBufferInputStream is = new StringBufferInputStream(
				xmlProperties);

		return new InputSource(is);
	}

	/**
	 * Using a data extraction task, this will build a simple Map of columns and
	 * rows This method assumes a 1-1 relation between what will be the x value
	 * and y value
	 * 
	 * @param det
	 * @param ySeries
	 * @param xSeries
	 * @return
	 * @throws BirtException
	 */
	public static Map getValueMap(final IDataExtractionTask det,
			final String ySeries, final String xSeries) throws BirtException {
		// extract the results from the task
		final IExtractionResults iExtractResults = det.extract();
		IDataIterator iData = null;

		// hashmap to return
		final Map uniqueMap = new HashMap();

		// if we have results, process them, otherwise, don't
		if (iExtractResults != null) {
			iData = iExtractResults.nextResultIterator();

			// iterate through the results
			if (iData != null) {
				// loop while there is still data in out iterator
				while (iData.next()) {
					Object objColumn1;
					Object objColumn2;
					try {
						objColumn1 = iData.getValue(ySeries);
					} catch (final DataException e) {
						objColumn1 = new String("");
					}
					try {
						objColumn2 = iData.getValue(xSeries);
					} catch (final DataException e) {
						objColumn2 = new String("");
					}

					Double newNumber = (Double) objColumn1;
					if (uniqueMap.keySet().contains(objColumn2)) {
						newNumber += (Double) uniqueMap.get(objColumn2);
					}
					uniqueMap.put(objColumn2, newNumber);
				}
				iData.close();
			}
		}

		// close the data extraction task and return our value map
		det.close();
		return uniqueMap;
	}

	/**
	 * Extract the XML resources from the extended item handle, used to get the
	 * XML representation of the chart from a report
	 * 
	 * @param eih
	 * @return
	 */
	public static String getXMLResources(final ExtendedItemHandle eih) {
		return (String) eih.getProperty("xmlRepresentation");
	}

	/**
	 * Retrieves the name of the X Series column from a chart XMLRepresentation
	 * 
	 * @param xmlProperties
	 * @return
	 * @throws XPathExpressionException
	 * @throws DOMException
	 */
	public static String getXSeries(final String xmlProperties)
			throws XPathExpressionException, DOMException {
		final InputSource inputSource = getInputSourceFromString(xmlProperties);
		final XPath xpath = XPathFactory.newInstance().newXPath();

		final DTMNodeList nodeList = (DTMNodeList) xpath.evaluate(
				"//SeriesDefinitions/Series/DataDefinition/Definition",
				inputSource, XPathConstants.NODESET);

		final String xSeries = nodeList.item(1).getTextContent();

		// we found the series definitions, now remove the expression and just
		// return
		// the key
		return xSeries.replace("row[\"", "").replace("\"]", "");
	}

	/**
	 * Retrieves the name of the Y Series column from a chart XMLRepresentation
	 * 
	 * @param xmlProperties
	 * @return
	 * @throws XPathExpressionException
	 * @throws DOMException
	 */
	public static String getYSeries(final String xmlProperties)
			throws XPathExpressionException, DOMException {
		final InputSource inputSource = getInputSourceFromString(xmlProperties);
		final XPath xpath = XPathFactory.newInstance().newXPath();

		final DTMNodeList nodeList = (DTMNodeList) xpath.evaluate(
				"//SeriesDefinitions/Series/DataDefinition/Definition",
				inputSource, XPathConstants.NODESET);

		final String ySeries = nodeList.item(0).getTextContent();

		return ySeries.replace("row[\"", "").replace("\"]", "");
	}

	/**
	 * Will render a Pie chart with given categories and values. Chart needs to
	 * be created beforehand, so pull from document first.
	 * 
	 * @param cwa
	 * @param categories
	 * @param values
	 * @throws ChartException
	 */
	public static void renderPieChart(final ChartWithoutAxes cwa,
			final TextDataSet categories, final NumberDataSet values)
			throws ChartException {
		// Create the png renderer
		final IDeviceRenderer idr = new PngRendererImpl();

		// create new run time context
		final RunTimeContext rtc = new RunTimeContext();
		rtc.setULocale(ULocale.getDefault());

		// create a new generator
		final Generator gr = Generator.instance();
		GeneratedChartState gcs = null;

		// clear any existing series definitions since we created out own
		cwa.getSeriesDefinitions().clear();

		// Plot the chart...
		/*
		 * Note: I commented this stuff out since I already designed my chart
		 * look and feel in the BIRT report designer, and all of that will
		 * already be set in my chart opened from a BIRT Report
		 * cwa.setSeriesThickness(25);
		 * cwa.getBlock().setBackground(ColorDefinitionImpl.WHITE()); Plot p =
		 * cwa.getPlot();
		 * cwa.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
		 * 
		 * p.getClientArea().setBackground(null);
		 * p.getClientArea().getOutline().setVisible(true);
		 * p.getOutline().setVisible(true);
		 * 
		 * Legend lg = cwa.getLegend(); lg.getText().getFont().setSize(16);
		 * lg.setBackground(null); lg.getOutline().setVisible(true);
		 * 
		 * // Title //cwa.getTitle( ).getLabel( ).getCaption( ).setValue(
		 * "Pie Chart" );//$NON-NLS-1$ cwa.getTitle( ).getOutline( ).setVisible(
		 * true );
		 */

		// define base series
		final Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categories);

		final SeriesDefinition sd = SeriesDefinitionImpl.create();
		sd.getSeriesPalette().shift(0);
		sd.getSeries().add(seCategory);
		cwa.getSeriesDefinitions().add(sd);

		// define pie seies
		final PieSeries categorySeries = (PieSeries) PieSeriesImpl.create();
		categorySeries.setDataSet(values);
		categorySeries.setSeriesIdentifier("Territories");

		final SeriesDefinition sdValues = SeriesDefinitionImpl.create();
		sdValues.getQuery().setDefinition("Census.Territories");//$NON-NLS-1$
		sdValues.getSeries().add(categorySeries);
		sdValues.getSeries().add(categorySeries);
		sd.getSeriesDefinitions().add(sdValues);

		// Set the chart size
		final Bounds bo = BoundsImpl.create(0, 0, 350, 275);

		// Now build the chart
		gcs = gr.build(idr.getDisplayServer(), cwa, bo, null, rtc, null);

		// Specify the file to write to.
		idr.setProperty(IDeviceRenderer.FILE_IDENTIFIER, "test.png"); //$NON-NLS-1$

		// generate the chart
		gr.render(idr, gcs);
	}

	/**
	 * Main: The delegator of out program. Starts the BIRT Platform, gets chart,
	 * extracts values, and renders chart
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			// start up the platform
			// note: needed to add STANDALONE = true, otherwise the chart engine
			// would not work.
			final PlatformConfig platformConfig = new PlatformConfig();
			platformConfig.setBIRTHome(BIRT_HOME);
			// standalone platform
			platformConfig.setProperty("STANDALONE", "true");
			ChartEngine.instance(platformConfig);
			Platform.startup(platformConfig);

			// create a new report engine factory
			final IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

			// create a new report engine
			final EngineConfig engineConfig = new EngineConfig();
			engineConfig.setBIRTHome(BIRT_HOME);
			final IReportEngine engine = factory
					.createReportEngine(engineConfig);

			// open the given report and create a new Data Extraction task to
			// get data from run report
			final IReportDocument ird = executeReport("C:/contracts/GWTBirt/BIRTGwt/src/reports/Charts/TerritorySalesPieChart.rptdesign");
			final IDataExtractionTask det = engine
					.createDataExtractionTask(ird);

			// get the report runnable from the document so we can grab the
			// chart information from the report
			final IReportRunnable r = ird.getReportRunnable();
			final ReportDesignHandle rh = (ReportDesignHandle) r
					.getDesignHandle();

			// for each element in the report (assuming only charts), go through
			// and grab info, then render to PNG
			for (final Iterator i = rh.getBody().getContents().iterator(); i
					.hasNext();) {
				final Object o = i.next();

				// make sure this is an extended item handle
				if (o instanceof ExtendedItemHandle) {
					final ExtendedItemHandle eih = (ExtendedItemHandle) o;

					// read in the XML Representation for getting the data
					// definitions from the chart, get the values of the X and Y
					// axis
					final String xSeries = getXSeries(getXMLResources(eih));
					final String ySeries = getYSeries(getXMLResources(eih));

					// Look into using serializer to grab from a report design.
					// alternative of doing this, although a little uglier
					// ChartEngine.instance().getSerializer().fromXml(arg0,
					// arg1)

					final ChartWithoutAxes cwa = (ChartWithoutAxes) eih
							.getReportItem().getProperty("chart.instance");

					// Get list of result sets
					final ArrayList resultSetList = (ArrayList) det
							.getResultSetList();

					// we know out data is in the first result set
					final IResultSetItem resultItem = (IResultSetItem) resultSetList
							.get(0);
					final String dispName = resultItem.getResultSetName();

					// tell the data extraction task to use the first result set
					det.selectResultSet(dispName);

					// retrieves the dataset with column/values as unique to map
					// to Pie Chart
					final Map uniqueMap = getValueMap(det, ySeries, xSeries);

					// crete the category, or X series, and values, or y series
					final TextDataSet categoryValues = createCategoriesDataSet(uniqueMap);
					final NumberDataSet seriesOneValues = createValueDataSet(uniqueMap);

					// now that we have data and our chart, render to image
					renderPieChart(cwa, categoryValues, seriesOneValues);
				}
			}
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		} catch (final XPathExpressionException e) {
			e.printStackTrace();
		} catch (final DOMException e) {
			e.printStackTrace();
		} catch (final EngineException e) {
			e.printStackTrace();
		} catch (final ChartException e) {
			e.printStackTrace();
		} catch (final BirtException e) {
			e.printStackTrace();
		}

		// shutdown the platform, we are done
		Platform.shutdown();
	}
}