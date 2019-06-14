package com.cirium.interview.flights;

import java.io.IOException;
import java.util.stream.Stream;

import com.cirium.interview.csv.CSVStreamReader;

public class Flights {
	public static Stream<Flight> fromFile(String path) throws IOException {
		return CSVStreamReader
				.parse(path)
				.map(map -> Flight.fromMap(map))
				.filter(flight -> {
					if (!flight.isValid()) {
						System.err.println(String.format(
								"Invalid flight [%s] in file [%s]",
								flight.getFlightCode(), path));
					}
					return true;
				});
	}
}
