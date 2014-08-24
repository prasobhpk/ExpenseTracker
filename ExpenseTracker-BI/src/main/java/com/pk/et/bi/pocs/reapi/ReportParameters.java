package com.pk.et.bi.pocs.reapi;




import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.model.api.CascadingParameterGroupHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;

public class ReportParameters {

	@SuppressWarnings("unchecked")
	static void executeReport() throws EngineException
	{
		HashMap parmDetails = new HashMap();
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


		//Open a report design 
		IReportRunnable design = engine.openReportDesign("reports/parameters.rptdesign"); 

		IGetParameterDefinitionTask task = engine.createGetParameterDefinitionTask( design );
		Collection params = task.getParameterDefns( true );


		//task.getSelectionListForCascadingGroup();
		Iterator iter = params.iterator( );
		while ( iter.hasNext( ) )
		{
			IParameterDefnBase param = (IParameterDefnBase) iter.next( );

			if ( param instanceof IParameterGroupDefn )
			{
				IParameterGroupDefn group = (IParameterGroupDefn) param;
				System.out.println( "Parameter Group: " + group.getName( ) );


				// Do something with the parameter group.
				// Iterate over group contents.

				Iterator i2 = group.getContents( ).iterator( );
				while ( i2.hasNext( ) )
				{
					IScalarParameterDefn scalar = (IScalarParameterDefn) i2.next( );
					//System.out.println("\t" + scalar.getName());
					parmDetails.put( scalar.getName(), loadParameterDetails( task, scalar, design, group));
				}

			}
			else
			{

				IScalarParameterDefn scalar = (IScalarParameterDefn) param;
				//System.out.println(param.getName());
				parmDetails.put( scalar.getName(),loadParameterDetails( task, scalar, design, null));		        	        


			}
		}

		task.close();
		engine.destroy();
		Platform.shutdown();
		System.out.println("Finished");
	}

	private static HashMap loadParameterDetails(IGetParameterDefinitionTask task, IScalarParameterDefn scalar, IReportRunnable report, IParameterGroupDefn group){



		HashMap parameter = new HashMap();

		if( group == null){
			parameter.put("Parameter Group", "Default");
		}else{
			parameter.put("Parameter Group", group.getName());			
		}
		parameter.put("Name", scalar.getName());
		parameter.put("Help Text", scalar.getHelpText());
		parameter.put("Display Name", scalar.getDisplayName());
		//this is a format code such as  > for UPPERCASE
		parameter.put("Display Format", scalar.getDisplayFormat());

		if( scalar.isHidden() ){
			parameter.put("Hidden", "Yes");
		}else{
			parameter.put("Hidden", "No");
		}
		if( scalar.isHidden() ){
			parameter.put("Hidden", "Yes");
		}else{
			parameter.put("Hidded", "No");
		}
		if( scalar.isRequired() ){
			parameter.put("Is Required", "Yes");			
		}else{
			parameter.put("Is Required", "No");
		}
		if( scalar.isValueConcealed() ){
			parameter.put("Conceal Entry", "Yes");  //ie passwords etc
		}else{
			parameter.put("Conceal Entry", "No");
		}


		switch (scalar.getControlType()) {
		case IScalarParameterDefn.TEXT_BOX:  parameter.put("Type", "Text Box"); break;
		case IScalarParameterDefn.LIST_BOX:  parameter.put("Type", "List Box"); break;
		case IScalarParameterDefn.RADIO_BUTTON:  parameter.put("Type", "List Box"); break;
		case IScalarParameterDefn.CHECK_BOX:  parameter.put("Type", "List Box"); break;
		default: parameter.put("Type", "Text Box");break;
		}


		switch (scalar.getDataType()) {
		case IScalarParameterDefn.TYPE_STRING:  parameter.put("Data Type", "String"); break;
		case IScalarParameterDefn.TYPE_FLOAT:  parameter.put("Data Type", "Float"); break;
		case IScalarParameterDefn.TYPE_DECIMAL:  parameter.put("Data Type", "Decimal"); break;
		case IScalarParameterDefn.TYPE_DATE_TIME:  parameter.put("Data Type", "Date Time"); break;
		case IScalarParameterDefn.TYPE_BOOLEAN:  parameter.put("Data Type", "Boolean"); break;
		default:  parameter.put("Data Type", "Any"); break;
		}


		ScalarParameterHandle parameterHandle = ( ScalarParameterHandle ) scalar.getHandle();	

		parameter.put("Default Value", scalar.getDefaultValue());
		parameter.put("Prompt Text", scalar.getPromptText());
		parameter.put("Data Set Expression", parameterHandle.getValueExpr());

		if(scalar.getControlType() !=  IScalarParameterDefn.TEXT_BOX)
		{
			//System.out.println("dynamic parameter");

			if ( parameterHandle.getContainer( ) instanceof CascadingParameterGroupHandle ){
				Collection sList = Collections.EMPTY_LIST;
				if ( parameterHandle.getContainer( ) instanceof CascadingParameterGroupHandle )
				{

					String groupName = parameterHandle.getContainer( ).getName( );
					//used for Cascading parms see IGetParameterDefinitionTask.java code for comments
					//task.evaluateQuery( groupName );

					//Need to load this for calls to get next level.
					//This just gets the first level
					Object [] keyValueTmp = new Object[1];
					sList = task.getSelectionListForCascadingGroup( groupName, keyValueTmp );
	

					for ( Iterator sl = sList.iterator( ); sl.hasNext( ); )
					{
						IParameterSelectionChoice sI = (IParameterSelectionChoice) sl.next( );


						Object value = sI.getValue( );
						Object label = sI.getLabel( );
						System.out.println( label + "--" + value);

					}		    			

				}       		
			}else{
				Collection selectionList = task.getSelectionList( scalar.getName() );

				if ( selectionList != null )
				{
					HashMap dynamicList = new HashMap();       

					for ( Iterator sliter = selectionList.iterator( ); sliter.hasNext( ); )
					{
						IParameterSelectionChoice selectionItem = (IParameterSelectionChoice) sliter.next( );

						Object value = selectionItem.getValue( );
						String label = selectionItem.getLabel( );

						//System.out.println( label + "--" + value);
						//Display label unless null then display value.  Value is the what should get passed to the report.
						dynamicList.put(value,label);

					}
					parameter.put("Selection List", dynamicList);
				}
			}

		}



		Iterator iter = parameter.keySet().iterator();
		System.out.println("======================Parameter =" + scalar.getName());
		while (iter.hasNext()) {
			String name = (String) iter.next(); 
			if( name.equals("Selection List")){
				HashMap selList = (HashMap)parameter.get(name);
				Iterator selIter = selList.keySet().iterator();
				while (selIter.hasNext()) {
					Object lbl = selIter.next();
					System.out.println( "Selection List Entry ===== Key = " + lbl + " Value = " + selList.get(lbl));
				}

			}else{
				System.out.println( name + " = " + parameter.get(name)); 				
			}
		}
		return parameter;

	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			executeReport( );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

}
