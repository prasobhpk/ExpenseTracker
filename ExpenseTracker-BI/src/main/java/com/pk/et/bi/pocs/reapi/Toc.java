package com.pk.et.bi.pocs.reapi;

import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.TOCNode;


public class Toc {

	public void runReport() throws EngineException
	{

		IReportEngine engine=null;
		EngineConfig config = null;

		try{

			config = new EngineConfig( );			
			config.setBIRTHome("C:\\birt\\birt-runtime-2_6_2\\birt-runtime-2_6_2\\ReportEngine");
			config.setLogConfig(null, Level.FINE);
			Platform.startup( config );
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			engine = factory.createReportEngine( config );
		}catch( Exception ex){
			ex.printStackTrace();
		}

		IReportDocument document = null;
		//Open the report design
		document = engine.openReportDocument("output/resample/customers.rptdocument"); 


		// Get the root of the table of contents.
		TOCNode td = document.findTOC( null );
		java.util.List children = td.getChildren( );
		long pNumber;
		// Loop through the top level table of contents entries.
		if ( children != null && children.size( ) > 0 ) {
			for ( int i = 0; i < children.size( ); i++ ) 	{
				// Find the required table of contents entry.
				TOCNode child = ( TOCNode ) children.get( i );
				//if ( child.getDisplayString( ).equals( "103" ) ) 		{
				// Get the number of the page that contains the data.
				pNumber = document.getPageNumber( child.getBookmark( ) );
				System.out.println( child.getDisplayString( ) + "Page to print is " + pNumber );
				//}
			}
		}		

		document.close();
		engine.destroy();
		Platform.shutdown();
		System.out.println("Finished");
	}	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{

			Toc ex = new Toc( );
			ex.runReport();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}


}


