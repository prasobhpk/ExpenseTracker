package com.pk.et.bi.pocs.de;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.ActionHandle;
import org.eclipse.birt.report.model.api.ReportElementHandle;
import org.eclipse.birt.report.model.api.RowOperationParameters;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.ScriptLibHandle;
import org.eclipse.birt.report.model.api.TextItemHandle;
import org.eclipse.birt.report.model.elements.JointDataSet;

import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.JointDataSetHandle;
import org.eclipse.birt.report.model.api.JoinConditionHandle;



import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.elements.ReportItem;

import org.eclipse.birt.report.model.api.elements.structures.EmbeddedImage;

import org.eclipse.birt.report.model.elements.Style;
import org.eclipse.birt.report.model.elements.ReportDesign;

import org.eclipse.birt.report.model.api.StyleHandle;

import org.eclipse.birt.report.model.api.elements.structures.AggregationArgument;
import org.eclipse.birt.report.model.api.elements.structures.DateTimeFormatValue;
import org.eclipse.birt.report.model.api.elements.structures.FormatValue;
import org.eclipse.birt.report.model.api.elements.structures.MapRule;
import org.eclipse.birt.report.model.api.elements.structures.HideRule;
import org.eclipse.birt.report.model.api.elements.structures.PropertyBinding;
import org.eclipse.birt.report.model.api.elements.structures.TOC;
import org.eclipse.birt.report.model.api.elements.structures.JoinCondition;
import org.eclipse.birt.report.model.api.elements.structures.ParamBinding;


import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

import org.eclipse.birt.report.model.api.elements.structures.HighlightRule;
import org.eclipse.birt.report.model.elements.interfaces.IJointDataSetModel;

import org.eclipse.birt.report.model.api.elements.structures.SortKey;
import org.eclipse.birt.report.model.api.SortKeyHandle;

import org.eclipse.birt.report.model.api.elements.structures.FilterCondition;
import org.eclipse.birt.report.model.api.elements.structures.Action;
import org.eclipse.birt.report.model.api.elements.structures.IncludeScript;
import java.util.Iterator;


import com.ibm.icu.util.ULocale;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class StructFactoryTest
{
	ReportDesignHandle designHandle = null;
	ElementFactory designFactory = null;
	StructureFactory structFactory = null;	

	public static void main( String[] args )
	{
		try
		{
			StructFactoryTest de = new StructFactoryTest();		
			de.buildReport();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( SemanticException e )
		{
			// TODO Auto-generated catch block
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
		
		PropertyBinding pb = new PropertyBinding();

		designHandle.getDataSources( ).add( dsHandle );
		long currid = dsHandle.getID();
		//pb.setName("odaUser");
		//pb.setID(currid);
		//pb.setValue("params[\"NewParameter\"].value");
		//PropertyHandle ph = designHandle.getPropertyHandle("propertyBindings");
		//ph.addItem(pb);

	}

	void buildDataSet( ) throws SemanticException
	{

		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds",
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle.setDataSource( "Data Source" );
		String qry = "Select * from customers";

		dsHandle.setQueryText( qry );

		addFilterCondition( dsHandle );

		designHandle.getDataSets( ).add( dsHandle );



	}
	void buildDataSet2( ) throws SemanticException
	{

		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds2",
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle.setDataSource( "Data Source" );
		String qry = "Select * from orderdetails where ordernumber = ?";

		dsHandle.setQueryText( qry );

		addFilterCondition( dsHandle );

		designHandle.getDataSets( ).add( dsHandle );



	}

	void buildJointDataSet( ) throws SemanticException
	{
		OdaDataSetHandle dsHandle1 = designFactory.newOdaDataSet( "ds1",
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle1.setDataSource( "Data Source" );
		String qry1 = "Select * from customers";

		dsHandle1.setQueryText( qry1 );


		OdaDataSetHandle dsHandle2 = designFactory.newOdaDataSet( "ds2",
		"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle2.setDataSource( "Data Source" );
		String qry2 = "Select * from orders";

		dsHandle2.setQueryText( qry2 );

		JointDataSetHandle jds = designFactory.newJointDataSet("test");

		designHandle.getDataSets( ).add( dsHandle1 );
		designHandle.getDataSets( ).add( dsHandle2 );

		jds.addDataSet("ds1");
		jds.addDataSet("ds2");

		String leftExpression = "dataSetRow[\"CUSTOMERNUMBER\"]"; 
		String rightExpression = "dataSetRow[\"CUSTOMERNUMBER\"]"; 	       
		JoinCondition condition = StructureFactory.createJoinCondition( );
		condition.setJoinType( DesignChoiceConstants.JOIN_TYPE_LEFT_OUT );
		condition.setOperator( DesignChoiceConstants.JOIN_OPERATOR_EQALS );
		condition.setLeftDataSet( "ds1" ); //$NON-NLS-1$
		condition.setRightDataSet( "ds2" ); //$NON-NLS-1$
		condition.setLeftExpression( leftExpression ); //$NON-NLS-1$
		condition.setRightExpression( rightExpression ); //$NON-NLS-1$

		PropertyHandle conditionHandle = jds
		.getPropertyHandle( JointDataSet.JOIN_CONDITONS_PROP );
		conditionHandle.addItem( condition );		

		designHandle.getDataSets( ).add( jds );


	}

	void addMapRule(TableHandle th){
		try{


			MapRule mr = structFactory.createMapRule();
			mr.setTestExpression("row[\"CustomerCreditLimit\"]");
			mr.setOperator(DesignChoiceConstants.MAP_OPERATOR_EQ);
			mr.setValue1("0");
			mr.setDisplay("N/A");

			PropertyHandle ph = th.getPropertyHandle(StyleHandle.MAP_RULES_PROP);
			ph.addItem(mr);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void addVisRule(ReportElementHandle rh){
		try{
			HideRule hr = structFactory.createHideRule();
			hr.setFormat("pdf");
			hr.setExpression("true");

			PropertyHandle ph = rh.getPropertyHandle(ReportItem.VISIBILITY_PROP);
			ph.addItem(hr);
		}catch (Exception e){
			e.printStackTrace();
		}
	}	
	void addBottomBorder(ReportElementHandle rh){
		try{


			rh.setProperty(StyleHandle.BORDER_BOTTOM_COLOR_PROP, "#000000");
			rh.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP, "solid");
			rh.setProperty(StyleHandle.BORDER_BOTTOM_WIDTH_PROP, "2px");

		}catch (Exception e){
			e.printStackTrace();
		}
	}		
	void addHighLightRule(RowHandle th){
		try{
			HighlightRule hr = structFactory.createHighlightRule();

			hr.setOperator(DesignChoiceConstants.MAP_OPERATOR_GT);
			hr.setTestExpression("row[\"CustomerCreditLimit\"]");
			hr.setValue1("100000");
			hr.setProperty(HighlightRule.BACKGROUND_COLOR_MEMBER, "blue");

			PropertyHandle ph = th.getPropertyHandle(StyleHandle.HIGHLIGHT_RULES_PROP);
			
			ph.addItem(hr);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void addSortKey(TableHandle th){
		try{
			SortKey sk = structFactory.createSortKey();
			//sk.setKey("row[\"CustomerName\"]");
			sk.setDirection(DesignChoiceConstants.SORT_DIRECTION_ASC);
			sk.setKey("if( params[\"srt\"].value){ if( params[\"srt\"].value == 'a' ){	row[\"CustomerName\"]; }else{ row[\"CustomerCity\"];}}");


			PropertyHandle ph = th.getPropertyHandle(TableHandle.SORT_PROP);
			ph.addItem(sk);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void modSortKey(TableHandle th){
		try{
			SortKeyHandle sk;
			PropertyHandle ph = th.getPropertyHandle(TableHandle.SORT_PROP);
			//get number or iterate
			sk = (SortKeyHandle)ph.get(0);
			sk.setDirection(DesignChoiceConstants.SORT_DIRECTION_DESC);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void addFilterCondition(OdaDataSetHandle dh){
		try{
			FilterCondition fc = structFactory.createFilterCond();
			fc.setExpr("row[\"COUNTRY\"]");
			fc.setOperator(DesignChoiceConstants.MAP_OPERATOR_EQ);
			fc.setValue1("'USA'");

			dh.addFilter(fc);

		}catch (Exception e){
			e.printStackTrace();
		}
	}	

	void addFilterCondition(TableHandle th){
		try{

			FilterCondition fc = structFactory.createFilterCond();
			fc.setExpr("row[\"CustomerCountry\"]");
			fc.setOperator(DesignChoiceConstants.MAP_OPERATOR_EQ);
			fc.setValue1("'USA'");

			PropertyHandle ph = th.getPropertyHandle(TableHandle.FILTER_PROP);
			
			ph.addItem(fc);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	void addHyperlink(LabelHandle lh){
		try{
			Action ac = structFactory.createAction();
			
			ActionHandle actionHandle = lh.setAction( ac );		
			//actionHandle.setURI("'http://www.google.com'");
			actionHandle.setLinkType(DesignChoiceConstants.ACTION_LINK_TYPE_DRILL_THROUGH);
			actionHandle.setReportName("c:/test/xyz.rptdesign");
			actionHandle.setTargetFileType("report-design");
			actionHandle.setTargetWindow("_blank");
			actionHandle.getMember("paramBindings");
			ParamBinding pb = structFactory.createParamBinding();
			pb.setParamName("order");
			pb.setExpression("row[\"ORDERNUMBER\"]");
			actionHandle.addParamBinding(pb);
			/*
            <structure name="action">
            <property name="linkType">drill-through</property>
            <property name="reportName">detail.rptdesign</property>
            <property name="targetWindow">_blank</property>
            <property name="targetFileType">report-design</property>
            <list-property name="paramBindings">
                <structure>
                    <property name="paramName">order</property>
                    <expression name="expression">row["ORDERNUMBER"]</expression>
                </structure>
            </list-property>
        </structure>
        */
        
        
			

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	void addToc(DataItemHandle dh){
		try{
			TOC myToc = structFactory.createTOC("row[\"CustomerName\"]");

			dh.addTOC(myToc);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	void addImage(){
		try{
			EmbeddedImage image = structFactory.createEmbeddedImage( );
			image.setType( DesignChoiceConstants.IMAGE_TYPE_IMAGE_JPEG );
			image.setData( load( "logo3.jpg" ) ); 
			image.setName( "mylogo" ); 

			designHandle.addImage( image );
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public byte[] load( String fileName ) throws IOException
	{
		InputStream is = null;

		is = new BufferedInputStream( this.getClass( ).getResourceAsStream(
				fileName ) );
		byte data[] = null;
		if ( is != null )
		{
			try
			{
				data = new byte[is.available( )];
				is.read( data );
			}
			catch ( IOException e1 )
			{
				throw e1;
			}
		}
		return data;
	}	
	void addScript(ReportDesignHandle rh){
		try{
			IncludeScript is = structFactory.createIncludeScript();
			is.setFileName("test.js");
			//PropertyHandle ph = rh.getPropertyHandle(ReportDesign.INCLUDE_SCRIPTS_PROP);
			//ph.addItem(is);
			

		}catch (Exception e){
			e.printStackTrace();
		}
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

		ReportDesignHandle design = null;



		try{
			//open a design or a template
			designHandle = session.createDesign();
			addScript( designHandle);
			designFactory = designHandle.getElementFactory( );
			//designHandle.findScriptLib("name").setName("test2");
			//Iterator iter = designHandle.scriptLibsIterator( );
			 //((ScriptLibHandle) iter.next()).drop() ;

			//designHandle.dropScriptLib( lib );

			buildDataSource();
			buildDataSet();
			buildJointDataSet();

			TableHandle table = designFactory.newTableItem( "table", 3 );
			table.setWidth( "100%" );
			table.setDataSet( designHandle.findDataSet( "ds" ) );
			
			
			// get first row in group header of group 0 .
			//RowOperationParameters parameters1 = new RowOperationParameters( 0, 0,
//							0 );
			// get second row in group header of group 0.
			//RowOperationParameters parameters2 = new RowOperationParameters( 0, 0,
//							1 );
			// get second row in table footer.
			//RowOperationParameters parameters3 = new RowOperationParameters( 2, -1,
//							1 );
			// get first row in table header.
			//RowOperationParameters parameters4 = new RowOperationParameters( 0, -1,
//							0 );
			// get second row in table detail which contain row span.
			//RowOperationParameters parameters5 = new RowOperationParameters( 2, -1,
//					1 );
			// get first row in group header of group 1.
			//RowOperationParameters parameters6 = new RowOperationParameters( 0, 1,
//							0 );	
			//second parm has to be -1 unless you are modifying a group
			//third parm specifies the row number
			//first parm is the slotid
			
			RowOperationParameters ro = new RowOperationParameters(2, -1, 0);
			table.insertRow(ro);
			



			PropertyHandle computedSet = table.getColumnBindings( ); 
			ComputedColumn  cs1, cs2, cs3, cs4, cs5;


			cs1 = StructureFactory.createComputedColumn();
			cs1.setName("CustomerName");
			cs1.setExpression("dataSetRow[\"CUSTOMERNAME\"]");
			computedSet.addItem(cs1);		
			cs2 = StructureFactory.createComputedColumn();
			cs2.setName("CustomerCity");
			cs2.setExpression("dataSetRow[\"CITY\"]");
			//cs2.setDataType(dataType)
			computedSet.addItem(cs2);
			cs3 = StructureFactory.createComputedColumn();
			cs3.setName("CustomerCountry");
			cs3.setExpression("dataSetRow[\"COUNTRY\"]");
			computedSet.addItem(cs3);
			cs4 = StructureFactory.createComputedColumn();
			cs4.setName("CustomerCreditLimit");
			cs4.setExpression("dataSetRow[\"CREDITLIMIT\"]");
			computedSet.addItem(cs4);
			
			cs5 = StructureFactory.createComputedColumn();
			cs5.setName("CustomerCreditLimitSum");
			cs5.setExpression("dataSetRow[\"CREDITLIMIT\"]");
			cs5.setAggregateFunction("sum");
			//cs5.setFilterExpression(expression)
			//All or group name
			//cs5.setAggregateOn(aggregateOn)
			computedSet.addItem(cs5);
			
			


			// table header
			RowHandle tableheader = (RowHandle) table.getHeader( ).get( 0 );

			ColumnHandle ch = (ColumnHandle)table.getColumns().get(0);
			ch.setProperty("width", "50%");
			

			ColumnHandle ch2 = (ColumnHandle)table.getColumns().get(1);
			ch2.setSuppressDuplicates(true);
			
			LabelHandle label1 = designFactory.newLabel("Label1" );	
			label1.setOnRender("var x = 3;");
			addBottomBorder(label1);
			label1.setText("Customer");
			CellHandle cell = (CellHandle) tableheader.getCells( ).get( 0 );

			cell.getContent( ).add( label1 );
			LabelHandle label2 = designFactory.newLabel("Label2" );	
			label2.setText("City");
			cell = (CellHandle) tableheader.getCells( ).get( 1 );
			cell.getContent( ).add( label2 );
			LabelHandle label3 = designFactory.newLabel("Label3" );	
			label3.setText("Credit Limit");
			cell = (CellHandle) tableheader.getCells( ).get( 2 );

			cell.getContent( ).add( label3 );


			// table detail (second Detail Row)
			RowHandle tabledetail = (RowHandle) table.getDetail( ).get( 1 );


			cell = (CellHandle) tabledetail.getCells( ).get( 0 );
			DataItemHandle data = designFactory.newDataItem( "data1" );
			data.setResultSetColumn("CustomerName");

			addToc( data );


			cell.getContent( ).add( data );
			cell = (CellHandle) tabledetail.getCells( ).get( 1 );
			data = designFactory.newDataItem( "data2" );
			data.setResultSetColumn("CustomerCity");
			cell.getContent( ).add( data );
			cell = (CellHandle) tabledetail.getCells( ).get( 2 );
			data = designFactory.newDataItem( "data3" );
			TextItemHandle tih =designFactory.newTextItem("mytextitem");
			//tih.setContentType(contentType);
			//tih.setContent(value);
			data.setResultSetColumn("CustomerCreditLimit");
			cell.getContent( ).add( data );

			FormatValue fv = structFactory.newFormatValue();
			fv.setPattern("#,##0.00{RoundingMode=HALF_UP}");
			fv.setCategory("custom");
			data.setProperty("numberFormat", fv);

			
			addHyperlink(label1);
			addMapRule(table);
			addHighLightRule(tabledetail);
			addSortKey(table);
			modSortKey(table);
			addFilterCondition(table);
			//addImage();

			RowHandle tablefooter = (RowHandle) table.getFooter().get( 0 );
			cell = (CellHandle) tablefooter.getCells( ).get( 0 );

			ImageHandle image1 = designFactory.newImage( "mylogo" );
			image1.setImageName( "mylogo" );
			//image1.sets
			//addVisRule( image1 );
			//cell.getContent( ).add( image1 );

			
			cell = (CellHandle) tablefooter.getCells( ).get( 2 );
			data = designFactory.newDataItem( "datasum" );

			//FormatValue formatValueToSet = new DateTimeFormatValue( );
			//formatValueToSet.setPattern( "dd/mm/yy" );
			//formatValueToSet.setCategory("Custom");
			//data.setProperty( StyleHandle.DATE_TIME_FORMAT_PROP,
			//		formatValueToSet );
			//has to have a style data.getStyle().setDateTimeFormat("dd/mm/yy");
			data.setResultSetColumn("CustomerCreditLimitSum");
			
			cell.getContent( ).add( data );			
			
			ScalarParameterHandle sph = designFactory.newScalarParameter("srt");
			sph.setIsRequired(false);
			//sph.setAllowNull(true);
			//sph.setAllowBlank(true);
			sph.setValueType(DesignChoiceConstants.PARAM_VALUE_TYPE_STATIC);
			sph.setDataType(DesignChoiceConstants.PARAM_TYPE_STRING);
			designHandle.getParameters().add(sph);			

			
			//delete the row added earlier:
			table.getDetail().get(0).drop();
			designHandle.getBody( ).add( table );
			//designHandle.findElement("mytable");

			
			// Save the design and close it.

			designHandle.saveAs("output/desample/structfactorytest.rptdesign" );
			designHandle.close( );
			Platform.shutdown();
			System.out.println("Finished");
		}catch (Exception e){
			e.printStackTrace();
		}		

	}
}

