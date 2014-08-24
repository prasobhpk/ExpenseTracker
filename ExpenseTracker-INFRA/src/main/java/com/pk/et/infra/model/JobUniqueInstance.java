package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.TimeProvider;
import com.pk.et.infra.util.TimeProviderProvider;

/**
 * Entity representing the state of a job's execution requested via the jms
 * queue.
 */
@Entity
// @Converts(value = {
// @Convert(attributeName = "startDate", converter =
// JodaDateTimeConverter.class),
// @Convert(attributeName = "endDate", converter = JodaDateTimeConverter.class)
// })
@Table(name = "JOB_UNIQUE_INSTANCE")
public class JobUniqueInstance extends BaseEntity<Long> {

	private static final long serialVersionUID = ETConstants.etVersion;

	protected static final String JOB_UNIQUE_INSTANCE_FIND_BY_NAME = "JOB_UNIQUE_INSTANCE_FIND_BY_NAME";

	private final TimeProvider timeProvider = TimeProviderProvider
			.getInstance().getTimeProvider();

	private Long id;

	private String name;

	private String owner;

	private DateTime startDate;

	private DateTime endDate;

	private JobStatus status;

	public static JobUniqueInstance create() {
		final JobUniqueInstance jobInstance = new JobUniqueInstance();
		return jobInstance;
	}

	@Override
	@Id
	@GeneratedValue(generator = "JOB_UNIQ_GEN", strategy = GenerationType.TABLE)
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "OWNER")
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	@Column(name = "START_DATE", columnDefinition = "TIMESTAMP")
	public DateTime getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final DateTime startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", columnDefinition = "TIMESTAMP")
	public DateTime getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final DateTime endDate) {
		this.endDate = endDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	public JobStatus getStatus() {
		return this.status;
	}

	public void setStatus(final JobStatus status) {
		this.status = status;
	}

	@Transient
	public TimeProvider getTimeProvider() {
		return this.timeProvider;
	}

	/**
	 * Set to COMPLETED the status if it's a success, ERROR otherwise.
	 * 
	 * @param isSuccess
	 *            the status.
	 */
	public void updateTerminalStatus(final boolean isSuccess) {
		if (isSuccess) {
			setStatus(JobStatus.COMPLETED);
		} else {
			setStatus(JobStatus.ERROR);
		}

		setEndDate(this.timeProvider.getCurrentDateTime());
	}

	public boolean isRequestable() {
		return this.status.isRequestable();
	}

	@Override
	public String toString() {
		return "JobUniqueInstance [id=" + this.id + ", name=" + this.name
				+ ", owner=" + this.owner + ", startDate=" + this.startDate
				+ ", endDate=" + this.endDate + ", status=" + this.status + "]";
	}

}
