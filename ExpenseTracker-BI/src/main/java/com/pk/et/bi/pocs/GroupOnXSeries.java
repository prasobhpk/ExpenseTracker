package com.pk.et.bi.pocs;

/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

import java.io.IOException;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.DataType;
import org.eclipse.birt.chart.model.attribute.SortOption;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
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

/**
 * Presents a bar chart with grouping on X series, which could be acheived in
 * the report designer as follows: Chart Builder -> Data -> X Series -> Set Dat
 * Sorting / Tick Grouping Enabled
 */
public class GroupOnXSeries {
	private static String BIRT_HOME = "D:/DEV_TOOLS/installed/birt-runtime-4_3_1/ReportEngine";

	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		new GroupOnXSeries().groupSeries();
		System.out.println("Finished");

	}

	/**
	 * Get the chart instance from the design file and group X series of the
	 * chart.
	 * 
	 * @return An instance of the simulated runtime chart model (containing
	 *         filled datasets)
	 */
	void groupSeries() {

		ReportDesignHandle designHandle = null;
		final DesignConfig config = new DesignConfig();
		config.setBIRTHome(BIRT_HOME);
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
					.openDesign("poc_resources/NonGroupOnXSeries.rptdesign");//$NON-NLS-1$
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

		cm.getTitle().getLabel().getCaption().setValue("Group On X Series");//$NON-NLS-1$

		cm.getTitle().getLabel().getCaption().getFont().setSize(12);
		cm.getTitle().getLabel().getCaption().getFont().setRotation(45);

		final SeriesDefinition sdX = ((ChartWithAxes) cm).getAxes().get(0)
				.getSeriesDefinitions().get(0);

		sdX.setSorting(SortOption.ASCENDING_LITERAL);
		sdX.getGrouping().setEnabled(true);
		sdX.getGrouping().setAggregateExpression("Sum");//$NON-NLS-1$
		sdX.getGrouping().setGroupType(DataType.NUMERIC_LITERAL);
		sdX.getGrouping().setGroupingInterval(1);

		try {
			designHandle.saveAs("poc_resources/GroupOnXSeries.rptdesign");//$NON-NLS-1$
		} catch (final IOException e) {
			e.printStackTrace();
		}
		Platform.shutdown();

	}

}
