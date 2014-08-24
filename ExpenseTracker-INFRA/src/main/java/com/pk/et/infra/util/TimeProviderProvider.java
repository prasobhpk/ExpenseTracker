package com.pk.et.infra.util;

/**
 * Proxy on TimeProvider
 * 
 */
public class TimeProviderProvider {

	private static TimeProviderProvider INSTANCE = new TimeProviderProvider();

	private TimeProvider TimeProvider;

	public static TimeProviderProvider getInstance() {
		return INSTANCE;
	}

	public TimeProvider getTimeProvider() {
		return this.TimeProvider;
	}

	public void setTimeProvider(final TimeProvider TimeProvider) {
		this.TimeProvider = TimeProvider;
	}

}
