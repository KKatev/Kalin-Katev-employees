package com.sirmasolutions.employees.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A project in this program's business logic simply contains a project id
 * and a list identifying some employees' assignment periods related to this
 * id, i.e. an employee may have multiple non-overlapping periods when they
 * have worked on this project.
 * 
 * @author kalin
 *
 */
public class Project {
	
	private final int projectID;
	
	private Set<EmployeeAssignmentPeriod> employeeAssignmentPeriods;
	
	public Project(int id) {
		this.projectID = id;
		employeeAssignmentPeriods = new LinkedHashSet<EmployeeAssignmentPeriod>();
	}
	
	public Set<EmployeeAssignmentPeriod> getEmployeeAssignmentsCopy() {
		return new LinkedHashSet<EmployeeAssignmentPeriod>(employeeAssignmentPeriods);
	}
	
	public void addEmployeeAssignment(EmployeeAssignmentPeriod period) {
		employeeAssignmentPeriods.add(period);
	}
	
}
