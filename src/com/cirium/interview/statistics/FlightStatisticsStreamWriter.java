package com.cirium.interview.statistics;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.cirium.interview.csv.CSVStreamWriter;

public class FlightStatisticsStreamWriter {
	private final static String FLIGHT_CODE = "Flight Code";
	private final static String DEPARTURE_DELAY = "Departure Difference In Minutes";
	private final static String ARRIVAL_DELAY = "Arrival Difference In Minutes";
	private final static String TOTAL_TIME = "Total Flight Time In Minutes";
	private final static List<String> COLUMN_NAMES = Arrays.asList(FLIGHT_CODE, DEPARTURE_DELAY, ARRIVAL_DELAY, TOTAL_TIME);
	
	public static void toFile(String path, Stream<FlightStatistics> statistics) throws IOException {
		CSVStreamWriter.write(path, COLUMN_NAMES, statistics.map(statistic -> {
			Map<String, Object> map = new HashMap<>();
			map.put(FLIGHT_CODE, statistic.getFlightCode());
			map.put(DEPARTURE_DELAY, statistic.getDepartureDelayInMinutes());
			map.put(ARRIVAL_DELAY, statistic.getArrivalDelayInMinutes());
			map.put(TOTAL_TIME, statistic.getFlightTimeInMinutes());
			return map;
		}));
	}
}
