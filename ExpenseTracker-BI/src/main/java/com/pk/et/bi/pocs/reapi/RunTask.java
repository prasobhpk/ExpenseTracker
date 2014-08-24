package com.pk.et.bi.pocs.reapi;



import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunTask;




public class RunTask {

	public void runReport() throws EngineException
	{

		IReportEngine engine=null;
		EngineConfig config = null;
		try{
	
			config = new EngineConfig( );
			//config.setTempDir(tmpDir)
			config.setBIRTHome("C:\\birt\\birt-runtime-2_6_2\\birt-runtime-2_6_2\\ReportEngine");
			config.setLogConfig(null, Level.OFF);
			//config.setResourcePath(resourcePath)
			Platform.startup( config );
			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			engine = factory.createReportEngine( config );
		}catch( Exception ex){
			ex.printStackTrace();
		}

		IReportRunnable design = null;
		//Open the report design
		design = engine.openReportDesign("Reports/ps.rptdesign"); 
		IRunTask task = engine.createRunTask(design); 		
		
		task.run("output/resample/ps.rptdocument");
				

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

			RunTask ex = new RunTask( );
			ex.runReport();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	
}


