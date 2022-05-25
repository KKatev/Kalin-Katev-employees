package com.sirmasolutions.employees.csv;

import java.util.Map;

import com.sirmasolutions.employees.domain.EmployeePair;

public class EmployeePairSummary {
	
	private final EmployeePair pair;
	
	private long totalWorkdays;
	
	private Map<Integer, Long> projectsToWorkdays;

	public EmployeePairSummary(EmployeePair pair) {
		super();
		this.pair = pair;
	}

	public EmployeePair getPair() {
		return pair;
	}

 
	public long getTotalWorkdays() {
		return totalWorkdays;
	}

	public void setTotalWorkdays(long totalWorkdays) {
		this.totalWorkdays = totalWorkdays;
	}

	public Map<Integer, Long> getProjectsToWorkdays() {
		return projectsToWorkdays;
	}

	public void setProjectsToWorkdays(Map<Integer, Long> projectsToWorkdays) {
		this.projectsToWorkdays = projectsToWorkdays;
	}

}
