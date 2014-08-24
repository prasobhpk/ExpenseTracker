package com.pk.et.bi.pocs.reapi;



import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;


public class RunAndRenderTask {

	public void runReport() throws EngineException
	{

		IRunAndRenderTask task=null;
		IReportEngine engine=null;
		EngineConfig config = null;

		try{
			//System.setProperty("java.io.tmpdir", "c:/temp/test/testsampledb");	
			config = new EngineConfig( );			
			config.setLogConfig("c:/dwn", Level.INFO);
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
			config.getAppContext().put(EngineConstants.WEBAPP_CLASSPATH_KEY, "C:\\work\\workspaces\\2.6.2workspaces\\EclipseCon2011\\APIs\\Reports\\eventjar.jar");			
			config.setBIRTHome("C:\\birt\\birt-runtime-2_6_2\\birt-runtime-2_6_2\\ReportEngine");
			//config.setLogConfig(null, Level.FINEST);
			Platform.startup( config );
			
			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			engine = factory.createReportEngine( config );
		

		IReportRunnable design = null;
		//Open the report design
		
		design = engine.openReportDesign("Reports/dataseteventhandler.rptdesign"); 
		task = engine.createRunAndRenderTask(design);		
		//task.setParameterValue("Top Count", (new Integer(5)));
		task.validateParameters();
			
		
		HTMLRenderOption options = new HTMLRenderOption();		
		options.setImageDirectory("./");
		options.setOutputFileName("output/resample/eventhandlerjar.html");
		options.setOutputFormat("html");
		
		//PDFRenderOption options = new PDFRenderOption();
		//options.setOutputFileName("output/resample/topn.pdf");
		//options.setSupportedImageFormats("PNG;GIF;JPG;BMP;SWF;SVG");
		//options.setOutputFormat("pdf");
		
		//EXCELRenderOption options = new EXCELRenderOption();	
		//options.setOutputFormat("xls");
		//options.setOutputFileName("output/resample/customers.xls");
		//options.setWrappingText(true);
		
		task.setRenderOption(options);
		task.run();
		task.close();
		engine.destroy();
		}catch( Exception ex){
			ex.printStackTrace();
		}				
		finally
		{
			if ( !task.getErrors( ).isEmpty( ) )

			{

				for ( Object e : task.getErrors( ) )

				{

					( (Exception) e ).printStackTrace( );

				}

			}			
			Platform.shutdown( );
			System.out.println("Finished");
		}
		
	}	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{

			RunAndRenderTask ex = new RunAndRenderTask( );
			ex.runReport();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}