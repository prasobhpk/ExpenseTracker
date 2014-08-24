package com.pk.et.infra.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializer for TimeProviderProvider
 * 
 */
@Service
public class TimeProviderProviderInitializer {

	@Autowired
	public void setTimeProvider(final TimeProvider TimeProvider) {
		TimeProviderProvider.getInstance().setTimeProvider(TimeProvider);
	}

}
