package com.cirium.interview.airport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import com.cirium.interview.csv.CSVStreamReader;

public class Airports {
	private final Map<String, Airport> airportMap;

	public Airports(Map<String, Airport> airportMap) {
		if (airportMap == null) {
			throw new IllegalArgumentException("Airport map cannot be null");
		}
		this.airportMap = airportMap;
	}
	
	public Airport getAirport(String airportCode) {
		return airportMap.get(airportCode);
	}

	public ZonedDateTime convertToZone(String airportCode, LocalDateTime date) {
		Airport airport = airportMap.get(airportCode);
		if (airport == null) {
			return null;
		}
		return airport.convertToZone(date);
	}

	public static Airports fromFile(String path) throws IOException {
		Map<String, Airport> airportMap = 
				CSVStreamReader
						.parse(path)
						.map(map -> Airport.fromMap(map))
						.filter(airport -> {
							if (!airport.isValid()) {
								System.err.println(String.format("Invalid airport [%s] in file [%s]", airport.getAirportCode(), path));
								return false;
							}
							return true;
						})
						.collect(
								Collectors.toMap(
										Airport::getAirportCode,
										airport -> airport,
										(oldValue, newValue) -> {
											System.err.println(String.format("Duplicate value [%s] in file [%s]", oldValue.getAirportCode(), path));
											return newValue;
										}));
		if (airportMap.isEmpty()) {
			throw new IOException(String.format("Unable to parse airports from file [%s]", path));
		}
		return new Airports(airportMap);
	}
}
