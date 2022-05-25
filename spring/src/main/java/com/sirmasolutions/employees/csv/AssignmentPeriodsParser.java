package com.sirmasolutions.employees.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.customconverter.ConvertGermanToBoolean;
import com.opencsv.exceptions.CsvException;
import com.sirmasolutions.employees.domain.ProjectAssignmentData;
import com.opencsv.CSVParser;

public class AssignmentPeriodsParser {

	public static List<ProjectAssignmentData> parse(InputStream inputStream, char separator, String dateFormat)
			throws IOException, CsvException, DateTimeParseException {
		
		List<ProjectAssignmentData> parsedData = new ArrayList<ProjectAssignmentData>()
;
		CSVParser csvParser = new CSVParserBuilder().withSeparator(separator).build();

		CsvToBean<RawProjectAssignmentData> csvToBean = new CsvToBeanBuilder<RawProjectAssignmentData>(
				new InputStreamReader(inputStream)).withType(RawProjectAssignmentData.class)
				.withIgnoreLeadingWhiteSpace(true)
				.withSeparator(separator).build();

		Iterator<RawProjectAssignmentData> csvDataIterator = csvToBean.iterator();
		
		while (csvDataIterator.hasNext()) {
			RawProjectAssignmentData csvLine = csvDataIterator.next();
			System.out.println("ID : " + csvLine.getEmpoyeeID());
			System.out.println("Project ID : " + csvLine.getProjectID());
			System.out.println("DateFrom : " + csvLine.getDateFrom());
			System.out.println("DateTo : " + csvLine.getDateTo());
			System.out.println("==========================");

			ProjectAssignmentData data = new ProjectAssignmentData(csvLine.getEmpoyeeID(), csvLine.getProjectID(),
					parseDateString(csvLine.getDateFrom(), dateFormat),
					parseDateString(csvLine.getDateTo(), dateFormat));
			parsedData.add(data);

		}
	
		return parsedData;
	}

	private static LocalDate parseDateString(String date, String dateFormat) throws DateTimeParseException  {
		String trim = date.trim();
		
		return trim.toLowerCase().equals("null") ? LocalDate.now()
				: LocalDate.parse(trim, DateTimeFormatter.ofPattern(dateFormat));
	}

}