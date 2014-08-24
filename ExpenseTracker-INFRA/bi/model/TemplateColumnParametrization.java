package com.pk.et.infra.model.bi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.Message;
import com.pk.et.infra.model.MessageId;
import com.pk.et.infra.model.MessageText;

@Entity
@Table(name = "TEMPL_COLUMN_PARAM")
public class TemplateColumnParametrization extends
		BaseEntity<TemplateColumnParametrizationId> {
	final static String TEMPLATE_PARAMETRIZATION = "TEMPLATE_PARAMETRIZATION";
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "reportItem", column = @Column(name = "REPORT_ITEM", nullable = false, length = 1020)),
			@AttributeOverride(name = "templateParametrization", column = @Column(name = TemplateColumnParametrization.TEMPLATE_PARAMETRIZATION, nullable = false)),
			@AttributeOverride(name = "reportColumn", column = @Column(name = "TEMPLATE_COLUMN", nullable = false)) })
	private TemplateColumnParametrizationId id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumns({
			@JoinColumn(name = "REPORT_ITEM", referencedColumnName = "REPORT_ITEM", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = TemplateColumnParametrization.TEMPLATE_PARAMETRIZATION, referencedColumnName = TemplateColumnParametrization.TEMPLATE_PARAMETRIZATION, nullable = false, insertable = false, updatable = false) })
	private ReportItemParametrization reportItem;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "key1", column = @Column(name = "TEMPLATE_COLUMN", nullable = false, insertable = false, updatable = false)),
			@AttributeOverride(name = "key2", column = @Column(name = TemplateColumnParametrization.TEMPLATE_PARAMETRIZATION, nullable = false, insertable = false, updatable = false)) })
	private MessageId labelMessageId;

	@ManyToOne
	@JoinColumn(name = "TEMPLATE_COLUMN", nullable = false, insertable = false, updatable = false)
	private TemplateColumn column;

	/**
	 * This is set when the message the column is referring to does not exist,
	 * i.e. we are doing a preview of a not saved report.
	 */
	@Transient
	private MessageText customMessageText;

	@Column(name = "HIDE_COLUMN")
	private boolean hideColumn;

	@Column(name = "INDEX_POSITION")
	private int indexPosition;

	@Column(name = "MANDATORY")
	private boolean isMandatory;

	public TemplateColumnParametrization() {
	}

	@Override
	public TemplateColumnParametrizationId getId() {
		return this.id;
	}

	@Override
	public void setId(final TemplateColumnParametrizationId id) {
		this.id = id;
	}

	public ReportItemParametrization getReportItem() {
		return this.reportItem;
	}

	public void setReportItem(final ReportItemParametrization reportItem) {
		this.reportItem = reportItem;
	}

	public TemplateColumn getColumn() {
		return this.column;
	}

	public void setColumn(final TemplateColumn column) {
		this.column = column;
	}

	public MessageText getCustomMessageText() {
		return this.customMessageText;
	}

	public void setCustomMessageText(final MessageText customMessageText) {
		this.customMessageText = customMessageText;
	}

	/**
	 * Create method.
	 * 
	 * @return A new column.
	 */
	public static TemplateColumnParametrization create() {
		return new TemplateColumnParametrization();
	}

	public static TemplateColumnParametrization create(
			final ReportItemParametrization reportItemParametrization,
			final TemplateColumn templateColumn) {
		final TemplateColumnParametrization templateColumnParametrization = new TemplateColumnParametrization();
		final TemplateColumnParametrizationId id = new TemplateColumnParametrizationId(
				reportItemParametrization.getReportItem().getId(),
				reportItemParametrization.getTemplateParametrization().getId(),
				templateColumn.getName());
		templateColumnParametrization.setId(id);
		return templateColumnParametrization;
	}

	public void setIndexPosition(final int indexPosition) {
		this.indexPosition = indexPosition;
	}

	public int getIndexPosition() {
		return this.indexPosition;
	}

	/**
	 * @return the hideColumn
	 */
	public boolean isHideColumn() {
		return this.hideColumn;
	}

	/**
	 * @param hideColumn
	 *            the hideColumn to set
	 */
	public void setHideColumn(final boolean hideColumn) {
		this.hideColumn = hideColumn;
	}

	/**
	 * Retrieve the message ids to use in the report. Retrieve the custom
	 * message id and the default message id.
	 * 
	 * @return the message ids list to use in the report.
	 */
	public List<MessageId> retrieveLabelMessageIds() {
		final List<MessageId> messageIds = new ArrayList<MessageId>();

		final Message columnLabelMessage = getColumn().getLabelMessage();
		if (columnLabelMessage != null && columnLabelMessage.getId() != null) {
			messageIds.add(columnLabelMessage.getId());
		}

		final MessageId lblMessageId = getLabelMessageId();
		if (lblMessageId != null) {
			messageIds.add(0, lblMessageId);
		} else {
			messageIds.add(0, columnLabelMessage.getId());
		}

		return messageIds;
	}

	@Override
	public BaseEntity<TemplateColumnParametrizationId> clone() {
		final TemplateColumnParametrization clone = (TemplateColumnParametrization) super
				.clone();
		clone.setId(new TemplateColumnParametrizationId(
				getId().getReportItem(), null, getId().getReportColumn()));
		if (this.labelMessageId != null) {
			final MessageId messageId = new MessageId(
					this.labelMessageId.getKey1(), null);
			clone.labelMessageId = messageId;
		}
		return clone;
	}

	public MessageId getLabelMessageId() {
		return this.labelMessageId;
	}

	public void setLabelMessageId(final MessageId labelMessageId) {
		this.labelMessageId = labelMessageId;
	}
}
