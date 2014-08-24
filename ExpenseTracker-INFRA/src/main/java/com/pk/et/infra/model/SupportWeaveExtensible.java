package com.pk.et.infra.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * Only to support weaving the AuditedObject. A MappedSuperClass 
 * does not get weaved (woven?) if there is no entity subclass present during
 * weaving. A MappedSuperClass is not ignored for weaving if it and a subclass
 * entity are in the persistence.xml during weaving. So if you have your
 * MappedSuperClass without any subclassed entity in one jar/project and your
 * entities in another jar/project and you compile the first project on its own,
 * you must have a dummy "weaving helper" entity in the first jar to enable
 * weaving of the MappedSuperClass. This dummy entity should be excluded from
 * the first jar and the "real application" persistence.xml.
 */
@Entity
public class SupportWeaveExtensible extends Extensible{
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
