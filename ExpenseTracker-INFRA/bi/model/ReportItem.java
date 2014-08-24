package com.pk.et.infra.model.bi;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@MappedSuperclass
public abstract class ReportItem extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private String name;
	private String height;
	private String width;
	private String containerName;
	private int gridRow;
	private int gridCell;

	@Override
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "REPORT_ITEM_NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "HEIGHT")
	public String getHeight() {
		return this.height;
	}

	public void setHeight(final String height) {
		this.height = height;
	}

	@Column(name = "WIDTH")
	public String getWidth() {
		return this.width;
	}

	public void setWidth(final String width) {
		this.width = width;
	}

	@Column(name = "GRID_ROW")
	public int getGridRow() {
		return this.gridRow;
	}

	public void setGridRow(final int gridRow) {
		this.gridRow = gridRow;
	}

	@Column(name = "GRID_CELL")
	public int getGridCell() {
		return this.gridCell;
	}

	public void setGridCell(final int gridCell) {
		this.gridCell = gridCell;
	}

	@Column(name = "CONTAINER")
	public String getContainerName() {
		return this.containerName;
	}

	public void setContainerName(final String containerName) {
		this.containerName = containerName;
	}

}
