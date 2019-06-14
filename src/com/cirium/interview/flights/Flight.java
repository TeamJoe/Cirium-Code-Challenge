package com.cirium.interview.flights;

import java.time.LocalDateTime;
import java.util.Map;

public class Flight {
	private static final String AIRLINE = "Airline";
	private static final String FLIGHT_NUMBER = "Flight number";
	private static final String DEPARTUTRE_AIRPORT = "Departure airport";
	private static final String ARRIVAL_AIRPORT = "Arrival airport";
	private static final String SCHEDULED_LOCAL_DEPARTURE = "Scheduled local departure";
	private static final String ACTUAL_LOCAL_DEPARTURE = "Actual local departure";
	private static final String SCHEDULED_LOCAL_ARRIVAL = "Scheduled local arrival";
	private static final String ACTUAL_LOCAL_ARRIVAL = "Actual local arrival";
	
	private String flightCode;
	private String departureAirport;
	private String arrivalAirport;
	private LocalDateTime scheduledLocalDeparture;
	private LocalDateTime actualLocalDeparture;
	private LocalDateTime scheduledLocalArrival;
	private LocalDateTime actualLocalArrival;
	
	public Flight(String flightCode,
			String departureAirport, String arrivalAirport,
			LocalDateTime scheduledLocalDeparture, LocalDateTime actualLocalDeparture,
			LocalDateTime scheduledLocalArrival, LocalDateTime actualLocalArrival) {
		this.flightCode = flightCode == null ? null : flightCode.trim();
		this.departureAirport = departureAirport == null ? null : departureAirport.trim();
		this.arrivalAirport = arrivalAirport == null ? null : arrivalAirport.trim();
		this.scheduledLocalDeparture = scheduledLocalDeparture;
		this.actualLocalDeparture = actualLocalDeparture;
		this.scheduledLocalArrival = scheduledLocalArrival;
		this.actualLocalArrival = actualLocalArrival;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public LocalDateTime getScheduledLocalDeparture() {
		return scheduledLocalDeparture;
	}

	public LocalDateTime getActualLocalDeparture() {
		return actualLocalDeparture;
	}

	public LocalDateTime getScheduledLocalArrival() {
		return scheduledLocalArrival;
	}
	
	public LocalDateTime getActualLocalArrival() {
		return actualLocalArrival;
	}
	
	boolean isValid() {
		return flightCode != null && !flightCode.isEmpty()
				&& departureAirport != null && !departureAirport.isEmpty()
				&& arrivalAirport != null && !arrivalAirport.isEmpty()
				&& scheduledLocalDeparture != null && actualLocalDeparture != null
				&& scheduledLocalArrival != null && actualLocalArrival != null;
	}
	
	static Flight fromMap(Map<String, String> values) {
		return new Flight(getFlightCode(values.get(AIRLINE), values.get(FLIGHT_NUMBER)), values.get(DEPARTUTRE_AIRPORT), values.get(ARRIVAL_AIRPORT), 
				getDateFromString(values.get(SCHEDULED_LOCAL_DEPARTURE)), getDateFromString(values.get(ACTUAL_LOCAL_DEPARTURE)),
				getDateFromString(values.get(SCHEDULED_LOCAL_ARRIVAL)), getDateFromString(values.get(ACTUAL_LOCAL_ARRIVAL)));
	}
	
	private static String getFlightCode(String airlines, String flightNumber) {
		String flightCode = String.format("%s %s", airlines == null ? "" : airlines.trim(), flightNumber == null ? "" : flightNumber.trim()).trim();
		if (flightCode.isEmpty()) {
			return null;
		}
		return flightCode;
	}
	
	private static LocalDateTime getDateFromString(String date) {
		try {
			return date == null || date.isEmpty() ? null : LocalDateTime.parse(date.trim());
		} catch (Throwable t) {
			System.err.println(String.format("Unable to parse Date [%s]", date.trim()));
			return null;
		}
	}
}
