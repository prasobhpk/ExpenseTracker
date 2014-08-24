package com.pk.et.bi.pocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;

import com.ibm.icu.util.ULocale;

public class AddChart {
	private static String BIRT_HOME = "D:/DEV_TOOLS/installed/birt-runtime-4_3_1/ReportEngine";

	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		new AddChart().addMyChart();

	}

	/**
	 * Get the chart instance from the design file and add series grouping key.
	 * 
	 * @return An instance of the simulated runtime chart model (containing
	 *         filled datasets)
	 */
	void addMyChart() {
		ReportDesignHandle designHandle = null;
		final DesignConfig config = new DesignConfig();
		config.setBIRTHome(BIRT_HOME);
		IDesignEngine engine = null;
		try {

			Platform.startup(config);
			final IDesignEngineFactory factory = (IDesignEngineFactory) Platform
					.createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
			engine = factory.createDesignEngine(config);

		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		final SessionHandle sessionHandle = engine
				.newSessionHandle((ULocale) null);

		try {
			designHandle = sessionHandle
					.openDesign("poc_resources/reporttemplate.rpttemplate");//$NON-NLS-1$
			final ElementFactory designFactory = designHandle
					.getElementFactory();
			designHandle.getBody().add(createMyChart(designFactory));
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			designHandle.saveAs("poc_resources/myaddedchart.rptdesign");//$NON-NLS-1$
			System.out.println("Finished");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		Platform.shutdown();

	}

	public static ExtendedItemHandle createMyChart(
			final ElementFactory designFactory) {

		final ExtendedItemHandle eih = designFactory.newExtendedItem(null,
				"Chart");
		ComputedColumn cs1 = null, cs2 = null;
		try {
			eih.setHeight("175pt");//$NON-NLS-1$
			eih.setWidth("450pt");//$NON-NLS-1$
			eih.setProperty(ExtendedItemHandle.DATA_SET_PROP, "ChartData");//$NON-NLS-1$
			eih.setProperty("outputFormat", "PNG");

			final PropertyHandle cs = eih.getColumnBindings();
			cs1 = StructureFactory.createComputedColumn();
			cs2 = StructureFactory.createComputedColumn();
			cs1.setName("xaxis");
			cs1.setDataType("string");
			cs2.setName("yaxis");
			cs2.setDataType("float");

			cs1.setExpression("dataSetRow[\"xaxis\"]");
			cs2.setExpression("dataSetRow[\"yaxis\"]");
			cs.addItem(cs1);
			cs.addItem(cs2);
		} catch (final SemanticException e) {
			e.printStackTrace();
		}

		// BAR CHARTS ARE BASED ON CHARTS THAT CONTAIN AXES

		final ChartWithAxes cwaBar = ChartWithAxesImpl.create();
		cwaBar.setType("Bar Chart"); //$NON-NLS-1$
		cwaBar.setSubType("Side-by-side");
		cwaBar.getBlock().setBounds(BoundsImpl.create(0, 0, 450, 175));

		final SampleData sd = DataFactory.eINSTANCE.createSampleData();

		final BaseSampleData sdBase = DataFactory.eINSTANCE
				.createBaseSampleData();

		sdBase.setDataSetRepresentation("Category-A, Category-B");//$NON-NLS-1$

		sd.getBaseSampleData().add(sdBase);

		final OrthogonalSampleData sdOrthogonal = DataFactory.eINSTANCE
				.createOrthogonalSampleData();

		sdOrthogonal.setDataSetRepresentation("4,12");//$NON-NLS-1$

		sdOrthogonal.setSeriesDefinitionIndex(0);

		sd.getOrthogonalSampleData().add(sdOrthogonal);

		cwaBar.setSampleData(sd);

		cwaBar.getBlock().setBackground(

		ColorDefinitionImpl.WHITE()

		);

		cwaBar.getBlock().getOutline().setVisible(true);

		cwaBar.setDimension(

		ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL

		);

		// CUSTOMIZE THE PLOT

		final Plot p = cwaBar.getPlot();

		p.getClientArea().setBackground(

		ColorDefinitionImpl.create(255, 255, 225)

		);

		p.getOutline().setVisible(false);

		cwaBar.getTitle().getLabel().getCaption().setValue(

		"Simple Bar Chart"

		);

		// CUSTOMIZE THE LEGEND

		final Legend lg = cwaBar.getLegend();

		lg.getText().getFont().setSize(16);

		lg.getInsets().set(10, 5, 0, 0);

		lg.setAnchor(Anchor.NORTH_LITERAL);

		// CUSTOMIZE THE X-AXIS

		final Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];

		xAxisPrimary.setType(AxisType.TEXT_LITERAL);

		xAxisPrimary.getMajorGrid().setTickStyle(

		TickStyle.BELOW_LITERAL

		);

		xAxisPrimary.getOrigin().setType(

		IntersectionType.VALUE_LITERAL

		);

		xAxisPrimary.getTitle().setVisible(false);

		// CUSTOMIZE THE Y-AXIS

		final Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(

		xAxisPrimary

		);

		yAxisPrimary.getMajorGrid().setTickStyle(

		TickStyle.LEFT_LITERAL

		);

		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);

		yAxisPrimary.getLabel().getCaption().getFont().setRotation(90);

		// INITIALIZE A COLLECTION WITH THE X-SERIES DATA

		final Vector vs = new Vector();

		vs.add("zero");

		vs.add("one");

		vs.add("two");

		final TextDataSet categoryValues = TextDataSetImpl.create(vs);

		// INITIALIZE A COLLECTION WITH THE Y-SERIES DATA

		final ArrayList vn1 = new ArrayList();

		vn1.add(new Double(25));

		vn1.add(new Double(35));

		vn1.add(new Double(-45));

		final NumberDataSet orthoValues1 = NumberDataSetImpl.create(

		vn1

		);

		// CREATE THE CATEGORY BASE SERIES

		final Series seCategory = SeriesImpl.create();
		final Query xQ = QueryImpl.create("row[\"xaxis\"]");
		seCategory.getDataDefinition().add(xQ);
		// seCategory.setDataSet(categoryValues);

		// CREATE THE VALUE ORTHOGONAL SERIES

		final BarSeries bs1 = (BarSeries) BarSeriesImpl.create();

		bs1.setSeriesIdentifier("My Bar Series");

		// bs1.setDataSet(orthoValues1);
		final Query yQ = QueryImpl.create("row[\"yaxis\"]");
		bs1.getDataDefinition().add(yQ);
		bs1.setRiserOutline(null);

		bs1.getLabel().setVisible(true);

		bs1.setLabelPosition(Position.INSIDE_LITERAL);

		// WRAP THE BASE SERIES IN THE X-AXIS SERIES DEFINITION

		final SeriesDefinition sdX = SeriesDefinitionImpl.create();

		sdX.getSeriesPalette().update(0); // SET THE COLORS IN THE PALETTE

		xAxisPrimary.getSeriesDefinitions().add(sdX);

		sdX.getSeries().add(seCategory);

		// WRAP THE ORTHOGONAL SERIES IN THE X-AXIS SERIES DEFINITION

		final SeriesDefinition sdY = SeriesDefinitionImpl.create();

		sdY.getSeriesPalette().update(1); // SET THE COLOR IN THE PALETTE

		yAxisPrimary.getSeriesDefinitions().add(sdY);

		sdY.getSeries().add(bs1);

		try {
			// Add ChartReportItemImpl to ExtendedItemHandle
			eih.getReportItem().setProperty("chart.instance", cwaBar);
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}

		return eih;

	}
}
