package com.pk.et.bi.pocs.de;
/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - created
 *******************************************************************************/



import java.io.IOException;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

import com.ibm.icu.util.ULocale;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class DeDemo
{

	public static void main( String[] args )
	{
		try
		{
			buildReport( );
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

	// This function shows how to build a very simple BIRT report with a
	// minimal set of content: a simple grid with an image and a label.

	static void buildReport( ) throws IOException, SemanticException
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

		// Create a new report design.
		ReportDesignHandle design = session.createDesign( );

		// The element factory creates instances of the various BIRT elements.
		ElementFactory factory = design.getElementFactory( );

		SimpleMasterPageHandle element = factory.newSimpleMasterPage( "Page Master" ); //$NON-NLS-1$
		LabelHandle labelft = factory.newLabel( null );
		labelft.setText( "My footer" ); //$NON-NLS-1$
		element.getPageFooter().add(labelft);
		design.getMasterPages( ).add( element );

		// Create a grid and add it to the "body" slot of the report
		// design.

		GridHandle grid = factory.newGridItem( null, 2 /* cols */, 1 /* row */ );

		ScalarParameterHandle sph = factory.newScalarParameter("srt");
		sph.setIsRequired(false);
		sph.setValueType(DesignChoiceConstants.PARAM_VALUE_TYPE_STATIC);
		sph.setDataType(DesignChoiceConstants.PARAM_TYPE_STRING);
		design.getParameters().add(sph);

		design.getBody( ).add( grid );

		// Note: Set the table width to 100% to prevent the label
		// from appearing too narrow in the layout view.

		grid.setWidth( "100%" ); //$NON-NLS-1$
		ColumnHandle ch = (ColumnHandle)grid.getColumns().get(0);
		ch.setProperty("width", "3in");

		// Get the first row.
		RowHandle row = (RowHandle) grid.getRows( ).get( 0 );


		// Create an image and add it to the first cell.
		ImageHandle image = factory.newImage( null );

		CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
		cell.getContent( ).add( image );
		image.setURL( "\"http://www.eclipse.org/birt/phoenix/tutorial/basic/multichip-4.jpg\"" ); 
		image.setScale(0.50);
		image.setSize("scale-to-item");



		// Create a label and add it to the second cell.

		LabelHandle label = factory.newLabel( null );
		cell = (CellHandle) row.getCells( ).get( 1 );
		cell.getContent( ).add( label );
		label.setText( "Hello, world!" ); //$NON-NLS-1$

		// Save the design and close it.

		design.saveAs( "output/desample/sample.rptdesign" ); //$NON-NLS-1$
		design.close( );
		session.closeAll(false);

		Platform.shutdown();
		System.out.println("Finished");

		// We're done!
	}
}

