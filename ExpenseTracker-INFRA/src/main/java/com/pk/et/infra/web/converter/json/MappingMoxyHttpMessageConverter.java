package com.pk.et.infra.web.converter.json;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.XMLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.pk.et.infra.rest.providers.ContextResolverForMOXY;

public class MappingMoxyHttpMessageConverter extends AbstractHttpMessageConverter<Object>{

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private boolean prefixJson = false;
	private String attributePrefix = null;
	private boolean formattedOutput = false;
	private boolean includeRoot = false;
	private boolean marshalEmptyCollections = true;
	private String valueWrapper;
	private boolean disableCaching = true;
	private Map<String, String> namespacePrefixMapper;
    private char namespaceSeperator = XMLConstants.DOT;
	
    @Autowired
    @Qualifier("contextResolverForMOXY")
    private ContextResolverForMOXY contextResolverForMOXY;
    /**
     * Construct a new {@code BindingJacksonHttpMessageConverter}.
     */
    public MappingMoxyHttpMessageConverter() {
    	super(new org.springframework.http.MediaType("application", "json", DEFAULT_CHARSET));
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



	/**
	 * Indicate whether the JSON output by this view should be prefixed with "{} &&". Default is false.
	 * <p>Prefixing the JSON string in this manner is used to help prevent JSON Hijacking.
	 * The prefix renders the string syntactically invalid as a script so that it cannot be hijacked.
	 * This prefix does not affect the evaluation of JSON, but if JSON validation is performed on the
	 * string, the prefix would need to be ignored.
	 */
	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}


	@Override
	public boolean canRead(Class<?> clazz, org.springframework.http.MediaType mediaType) {
		return true;
	}

	@Override
	public boolean canWrite(Class<?> clazz, org.springframework.http.MediaType mediaType) {
		return true;
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canRead/Write instead
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		try {
            JAXBContext jaxbContext = contextResolverForMOXY.getContext();
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, attributePrefix);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_PREFIX_MAPPER, namespacePrefixMapper);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_SEPARATOR, namespaceSeperator);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_VALUE_WRAPPER, valueWrapper);

            StreamSource jsonSource;
            jsonSource = new StreamSource(inputMessage.getBody());

            return unmarshaller.unmarshal(jsonSource, clazz).getValue();
		}
		catch (IOException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		try {
				//Class<?> domainClass = getDomainClass(genericType);
	            JAXBContext jaxbContext = contextResolverForMOXY.getContext();
	            Marshaller marshaller = jaxbContext.createMarshaller();
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
	            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	            marshaller.setProperty(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, attributePrefix);
	            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	            marshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, marshalEmptyCollections);
	            marshaller.setProperty(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, namespaceSeperator);
	            marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, valueWrapper);
	            marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, namespacePrefixMapper);

	            marshaller.marshal(object, outputMessage.getBody());
		}
		catch (IOException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
