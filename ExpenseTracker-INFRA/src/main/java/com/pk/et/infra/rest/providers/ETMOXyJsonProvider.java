package com.pk.et.infra.rest.providers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

/**
 * 
 * @author Prasobh
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ETMOXyJsonProvider extends MOXyJsonProvider {

}