package com.cooksys.ftd.assignments.file.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement


public class Session {
     private String location;
     private String startDate;
     private List<Student> students;
     private Instructor instructor;

    public Session(){}
 
    public Session(String location, String startDate,Instructor instructor, List<Student>students){
    	this.location=location;
    	this.startDate=startDate;
    	this.instructor=instructor;
    	this.students = students;
    }

    public String getLocation() {
        return location;
    }
   
    public void setLocation(String location) {
        this.location = location;
    }
    @XmlElement(name="start-date")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
   
    @XmlElement(name="student")
    public List<Student> getStudents() {
        return students;
    }   
    
    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
      
  
    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
