package com.pk.et.infra.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.JoinFetch;


/**
 * Application specific class for an extensible type.
 * All extensible classes should extend this class.
 * @author Prasobh PK
 */
@MappedSuperclass
public abstract class Extensible {

	/**
	 * Extended attributes
	 */
	protected Map<String, Object> attributes = new HashMap<String, Object>();
	protected Map<Extension, ExtensionValue> extensions = new HashMap<Extension, ExtensionValue>();
	
	@SuppressWarnings("unchecked")
	public <T> T getExtension(String attributeName) {
		// return (T) this.attributes.get(attributeName);
		Extension ext = new Extension(getClass().getSimpleName(), attributeName);
		ExtensionValue extVal = extensions.get(ext);
		return (T) (extVal != null ? extVal.getValue() : "");
	}

	public Object setExtension(String attributeName, Object value) {
		return this.attributes.put(attributeName, value);
	}

	@Transient
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@MapKey(name = "extension")
	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinFetch
	public Map<Extension, ExtensionValue> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<Extension, ExtensionValue> extensions) {
		this.extensions = extensions;
	}
}
