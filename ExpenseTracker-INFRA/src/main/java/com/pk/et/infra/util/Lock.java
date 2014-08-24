package com.pk.et.infra.util;

import com.pk.et.infra.service.impl.LockService;

/**
 * Lock values used to avoid concurrent actions. These values should be used
 * with the {@link LockService}.
 */
public enum Lock {

	/**
	 * Lock took when importing mtm data.
	 */
	MTM_IMPORT("mtmFeederJob");

	private Lock(final String name) {
		this.name = name;
	}

	private final String name;

	public String getName() {
		return this.name;
	}

}
