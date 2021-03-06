package com.sirmasolutions.employees.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sirmasolutions.employees.csv.EmployeePairSummary;
import com.sirmasolutions.employees.domain.CommonProjectsData;
import com.sirmasolutions.employees.domain.EmployeeAssignmentPeriod;
import com.sirmasolutions.employees.domain.EmployeePair;
import com.sirmasolutions.employees.domain.ProjectAssignmentData;

public class ProjectDataProcessing {

	public static Map<EmployeePair, Map<Integer, Long>> computePairwiseCommonProjectWorkdays(List<ProjectAssignmentData> data) {
		
		Map<EmployeePair, Map<Integer, Long>> globalWorkdaysPerPair = new HashMap<EmployeePair, Map<Integer, Long>>();
	
		Map<Integer, List<EmployeeAssignmentPeriod>> projectToAssignmentsMap = computeProjectAssignmentPeriodsMap(data);
		
		for (int projectID : projectToAssignmentsMap.keySet()) {
			var pairWorkdaysForProject = extractPairedEmployeesPeriods(projectToAssignmentsMap.get(projectID));
			
			for (var pair : pairWorkdaysForProject.keySet()) {
				var projectsToWorkdays = globalWorkdaysPerPair.computeIfAbsent(pair, key -> new HashMap<Integer, Long>());
				projectsToWorkdays.merge(projectID, pairWorkdaysForProject.get(pair), (acc, newValue) -> acc + newValue ); 
			}
			
		}

		return globalWorkdaysPerPair;
	}

	private static Map<Integer, List<EmployeeAssignmentPeriod>> computeProjectAssignmentPeriodsMap(List<ProjectAssignmentData> projectAssignments) {
		Map<Integer, List<EmployeeAssignmentPeriod>> projectToAssignmentsMap = new HashMap<Integer, List<EmployeeAssignmentPeriod>>();
		
		for (var projectAssignment : projectAssignments) {
			var projectList = projectToAssignmentsMap.computeIfAbsent(projectAssignment.getProjectID(), key -> new LinkedList<EmployeeAssignmentPeriod>());
			projectList.add(new EmployeeAssignmentPeriod(projectAssignment.getEmpoyeeID(), projectAssignment.getDateFrom(), projectAssignment.getDateTo()));
		}
		
		return projectToAssignmentsMap;
	}
	
	public static Map<EmployeePair, Long> extractPairedEmployeesPeriods(List<EmployeeAssignmentPeriod> assignments) {
		
		Map<EmployeePair, Long> pairMap = new HashMap<EmployeePair, Long>();
		
		ArrayList<EmployeeAssignmentPeriod> indexedList = new ArrayList<EmployeeAssignmentPeriod>(assignments);
		
		for (int i = 0; i < indexedList.size() - 1; i++) {
			EmployeeAssignmentPeriod outer = indexedList.get(i);
			
			for (int j = i + 1; j < indexedList.size(); j++) {
				EmployeeAssignmentPeriod inner = indexedList.get(j);	 
				
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
	
	public static Optional<EmployeePairSummary> findBestPair( Map<EmployeePair, Map<Integer, Long>> pairToProjectWorkdays) {
		long max = 0;
		EmployeePair bestPair = null;
		
		for (var pair : pairToProjectWorkdays.keySet()) {
			long pairSum = 0;
			
			pairSum = pairToProjectWorkdays.get(pair).values().stream().reduce((long) 0, Long::sum);
			
			if (max < pairSum) {
				bestPair = pair;
				max = pairSum;
			}
		}
		
		EmployeePairSummary summary = new EmployeePairSummary(bestPair);
		summary.setProjectsToWorkdays(pairToProjectWorkdays.get(bestPair));
		summary.setTotalWorkdays(max);
		return Optional.ofNullable(summary);
	}
	
}
