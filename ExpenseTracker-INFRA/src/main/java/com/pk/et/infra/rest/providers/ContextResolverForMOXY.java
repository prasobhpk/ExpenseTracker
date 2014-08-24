package com.pk.et.infra.rest.providers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @author Prasobh
 */

@Provider
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public final class ContextResolverForMOXY implements
		ContextResolver<JAXBContext> {
	@PersistenceUnit
	private EntityManagerFactory emf;

	private JAXBContext context;
	@Value("#{oxmBindings['oxm.bindings'].split(';')}")
	private List<String> oxmBindings;

	@Value("#{oxmBindings['oxm.context'].split(';')}")
	private List<String> boundedNonEntityClasses;

	public ContextResolverForMOXY() throws Exception {
		// this.context =
		// JAXBContextFactory.createContext(_touchables.toArray(new
		// Class[_touchables.size()]), getProperties());
	}

	/**
	 * Looks like we need to worry about accidental data binding for types we
	 * shouldn't be handling. This is probably not a very good way to do it, but
	 * let's start by listing things we are handling.
	 */
	public final Set<Class<?>> _touchables = new HashSet<Class<?>>();

	@PostConstruct
	public void init() throws Exception {
		// adding all entity classes to context
		final Metamodel model = this.emf.getMetamodel();
		final Set<EntityType<?>> types = model.getEntities();
		for (final EntityType<?> type : types) {
			this._touchables.add(type.getJavaType());
		}

		// adding non entity classes to context
		for (final String boundedClass : this.boundedNonEntityClasses) {
			try {
				if (!"".equals(boundedClass)) {
					this._touchables.add(Class.forName(boundedClass));
				}
			} catch (final ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.context = JAXBContextFactory.createContext(
				this._touchables.toArray(new Class[this._touchables.size()]),
				getProperties());

	}

	public JAXBContext getContext(final Class<?> objectType) {
		try {
			return (this._touchables.contains(objectType)) ? this.context
					: JAXBContextFactory.createContext(
							new Class[] { objectType }, getProperties());
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JAXBContext getContext() {
		return this.context;
	}

	private Map<String, Object> getProperties() {
		final Map<String, Object> properties = new HashMap<String, Object>(1);
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE,
				this.oxmBindings);
		return properties;
	}
}
