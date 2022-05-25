package com.sirmasolutions.employees.csv;

import com.opencsv.bean.CsvBindByPosition;

 /**
 * My initial idea was to use LocalDate for dateFrom and dateTo and
 * use @CsvCustomBindByPosition with a custom LocalDate converter that also
 * interprets "NULL". However, this would require providing the given date
 * pattern ("yyyy-MM-dd" or whatever) at compile time, and this is a no-go for a
 * dynamic parser. Therefore, I will parse the data in two steps - first to
 * RawProjectAssignmentData, then to productive ProjectAssignment with
 * LocalDate.
 * 
 * @author kalin
 *
 */
public class RawProjectAssignmentData {

	@CsvBindByPosition(position = 0)
	private int empoyeeID;

	@CsvBindByPosition(position = 1)
	private int projectID;

	@CsvBindByPosition(position = 2)
	private String dateFrom;

	@CsvBindByPosition(position = 3)
	private String dateTo;

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

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}