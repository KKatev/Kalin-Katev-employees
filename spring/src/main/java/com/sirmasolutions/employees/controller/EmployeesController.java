package com.sirmasolutions.employees.controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.exceptions.CsvException;
import com.sirmasolutions.employees.csv.AssignmentPeriodsParser;
import com.sirmasolutions.employees.csv.EmployeePairSummary;
import com.sirmasolutions.employees.domain.CommonProjectsData;
import com.sirmasolutions.employees.domain.EmployeePair;
import com.sirmasolutions.employees.domain.ProjectAssignmentData;
import com.sirmasolutions.employees.processing.ProjectDataProcessing;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@PostMapping("/uploadProjectPeriods")
	public EmployeePairResponse handleCSVUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(defaultValue = "yyyy-MM-dd") String dateFormat, @RequestParam(defaultValue = ",") String separatorString) throws IOException, DateTimeParseException, CsvException {
		
		if (separatorString.length() != 1) {
			throw new IllegalArgumentException("The csv separator must be exactly 1 character long. Separator is " + separatorString);
		}
		
		char separator = separatorString.charAt(0);
	  
		List<ProjectAssignmentData> assignments = AssignmentPeriodsParser.parse(file.getInputStream(), separator, dateFormat);
		
		Map<EmployeePair, Map<Integer, Long>> employeePairsToProjectWorkdays = ProjectDataProcessing.process(assignments);
		
		EmployeePairSummary bestPair = ProjectDataProcessing.findBestPair(employeePairsToProjectWorkdays);
		
		System.out.println("The employees with most workdays on common projects are:");
		System.out.printf("%d, %d, %d%n",bestPair.getPair().getFirstEmployeeID(), bestPair.getPair().getSecondEmployeeID(), bestPair.getTotalWorkdays());
		
		List<CommonProjectsData> list = toSerializableData(bestPair);

		return new EmployeePairResponse(list, bestPair.getPair().getFirstEmployeeID(), bestPair.getPair().getSecondEmployeeID(), bestPair.getTotalWorkdays());
	}

	@ExceptionHandler(DateTimeException.class)
	public final ResponseEntity<String> handleDateTimeException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		String details = ex.getMessage();
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(CsvException.class)
	public final ResponseEntity<String> handleCsvException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		String details = ex.getMessage();
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<String> handleIOException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		String details = ex.getMessage();
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<String> handleIllegalArgumentException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		String details = ex.getMessage();
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	private List<CommonProjectsData> toSerializableData(EmployeePairSummary summary) {
		ArrayList<CommonProjectsData> list = new ArrayList<CommonProjectsData>();

		for (var entry : summary.getProjectsToWorkdays().entrySet()) {
			list.add(new CommonProjectsData(summary.getPair().getFirstEmployeeID(), summary.getPair().getSecondEmployeeID(), entry.getKey(), entry.getValue()));
		}
		
		return list;
	}

	class EmployeePairResponse {
		private final List<CommonProjectsData> list;
		private final int firstEmployee;
		private final int secondEmployee;
		private final long workdays;
		
		public List<CommonProjectsData> getList() {
			return list;
		}

		public int getFirstEmployee() {
			return firstEmployee;
		}

		public int getSecondEmployee() {
			return secondEmployee;
		}

		public long getWorkdays() {
			return workdays;
		}

		public EmployeePairResponse(List<CommonProjectsData> list, int firstEmployee, int secondEmployee,
				long workdays) {
			super();
			this.list = list;
			this.firstEmployee = firstEmployee;
			this.secondEmployee = secondEmployee;
			this.workdays = workdays;
		}
	}
}
