package com.pk.et.bi.pocs.reapi;



import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;





public class RenderTask {

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
		document = engine.openReportDocument("output/resample/ps.rptdocument"); 
		IRenderOption options = new RenderOption();		
		options.setOutputFormat("doc");
		options.setOutputFileName("output/resample/ps.doc");
		options.setOption( IRenderOption.HTML_PAGINATION,
				Boolean.TRUE );
		
		

		
//			
//		if( options.getOutputFormat().equalsIgnoreCase("html")){
//			HTMLRenderOption htmlOptions = new HTMLRenderOption( options);
//			htmlOptions.setImageDirectory("output/image");
//			//set this if you want your image source url to be altered
//			//htmlOptions.setBaseImageURL("http://myhos/prependme?image=");
//			htmlOptions.setHtmlRtLFlag(false);
//			htmlOptions.setEmbeddable(false);
//		}else if( options.getOutputFormat().equalsIgnoreCase("pdf") ){
//			
//			PDFRenderOption pdfOptions = new PDFRenderOption( options );
//		  	/*  CLIP_CONTENT:             clip the content
//		  	 *	FIT_TO_PAGE_SIZE:         scale the content to fit into the page
//		  	 *	OUTPUT_TO_MULTIPLE_PAGES: divided the content into multiple pages
//		  	 *	ENLARGE_PAGE_SIZE:        enlarge the page size to contain all the content.
//		  	 */
//			pdfOptions.setOption( IPDFRenderOption.PAGE_OVERFLOW, Integer.valueOf( PDFRenderOption.FIT_TO_PAGE_SIZE ) );
//
//		}
		
		IRenderTask task = engine.createRenderTask(document); 		
		task.setRenderOption(options);
		//task.setPageRange("1-3");
		//task.setPageNumber(0);
		//task.setReportlet("chart3");
		task.render();
		task.close();
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

			RenderTask ex = new RenderTask( );
			ex.runReport();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		System.exit(0);
	}

	
}


