package com.sirmasolutions.employees.domain;

public class CommonProjectsData {
	private int firstEmployee;
	private int secondEmployee;
	private int projectID;
	private long daysWorked;
	
	public CommonProjectsData(int firstEmployee, int secondEmployee, int projectID, long daysWorked) {
		this.firstEmployee = firstEmployee;
		this.secondEmployee = secondEmployee;
		this.projectID = projectID;
		this.daysWorked = daysWorked;
	}
	
	public int getFirstEmployee() {
		return firstEmployee;
	}
	public void setFirstEmployee(int firstEmployee) {
		this.firstEmployee = firstEmployee;
	}
	public int getSecondEmployee() {
		return secondEmployee;
	}
	public void setSecondEmployee(int secondEmployee) {
		this.secondEmployee = secondEmployee;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public long getDaysWorked() {
		return daysWorked;
	}
	public void setDaysWorked(long daysWorked) {
		this.daysWorked = daysWorked;
	}

 
}
