package com.cirium.interview.statistics;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.cirium.interview.airport.Airport;
import com.cirium.interview.airport.Airports;
import com.cirium.interview.flights.Flight;

public class FlightStatisticsTest {
	@Test
    public void test_null_fields() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight(null, null, null, null, null, null, null);
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertNull(statistics.getFlightCode());
		Assert.assertNull(statistics.getDepartureDelayInMinutes());	
		Assert.assertNull(statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_zone_positive_values() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertEquals((Long)35l, statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_different_time_zone_positive_values() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"), createAirport("A2", "America/New_York"));
		Flight flight = new Flight("AL 123", "A2", "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertEquals((Long)215l, statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_zone_negative_values() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", createTime("2017-01-01T12:40:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:00:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)(-10l), statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)(-5l), statistics.getArrivalDelayInMinutes());	
		Assert.assertEquals((Long)(-30l), statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_departing_airport() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", null, "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_arriving_airport() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", null, createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_invalid_departing_airport() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A3", "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_invalid_arriving_airport() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A3", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_estimated_departure() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", null, createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertNull(statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertEquals((Long)35l, statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_actual_departure() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", createTime("2017-01-01T12:00:00"), null, createTime("2017-01-01T12:30:00"), createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertNull(statistics.getDepartureDelayInMinutes());	
		Assert.assertEquals((Long)10l, statistics.getArrivalDelayInMinutes());	
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_estimated_arrival() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), null, createTime("2017-01-01T12:40:00"));
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertNull(statistics.getArrivalDelayInMinutes());	
		Assert.assertEquals((Long)35l, statistics.getFlightTimeInMinutes());
    }
	
	@Test
    public void test_same_time_no_actual_arrival() {
		Airports airports = fromAirports(createAirport("A1", "America/Los_Angeles"));
		Flight flight = new Flight("AL 123", "A1", "A1", createTime("2017-01-01T12:00:00"), createTime("2017-01-01T12:05:00"), createTime("2017-01-01T12:30:00"), null);
		FlightStatistics statistics = FlightStatistics.process(flight, airports);
		Assert.assertEquals("AL 123", statistics.getFlightCode());
		Assert.assertEquals((Long)5l, statistics.getDepartureDelayInMinutes());	
		Assert.assertNull(statistics.getArrivalDelayInMinutes());
		Assert.assertNull(statistics.getFlightTimeInMinutes());
    }
	
	private LocalDateTime createTime(String date) {
		return LocalDateTime.parse(date);
	}
	
	private Airport createAirport(String id, String timeZone) {
		return new Airport(id, id, TimeZone.getTimeZone(timeZone).toZoneId());
	}
	
	private Airports fromAirports(Airport... airports) {
		Map<String, Airport> airportMap = new HashMap<>();
		for (Airport airport : airports) {
			airportMap.put(airport.getAirportCode(), airport);
		}
		return new Airports(airportMap);
	}
}
