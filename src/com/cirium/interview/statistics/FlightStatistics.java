package com.cirium.interview.statistics;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.cirium.interview.airport.Airports;
import com.cirium.interview.flights.Flight;

public class FlightStatistics {
	private String flightCode;
	private Long departureDelayInMinutes;
	private Long arrivalDelayInMinutes;
	private Long flightTimeInMinutes;
	
	public FlightStatistics(String flightCode, Long departureDelayInMinutes,
			Long arrivalDelayInMinutes, Long flightTimeInMinutes) {
		super();
		this.flightCode = flightCode == null ? null : flightCode.trim();
		this.departureDelayInMinutes = departureDelayInMinutes;
		this.arrivalDelayInMinutes = arrivalDelayInMinutes;
		this.flightTimeInMinutes = flightTimeInMinutes;
	}	
	
	public String getFlightCode() {
		return flightCode;
	}

	public Long getDepartureDelayInMinutes() {
		return departureDelayInMinutes;
	}

	public Long getArrivalDelayInMinutes() {
		return arrivalDelayInMinutes;
	}

	public Long getFlightTimeInMinutes() {
		return flightTimeInMinutes;
	}

	public static FlightStatistics process(Flight flight, Airports airports) {
		Long departureDelayInMinutes = flight.getScheduledLocalDeparture() == null || flight.getActualLocalDeparture() == null ? null : ChronoUnit.MINUTES.between(flight.getScheduledLocalDeparture(), flight.getActualLocalDeparture());
		Long arrivalDelayInMinutes = flight.getScheduledLocalArrival() == null || flight.getActualLocalArrival() == null ? null : ChronoUnit.MINUTES.between(flight.getScheduledLocalArrival(), flight.getActualLocalArrival());
		
		ZonedDateTime departure = airports.convertToZone(flight.getDepartureAirport(), flight.getActualLocalDeparture());
		ZonedDateTime arrival = airports.convertToZone(flight.getArrivalAirport(), flight.getActualLocalArrival());
		Long flightTimeInMinutes = departure == null || arrival == null ? null : ChronoUnit.MINUTES.between(departure, arrival);
		
		return new FlightStatistics(flight.getFlightCode(), departureDelayInMinutes, arrivalDelayInMinutes, flightTimeInMinutes);
	}
}
