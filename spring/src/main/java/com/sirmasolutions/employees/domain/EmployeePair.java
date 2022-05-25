package com.sirmasolutions.employees.domain;

import java.util.HashSet;
import java.util.Objects;

/**
 * The idea behind this class is that it provides a convenient 
 * way to put employee pairs in a map without having to take 
 * care of collisions or complex retrieval logic. 
 * 
 * For example, we can map each pair to their respective common
 * work time on any (or all) projects.
 * 
 * The way the constructor, hashCode() and equals() are implemented 
 * guarantees that Pair(a,b) equals Pair(b,a) 
 * 
 * @author kalin
 *
 */
public class EmployeePair {
	
	private final int firstEmployeeID;
	private final int secondEmployeeID;

	public int getFirstEmployeeID() {
		return firstEmployeeID;
	}

	public int getSecondEmployeeID() {
		return secondEmployeeID;
	}

	public EmployeePair(int employeeA, int employeeB) throws IllegalArgumentException {
		if (employeeA == employeeB) {
			throw new IllegalArgumentException(String.format("Invalid employee IDs %d and %d - you cannot pair an employee with themselves", employeeA, employeeB));
		}
		
		this.firstEmployeeID = employeeA < employeeB ? employeeA : employeeB;
		this.secondEmployeeID = employeeA > employeeB ? employeeA : employeeB;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		EmployeePair that = (EmployeePair) other;
		return ((this.firstEmployeeID == that.firstEmployeeID) && (this.secondEmployeeID == that.secondEmployeeID))
				|| ((this.firstEmployeeID == that.secondEmployeeID) && (this.secondEmployeeID == that.firstEmployeeID));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.firstEmployeeID, this.secondEmployeeID);
	}

	@Override
	public String toString() {
		return "Pair [" + firstEmployeeID + ", " + secondEmployeeID + "]";
	}
	
	

}