package com.cirium.interview.main;

import java.io.IOException;
import java.util.stream.Stream;

import com.cirium.interview.airport.Airports;
import com.cirium.interview.flights.Flights;
import com.cirium.interview.statistics.FlightStatistics;
import com.cirium.interview.statistics.FlightStatisticsStreamWriter;

public class Main {
	public static void main(String [] args)
	{
		if (args.length != 3) {
			System.out.println(String.format("Expected usuage `\"airportCSVFileLocation\" \"flightCSVFileLocation\" \"outputCSVFileLocation\"`"));
			return;
		}
		
		try {
			process(args[0], args[1], args[2]);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
	private static void process(String airportCSVFileLocation, String flightCSVFileLocation, String outputCSVFileLocation) throws IOException {
		Airports airports = Airports.fromFile(airportCSVFileLocation);
		Stream<FlightStatistics> statistics = Flights.fromFile(flightCSVFileLocation)
				.map(flight -> FlightStatistics.process(flight, airports));
		FlightStatisticsStreamWriter.toFile(outputCSVFileLocation, statistics);
	}	
}
