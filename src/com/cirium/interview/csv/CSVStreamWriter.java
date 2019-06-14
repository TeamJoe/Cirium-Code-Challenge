package com.cirium.interview.csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVStreamWriter {
	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	
	public static void write(String pathValue, List<String> columnNames, Stream<Map<String, Object>> values) throws IOException {
		write(pathValue, null, null, columnNames, values);
	}

	public static void write(String pathValue, Character quote,
			Character separator, List<String> columnNames, Stream<Map<String, Object>> values) throws IOException {
		Path path = Paths.get(pathValue);
		if (Files.exists(path) && !Files.isWritable(path)) {
			throw new IOException(String.format("Cannot write to file [%s]", path));
		}
		
		if (columnNames == null || columnNames.isEmpty()) {
			throw new IllegalArgumentException("Column names are expected");
		}
		
		if (values == null) {
			throw new IllegalArgumentException("Value stream cannot be null");
		}


		if (quote == null || quote == ' ') {
			quote = DEFAULT_QUOTE;
		}

		if (separator == null || separator == ' ') {
			separator = DEFAULT_SEPARATOR;
		}
		
		printFile(path, quote, separator, columnNames, values);
	}
	
	private static void printFile(Path path, Character quote,
			Character separator, List<String> columnNames, Stream<Map<String, Object>> values) throws IOException {
		try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path))) {
			pw.println(formatLine(columnNames.stream(), quote, separator));
			values.map(value -> formatLine(columnNames, value, quote, separator)).forEach(pw::println);
		}
	}
	
	private static String formatLine(List<String> columnNames, Map<String, ?> values, char quote,
			char separator) {
		return formatLine(columnNames.stream().map(columnName -> values.get(columnName)), quote, separator);
	}
	
	private static String formatLine(Stream<?> values, char quote,
			char separator) {
		if (values == null) {
			return "";
		}
		List<String> processedValues = values
				.map(value -> value == null ? "" : value.toString())
				.map(value -> !value.contains(Character.toString(separator)) && !value.contains(Character.toString(quote)) ? value : 
						String.format("%1$c%2$s%1$c", quote, value.replace(Character.toString(quote), String.format("%1$c%1$c", quote))))
						.collect(Collectors.toList());
		return String.join(Character.toString(separator), processedValues);
	}
}
