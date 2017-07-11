package com.cooksys.ftd.assignments.file.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


public class Student {	
	 
	public Student(){}
	
    private Contact contact;
    
    public Student(Contact contact){
		this.contact=contact;
	}

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
