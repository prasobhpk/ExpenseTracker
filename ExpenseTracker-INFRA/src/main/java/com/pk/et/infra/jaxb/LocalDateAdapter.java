package com.pk.et.infra.jaxb;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.LocalDate;
 
public class LocalDateAdapter
    extends XmlAdapter<String, Date>{
 
    public Date unmarshal(String v) throws Exception {
        return new LocalDate(v).toDate();
    }
 
    public String marshal(Date v) throws Exception {
        return new LocalDate(v).toString();
    }
 
}
