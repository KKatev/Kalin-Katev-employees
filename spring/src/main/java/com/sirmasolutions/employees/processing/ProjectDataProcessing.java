package com.sirmasolutions.employees.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sirmasolutions.employees.csv.EmployeePairSummary;
import com.sirmasolutions.employees.domain.CommonProjectsData;
import com.sirmasolutions.employees.domain.EmployeeAssignmentPeriod;
import com.sirmasolutions.employees.domain.EmployeePair;
import com.sirmasolutions.employees.domain.ProjectAssignmentData;

public class ProjectDataProcessing {
	
	public static Map<EmployeePair, Map<Integer, Long>> process(List<ProjectAssignmentData> data) {
		
		Map<Integer, List<EmployeeAssignmentPeriod>> projectToAssignmentsMap = new HashMap<Integer, List<EmployeeAssignmentPeriod>>();
		Map<EmployeePair, Map<Integer, Long>> globalWorkdaysPerPair = new HashMap<EmployeePair, Map<Integer, Long>>();
	
		for (var projectAssignment : data) {
			var projectList = projectToAssignmentsMap.computeIfAbsent(projectAssignment.getProjectID(), key -> new LinkedList<EmployeeAssignmentPeriod>());
			projectList.add(new EmployeeAssignmentPeriod(projectAssignment.getEmpoyeeID(), projectAssignment.getDateFrom(), projectAssignment.getDateTo()));
		}
		
		for (int projectID : projectToAssignmentsMap.keySet()) {
			var pairWorkdaysMap = extractPairedEmployeesPeriods(projectToAssignmentsMap.get(projectID));
			
			for (var pair : pairWorkdaysMap.keySet()) {
				var projectsToWorkdays = globalWorkdaysPerPair.computeIfAbsent(pair, key -> new HashMap<Integer, Long>());
				var currentProjectWorkdays = projectsToWorkdays.computeIfAbsent(projectID, key -> (long) 0);
				projectsToWorkdays.put(projectID, currentProjectWorkdays + pairWorkdaysMap.get(pair));
			}
		}
		
		return globalWorkdaysPerPair;
	}
	
	public static Map<EmployeePair, Long> extractPairedEmployeesPeriods(List<EmployeeAssignmentPeriod> assignments) {
		
		Map<EmployeePair, Long> pairMap = new HashMap<EmployeePair, Long>();
		
		ArrayList<EmployeeAssignmentPeriod> indexedList = new ArrayList<EmployeeAssignmentPeriod>(assignments);
		
		for (int i = 0; i < indexedList.size() - 1; i++) {
			EmployeeAssignmentPeriod outer = indexedList.get(i);
			
			System.out.println("Outer: " + outer);
			 
			
			for (int j = i + 1; j < indexedList.size(); j++) {
				EmployeeAssignmentPeriod inner = indexedList.get(j);
				System.out.println("Inner: " + inner);
				 
				
				if(outer.getEmployeeID() != inner.getEmployeeID()) { //assuming one employee may have two or more assignment periods on the same project
					EmployeePair pair = new EmployeePair(outer.getEmployeeID(), inner.getEmployeeID());
					
					long pairAccumulator = pairMap.computeIfAbsent(pair, key -> (long) 0);
					
					var days = EmployeeAssignmentPeriod.getIntersectingDays(outer, inner);
					pairMap.put(pair, pairAccumulator + days);
				}
			}
		}
		
		return pairMap;
	}
	
	public static EmployeePairSummary findBestPair( Map<EmployeePair, Map<Integer, Long>> pairToWorkdays) {
		long max = 0;
		EmployeePair bestPair = null;
		
		for (var pair : pairToWorkdays.keySet()) {
			long pairSum = 0;
		 
			for (var days : pairToWorkdays.get(pair).values()) {
				pairSum += days;
			}
			
			if (max < pairSum) {
				bestPair = pair;
				max = pairSum;
			}
		}
		
		EmployeePairSummary summary = new EmployeePairSummary(bestPair);
		summary.setProjectsToWorkdays(pairToWorkdays.get(bestPair));
		summary.setTotalWorkdays(max);
		return summary;
	}
	
}
