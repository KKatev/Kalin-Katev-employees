package com.sirmasolutions.employees.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeAssignmentPeriod {
	
	private final int employeeID;
	private final LocalDate dateFrom;
	private final LocalDate dateTo;
	
	public EmployeeAssignmentPeriod(int employeeID, LocalDate dateFrom, LocalDate dateTo) throws IllegalArgumentException {
		if (dateFrom.isAfter(dateTo) || dateFrom.equals(dateTo)) {
			throw new IllegalArgumentException(String.format("Invalid dates - starting date dateFrom (%tD) must be before end date dateTo (%tD)", dateFrom, dateTo));
		}
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.employeeID = employeeID;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	
	public LocalDate getDateFrom() {
		return dateFrom;
	}
	
	public LocalDate getDateTo() {
		return dateTo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateFrom, dateTo, employeeID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeAssignmentPeriod other = (EmployeeAssignmentPeriod) obj;
		return Objects.equals(dateFrom, other.dateFrom) && Objects.equals(dateTo, other.dateTo)
				&& employeeID == other.employeeID;
	}
	
	@Override
	public String toString() {
		return "EmployeeAssignmentPeriod [employeeID=" + employeeID + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ "]";
	}

	public static long getIntersectingDays(EmployeeAssignmentPeriod aPeriod, EmployeeAssignmentPeriod otherPeriod) {
		if (aPeriod.getDateTo().isBefore(otherPeriod.getDateFrom()) || aPeriod.getDateFrom().isAfter(otherPeriod.getDateTo())) {
			return 0;
		}
		
		System.out.println("Intersection of " + aPeriod + " and " + otherPeriod);
		
		return computeDayIntersection(aPeriod, otherPeriod);
	}
	
	private static long computeDayIntersection(EmployeeAssignmentPeriod aPeriod, EmployeeAssignmentPeriod otherPeriod) {
		LocalDate intersectionStart = aPeriod.getDateFrom().isAfter(otherPeriod.getDateFrom()) ? aPeriod.getDateFrom() : otherPeriod.getDateFrom();
		LocalDate intersectionEnd = aPeriod.getDateTo().isBefore(otherPeriod.getDateTo()) ? aPeriod.getDateTo() : otherPeriod.getDateTo();
		
		System.out.println("Intersection start: " + intersectionStart);
		System.out.println("Intersection end: " + intersectionEnd);
		System.out.println("Duration: " + Duration.between(intersectionStart.atStartOfDay(), intersectionEnd.atStartOfDay()).toDays());
		
		return Duration.between(intersectionStart.atStartOfDay(), intersectionEnd.atStartOfDay()).toDays();
	}
}
