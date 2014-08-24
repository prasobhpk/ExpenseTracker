package com.pk.et.bi.pocs.reapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.data.engine.api.IConditionalExpression;
import org.eclipse.birt.data.engine.api.IFilterDefinition;
import org.eclipse.birt.data.engine.api.querydefn.ConditionalExpression;
import org.eclipse.birt.data.engine.api.querydefn.FilterDefinition;
import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IDataExtractionTask;
import org.eclipse.birt.report.engine.api.IDataIterator;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IResultMetaData;
import org.eclipse.birt.report.engine.api.IResultSetItem;

public class DataExtract {

	static void readReport() throws EngineException
	{
		IReportEngine engine=null;
		EngineConfig config = null;

		try{
	
			config = new EngineConfig( );			
			config.setBIRTHome("C:\\birt\\birt-runtime-2_6_2\\birt-runtime-2_6_2\\ReportEngine");
			config.setLogConfig(null, Level.OFF);
			Platform.startup( config );
			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			engine = factory.createReportEngine( config );
		}catch( Exception ex){
			ex.printStackTrace();
		}
		

		
		IReportDocument iReportDocument = engine.openReportDocument("output/resample/customers.rptdocument");
		
		//Setup data extraction task
		IDataExtractionTask task = engine.createDataExtractionTask(iReportDocument);
		
	
		/**
		 * returns the metadata corresponding to the data stored in the report
		 * document.  Could specify a component.
		 */
		
		ArrayList resultSetList = (ArrayList)task.getResultSetList( );

		IResultSetItem resultItem = null; 		
		Iterator it = resultSetList.iterator();
		while (it.hasNext()) {
		    resultItem = (IResultSetItem)it.next();
		    System.out.println("ResultSetName ====> " + resultItem.getResultSetName( ));
		} 		
		
		resultItem = (IResultSetItem)resultSetList.get( 0 );
		String dispName = resultItem.getResultSetName( );
		//Name Tables for ease
		task.selectResultSet( dispName );

		
		//Return Only customers between 201-300
		IFilterDefinition[] FilterExpression = new IFilterDefinition[2];
		FilterExpression[0] = new FilterDefinition( new ConditionalExpression( "row[\"CUSTOMERNUMBER\"]",
		IConditionalExpression.OP_GE, "201", null ) );
		FilterExpression[1] = new FilterDefinition( new ConditionalExpression( "row[\"CUSTOMERNUMBER\"]",
		IConditionalExpression.OP_LT, "300", null ) );
		task.setFilters( FilterExpression );
		
		
		
		
		IExtractionResults iExtractResults = task.extract();

		IDataIterator iData = null;
		try{
			if ( iExtractResults != null )
			{

				iData = iExtractResults.nextResultIterator( );
				//Get metadata on retrieved results
				IResultMetaData irmd = iData.getResultMetaData();
				if ( iData != null  ){
					int colCount = irmd.getColumnCount();
					System.out.println("Cloumn Count =" + colCount );
					for( int j=0; j< colCount; j++){
						System.out.println("Cloumn Name =" + irmd.getColumnName(j) );
						System.out.println("Cloumn Type =" + irmd.getColumnTypeName(j) );
						
					}
				
					while ( iData.next( ) )
					{	
						//Just disply the first two columns
						Object objColumn1;
					    Object objColumn2;
						try{
							objColumn1 = iData.getValue(4);
						}catch(DataException e){
							objColumn1 = new String("");
						}
						try{
							objColumn2 = iData.getValue(12);
						}catch(DataException e){
							objColumn2 = new String("");
						}
							System.out.println( objColumn1 + " , " + objColumn2 );
					}
					iData.close();
				}
			}
		}
		catch( Exception e){
			e.printStackTrace();
		}

		task.close();
		engine.destroy();
		Platform.shutdown();
		System.out.println("Finished");
		System.exit(0);
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			readReport( );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

}
