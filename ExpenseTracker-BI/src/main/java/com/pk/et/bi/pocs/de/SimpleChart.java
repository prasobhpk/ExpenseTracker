package com.pk.et.bi.pocs.de;

import java.io.IOException;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.PaletteImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.structures.PropertyBinding;

import com.ibm.icu.util.ULocale;


public class SimpleChart

{

    private ReportDesignHandle reportDesignHandle = null;

    private ElementFactory elementFactory = null;

    private OdaDataSourceHandle odaDataSourceHandle = null;

    private String dataSourceName = "datasource";

    private String dataSetName = "maindataset";
    private SessionHandle sessionHandle =null;

	org.eclipse.birt.report.model.api.elements.structures.ComputedColumn cs1, cs2 = null;

    public static void main(String args[])

    {
        try {

            new SimpleChart().createReport();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void createReport() throws SemanticException, IOException

    {
        System.out.println("Start");
        init();

        createMasterPages();

        buildDataSource();
        buildDataSet();        
        createBody();
        
        reportDesignHandle.saveAs("output/desample/simplechart.rptdesign");  
        reportDesignHandle.close( );
		Platform.shutdown();
		System.out.println("Finished");        

    }


    private void init(){


		DesignConfig config = new DesignConfig( );
		config.setBIRTHome("C:/birt/birt-runtime-2_6_2/birt-runtime-2_6_2/ReportEngine");
		IDesignEngine engine = null;

        try {

            Platform.startup(config);

            IDesignEngineFactory factory = (IDesignEngineFactory) Platform

            .createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);

            engine = factory.createDesignEngine(config);

        } catch (Exception ex) {

            ex.printStackTrace();

        }


        // we need a handle of session of design engine

        sessionHandle = engine.newSessionHandle(ULocale.ENGLISH);
        reportDesignHandle = sessionHandle.createDesign();
        elementFactory = reportDesignHandle.getElementFactory();

    }


    private void createMasterPages() throws ContentException, NameException

    {

        SimpleMasterPageHandle simpleMasterPage =
            elementFactory.newSimpleMasterPage("Master Page");

        reportDesignHandle.getMasterPages().add(simpleMasterPage);

    }

	void buildDataSource( ) throws SemanticException
	{

		OdaDataSourceHandle dsHandle = elementFactory.newOdaDataSource(
				"Data Source", "org.eclipse.birt.report.data.oda.jdbc" );
		dsHandle.setProperty( "odaDriverClass",
		"org.eclipse.birt.report.data.oda.sampledb.Driver" );
		dsHandle.setProperty( "odaURL", "jdbc:classicmodels:sampledb" );
		dsHandle.setProperty( "odaUser", "ClassicModels" );
		dsHandle.setProperty( "odaPassword", "" );
		
		PropertyBinding pb = new PropertyBinding();

		reportDesignHandle.getDataSources( ).add( dsHandle );

	}

	void buildDataSet( ) throws SemanticException
	{

		OdaDataSetHandle dsHandle = elementFactory.newOdaDataSet( dataSetName,
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle.setDataSource( "Data Source" );
		String qry = "Select PRODUCTCODE, QUANTITYORDERED from orderdetails where ordernumber = 10104";

		dsHandle.setQueryText( qry );		
		reportDesignHandle.getDataSets( ).add( dsHandle );

		


	}

    private void createBody() throws SemanticException

    {
        ExtendedItemHandle extendedItemHandle = elementFactory.newExtendedItem("Simple Chart", "Chart");
        extendedItemHandle.setWidth("700px");
        extendedItemHandle.setHeight("500px");
        extendedItemHandle.setProperty(ExtendedItemHandle.DATA_SET_PROP, dataSetName);
        extendedItemHandle.setProperty("outputFormat","PNG");


    
        Chart c = createChart();

        extendedItemHandle.getReportItem().setProperty( "chart.instance", c );

        reportDesignHandle.getBody().add(extendedItemHandle);

      
		//PropertyHandle computedSet = extendedItemHandle.getColumnBindings( );
		//computedSet.clearValue();
		
		cs1 = StructureFactory.createComputedColumn( );
		cs1.setName( "PRODUCTCODE" );
		cs1.setExpression( "dataSetRow[\"PRODUCTCODE\"]");
		cs1.setDataType( "string" );
		cs1.setAggregateOn(null);
		
		
		cs2 = StructureFactory.createComputedColumn( );
		cs2.setName( "QUANTITYORDERED" );
		cs2.setExpression( "dataSetRow[\"QUANTITYORDERED\"]");
		cs2.setDataType( "integer" );		
		
		extendedItemHandle.addColumnBinding(cs1, true);
		extendedItemHandle.addColumnBinding(cs2, true);
		

    }

    private Chart createChart() {

        ChartWithAxes cwaLine = ChartWithAxesImpl.create();
        cwaLine.setType( "Line Chart" ); //$NON-NLS-1$
        cwaLine.setSubType( "Overlay" ); //$NON-NLS-1$
        cwaLine.getBlock().getBounds().setWidth(600);
        cwaLine.getBlock().getBounds().setHeight(400);

        
        // Plot
        cwaLine.getBlock().setBackground( ColorDefinitionImpl.WHITE() );
        Plot p = cwaLine.getPlot();
        p.getClientArea().setBackground( ColorDefinitionImpl.create( 255, 255,
                225 ) );

        // Title
        cwaLine.getTitle().getLabel().getCaption().setValue("Overlay test Line Chart" );
        cwaLine.getTitle().setVisible(true);

        // Legend
        cwaLine.getLegend().setVisible( true );
		Legend lg = cwaLine.getLegend();
		lg.setItemType(LegendItemType.CATEGORIES_LITERAL);

        // X-Axis
        Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
        xAxisPrimary.setType( AxisType.TEXT_LITERAL );
        xAxisPrimary.getMajorGrid().setTickStyle( TickStyle.BELOW_LITERAL );
        xAxisPrimary.getOrigin().setType( IntersectionType.MIN_LITERAL );

        // Y-Axis
        Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis( xAxisPrimary );
        yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
        yAxisPrimary.getMajorGrid().setTickStyle( TickStyle.RIGHT_LITERAL );
        yAxisPrimary.getLabel().getCaption().setValue("TEST");
        yAxisPrimary.getLabel().setVisible(true);
   
		SampleData sd = DataFactory.eINSTANCE.createSampleData( );
		BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData( );
		sdBase.setDataSetRepresentation( "Category-A, Category-B" );//$NON-NLS-1$
		sd.getBaseSampleData( ).add( sdBase );


		OrthogonalSampleData sdOrthogonal = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal.setDataSetRepresentation( "4,12" );//$NON-NLS-1$
		sdOrthogonal.setSeriesDefinitionIndex( 0 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal );

		cwaLine.setSampleData( sd );

        // X-Series


        
		Series seCategory = SeriesImpl.create( );
		// seCategory.setDataSet( categoryValues );
		
		// Set category expression.
		seCategory.getDataDefinition( )
				.add( QueryImpl.create( "row[\"PRODUCTCODE\"]" ) );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		Palette palx = PaletteImpl.create(10, false);
		//sdY.getSeriesPalette( ).shift(1);
		sdX.setSeriesPalette(palx);
		
		
		//sdX.getSeriesPalette( ).shift( 1 );
        //sdX.setSorting(SortOption.ASCENDING_LITERAL);
		// Set default grouping.
		//SeriesGrouping grouping = sdX.getGrouping( );
		//grouping.getAggregateExpression();
		//grouping.setEnabled( false );
		//grouping.setGroupType( DataType.TEXT_LITERAL );
		//grouping.setGroupingUnit( GroupingUnitType.STRING_PREFIX_LITERAL );
		//grouping.setGroupingInterval( 1 );
		//grouping.setAggregateExpression( "Sum" ); // Set Count aggregation. //$NON-NLS-1$

		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );
		// Y-Series
		LineSeries bs1 = (LineSeries) LineSeriesImpl.create( );
	
		bs1.getDataDefinition( ).add( QueryImpl.create( "row[\"QUANTITYORDERED\"]" ) );
		bs1.getLabel( ).setVisible( true );
		

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );		
		//Palette pal = PaletteImpl.create(10, false);
		//sdY.getSeriesPalette( ).shift(1);
		//sdY.setSeriesPalette(pal);
		
		sdY.getGrouping().setEnabled(false);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		
		sdY.getSeries( ).add( bs1 );        
        

        return cwaLine;
    }
} 