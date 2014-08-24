package com.pk.et.bi.pocs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.LibraryHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;

import com.ibm.icu.util.ULocale;

public class ChartBuilder7 {
	private static String BIRT_HOME = "D:/DEV_TOOLS/installed/birt-runtime-4_3_1/ReportEngine";

	public static void main(final String[] args) {
		try {
			final ChartBuilder7 cb = new ChartBuilder7();

			cb.buildReport();
			cb.runReport();
			cb.renderReport();
			// cb.renderReportlet();

			cb.shutdown();
		} catch (final ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final DesignFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final BirtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// factories and engines used in program
	IDesignEngine dengine;
	IDesignEngineFactory designFactory;
	IReportEngine rengine; // stores the single instance of the report engine

	SessionHandle session;

	public ChartBuilder7() throws BirtException {
		final PlatformConfig platformConfig = new PlatformConfig();
		platformConfig.setBIRTHome(BIRT_HOME);
		Platform.startup(platformConfig);

		// create a new report engine factory
		final IReportEngineFactory reportFactory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

		// create a new report engine
		final EngineConfig engineConfig = new EngineConfig();
		engineConfig.setBIRTHome(BIRT_HOME); // will replace with
		// configuration file
		engineConfig.setResourcePath("C:/temp");
		this.rengine = reportFactory.createReportEngine(engineConfig);

		final DesignConfig dconfig = new DesignConfig();
		dconfig.setBIRTHome(BIRT_HOME);

		// try to start up the eclipse platform
		this.designFactory = (IDesignEngineFactory) Platform
				.createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
		this.dengine = this.designFactory.createDesignEngine(dconfig);

		// create a new session, open the library, and retrieve the first data
		// source since it is uniform in our library
		this.session = this.dengine.newSessionHandle(ULocale.ENGLISH);

	}

	/**
	 * Adds data binding information to chart retrieved from library
	 * 
	 * @param eih
	 */

	public void addPieChartFromLibrary(final ExtendedItemHandle eih) {
		try {
			eih.setHeight("5.406in");
			eih.setWidth("7.927in");
			// create chart and set standard options
			final ChartWithoutAxes cwaPie = (ChartWithoutAxes) eih
					.getReportItem().getProperty("chart.instance");

			// clear any existing definitions from the chart
			cwaPie.getSeriesDefinitions().clear();

			// Set the dimension
			cwaPie.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);
			cwaPie.setType("Pie Chart");
			cwaPie.setSubType("Standard Pie Chart");
			cwaPie.getBlock().getBounds().setWidth(500);
			cwaPie.getBlock().getBounds().setHeight(400);
			// Plot
			cwaPie.setSeriesThickness(10);

			// Legend
			final Legend lg = cwaPie.getLegend();
			lg.getOutline().setVisible(true);

			lg.getTitle().setVisible(true);
			lg.getTitle().getCaption().setValue("Groups");

			// Title
			cwaPie.getTitle().getLabel().getCaption().setValue("Pie Chart");//$NON-NLS-1$
			// create actual series data. This will not be in the library chart
			final Series seCategory = SeriesImpl.create();

			// This is where things differ from standalone charts. A Query is an
			// EList
			// that contains an expression for data. Keep in mind we need to set
			// out
			// data set below, otherwise setting these expressions are pointless
			final Query query = QueryImpl.create("row[\"PRODUCTCODE\"]");//$NON-NLS-1$
			// When creating a series, getDataDefinition() is expecting the
			// EList,
			// and the Query is an instance of that
			seCategory.getDataDefinition().add(query);

			final SeriesDefinition series = SeriesDefinitionImpl.create();
			series.getSeries().add(seCategory);
			cwaPie.getSeriesDefinitions().add(series);
			// to change the color of the next slice
			// series.getSeriesPalette().shift(1);

			// Orthogonal Series
			final PieSeries sePie = (PieSeries) PieSeriesImpl.create();
			final Query query2 = QueryImpl.create("row[\"QUANTITYORDERED\"]");
			sePie.getDataDefinition().add(query2);
			// This defines the amount by which the slices are displaced from
			// the center of the pie
			sePie.setExplosion(5);

			final SeriesDefinition sdEmpCount = SeriesDefinitionImpl.create();
			// sdEmpCount.getSeriesPalette().shift(2);
			series.getSeriesDefinitions().add(sdEmpCount);
			sdEmpCount.getSeries().add(sePie);

			// add to report
			// eih.loadExtendedElement(); // this may or may not need to
			// be called - it's typically used for reading (if already in the
			// rpt design file)
			// eih.getReportItem().setProperty("chart.instance", cwaPie);
			eih.setProperty("outputFormat", "PNG");
			eih.setProperty("dataSet", "newdt"); // tell the chart
													// which dataset to
													// use to bind the
													// chart to the
													// report

			// do the bindings to the extended element handle
			final PropertyHandle computedSet = eih.getColumnBindings();
			final ComputedColumn cs1 = new ComputedColumn();
			cs1.setExpression("dataSetRow[\"PRODUCTCODE\"]");//$NON-NLS-1$
			cs1.setName("OrgName");
			cs1.setDataType("string");
			computedSet.addItem(cs1);

			final ComputedColumn cs2 = new ComputedColumn();
			cs2.setExpression("dataSetRow[\"QUANTITYORDERED\"]");//$NON-NLS-1$
			cs2.setName("Candidates");
			cs2.setDataType("integer");
			computedSet.addItem(cs2);
		} catch (final Exception E) {
			E.printStackTrace();
		}
	}

	/**
	 * Build report
	 * 
	 * @throws ContentException
	 * @throws NameException
	 * @throws SemanticException
	 * @throws DesignFileException
	 * @throws IOException
	 */
	public void buildReport() throws ContentException, NameException,
			SemanticException, DesignFileException, IOException {
		// create a new report

		final ReportDesignHandle reportDesign = this.session.createDesign();
		final ElementFactory ef = reportDesign.getElementFactory();

		// add new master page element
		final DesignElementHandle element = ef
				.newSimpleMasterPage("Page Master");
		reportDesign.getMasterPages().add(element);

		// get the session, open the library
		final SessionHandle session = this.dengine
				.newSessionHandle(ULocale.ENGLISH);
		final LibraryHandle library = session
				.openLibrary("poc_resources/chart_library.rptlibrary");

		// Add library to the report
		reportDesign.includeLibrary("poc_resources/chart_library.rptlibrary",
				"mylib");
		// get the data source, data set and pie chart from library
		final DesignElementHandle dataSourceHandleFromLib = library
				.findDataSource("Data Source");
		final DesignElementHandle dataSetHandleFromLib = library
				.findDataSet("Data Set");
		final DesignElementHandle pieChartHandleFromLib = library
				.findElement("PieChart");

		// create data source , data set and pie chart on report using the lib
		// items
		final DesignElementHandle dataSourceHandle = reportDesign
				.getElementFactory().newElementFrom(dataSourceHandleFromLib,
						"newds");
		final DesignElementHandle dataSetHandle = reportDesign
				.getElementFactory().newElementFrom(dataSetHandleFromLib,
						"newdt");

		// add the data sources and data sets to new report
		reportDesign.getDataSources().add(dataSourceHandle);
		reportDesign.getDataSets().add(dataSetHandle);

		// create a new extended item handle and add. This is read in the
		// library
		// so we do not need to manually create visual elements. Then add to the
		// body of our
		// new report design
		final ExtendedItemHandle pieChartHandle = (ExtendedItemHandle) reportDesign
				.getElementFactory().newElementFrom(pieChartHandleFromLib,
						"newPieChart");
		reportDesign.getBody().add(pieChartHandle);

		// modify the chart with data bindings so that it is bound to our new
		// report design
		this.addPieChartFromLibrary(pieChartHandle);

		// add some sample labels
		final LabelHandle lh = ef.newLabel("New Label");
		lh.setText("Can you see me?");
		reportDesign.getBody().add(lh);

		// set my initial properties for the new report
		final String reportName = "Example Chart";
		reportDesign.setDisplayName(reportName);
		reportDesign.setDescription(reportName);
		reportDesign.setIconFile("/templates/blank_report.gif");
		reportDesign.setFileName("C:/Temp/" + reportName + ".rptdesign");
		reportDesign.setDefaultUnits("in");
		reportDesign.setProperty("comments", reportName);
		reportDesign.setProperty(IReportRunnable.TITLE, reportName);

		// save report design
		reportDesign.saveAs("C:/Temp/" + reportName + ".rptdesign");
	}

	/**
	 * Render full report
	 */
	public void renderReport() {
		try {
			final IReportDocument rptdoc = this.rengine
					.openReportDocument("C:/temp/tempDoc.rptDocument");

			// Create Render Task
			final IRenderTask rtask = this.rengine.createRenderTask(rptdoc);

			// Set Render context to handle url and image locataions
			final HTMLRenderContext renderContext = new HTMLRenderContext();
			// Set the Base URL for all actions
			renderContext.setBaseURL("http://localhost/");
			// Tell the Engine to prepend all images with this URL - Note this
			// requires using the HTMLServerImageHandler
			renderContext.setBaseImageURL("http://localhost/myimages");
			// Tell the Engine where to write the images to
			renderContext.setImageDirectory("C:/xampplite/htdocs/myimages");
			// Tell the Engine what image formats are supported. Note you must
			// have SVG in the string
			// to render charts in SVG.
			renderContext.setSupportedImageFormats("JPG;PNG;BMP;SVG");
			final HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
			contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
					renderContext);
			rtask.setAppContext(contextMap);

			// Set rendering options - such as file or stream output,
			// output format, whether it is embeddable, etc
			final HTMLRenderOption options = new HTMLRenderOption();

			// Remove HTML and Body tags
			// options.setEmbeddable(true);

			// Set ouptut location
			options.setOutputFileName("C:/temp/outputReport.html");

			// Set output format
			options.setOutputFormat("html");
			rtask.setRenderOption(options);

			rtask.render();

			// render the report and destroy the engine
			// Note - If the program stays resident do not shutdown the Platform
			// or the Engine
			rtask.close();
		} catch (final EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * render just a reportlet
	 */
	public void renderReportlet() {
		try {
			final IReportDocument rptdoc = this.rengine
					.openReportDocument("C:/temp/tempDoc.rptDocument");

			// Create Render Task
			final IRenderTask rtask = this.rengine.createRenderTask(rptdoc);

			// Set Render context to handle url and image locataions
			final HTMLRenderContext renderContext = new HTMLRenderContext();
			// Set the Base URL for all actions
			renderContext.setBaseURL("http://localhost/");
			// Tell the Engine to prepend all images with this URL - Note this
			// requires using the HTMLServerImageHandler
			renderContext.setBaseImageURL("http://localhost/myimages");
			// Tell the Engine where to write the images to
			renderContext.setImageDirectory("C:/xampplite/htdocs/myimages");
			// Tell the Engine what image formats are supported. Note you must
			// have SVG in the string
			// to render charts in SVG.
			renderContext.setSupportedImageFormats("JPG;PNG;BMP;SVG");
			final HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
			contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
					renderContext);
			rtask.setAppContext(contextMap);

			// Set rendering options - such as file or stream output,
			// output format, whether it is embeddable, etc
			final HTMLRenderOption options = new HTMLRenderOption();

			// Remove HTML and Body tags
			// options.setEmbeddable(true);

			// Set ouptut location
			options.setOutputFileName("C:/temp/outputReportlet.html");

			// Set output format
			options.setOutputFormat("html");
			rtask.setRenderOption(options);

			rtask.setReportlet("ChartToRender");
			rtask.render();

			// render the report and destroy the engine
			// Note - If the program stays resident do not shutdown the Platform
			// or the Engine
			rtask.close();
		} catch (final EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Run report
	 */
	public void runReport() {
		try {
			final IReportRunnable design = this.rengine
					.openReportDesign("C:/Temp/" + "Example Chart"
							+ ".rptdesign");
			final IRunTask runTask = this.rengine.createRunTask(design);

			// use the default locale
			runTask.setLocale(Locale.getDefault());

			runTask.run("C:/temp/tempDoc.rptDocument");
			runTask.close();
		} catch (final EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdown() {
		Platform.shutdown();
	}
}
