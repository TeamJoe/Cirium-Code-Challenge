package com.cirium.interview.airport;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.Test;

public class AirportsTest {
	@Test
	public void testAiportsLoading() throws Throwable {
		String path = getFilePath("resources/AirportTest.csv");
		Airports airports = Airports.fromFile(path);
		Assert.assertNotNull(airports.getAirport("MissingColumn"));
		Assert.assertEquals("MissingColumn", airports.getAirport("MissingColumn").getAirportCode());
		Assert.assertEquals("Portland, Oregon International Airport", airports.getAirport("MissingColumn").getAirportName());
		Assert.assertEquals(ZoneId.of("America/Los_Angeles"), airports.getAirport("MissingColumn").getTimeZone());
		Assert.assertNotNull(airports.getAirport("ExtraColumn"));
		Assert.assertEquals("ExtraColumn", airports.getAirport("ExtraColumn").getAirportCode());
		Assert.assertEquals("Still \"Validish\"", airports.getAirport("ExtraColumn").getAirportName());
		Assert.assertEquals(ZoneId.of("America/Los_Angeles"), airports.getAirport("ExtraColumn").getTimeZone());
		Assert.assertNull(airports.getAirport("NoTimeZone"));
		Assert.assertNull(airports.getAirport("EmptyZone"));
		Assert.assertNull(airports.getAirport("InvalidZone"));
		Assert.assertNull(airports.getAirport("JustCode"));
		Assert.assertNull(airports.getAirport(""));
		Assert.assertNull(airports.getAirport(null));		
	}
	
	@Test
	public void testAiportsConvertInvalidAirport() throws Throwable {
		String path = getFilePath("resources/AirportTest.csv");
		Airports airports = Airports.fromFile(path);
		Assert.assertNull(airports.convertToZone("InvalidZone", LocalDateTime.parse("2017-01-01T16:08:00")));
	}
	
	@Test
	public void testAiportsConvertInvalidTime() throws Throwable {
		String path = getFilePath("resources/AirportTest.csv");
		Airports airports = Airports.fromFile(path);
		Assert.assertNull(airports.convertToZone("MissingColumn", null));
	}
	
	@Test
	public void testAiportsConvertTime() throws Throwable {
		String path = getFilePath("resources/AirportTest.csv");
		Airports airports = Airports.fromFile(path);
		Assert.assertEquals(ZoneId.of("America/Los_Angeles"), airports.convertToZone("MissingColumn", LocalDateTime.parse("2017-01-01T16:08:00")).getZone());
	}

	private String getFilePath(String fileName) throws URISyntaxException {
		URL res = getClass().getClassLoader().getResource(fileName);
		File file = Paths.get(res.toURI()).toFile();
		return file.getAbsolutePath();
	}
}
