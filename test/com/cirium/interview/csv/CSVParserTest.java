package com.cirium.interview.csv;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Copied from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 * 
 * I don't really feel like adding a dependency manager just so I can have a CSV Parser.
 */
public class CSVParserTest {
	@Test
    public void test_no_quote() {

        String line = "10,AU,Australia";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");

    }

    @Test
    public void test_no_quote_but_double_quotes_in_column() throws Exception {

        String line = "10,AU,Aus\"\"tralia";

        List<String> result = CSVParser.parseLine(line);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Aus\"tralia");

    }

    @Test
    public void test_double_quotes() {

        String line = "\"10\",\"AU\",\"Australia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");

    }

    @Test
    public void test_double_quotes_but_double_quotes_in_column() {

        String line = "\"10\",\"AU\",\"Aus\"\"tralia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Aus\"tralia");

    }

    @Test
    public void test_double_quotes_but_comma_in_column() {

        String line = "\"10\",\"AU\",\"Aus,tralia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Aus,tralia");
    }
    
    @Test
    public void test_some_double_quotes_but_comma_in_column() {

        String line = "10,\"AU\",Australia";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_some_other_double_quotes_but_comma_in_column() {

        String line = "\"10\",AU,\"Australia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_start_double_quotes_but_comma_in_column() {

        String line = "\"\"10,AU,\"Australia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "\"10");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_end_double_quotes_but_comma_in_column() {

        String line = "10\"\",AU,\"Australia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "10\"");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_wrap_double_quotes_but_comma_in_column() {

        String line = "\"\"10\"\",AU,\"Australia\"";
        List<String> result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "\"10\"");
        Assert.assertEquals(result.get(1), "AU");
        Assert.assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_custom_quotes_but_comma_in_column() {

        String line = "''10''|A''U|'Austra''lia'";
        List<String> result = CSVParser.parseLine(line, '|', '\'');

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "'10'");
        Assert.assertEquals(result.get(1), "A'U");
        Assert.assertEquals(result.get(2), "Austra'lia");
    }
    
    @Test
    public void test_empty_column() {

        String line = "''||''";
        List<String> result = CSVParser.parseLine(line, '|', '\'');

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "");
        Assert.assertEquals(result.get(1), "");
        Assert.assertEquals(result.get(2), "");
    }
}
