package com.pk.et.bi.pocs;

import java.io.IOException;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;

import com.ibm.icu.util.ULocale;

public class GroupOnYAxis {

	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		new GroupOnYAxis().groupKey();
		System.out.println("Finished");

	}

	/**
	 * Get the chart instance from the design file and add series grouping key.
	 * 
	 * @return An instance of the simulated runtime chart model (containing
	 *         filled datasets)
	 */
	void groupKey() {
		ReportDesignHandle designHandle = null;
		final DesignConfig config = new DesignConfig();
		config.setBIRTHome("C:/birt/birt-runtime-2_3_0/birt-runtime-2_3_0/ReportEngine");
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
					.openDesign("report/NonGroupOnYAxis.rptdesign");//$NON-NLS-1$
		} catch (final DesignFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final ExtendedItemHandle eih = (ExtendedItemHandle) designHandle
				.getBody().getContents().get(0);

		Chart cm = null;
		try {
			cm = (Chart) eih.getReportItem().getProperty("chart.instance"); //$NON-NLS-1$
		} catch (final ExtendedElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cm.getTitle().getLabel().getCaption().setValue("Group On Y Axis");//$NON-NLS-1$

		final Axis axisBase = ((ChartWithAxes) cm).getAxes().get(0); // X-Axis
		final Axis axisOrth = axisBase.getAssociatedAxes().get(0); // Y-Axis
		final SeriesDefinition sdY = axisOrth.getSeriesDefinitions().get(0); // Y-Series

		final SeriesDefinition sdGroup = SeriesDefinitionImpl.create();
		final Query query = QueryImpl.create("row[\"Month\"]");//$NON-NLS-1$
		sdGroup.setQuery(query);

		axisOrth.getSeriesDefinitions().clear(); // Clear the original
													// Y-Series (sdY)
		axisOrth.getSeriesDefinitions().add(0, sdGroup);
		sdGroup.getSeries().add(sdY.getSeries().get(0));

		try {
			designHandle.saveAs("report/GroupOnYAxis.rptdesign");//$NON-NLS-1$
			System.out.println("Finished");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		Platform.shutdown();

	}

}
