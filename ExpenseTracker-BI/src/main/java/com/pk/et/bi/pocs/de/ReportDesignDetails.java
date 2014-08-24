package com.pk.et.bi.pocs.de;



import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.IncludedCssStyleSheetHandle;
import org.eclipse.birt.report.model.api.ListingHandle;
import org.eclipse.birt.report.model.api.MemberHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.StructureHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.metadata.IStructureDefn;

import com.ibm.icu.util.ULocale;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class ReportDesignDetails
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


	static void buildReport( ) throws IOException, SemanticException
	{
		// Create a session handle. This is used to manage all open designs.
		// Your app need create the session only once.

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
			design = session.openDesign("Reports/TopNPercent.rptdesign" );


			List cssStyleSheets = design.getAllStyles();//getAllCssStyleSheets();

			int size = cssStyleSheets.size();
			Iterator myincludecssiter = design.includeCssesIterator();
			while ( myincludecssiter.hasNext( ) ){
				IncludedCssStyleSheetHandle css = (IncludedCssStyleSheetHandle)myincludecssiter.next();
				//System.out.println( css.getFileName());

			}

			SlotHandle sh = design.getBody();
			System.out.println( "Contents Count: " + sh.getCount());
			Iterator it = sh.iterator();
			while( it.hasNext()){
				DesignElementHandle de =(DesignElementHandle)it.next();
				//System.out.println(de.getName());

			}

			sh = design.getDataSources();
			it = sh.iterator();
			while( it.hasNext()){
				DesignElementHandle de =(DesignElementHandle)it.next();
				//System.out.println(de.getName());

			}
			sh = design.getDataSets();
			it = sh.iterator();
			while( it.hasNext()){
				DesignElementHandle de =(DesignElementHandle)it.next();
				//System.out.println(de.getName());

			}

			sh = design.getMasterPages();
			it = sh.iterator();
			while( it.hasNext()){
				DesignElementHandle de =(DesignElementHandle)it.next();
				//System.out.println(de.getName());

			}			

			sh = design.getParameters();
			it = sh.iterator();
			while( it.hasNext()){
				DesignElementHandle de =(DesignElementHandle)it.next();
				//System.out.println(de.getName());

			}	


			System.out.println("Slot Count for a report design " + design.getDefn().getSlotCount());
			for( int i=0; i < design.getDefn().getSlotCount(); i++){
				//System.out.println(" Slot " + i + " -- " + design.getDefn().getSlot(i).getName());
			}




			Iterator pit = design.getPropertyIterator();
			while( pit.hasNext()){
				PropertyHandle ph = (PropertyHandle) pit.next();

				IStructureDefn structDefn = ph.getPropertyDefn().getStructDefn( );
				if ( structDefn != null )

				{
					//System.out.println("ListProperty " + ph.getPropertyDefn().getDisplayName());


					Iterator structIterator = ph.iterator( );
					while ( structIterator.hasNext( ) )

					{

						StructureHandle structHandle = (StructureHandle) structIterator.next( );
						Iterator memberIterator = structHandle.iterator( );
						while ( memberIterator.hasNext( ) )

						{
							MemberHandle memHandle = (MemberHandle) memberIterator.next( );
							//System.out.println( " Structure Item " + memHandle.getDefn( ).getDisplayName( ) + "---" + memHandle.getValue() );
						}

					}

				}else{

					System.out.println("StandardProperty " + ph.getPropertyDefn().getDisplayName() + "--" +ph.getValue());

				}
			}


			// Instantiate a slot handle and iterator for the body slot.
			SlotHandle shBody = design.getBody();
			Iterator slotIterator = shBody.iterator();
			// To retrieve top-level report items, iterate over the body.
			while (slotIterator.hasNext()) {
				Object shContents = slotIterator.next();
				// To get the contents of the top-level report items,
				// instantiate slot handles.
				if (shContents instanceof GridHandle) 	{
					GridHandle grid = ( GridHandle ) shContents;
					parseGrid( grid );
				}
				if (shContents instanceof ListingHandle) 	{
					ListingHandle list = ( ListingHandle ) shContents;
					parseList( list );
				}
				if (shContents instanceof ImageHandle) {
					String imageSource = ( ( ImageHandle ) 
							shContents ).getSource( ); 
					ImageHandle ih =(ImageHandle)shContents;
					System.out.println(ih.getImageName());
					// Check that the image has a URI.

				}
				if( shContents instanceof ExtendedItemHandle){
					String type = ((ExtendedItemHandle)shContents).getExtensionName();
					if( type.compareToIgnoreCase("Crosstab")==0){
						System.out.println("Found a crosstab");
					}
					if( type.compareToIgnoreCase("Chart")==0){
						Chart cm = (Chart) ((ExtendedItemHandle)shContents).getReportItem( ).getProperty( "chart.instance" ); 
						System.out.println("Found chart of type " +cm.getType());
					}

				}
			}

			design.close( );
			Platform.shutdown();

		}catch(Exception e){
			e.printStackTrace();
		}		
		System.out.println("Finished");

		// We're done!
	}

	public static void parseGrid(GridHandle grid){
		SlotHandle grRows = grid.getRows( );
		Iterator rowIterator = grRows.iterator( );
		parseRow( rowIterator);
	}
	public static void parseList(ListingHandle list){
		SlotHandle grRows = list.getHeader();
		Iterator rowIterator = grRows.iterator( );
		parseRow( rowIterator);
		grRows = list.getDetail();
		rowIterator = grRows.iterator( );
		parseRow( rowIterator);
		grRows = list.getFooter();
		rowIterator = grRows.iterator( );
		parseRow( rowIterator);
		grRows = list.getGroups();
		rowIterator = grRows.iterator( );
		parseRow( rowIterator);
	}

	public static void parseRow( Iterator rowIterator){

		while (rowIterator.hasNext()) {
			// Get RowHandle objects.
			Object rowSlotContents = rowIterator.next();
			// To find the image element, iterate over the grid.
			SlotHandle cellSlot = 
				( ( RowHandle ) rowSlotContents ).getCells( );
			Iterator cellIterator = cellSlot.iterator( );
			while ( cellIterator.hasNext( ) ) {
				// Get a CellHandle object.
				Object cellSlotContents = cellIterator.next( ); 
				SlotHandle cellContentSlot = 
					((CellHandle) cellSlotContents).getContent( );
				Iterator cellContentIterator = 
					cellContentSlot.iterator( );
				while (cellContentIterator.hasNext( )) {
					// Get a DesignElementHandle object.
					Object cellContents = 
						cellContentIterator.next( );
					// Check that the element is an image.
					if (cellContents instanceof ImageHandle) {
						String imageSource = ( ( ImageHandle ) 
								cellContents ).getSource( ); 
						ImageHandle ih =(ImageHandle)cellContents;
						System.out.println(ih.getImageName());
						// Check that the image has a URI.

					}
					if (cellContents instanceof GridHandle ){
						parseGrid( (GridHandle)cellContents);

					}
					if (cellContents instanceof ListingHandle ){
						parseList( (ListingHandle)cellContents);

					}
					if( cellContents instanceof ExtendedItemHandle){
						String type = ((ExtendedItemHandle)cellContents).getExtensionName();
						if( type.compareToIgnoreCase("Crosstab")==0){
							System.out.println("Found a crosstab");
						}
						if( type.compareToIgnoreCase("Chart")==0){
							try{
								Chart cm = (Chart) ((ExtendedItemHandle)cellContents).getReportItem( ).getProperty( "chart.instance" ); 
								System.out.println("Found chart of type " +cm.getType());
							}catch(Exception e)
							{
								e.printStackTrace();
							}
						}

					}					

				}
			}
		}
	}


}

