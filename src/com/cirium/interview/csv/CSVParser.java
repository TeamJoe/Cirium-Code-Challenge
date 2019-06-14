package com.cirium.interview.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Copied from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 * 
 * I don't really feel like adding a dependency manager just so I can have a CSV Parser.
 */
public class CSVParser {
	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	
	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, null, null);
	}

	public static List<String> parseLine(String cvsLine, Character separators) {
		return parseLine(cvsLine, separators, null);
	}

	public static List<String> parseLine(String cvsLine, Character separators,
			Character customQuote) {

		List<String> result = new ArrayList<>();

		// if empty, return!
		if (cvsLine == null || cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == null || customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == null || separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;

		char[] chars = cvsLine.toCharArray();

		for (int position = 0; position < chars.length; position++) {
			char currentCharacter = chars[position];
			Character nextCharacter = position+1 >= chars.length ? null : chars[position+1];
			Character nextNextCharacter = position+2 >= chars.length ? null : chars[position+2];

			if (inQuotes) {
				if (currentCharacter == customQuote
						&& nextCharacter == customQuote) {
					curVal.append(customQuote);
					position++;
				} else if (currentCharacter != customQuote) {
					curVal.append(currentCharacter);
				} else {
					inQuotes = false;
				}
			} else {
				if (currentCharacter == customQuote
						&& nextCharacter != customQuote) {
					inQuotes = true;
				} else if (currentCharacter == customQuote
						&& nextCharacter == customQuote
						&& (nextNextCharacter == separators || nextNextCharacter == null)
						&& curVal.length() == 0) {
					position++;
				} else if (currentCharacter == customQuote
						&& nextCharacter == customQuote) {
					curVal.append(customQuote);
					position++;
				} else if (currentCharacter == separators) {
					result.add(curVal.toString());
					curVal = new StringBuffer();
				} else if (currentCharacter == '\r') {
					// ignore LF characters
					continue;
				} else if (currentCharacter == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(currentCharacter);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}
}
