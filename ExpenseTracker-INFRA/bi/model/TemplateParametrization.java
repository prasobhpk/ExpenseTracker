package com.pk.et.infra.model.bi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pk.et.infra.model.BaseEntity;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

@Entity
@Table(name = "TEMPLATE_PARAMETRIZATION")
public class TemplateParametrization extends BaseEntity<String> {

	static final String LOAD_TEMPLATE_PARAM = "LOAD_TEMPLATE_PARAM";
	static final String FIND_PUBLIC_OR_CUSTOM_TEMPLATE_PARAM = "FIND_PUBLIC_OR_CUSTOM_TEMPLATE_PARAM";
	public static final String FIND_DEFAULT_PUBLIC_OR_CUSTOM_TEMPLATE_PARAM = "FIND_DEFAULT_PUBLIC_OR_CUSTOM_TEMPLATE_PARAM";
	static final String FIND_BY_REPORT_TYPE = "FIND_BY_REPORT_TYPE";

	@Id
	@Column(name = "NAME", unique = true, nullable = false)
	private String id;

	@Column(name = "NAME", unique = true, nullable = false, updatable = false, insertable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEMPLATE")
	private Template template;

	@Column(name = "IS_DEFAULT")
	private boolean isDefault;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "PARENT_TEMPLATE")
	private TemplateParametrization parentTemplate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "templateParametrization", cascade = { CascadeType.REMOVE })
	private Set<ReportItemParametrization> reportItems;

	/**
	 * Default constructor should only be used by the exporter. Use
	 * {@link #TemplateParametrization(String)} to construct a
	 * {@link TemplateParametrization} with a given name used as its primary
	 * key.
	 */
	public TemplateParametrization() {
	}

	/**
	 * Create a {@link TemplateParametrization} entity and set is name
	 * attribute. Will also set its id as the name is used as the primary key.
	 * 
	 * @param name
	 *            Name of the template.
	 */
	public TemplateParametrization(final String name) {
		this.name = name;
		// this.id = new TemplateParametrizationId(name);
		this.id = name;
	}

	public String getName() {
		return this.name;
	}

	public Template getTemplate() {
		return this.template;
	}

	public void setTemplate(final Template template) {
		this.template = template;
	}

	public boolean isIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(final boolean isDefault) {
		this.isDefault = isDefault;
	}

	public TemplateParametrization getParentTemplate() {
		return this.parentTemplate;
	}

	public void setParentTemplate(final TemplateParametrization parentTemplate) {
		this.parentTemplate = parentTemplate;
	}

	public Set<ReportItemParametrization> getReportItems() {
		return Collections.unmodifiableSet(this.reportItems);
	}

	public void setReportItems(final Set<ReportItemParametrization> reportItems) {
		this.reportItems = reportItems;
	}

	/**
	 * Associates a grid to the template.
	 * 
	 * @param grid
	 *            the grid to add.
	 */
	public void addReportItem(final ReportItemParametrization item) {
		if (!this.reportItems.contains(item)) {
			this.reportItems.add(item);
		}
	}

	/**
	 * Removes a grid from the template.
	 * 
	 * @param item
	 *            the grid to remove.
	 */
	public void removeGrid(final ReportItemParametrization item) {
		if (this.reportItems.contains(item)) {
			this.reportItems.remove(item);
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Setting the id will also set the {@link #name} attribute used as the
	 * primary key.
	 */
	@Override
	public void setId(final String id) {
		this.id = id;
		this.name = id;
	}

	/**
	 * Create method.
	 * 
	 * @return A new template.
	 */
	public static TemplateParametrization create() {
		final TemplateParametrization templateParametrization = new TemplateParametrization();
		templateParametrization.reportItems = new HashSet<ReportItemParametrization>();
		return templateParametrization;
	}

	/**
	 * Retrieve all the message ids that belongs to the template
	 * parametrization. We retrieve all the message ids linked to the template,
	 * all the message ids linked to the template column (template column or
	 * template column parametrization) label and all the message ids that
	 * belong to the translated columns (client way for example).
	 * 
	 * @return all the message ids that belongs to the template parametrization.
	 */
	/*
	 * public List<MessageId> retrieveAllMessageIds( final
	 * ColumnParametrizationsAdder columnParametrizationDecorator) { final
	 * List<MessageId> allMessageIds = new ArrayList<MessageId>();
	 * 
	 * for (final Message templateMessage : getTemplate().getMessages()) {
	 * allMessageIds.add(templateMessage.getId()); }
	 * 
	 * for (final TemplateGridParametrization templateGridParametrization :
	 * getGrids()) { for (final TemplateColumnParametrization
	 * templateColumnParametrization : templateGridParametrization
	 * .getColumns(columnParametrizationDecorator)) {
	 * allMessageIds.addAll(templateColumnParametrization
	 * .retrieveLabelMessageIds()); final TemplateColumn column =
	 * templateColumnParametrization .getColumn(); if
	 * (column.isValueTranslated()) {
	 * allMessageIds.addAll(Message.findMessageIdsByContext( this.entityManager,
	 * column.getName())); } } } return allMessageIds; }
	 */

	@Override
	public TemplateParametrization clone() {
		TemplateParametrization clone = null;
		clone = (TemplateParametrization) super.clone();
		clone.reportItems = new HashSet<ReportItemParametrization>();
		final Set<ReportItemParametrization> itemsToClone = getReportItems();
		for (final ReportItemParametrization templateGridParametrization : itemsToClone) {
			final ReportItemParametrization itemCloned = templateGridParametrization
					.clone();
			clone.addReportItem(itemCloned);
			itemCloned.setTemplateParametrization(clone);
		}
		return clone;
	}

}
