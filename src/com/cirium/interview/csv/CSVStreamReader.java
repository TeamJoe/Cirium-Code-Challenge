package com.cirium.interview.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CSVStreamReader {
	public static Stream<Map<String, String>> parse(String pathValue) throws IOException {
		return parse(pathValue, null, null);
	}

	public static Stream<Map<String, String>> parse(String pathValue, Character quote,
			Character separator) throws IOException {
		Path path = Paths.get(pathValue);
		if (!Files.isReadable(path)) {
			throw new IOException(String.format("Cannot read file [%s]", pathValue));
		}
		
		List<String> columnNames = getColumnNames(path, quote, separator);
		
		return Files.lines(path).parallel()
				.filter(line -> line != null && !line.isEmpty())
				.map(line -> CSVParser.parseLine(line, separator, quote))
				.filter(line -> line != null && !line.isEmpty() && !line.equals(columnNames))
				.map(line -> {
					Map<String, String> map = new HashMap<>();
					Iterator<String> columnName = columnNames.iterator();
					Iterator<String> value = line.iterator();
					while (columnName.hasNext() && value.hasNext()) map.put(columnName.next(), value.next());
					
					if (columnName.hasNext()) {
						System.err.println(String.format("File [%s] contains incomplete lines", pathValue));
					} else if (value.hasNext()) {
						System.err.println(String.format("File [%s] contains lines with extra columns", pathValue));
					}
					
					return map;
				});
	}

	private static List<String> getColumnNames(Path path, Character quote,
			Character separator) throws IOException {
		return Files.lines(path)
				.filter(line -> line != null && !line.isEmpty())
				.map(line -> CSVParser.parseLine(line, separator, quote))
				.filter(line -> line != null && !line.isEmpty()).findFirst()
				.orElseThrow(() -> new IOException(String.format("File [%s] is empty", path)));
	}
}
