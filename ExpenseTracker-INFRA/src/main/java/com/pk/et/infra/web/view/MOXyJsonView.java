package com.pk.et.infra.web.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.XMLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import com.pk.et.infra.model.Model;
import com.pk.et.infra.rest.providers.ContextResolverForMOXY;
//@Component("MOXyJsonView")
public class MOXyJsonView extends AbstractView {
	
	@Autowired
	@Qualifier("contextResolverForMOXY")
	private ContextResolverForMOXY contextResolverForMOXY;
	/**
	 * Default content type. Overridable as bean property.
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/json";
	
	private String attributePrefix = null;
	private boolean formattedOutput = false;
	private boolean includeRoot = false;
	private boolean marshalEmptyCollections = true;
	private String valueWrapper;
	private boolean disableCaching = true;
	private boolean extractValueFromSingleKeyModel = false;
	private Set<String> modelKeys;
	private Map<String, String> namespacePrefixMapper;
    private char namespaceSeperator = XMLConstants.DOT;
	
	public MOXyJsonView(){
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	public String getAttributePrefix() {
		return attributePrefix;
	}

	public void setAttributePrefix(String attributePrefix) {
		this.attributePrefix = attributePrefix;
	}

	public boolean isFormattedOutput() {
		return formattedOutput;
	}

	public void setFormattedOutput(boolean formattedOutput) {
		this.formattedOutput = formattedOutput;
	}

	public boolean isIncludeRoot() {
		return includeRoot;
	}

	public void setIncludeRoot(boolean includeRoot) {
		this.includeRoot = includeRoot;
	}

	public boolean isMarshalEmptyCollections() {
		return marshalEmptyCollections;
	}

	public void setMarshalEmptyCollections(boolean marshalEmptyCollections) {
		this.marshalEmptyCollections = marshalEmptyCollections;
	}

	public String getValueWrapper() {
		return valueWrapper;
	}

	public void setValueWrapper(String valueWrapper) {
		this.valueWrapper = valueWrapper;
	}

	public boolean isDisableCaching() {
		return disableCaching;
	}

	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
	}

	public boolean isExtractValueFromSingleKeyModel() {
		return extractValueFromSingleKeyModel;
	}

	public void setExtractValueFromSingleKeyModel(
			boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}
	/**
	 * Set the attribute in the model that should be rendered by this view.
	 * When set, all other model attributes will be ignored.
	 */
	public void setModelKey(String modelKey) {
		this.modelKeys = Collections.singleton(modelKey);
	}
	
	/**
	 * Set the attributes in the model that should be rendered by this view.
	 * When set, all other model attributes will be ignored.
	 */
	public void setModelKeys(Set<String> modelKeys) {
		this.modelKeys = modelKeys;
	}

	/**
	 * Return the attributes in the model that should be rendered by this view.
	 */
	public Set<String> getModelKeys() {
		return this.modelKeys;
	}
	
	
	public Map<String, String> getNamespacePrefixMapper() {
		return namespacePrefixMapper;
	}

	public void setNamespacePrefixMapper(Map<String, String> namespacePrefixMapper) {
		this.namespacePrefixMapper = namespacePrefixMapper;
	}

	public char getNamespaceSeperator() {
		return namespaceSeperator;
	}

	public void setNamespaceSeperator(char namespaceSeperator) {
		this.namespaceSeperator = namespaceSeperator;
	}

	@Override
	protected void prepareResponse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");
		if (this.disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object value = filterModel(model);
//		Class<?> domainClass = getDomainClass(genericType);
        JAXBContext jaxbContext = contextResolverForMOXY.getContext();
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, DEFAULT_CONTENT_TYPE);
        marshaller.setProperty(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, attributePrefix);
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
        marshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, marshalEmptyCollections);
        marshaller.setProperty(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, namespaceSeperator);
        marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, valueWrapper);
        marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, namespacePrefixMapper);

//        Map<String, String> mediaTypeParameters = mediaType.getParameters();
//        if(mediaTypeParameters.containsKey(CHARSET)) {
//            String charSet = mediaTypeParameters.get(CHARSET);
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, charSet);
//        }

        marshaller.marshal(value, response.getOutputStream());
	}
	
	/**
	 * Filters out undesired attributes from the given model.
	 * The return value can be either another {@link Map} or a single value object.
	 * <p>The default implementation removes {@link BindingResult} instances and entries
	 * not included in the {@link #setRenderedAttributes renderedAttributes} property.
	 * @param model the model, as passed on to {@link #renderMergedOutputModel}
	 * @return the object to be rendered
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		if(this.extractValueFromSingleKeyModel && result.size() == 1){
			return result.values().iterator().next();
		}
		else{
			Model mod=new Model();
			mod.setData(result);
			return mod;
		}
	}
	


}
