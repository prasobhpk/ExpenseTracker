package com.pk.et.bi.pocs.de;


import java.io.IOException;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.item.crosstab.core.ICrosstabConstants;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabCellHandle;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabReportItemHandle;
import org.eclipse.birt.report.item.crosstab.core.de.DimensionViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.LevelViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.MeasureViewHandle;
import org.eclipse.birt.report.item.crosstab.core.util.CrosstabExtendedItemFactory;
import org.eclipse.birt.report.model.api.ComputedColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DataSetHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.olap.MeasureGroupHandle;
import org.eclipse.birt.report.model.api.olap.MeasureHandle;
import org.eclipse.birt.report.model.api.olap.TabularCubeHandle;
import org.eclipse.birt.report.model.api.olap.TabularDimensionHandle;
import org.eclipse.birt.report.model.api.olap.TabularHierarchyHandle;
import org.eclipse.birt.report.model.api.olap.TabularLevelHandle;
import org.eclipse.birt.report.model.elements.interfaces.ICubeModel;

import com.ibm.icu.util.ULocale;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class CreateDataCube
{
	ReportDesignHandle designHandle = null;
	ElementFactory designFactory = null;
	StructureFactory structFactory = null;	

	public static void main( String[] args )
	{
		try
		{
			CreateDataCube de = new CreateDataCube();		
			de.buildReport();
		}
		catch ( IOException e )
		{	
			e.printStackTrace();
		}
		catch ( SemanticException e )
		{
			e.printStackTrace();
		}
	}

	void buildDataSource( ) throws SemanticException
	{

		OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource(
				"Data Source", "org.eclipse.birt.report.data.oda.jdbc" );
		dsHandle.setProperty( "odaDriverClass",
		"org.eclipse.birt.report.data.oda.sampledb.Driver" );
		dsHandle.setProperty( "odaURL", "jdbc:classicmodels:sampledb" );
		dsHandle.setProperty( "odaUser", "ClassicModels" );
		dsHandle.setProperty( "odaPassword", "" );
		designHandle.getDataSources( ).add( dsHandle );


	}

	void buildDataSet( ) throws SemanticException
	{

		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds",
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle.setDataSource( "Data Source" );
		String qry = "Select * from customers";

		dsHandle.setQueryText( qry );


		designHandle.getDataSets( ).add( dsHandle );



	}
	void buidCube() throws SemanticException
	{
		TabularCubeHandle cubeHandle = designHandle.getElementFactory( )
		.newTabularCube( "MyCube" );
		cubeHandle.setDataSet((DataSetHandle)designHandle.getDataSets().get(0));
		designHandle.getCubes( ).add( cubeHandle );

		// measure group
		MeasureGroupHandle measureGroupHandle = designHandle
		.getElementFactory( ).newTabularMeasureGroup(
		"testMeasureGroup" );
		cubeHandle.setProperty( ICubeModel.MEASURE_GROUPS_PROP,
				measureGroupHandle );

		// measure
		measureGroupHandle.add( MeasureGroupHandle.MEASURES_PROP, designFactory.newTabularMeasure( null ) );
		MeasureHandle measure = (MeasureHandle) measureGroupHandle.getContent(MeasureGroupHandle.MEASURES_PROP, 0 );
		measure.setName( "CREDITLIMIT" );
		measure.setMeasureExpression( "dataSetRow['CREDITLIMIT']" );
		measure.setFunction( DesignChoiceConstants.MEASURE_FUNCTION_SUM );
		measure.setCalculated( false );
		measure.setDataType( DesignChoiceConstants.COLUMN_DATA_TYPE_FLOAT );


		// dimension
		TabularDimensionHandle dimension =  designFactory.newTabularDimension( null );
		cubeHandle.add(TabularCubeHandle.DIMENSIONS_PROP, dimension  );
		dimension.setTimeType( false );
		//dimension.setDefaultHierarchy( factory
		//		.newTabularHierarchy( "testDefaultHierarchy" ) ); 

		// hierarchy
		dimension.add( TabularDimensionHandle.HIERARCHIES_PROP, designFactory.newTabularHierarchy( null ) );	
		TabularHierarchyHandle hierarchy = (TabularHierarchyHandle) dimension.getContent( TabularDimensionHandle.HIERARCHIES_PROP, 0 );
		//hierarchy.setName( namePrix + hierarchy.getName( ) );
		hierarchy.setDataSet( (DataSetHandle)designHandle.getDataSets().get(0) ); 

		// level
		hierarchy.add( TabularHierarchyHandle.LEVELS_PROP, designFactory.newTabularLevel( dimension, null ) );
		TabularLevelHandle level = (TabularLevelHandle) hierarchy.getContent(TabularHierarchyHandle.LEVELS_PROP, 0 );
		level.setName( "testlevel" );
		level.setColumnName( "CUSTOMERNAME" );
		level.setDataType( DesignChoiceConstants.COLUMN_DATA_TYPE_STRING );

		/*
		level.setDateTimeLevelType( DesignChoiceConstants.DATE_TIME_LEVEL_TYPE_QUARTER );
		level.setDateTimeFormat( "mm" ); //$NON-NLS-1$
		// level.setInterval( DesignChoiceConstants.INTERVAL_MONTH );
		level.setIntervalRange( 5 );
		level.setIntervalBase( valuePrix + level.getIntervalBase( ) );
		 */

		ExtendedItemHandle xtab = CrosstabExtendedItemFactory.createCrosstabReportItem(designHandle, cubeHandle, "MyCrosstab" );
		designHandle.getBody().add(xtab);


		IReportItem reportItem = xtab.getReportItem( );
		CrosstabReportItemHandle xtabHandle = (CrosstabReportItemHandle) reportItem;
		//ICrosstabConstants.COLUMN_AXIS_TYPE
		//ICrosstabConstants.ROW_AXIS_TYPE

		//DimensionViewHandle dvh = xtabHandle.insertDimension(dimension, ICrosstabConstants.COLUMN_AXIS_TYPE, 0);
		DimensionViewHandle dvh = xtabHandle.insertDimension(dimension, ICrosstabConstants.ROW_AXIS_TYPE, 0);
		LevelViewHandle levelViewHandle =dvh.insertLevel(level, 0);
		CrosstabCellHandle cellHandle = levelViewHandle.getCell( );
		DesignElementHandle eii = xtabHandle.getModelHandle( );

		ComputedColumn bindingColumn = StructureFactory.newComputedColumn( eii, level.getName( ) );
		ComputedColumnHandle bindingHandle = ((ReportItemHandle) eii).addColumnBinding( bindingColumn,false );
		bindingColumn.setDataType( level.getDataType( ) );
		String exp = "dimension['" + dimension.getName()+"']['"+level.getName()+"']";
		bindingColumn.setExpression( exp);

		DataItemHandle dataHandle = designFactory.newDataItem( level.getName( ) );
		dataHandle.setResultSetColumn( bindingHandle.getName( ) );
		cellHandle.addContent( dataHandle );

		MeasureViewHandle mvh = xtabHandle.insertMeasure(measure, 0);
		//mvh.addHeader( );

		LabelHandle labelHandle = designFactory.newLabel( null );
		labelHandle.setText( measure.getName() );
		mvh.getHeader( ).addContent( labelHandle );

	}


	void buildReport() throws IOException, SemanticException
	{

		DesignConfig config = new DesignConfig( );
		config.setBIRTHome("C:/birt/birt-runtime-2_6_2/birt-runtime-2_6_2/ReportEngine");
		IDesignEngine engine = null;
		try{


			Platform.startup( config );
			IDesignEngineFactory factory = (IDesignEngineFactory) Platform
			.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( config );

		}catch( Exception ex){
			ex.printStackTrace();
		}


		SessionHandle session = engine.newSessionHandle( ULocale.ENGLISH ) ;


		try{
			//open a design or a template
			designHandle = session.createDesign();

			designFactory = designHandle.getElementFactory( );

			buildDataSource();
			buildDataSet();
			buidCube();




			// Save the design and close it.

			designHandle.saveAs("output/desample/cubetest.rptdesign" );
			designHandle.close( );
			Platform.shutdown();
			System.out.println("Finished");
		}catch (Exception e){
			e.printStackTrace();
		}		

	}
}

