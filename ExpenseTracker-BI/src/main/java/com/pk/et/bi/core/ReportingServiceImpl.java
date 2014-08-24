package com.pk.et.bi.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.data.engine.api.DataEngine;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DataSetHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.MasterPageHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScriptDataSetHandle;
import org.eclipse.birt.report.model.api.ScriptDataSourceHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SharedStyleHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TableGroupHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.TextDataHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.command.StyleException;
import org.eclipse.birt.report.model.api.core.IModuleModel;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.DateFormatValue;
import org.eclipse.birt.report.model.api.elements.structures.HighlightRule;
import org.eclipse.birt.report.model.api.elements.structures.ResultSetColumn;
import org.eclipse.birt.report.model.api.elements.structures.SortKey;
import org.eclipse.birt.report.model.elements.Style;
import org.eclipse.birt.report.model.elements.interfaces.IDesignElementModel;
import org.eclipse.birt.report.model.elements.interfaces.IMasterPageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ibm.icu.util.ULocale;
import com.pk.et.infra.bi.ReportDataType;
import com.pk.et.infra.util.ETTechnicalException;

/**
 * RepotingService implementation that uses BIRT.
 */
// @Service
public class ReportingServiceImpl implements ReportingService {

	static final Logger LOG = LoggerFactory
			.getLogger(ReportingServiceImpl.class);

	private final boolean applyBirtMemoryConfigs;

	private final int dataSetCacheRowLimit;

	private final int memory_buffer_size;

	private final String birtHome;
	private final String birtResourcesPath;
	private final boolean birtEngineEagerStart;
	private final String birtLogLevel;
	private final String logDirectory;

	IReportEngine reportEngine = null;

	IDesignEngine designEngine = null;

	@Autowired
	public ReportingServiceImpl(
			@Value("#{birtConf['birt.home']}") final String birtHome,
			@Value("#{birtConf['birt.resources']}") final String birtResourcesPath,
			@Value("#{birtConf['birt.engine.eager.start']}") final boolean birtEngineEagerStart,
			@Value("#{birtConf['birt.apply_memory_configs']}") final boolean applyBirtMemoryConfigs,
			@Value("#{birtConf['birt.data_engine_data_set_cache_row_limit']}") final int dataSetCacheRowLimit,
			@Value("#{birtConf['birt.data_engine_memory_buffer_size']}") final int memory_buffer_size,
			@Value("#{birtConf['birt.loglevel']}") final String logLevel,
			@Value("#{birtConf['birt.logDirectory']}") final String logDirectory)
			throws IOException {
		this.birtHome = birtHome;
		this.birtResourcesPath = birtResourcesPath;
		this.birtEngineEagerStart = birtEngineEagerStart;
		this.applyBirtMemoryConfigs = applyBirtMemoryConfigs;
		this.dataSetCacheRowLimit = dataSetCacheRowLimit;
		this.memory_buffer_size = memory_buffer_size;
		this.birtLogLevel = logLevel;
		this.logDirectory = logDirectory;
		/*
		 * To avoid the casual (1 per week in production) class loaders deadlock
		 * induce by BIRT engine start, we eager load the BIRT engine at the
		 * start of the webapp. We already used a BIRT version that include the
		 * fix of the bug 318277 but it seems that the bug is not fixed in the
		 * right way. https://bugs.eclipse.org/bugs/show_bug.cgi?id=318277 For a
		 * quick start during development (local deployment on Tomcat from
		 * Eclipse) or automated tests, we let the possibility to enable a lazy
		 * start (i.e. start the BIRT engine only when required).
		 */
		if (this.birtEngineEagerStart) {
			start();
		}
	}

	/**
	 * Start the engine. This method mainly load BIRT.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void start() throws IOException {
		LOG.info("BIRT engine starting...");

		final EngineConfig engineConfig = new EngineConfig();

		if (this.birtLogLevel != null && this.logDirectory != null) {
			Level level = Level.OFF;
			if ("SEVERE".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.SEVERE;
			} else if ("WARNING".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.WARNING;
			} else if ("INFO".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.INFO;
			} else if ("CONFIG".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.CONFIG;
			} else if ("FINE".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.FINE;
			} else if ("FINER".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.FINER;
			} else if ("FINEST".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.FINEST;
			} else if ("OFF".equalsIgnoreCase(this.birtLogLevel)) {
				level = Level.OFF;
			}

			engineConfig.setLogConfig(this.logDirectory, level);
		}
		if (this.applyBirtMemoryConfigs) {
			engineConfig.getAppContext().put(DataEngine.MEMORY_USAGE,
					DataEngine.MEMORY_USAGE_CONSERVATIVE);
			engineConfig.getAppContext().put(
					DataEngine.DATA_SET_CACHE_ROW_LIMIT,
					this.dataSetCacheRowLimit);
			engineConfig.getAppContext().put(DataEngine.MEMORY_BUFFER_SIZE,
					this.memory_buffer_size);
			LOG.info(
					"Applied the memory Settings - DataEngine.DATA_SET_CACHE_ROW_LIMIT {} & DataEngine.MEMORY_BUFFER_SIZE {}",
					new Object[] { this.dataSetCacheRowLimit,
							this.memory_buffer_size });

		}

		initPlatformConfig(engineConfig);
		engineConfig.setResourcePath(this.birtResourcesPath);
		try {
			Platform.startup(engineConfig);
		} catch (final BirtException ex) {
			throw new ETTechnicalException(ex);
		}
		final IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		this.reportEngine = factory.createReportEngine(engineConfig);

		final DesignConfig designConfig = new DesignConfig();
		initPlatformConfig(designConfig);
		final IDesignEngineFactory designFactory = (IDesignEngineFactory) Platform
				.createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
		this.designEngine = designFactory.createDesignEngine(designConfig);

		LOG.info("BIRT engine started");
	}

	protected void initPlatformConfig(final PlatformConfig config) {
		if (this.birtHome == null) {
			throw new ETTechnicalException("Birt Home is undefined!");
		}
		if (!new File(this.birtHome).exists()) {
			throw new ETTechnicalException("Birt Home <" + this.birtHome
					+ "> does not exist!");
		}
		config.setBIRTHome(this.birtHome);
	}

	/**
	 * Returns the engine. Creates it if necessary.
	 * 
	 * @return the current BIRT engine
	 */
	public synchronized IReportEngine getEngine() {
		if (this.reportEngine == null) {
			try {
				start();
			} catch (final IOException e) {
				LOG.error("error while retrieving birt engine", e);
			}
		}
		return this.reportEngine;
	}

	/**
	 * Generates a report in XLS format.
	 * 
	 * @param task
	 *            BIRT rendering task.
	 * @param fileName
	 *            the file name for the file generated.
	 */

	public void renderExcel(final ReportingTask task, final String fileName) {
		final EXCELRenderOption option = new EXCELRenderOption();
		option.setEnableMultipleSheet(true);
		option.setHideGridlines(true);
		render(task, option, "xls", fileName);
	}

	/**
	 * Generates a report in PDF format.
	 * 
	 * @param task
	 *            BIRT rendering task.
	 * @param fileName
	 *            the file name for the file generated.
	 */

	public void renderPdf(final ReportingTask task, final String fileName) {
		render(task, new PDFRenderOption(), "pdf", fileName);
	}

	private void render(final ReportingTask task, final RenderOption options,
			final String outputFormat, final String fileName) {
		options.setOutputFormat(outputFormat);
		options.setOutputFileName(fileName);
		final ReportingTaskImpl castedTask = (ReportingTaskImpl) task;
		castedTask.setErrorHandlingOption();
		castedTask.setRenderOption(options);
		castedTask.run();
		castedTask.logErrors();
	}

	public static class ReportingTaskImpl implements ReportingTask {

		IRunAndRenderTask birtTask;

		public ReportingTaskImpl(final IRunAndRenderTask birtTask) {
			this.birtTask = birtTask;
		}

		public void setRenderOption(final RenderOption ro) {
			this.birtTask.setRenderOption(ro);
		}

		public void run() {
			try {
				this.birtTask.run();
			} catch (final EngineException exc) {
				throw new ReportingException(exc);
			}
		}

		public void setLocal(final Locale locale) {
			this.birtTask.setLocale(locale);
		}

		public void close() {
			this.birtTask.close();
		}

		@SuppressWarnings("unchecked")
		public void logErrors() {
			final List<Throwable> errors = this.birtTask.getErrors();
			for (final Throwable error : errors) {
				LOG.error("error on report generation", error);
			}
			if (errors.size() > 0) {
				throw new ReportingException(errors.toString());
			}
		}

		public void setErrorHandlingOption() {
			this.birtTask
					.setErrorHandlingOption(IRunAndRenderTask.CANCEL_ON_ERROR);
		}
	}

	/**
	 * Returns the design engine. Creates it if necessary.
	 * 
	 * @return the current BIRT design engine
	 */
	synchronized IDesignEngine getDesignEngine() {
		if (this.designEngine == null) {
			try {
				start();
			} catch (final IOException e) {
				LOG.error("error while retrieving design engine", e);
			}
		}
		return this.designEngine;
	}

	public static class DesignContextImpl implements DesignContext {

		SessionHandle session;
		ReportDesignHandle reportDesignHandle;

		public DesignContextImpl(final SessionHandle session,
				final ReportDesignHandle reportDesignHandle) {
			super();
			this.session = session;
			this.reportDesignHandle = reportDesignHandle;
		}

		public SessionHandle getSession() {
			return this.session;
		}

		public void saveDesign() {
			try {
				this.reportDesignHandle.save();
			} catch (final IOException exc) {
				throw new ReportingException(exc);
			}
			this.reportDesignHandle.close();
		}

		public void closeDesign() {
			try {
				getSession().closeAll(false);
			} catch (final IOException e) {
				throw new ReportingException(e);
			}
		}

		public void createDataSource(final String dataSourceName) {
			final ScriptDataSourceHandle dataSourceHandle = getReportDesignHandle()
					.getElementFactory().newScriptDataSource(dataSourceName);
			try {
				getReportDesignHandle().getDataSources().add(dataSourceHandle);
			} catch (final ContentException exc) {
				throw new ReportingException(exc);
			} catch (final NameException exc) {
				throw new ReportingException(exc);
			}
		}

		public ScriptDataSetHandle createDataSet(final String dataSourceName,
				final String dataSetName,
				final List<ColumnDefinition> columnDefinitions) {
			// Data Set
			final ScriptDataSetHandle dataSetHandle = getReportDesignHandle()
					.getElementFactory().newScriptDataSet(dataSetName);
			try {
				dataSetHandle.setDataSource(dataSourceName);
				getReportDesignHandle().getDataSets().add(dataSetHandle);
			} catch (final SemanticException exc) {
				throw new ReportingException(exc);
			}
			final PropertyHandle computedSet = dataSetHandle
					.getPropertyHandle(ScriptDataSetHandle.RESULT_SET_PROP);

			int position = 1;
			for (final ColumnDefinition columnDef : columnDefinitions) {
				// Since this is a Scripted Data Source you need to tell the
				// DataSet what the columns are.
				final ResultSetColumn resultColumn = StructureFactory
						.createResultSetColumn();
				resultColumn.setPosition(position++);
				resultColumn.setColumnName(columnDef.getName());
				if (columnDef.getDataType().equals(ReportDataType.PERCENTAGE)) {
					resultColumn.setDataType(ReportDataType.DECIMAL.getValue());
				} else {
					resultColumn
							.setDataType(columnDef.getDataType().getValue());
				}
				try {
					computedSet.addItem(resultColumn);
				} catch (final SemanticException exc) {
					throw new ReportingException(exc);
				}
			}
			return dataSetHandle;
		}

		public void createListDataSetWithEventHandlerClass(
				final String dataSourceName, final String dataSetName,
				final String eventHandlerClass,
				final ReportDataType reportDataType) {
			try {
				final ScriptDataSetHandle dataSetHandle = getReportDesignHandle()
						.getElementFactory().newScriptDataSet(dataSetName);
				dataSetHandle.setDataSource(dataSourceName);
				dataSetHandle.setEventHandlerClass(eventHandlerClass);

				getReportDesignHandle().getDataSets().add(dataSetHandle);

				final PropertyHandle computedSet = dataSetHandle
						.getPropertyHandle(ScriptDataSetHandle.RESULT_SET_PROP);
				final ResultSetColumn resultColumn = StructureFactory
						.createResultSetColumn();
				resultColumn.setPosition(1);
				resultColumn.setColumnName("value");
				if (reportDataType.equals(ReportDataType.LIST_DATE)) {
					resultColumn.setDataType(ReportDataType.DATE.getValue());
				} else if (reportDataType.equals(ReportDataType.LIST_DECIMAL)) {
					resultColumn.setDataType(ReportDataType.DECIMAL.getValue());
				} else if (reportDataType.equals(ReportDataType.LIST_STRING)) {
					resultColumn.setDataType(ReportDataType.STRING.getValue());
				}
				computedSet.addItem(resultColumn);
			} catch (final SemanticException exc) {
				throw new ReportingException(exc);
			}
		}

		public ReportDataSet createDataSetWithOpenCodeAndFetchCode(
				final String dataSourceName, final String dataSetName,
				final String openCode, final String fetchCode,
				final List<ColumnDefinition> columnDefinitions) {
			// Data Set
			final ScriptDataSetHandle dataSetHandle = createDataSet(
					dataSourceName, dataSetName, columnDefinitions);
			try {
				dataSetHandle.setOpen(openCode);
				dataSetHandle.setFetch(fetchCode);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportDataSetImpl(dataSetHandle);
		}

		public ReportDataSet createDataSetsWithEventHandlerClass(
				final String dataSourceName, final String dataSetName,
				final List<ColumnDefinition> columnDefinitions,
				final String eventHandlerClass) {
			// Data Set
			final ScriptDataSetHandle dataSetHandle = createDataSet(
					dataSourceName, dataSetName, columnDefinitions);
			try {
				dataSetHandle.setEventHandlerClass(eventHandlerClass);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportDataSetImpl(dataSetHandle);
		}

		/**
		 * Every report needs a MasterPage
		 * 
		 * This report modifies the Master Page constants. Including adding a
		 * Title to the Report
		 * 
		 * @param reportDesignHandle
		 */

		public void createMasterPage(final String masterPageName,
				final String orientation, final String margin) {
			final MasterPageHandle masterPageHandle = getReportDesignHandle()
					.getElementFactory().newSimpleMasterPage(masterPageName);

			try {
				getReportDesignHandle().getMasterPages().add(masterPageHandle);

				// pick an orientation (does not make sense if using custom page
				// sizing)
				masterPageHandle.setOrientation(orientation);

				// Set the margins
				masterPageHandle.setProperty(IMasterPageModel.TOP_MARGIN_PROP,
						margin);
				masterPageHandle.setProperty(IMasterPageModel.LEFT_MARGIN_PROP,
						margin);
				masterPageHandle.setProperty(
						IMasterPageModel.BOTTOM_MARGIN_PROP, margin);
				masterPageHandle.setProperty(
						IMasterPageModel.RIGHT_MARGIN_PROP, margin);
			} catch (final SemanticException exc) {
				throw new ReportingException(exc);
			}
		}

		public void saveAs(final String fileName) {
			try {
				getReportDesignHandle().saveAs(fileName);
			} catch (final IOException e) {
				throw new ReportingException(e);
			}
		}

		public ReportTableImpl createTable(final String tableName,
				final int columnCount, final int headerRow,
				final int detailRow, final int footerRow) {
			final ElementFactory elementFactory = getReportDesignHandle()
					.getElementFactory();
			final TableHandle table = elementFactory.newTableItem(tableName,
					columnCount, headerRow, detailRow, footerRow);
			try {
				table.setPageBreakInterval(65000);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportTableImpl(table, getReportDesignHandle());
		}

		protected DesignElementHandle findElementFromSlotByName(
				final SlotHandle slot, final String name) {
			final FindElementByNameProcessor processor = new FindElementByNameProcessor(
					name);
			processor.walkAndProcess(slot);
			return processor.getElementHandle();
		}

		protected class FindElementByNameProcessor extends
				DesignElementHandleProcessor {
			protected String name;
			protected DesignElementHandle elementHandle;

			public FindElementByNameProcessor(final String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}

			public DesignElementHandle getElementHandle() {
				return this.elementHandle;
			}

			@Override
			public boolean process(final DesignElementHandle elementHandle) {
				boolean mustContinue = true;
				if (this.name.equals(elementHandle.getName())) {
					this.elementHandle = elementHandle;
					mustContinue = false;
				}
				return mustContinue;
			}
		}

		protected abstract class DesignElementHandleProcessor {
			public abstract boolean process(DesignElementHandle elementHandle);

			@SuppressWarnings("unchecked")
			public boolean walkAndProcess(final SlotHandle slot) {
				boolean mustContinue = true;
				// walk & process the slot
				final List<DesignElementHandle> handles = slot.getContents();
				final Iterator<DesignElementHandle> handlesIt = handles
						.iterator();
				while (handlesIt.hasNext() && mustContinue) {
					final DesignElementHandle handle = handlesIt.next();
					mustContinue = process(handle);
					if (mustContinue) {
						// walk & process recursively the children
						final int slotCount = handle.getDefn().getSlotCount();
						int i = 0;
						while (i < slotCount && mustContinue) {
							mustContinue = walkAndProcess(handle.getSlot(i));
							i++;
						}
					}
				}
				return mustContinue;
			}
		}

		public ReportGrid findGridByName(final String gridName) {
			final GridHandle grid = (GridHandle) findElementFromSlotByName(
					getReportDesignHandle().getBody(), gridName);
			return new ReportGridImpl(grid, getReportDesignHandle());
		}

		public ReportItem createLabel(final String text) {
			final LabelHandle label = getReportDesignHandle()
					.getElementFactory().newLabel(null);
			try {
				label.setText(text);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportItemImpl(label);
		}

		public ReportItem createTextData(final String expression) {
			final TextDataHandle textData = getReportDesignHandle()
					.getElementFactory().newTextData(null);
			try {
				textData.setValueExpr(expression);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportItemImpl(textData);
		}

		public void changeMasterPageWidth(final String masterPageName,
				final Integer masterPageWidth) {
			final MasterPageHandle masterPageHandle = (MasterPageHandle) findElementFromSlotByName(
					getReportDesignHandle().getMasterPages(), masterPageName);
			try {
				masterPageHandle
						.setPageType(DesignChoiceConstants.PAGE_SIZE_CUSTOM);
				masterPageHandle.setProperty("width", masterPageWidth
						+ DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}

		}

		/**
		 * Create a style and add it in the design context in order to use the
		 * style later.
		 */

		public ReportStyle createStyle(final String styleName) {
			final SharedStyleHandle style = getReportDesignHandle()
					.getElementFactory().newStyle(styleName);
			try {
				getReportDesignHandle().getStyles().add(style);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportStyleImpl(style);
		}

		public ReportStyle createStyle() {
			final SharedStyleHandle style = getReportDesignHandle()
					.getElementFactory().newStyle(null);
			try {
				getReportDesignHandle().getStyles().add(style);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportStyleImpl(style);
		}

		public ReportItem createDateItem(final String columnName,
				final String dateFormat) {
			final DataItemHandle textData = getReportDesignHandle()
					.getElementFactory().newDataItem(null);
			try {
				textData.setResultSetColumn(columnName);
				final DateFormatValue formatValueToSet = new DateFormatValue();
				formatValueToSet.setPattern(dateFormat);
				formatValueToSet.setCategory("Custom");
				textData.clearProperty(StyleHandle.DATE_TIME_FORMAT_PROP);
				textData.setProperty(StyleHandle.DATE_TIME_FORMAT_PROP,
						formatValueToSet);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportItemImpl(textData);
		}

		public void defineReportTheme(final String name) {
			try {
				getReportDesignHandle().setThemeName(
						"clipsReportLibraryTheme." + name);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public ReportTable findTableByName(final String tableName) {
			final TableHandle table = (TableHandle) findElementFromSlotByName(
					getReportDesignHandle().getBody(), tableName);
			return new ReportTableImpl(table, getReportDesignHandle());
		}

		public ReportGrid createGrid(final int columnNum, final int rowNum) {
			final GridHandle gridHandle = getReportDesignHandle()
					.getElementFactory().newGridItem(null, columnNum, rowNum);
			return new ReportGridImpl(gridHandle, getReportDesignHandle());
		}

		@SuppressWarnings("unchecked")
		public ReportStyle findStyle(final String styleName) {
			SharedStyleHandle styleHandleFound = null;

			final List<SharedStyleHandle> allStylesReport = getReportDesignHandle()
					.getAllStyles();

			for (final SharedStyleHandle styleHandle : allStylesReport) {
				if (styleHandle.getName().equals(styleName)) {
					styleHandleFound = styleHandle;
				}
			}
			return new ReportStyleImpl(styleHandleFound);
		}

		public ReportDesignHandle getReportDesignHandle() {
			return this.reportDesignHandle;
		}
	}

	public static class ReportStyleImpl implements ReportStyle {

		SharedStyleHandle sharedStyleHandle;

		public ReportStyleImpl(final SharedStyleHandle sharedStyleHandle) {
			super();
			this.sharedStyleHandle = sharedStyleHandle;
		}

		/**
		 * @return the sharedStyleHandle.
		 */
		public SharedStyleHandle getSharedStyleHandle() {
			return this.sharedStyleHandle;
		}

		public void addSolidBorder(final int borderWidth) {
			addSolidTopBorder(borderWidth);
			addSolidBottomBorder(borderWidth);
			addSolidLeftBorder(borderWidth);
			addSolidRightBorder(borderWidth);
		}

		public void addSolidTopBorder(final int borderWidth) {
			try {
				getSharedStyleHandle().setBorderTopStyle(
						DesignChoiceConstants.LINE_STYLE_SOLID);
				getSharedStyleHandle().getBorderTopWidth().setValue(
						borderWidth + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addSolidBottomBorder(final int borderWidth) {
			try {
				getSharedStyleHandle().setBorderBottomStyle(
						DesignChoiceConstants.LINE_STYLE_SOLID);
				getSharedStyleHandle().getBorderBottomWidth().setValue(
						borderWidth + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addSolidLeftBorder(final int borderWidth) {
			try {
				getSharedStyleHandle().setBorderLeftStyle(
						DesignChoiceConstants.LINE_STYLE_SOLID);
				getSharedStyleHandle().getBorderLeftWidth().setValue(
						borderWidth + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addSolidRightBorder(final int borderWidth) {
			try {
				getSharedStyleHandle().setBorderRightStyle(
						DesignChoiceConstants.LINE_STYLE_SOLID);
				getSharedStyleHandle().getBorderRightWidth().setValue(
						borderWidth + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFontColor(final String fontColor) {
			try {
				getSharedStyleHandle().setProperty(StyleHandle.COLOR_PROP,
						fontColor);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFontWeight(final String fontWeight) {
			try {
				getSharedStyleHandle().setProperty(
						StyleHandle.FONT_WEIGHT_PROP, fontWeight);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setBackgroundColor(final String backgroundColor) {
			try {
				getSharedStyleHandle().setProperty(
						StyleHandle.BACKGROUND_COLOR_PROP, backgroundColor);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFont(final String font) {
			try {
				getSharedStyleHandle().setProperty(
						StyleHandle.FONT_FAMILY_PROP, font);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFontSize(final int fontSize) {
			try {
				getSharedStyleHandle().setProperty(StyleHandle.FONT_SIZE_PROP,
						fontSize + DesignChoiceConstants.UNITS_PT);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setTextAlign(final String textAlign) {
			try {
				getSharedStyleHandle().setTextAlign(textAlign);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setNumberFormat(final String numberFormat) {
			try {
				getSharedStyleHandle().setNumberFormat(numberFormat);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setVerticalAlign(final String verticalAlign) {
			try {
				getSharedStyleHandle().setVerticalAlign(verticalAlign);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setWhiteSpaceWrap(final String wrapValue) {
			try {
				getSharedStyleHandle().setWhiteSpace(wrapValue);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}
	}

	public static class ReportItemImpl implements ReportItem {

		ReportItemHandle reportItemHandle;

		public ReportItemImpl(final ReportItemHandle reportItemHandle) {
			super();
			this.reportItemHandle = reportItemHandle;
		}

		/**
		 * @return the reportItemHandle
		 */
		public ReportItemHandle getReportItemHandle() {
			return this.reportItemHandle;
		}

		public void setOnRender(final String scriptOnRender) {
			try {
				getReportItemHandle().setOnRender(scriptOnRender);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setStringProperty(final String propName, final String value) {
			try {
				getReportItemHandle().setStringProperty(propName, value);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setStyleName(final String styleName) {
			try {
				getReportItemHandle().setStyleName(styleName);
			} catch (final StyleException e) {
				throw new ReportingException(e);
			}
		}

		public void setStyle(final ReportStyle style) {
			try {
				getReportItemHandle().setStyle(
						((ReportStyleImpl) style).getSharedStyleHandle());
			} catch (final StyleException e) {
				throw new ReportingException(e);
			}
		}
	}

	protected SessionHandle newSessionHandle() {
		final SessionHandle session = getDesignEngine().newSessionHandle(
				ULocale.ENGLISH);
		session.setResourceFolder(this.birtResourcesPath);
		return session;
	}

	public DesignContext createDesign() {
		final SessionHandle session = newSessionHandle();
		final ReportDesignHandle reportDesignHandle = session.createDesign();
		try {
			addReportProperties(reportDesignHandle);
		} catch (final SemanticException exc) {
			throw new ReportingException(exc);
		}
		return new DesignContextImpl(session, reportDesignHandle);
	}

	public DesignContext openDesign(final String filename) {
		final SessionHandle session = newSessionHandle();
		ReportDesignHandle reportDesignHandle;
		try {
			reportDesignHandle = session.openDesign(filename);
		} catch (final DesignFileException exc) {
			throw new ReportingException(exc);
		}
		return new DesignContextImpl(session, reportDesignHandle);
	}

	/**
	 * Report Properties represent Meta-Data about the report.
	 * 
	 * @param designHandle
	 * @throws SemanticException
	 */
	protected void addReportProperties(
			final ReportDesignHandle reportDesignHandle)
			throws SemanticException {
		reportDesignHandle.setProperty(IDesignElementModel.COMMENTS_PROP,
				"COMMENT: Report generated through CLIPS application");
		reportDesignHandle.setProperty(IModuleModel.AUTHOR_PROP,
				"AUTHOR: CLIPS application");
	}

	public static class ReportGridImpl implements ReportGrid {
		GridHandle gridHandle;
		ReportDesignHandle reportDesignHandle;

		public ReportGridImpl(final GridHandle gridHandle,
				final ReportDesignHandle reportDesignHandle) {
			super();
			this.gridHandle = gridHandle;
			this.reportDesignHandle = reportDesignHandle;
		}

		/**
		 * @return the gridHandle
		 */
		public GridHandle getGridHandle() {
			return this.gridHandle;
		}

		/**
		 * @return the {@link ReportDesignHandle}
		 */
		public ReportDesignHandle getReportDesignHandle() {
			return this.reportDesignHandle;
		}

		public void addTable(final ReportTable table, final int nbRows,
				final int nbColumns) {
			try {
				getGridHandle().getCellContent(nbRows, nbColumns).add(
						((ReportTableImpl) table).getTable());
			} catch (final ContentException e) {
				throw new ReportingException(e);
			} catch (final NameException e) {
				throw new ReportingException(e);
			}
		}

		public void addReportItem(final ReportItem reportItem,
				final int nbRows, final int nbColumns) {
			try {
				getGridHandle().getCellContent(nbRows, nbColumns).add(
						((ReportItemImpl) reportItem).getReportItemHandle());
			} catch (final ContentException e) {
				throw new ReportingException(e);
			} catch (final NameException e) {
				throw new ReportingException(e);
			}
		}

		public void addGrid(final ReportGrid grid, final int nbRows,
				final int nbColumns) {
			try {
				getGridHandle().getCellContent(nbRows, nbColumns).add(
						((ReportGridImpl) grid).getGridHandle());
			} catch (final ContentException e) {
				throw new ReportingException(e);
			} catch (final NameException e) {
				throw new ReportingException(e);
			}
		}

		public void setFirstColumnWidth(final Integer firstColumnWidth) {
			try {
				getGridHandle()
						.getColumns()
						.get(0)
						.setProperty(
								"width",
								firstColumnWidth
										+ DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFirstLineHeight(final Integer firstLineHeight) {
			try {
				getGridHandle()
						.getRows()
						.get(0)
						.setProperty(
								"height",
								firstLineHeight
										+ DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void avoidPageBreakInside() {
			try {
				getGridHandle().setStringProperty(Style.PAGE_BREAK_INSIDE_PROP,
						DesignChoiceConstants.PAGE_BREAK_INSIDE_AVOID);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}
	}

	public static class ReportDataSetImpl implements ReportDataSet {
		ScriptDataSetHandle dataSetHandle;

		public ReportDataSetImpl(final ScriptDataSetHandle dataSetHandle) {
			super();
			this.dataSetHandle = dataSetHandle;
		}

		public ScriptDataSetHandle getDataSetHandle() {
			return this.dataSetHandle;
		}
	}

	public static class ReportTableImpl implements ReportTable {

		TableHandle table;
		ReportDesignHandle reportDesignHandle;

		public ReportTableImpl(final TableHandle table,
				final ReportDesignHandle reportDesignHandle) {
			super();
			this.table = table;
			this.reportDesignHandle = reportDesignHandle;
		}

		public TableHandle getTable() {
			return this.table;
		}

		public ReportDesignHandle getReportDesignHandle() {
			return this.reportDesignHandle;
		}

		public void bindDataSet(final String dataSetName,
				final ReportDataSet reportDataSet) {
			try {
				getTable().setProperty(TableHandle.DATA_SET_PROP, dataSetName);
				// bind the data set columns to the table
				final DataSetHandle dataSetHandle = ((ReportDataSetImpl) reportDataSet)
						.getDataSetHandle();
				@SuppressWarnings("unchecked")
				final List<ResultSetColumn> resultSetCols = dataSetHandle
						.getListProperty(DataSetHandle.RESULT_SET_PROP);
				final PropertyHandle boundCols = getTable().getColumnBindings();
				for (final ResultSetColumn rsHandle : resultSetCols) {
					final ComputedColumn col = StructureFactory
							.createComputedColumn();
					col.setName(rsHandle.getColumnName());
					col.setExpression("dataSetRow[\""
							+ rsHandle.getColumnName() + "\"]");
					boundCols.addItem(col);
				}
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void bindListDataSet(final String listDataSetName) {
			try {
				getTable().setProperty(TableHandle.DATA_SET_PROP,
						listDataSetName);
				final ComputedColumn col = StructureFactory
						.createComputedColumn();
				col.setName("value");
				col.setExpression("dataSetRow[\"value\"]");
				final PropertyHandle boundCols = getTable().getColumnBindings();
				boundCols.addItem(col);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}

		}

		public void bindColumn(final int columnIndex,
				final ReportItem headerItem, final ReportItem dataItem,
				final String headerStyle, final ReportStyle dataStyle) {
			bindHeaderCell(columnIndex, headerItem, headerStyle);
			bindDetailCell(0, columnIndex, dataItem, dataStyle);
		}

		public void bindDetailCell(final int detailRowIndex,
				final int columnIndex, final ReportItem dataItem,
				final ReportStyle dataStyle) {
			try {
				final RowHandle detail = (RowHandle) getTable().getDetail()
						.get(detailRowIndex);
				final CellHandle detailCell = (CellHandle) detail.getCells()
						.get(columnIndex);
				detailCell.setStyle(((ReportStyleImpl) dataStyle)
						.getSharedStyleHandle());
				detailCell.getContent().add(
						((ReportItemImpl) dataItem).getReportItemHandle());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void bindHeaderCell(final int columnIndex,
				final ReportItem headerItem, final String headerStyle) {
			try {
				final RowHandle header = (RowHandle) getTable().getHeader()
						.get(0);
				final CellHandle headerCell = (CellHandle) header.getCells()
						.get(columnIndex);
				headerCell.setStyleName(headerStyle);
				headerCell.getContent().add(
						((ReportItemImpl) headerItem).getReportItemHandle());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addToBody() {
			try {
				getReportDesignHandle().getBody().add(getTable());
			} catch (final ContentException e) {
				throw new ReportingException(e);
			} catch (final NameException e) {
				throw new ReportingException(e);
			}
		}

		public void sortWithAGivenColumn(final String columnName,
				final boolean isAscending) {
			final String sortingDirection = isAscending ? "asc" : "desc";
			final SortKey key = StructureFactory.createSortKey();
			key.setKey("row[\"" + columnName + "\"]");
			key.setDirection(sortingDirection);
			final PropertyHandle sort = getTable().getPropertyHandle(
					TableHandle.SORT_PROP);
			try {
				sort.addItem(key);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public ReportTableGroup addAGroup(final String columnName) {
			final ElementFactory elementFactory = getReportDesignHandle()
					.getElementFactory();
			final TableGroupHandle tableGroup = elementFactory.newTableGroup();
			try {
				tableGroup.setKeyExpr("row[\"" + columnName + "\"]");
				tableGroup.setSortDirection("asc");
				getTable().getGroups().add(tableGroup);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
			return new ReportTableGroupImpl(tableGroup, getReportDesignHandle());
		}

		public void addSumAggregation(final String aggregationName,
				final String aggregationColumnName) {
			final ComputedColumn computedColumn = createComputedColumn(
					aggregationName, aggregationColumnName);
			try {
				getTable().getColumnBindings().addItem(computedColumn);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		private ComputedColumn createComputedColumn(
				final String aggregationName, final String aggregationColumnName) {
			final ComputedColumn computedColumn = StructureFactory
					.createComputedColumn();
			computedColumn.setName(aggregationName);
			computedColumn.setExpression("dataSetRow[\""
					+ aggregationColumnName + "\"]");
			computedColumn
					.setAggregateFunction(DesignChoiceConstants.AGGREGATION_FUNCTION_SUM);
			return computedColumn;
		}

		public void addReportItemInTableFooter(final ReportItem reportItem,
				final Integer index, final ReportStyle style,
				final Integer footerIndex) {
			try {
				final RowHandle footer = (RowHandle) getTable().getFooter()
						.get(footerIndex);
				final CellHandle cell = (CellHandle) footer.getCells().get(
						index);
				cell.setStyle(((ReportStyleImpl) style).getSharedStyleHandle());
				cell.getContent().add(
						((ReportItemImpl) reportItem).getReportItemHandle());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addSumAggregationOnGroup(final String aggregationName,
				final String aggregationColumnName, final String groupName) {
			final ComputedColumn computedColumn = createComputedColumn(
					aggregationName, aggregationColumnName);
			computedColumn.setAggregateOn(groupName);
			try {
				getTable().getColumnBindings().addItem(computedColumn);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}

		}

		public void setColumnWidth(final int columnIndex, final Integer width) {
			try {
				getTable()
						.getColumns()
						.get(columnIndex)
						.setProperty("width",
								width + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void addSubTable(final int columnIndex,
				final ReportItem headerItem, final String tableHeaderCellStyle,
				final ReportStyle tableCellContentStyle,
				final ReportTable subTable) {
			try {
				final RowHandle header = (RowHandle) getTable().getHeader()
						.get(0);
				final RowHandle detail = (RowHandle) getTable().getDetail()
						.get(0);

				final CellHandle headerCell = (CellHandle) header.getCells()
						.get(columnIndex);
				headerCell.setStyleName(tableHeaderCellStyle);
				headerCell.getContent().add(
						((ReportItemImpl) headerItem).getReportItemHandle());

				final CellHandle detailCell = (CellHandle) detail.getCells()
						.get(columnIndex);
				detailCell.setStyle(((ReportStyleImpl) tableCellContentStyle)
						.getSharedStyleHandle());
				detailCell.getContent().add(
						((ReportTableImpl) subTable).getTable());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}

		}

		public void addHighLightRules(final ReportStyle styleEven,
				final ReportStyle styleOdd) {
			try {
				final RowHandle detail = (RowHandle) getTable().getDetail()
						.get(0);

				final HighlightRule highlightRuleEven = new HighlightRule();
				highlightRuleEven.setTestExpression("row.__rownum % 2");
				highlightRuleEven.setOperator("eq");
				highlightRuleEven.setValue1(Arrays.asList(new Integer(0)));
				highlightRuleEven.setStyle(((ReportStyleImpl) styleEven)
						.getSharedStyleHandle());

				final HighlightRule highlightRuleOdd = new HighlightRule();
				highlightRuleOdd.setTestExpression("row.__rownum % 2");
				highlightRuleOdd.setOperator("eq");
				highlightRuleOdd.setValue1(Arrays.asList(new Integer(1)));
				highlightRuleOdd.setStyle(((ReportStyleImpl) styleOdd)
						.getSharedStyleHandle());

				final PropertyHandle propertyHandle = detail
						.getPropertyHandle("highlightRules");
				propertyHandle.addItem(highlightRuleEven);
				propertyHandle.addItem(highlightRuleOdd);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFooterStyle(final ReportStyle footerStyle,
				final Integer footerIndex) {
			final RowHandle footer = (RowHandle) getTable().getFooter().get(
					footerIndex);
			try {
				footer.setStyle(((ReportStyleImpl) footerStyle)
						.getSharedStyleHandle());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setHeaderHeight(final int indexOfHeader, final int height) {
			try {
				getTable()
						.getHeader()
						.get(indexOfHeader)
						.setProperty("height",
								height + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}

		public void setFooterHeight(final int indexOfFooter, final int height) {
			try {
				getTable()
						.getFooter()
						.get(indexOfFooter)
						.setProperty("height",
								height + DesignChoiceConstants.UNITS_PX);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}
	}

	public static class ReportTableGroupImpl implements ReportTableGroup {

		TableGroupHandle tableGroup;
		ReportDesignHandle reportDesignHandle;

		public ReportTableGroupImpl(final TableGroupHandle tableGroup,
				final ReportDesignHandle reportDesignHandle) {
			super();
			this.tableGroup = tableGroup;
			this.reportDesignHandle = reportDesignHandle;
		}

		public TableGroupHandle getTableGroup() {
			return this.tableGroup;
		}

		public ReportDesignHandle getReportDesignHandle() {
			return this.reportDesignHandle;
		}

		public void addFooter(final int nbColumn,
				final ReportStyle footerStyle, final int rowHeightInPixels) {
			final RowHandle groupFooter = getReportDesignHandle()
					.getElementFactory().newTableRow(nbColumn);
			try {
				if (rowHeightInPixels > 0) {
					final String heightProperty = rowHeightInPixels
							+ DesignChoiceConstants.UNITS_PX;
					groupFooter.setProperty("height", heightProperty);

				}
				if (footerStyle != null) {
					groupFooter.setStyle(((ReportStyleImpl) footerStyle)
							.getSharedStyleHandle());
				}
				getTableGroup().getFooter().add(groupFooter);

			} catch (final ContentException e) {
				throw new ReportingException(e);
			} catch (final NameException e) {
				throw new ReportingException(e);
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}

		}

		public String getName() {
			return getTableGroup().getName();
		}

		public void addReportItemInTableGroupFooter(
				final ReportItem reportItem, final Integer index,
				final ReportStyle style) {
			try {
				final RowHandle footer = (RowHandle) getTableGroup()
						.getFooter().get(0);
				final CellHandle cell = (CellHandle) footer.getCells().get(
						index);
				cell.setStyle(((ReportStyleImpl) style).getSharedStyleHandle());
				cell.getContent().add(
						((ReportItemImpl) reportItem).getReportItemHandle());
			} catch (final SemanticException e) {
				throw new ReportingException(e);
			}
		}
	}

	public ReportingTask createTask(final InputStream openStream) {
		IReportRunnable design;
		try {

			/* TODO Separate Run and Render Task. */

			design = getEngine().openReportDesign(openStream);
			final IRunAndRenderTask task = getEngine().createRunAndRenderTask(
					design);
			return new ReportingTaskImpl(task);

		} catch (final EngineException exc) {
			throw new ReportingException(exc);
		}
	}

}
