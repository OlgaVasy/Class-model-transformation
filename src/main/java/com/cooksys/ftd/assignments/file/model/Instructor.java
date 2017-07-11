package com.cooksys.ftd.assignments.file.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Instructor {
    private Contact contact;

    public Instructor(Contact contact) {
		this.contact = contact;
	}
    
    public Instructor(){}
    @XmlElement
	public Contact getContact() {
        return contact;
    }
   
    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
