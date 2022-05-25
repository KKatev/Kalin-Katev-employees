package com.sirmasolutions.employees.domain;

import java.time.LocalDate;


public class ProjectAssignmentData {
    private int empoyeeID;

    private int projectID;

    private LocalDate dateFrom;
	
    private LocalDate dateTo;
    
    public ProjectAssignmentData(int empoyeeID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
    	
		this.empoyeeID = empoyeeID;
		this.projectID = projectID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public int getEmpoyeeID() {
		return empoyeeID;
	}

	public void setEmpoyeeID(int empoyeeID) {
		this.empoyeeID = empoyeeID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "ProjectAssignmentData [empoyeeID=" + empoyeeID + ", projectID=" + projectID + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + "]";
	}
	

}
