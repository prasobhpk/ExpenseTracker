package com.pk.et.infra.jaxb;

import javax.xml.bind.JAXBContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pk.et.infra.rest.providers.ContextResolverForMOXY;

public class MoxyMarshaller extends Jaxb2Marshaller {
	@Autowired
	@Qualifier("contextResolverForMOXY")
	private ContextResolverForMOXY contextResolverForMOXY;

	public MoxyMarshaller() {
		setContextPath("test");
	}

	@Override
	public synchronized JAXBContext getJaxbContext() {
		return this.contextResolverForMOXY.getContext();
	}
}
