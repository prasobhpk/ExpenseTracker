package com.pk.et.bi.pocs.de;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.model.api.ReportDesignHandle;

public class ModifyRunningReport {
	private static String BIRT_HOME = "D:/DEV_TOOLS/installed/birt-runtime-4_3_1/ReportEngine";

	public void runReport() throws EngineException {

		IReportEngine engine = null;
		EngineConfig config = null;

		try {

			config = new EngineConfig();
			config.setBIRTHome(BIRT_HOME);
			// config.setLogConfig(null, Level.FINE);

			Platform.startup(config);
			final IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);

			IReportRunnable design = null;
			// Open the report design
			design = engine
					.openReportDesign("poc_resources/TopNPercent.rptdesign");
			final ReportDesignHandle report = (ReportDesignHandle) design
					.getDesignHandle();
			report.findElement("NewChart").drop();
			// Create task to run and render the report,
			final IRunAndRenderTask task = engine
					.createRunAndRenderTask(design);
			task.setParameterValue("Top Percentage", new Integer(3));
			task.setParameterValue("Top Count", new Integer(5));
			task.validateParameters();

			final HTMLRenderOption options = new HTMLRenderOption();
			options.setOutputFileName("poc_output/desample/ModifiedTopNPercent.html");
			options.setOutputFormat("html");
			options.setImageDirectory("images");

			task.setRenderOption(options);

			task.run();

			task.close();
			engine.destroy();
			Platform.shutdown();
			System.out.println("Finished");

		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		try {

			final ModifyRunningReport ex = new ModifyRunningReport();
			ex.runReport();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
