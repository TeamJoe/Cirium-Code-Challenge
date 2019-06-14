Please implement a solution in the object oriented language of your own choosing, in the style that you are comfortable with.  The code will be judged by best practices in the language of your choice as well as software engineering principles in general.  Good code is correct, safe when exposed to unexpected inputs, understandable and concise.  Use of well-supported open source libraries is encouraged.

## Inputs 
### flights.csv

This comma-separated list of values (CSV) represents pieces of information for specific flights.   

Columns:
```
    [0] airline code
    [1] flight number
    [2] departure airport code
    [3] arrival airport code
    [4] scheduled departure time (local time at the airport)
    [5] actual departure time (local time at the airport)
    [6] scheduled arrival time (local time at the airport)
    [7] actual arrival time (local time at the airport)
```

### airports.csv

This CSV represents the name and timezone of the given airport.  

Columns:
```
    [0] airport code
    [1] airport name
    [2] IANA time zone name
```

## The problem statement
Given the input CSV files, generate the output CSV file.

You will need to calculate 3 fields: departure delay (in minutes), arrival delay (in minutes), and actual flight time (in minutes).  Note that you will need to take relative time zones and daylight savings time into account.  Please use an appropriate time and date library.

Construct the part of your program that takes information about a flight (a model of a row from `flights.csv`) and produces an output model as if it were going to be used as a library by other programmers.

1. You should not assume that the input files are complete.  Errors or unexpected inputs should be handled.
2. Please do not implement a user interface or RESTful interface.  Demonstrating your correctness in a unit test is desirable.  The code that generates the output CSV can live in a unit test.
3. (Optional, if you're comfortable with multithreading) Concurrently process the input data from `flights.csv` using multiple threads. Ensure the output is in the same order as the input.
4. Your submission should include:
    * The source code to your solution, complete with unit tests and any build scripts or dependency declarations you used.
    * Simple documentation on how to build and run your code.
    * Optionally, an output.csv file with the complete output table (See the Output section below)
5. Please do NOT upload your code sample to a public Github (or other) repository.

## Output

Columns:
```
    [0] airline code and flight number
    [1] difference (in minutes) between the scheduled departure time and the actual departure time
    [2] difference (in minutes) between the scheduled arrival time and the actual arrival time.
    [3] total duration of the flight (in minutes).  This is the difference between the actual arrival time and the actual departure time, adjusted for timezone offsets.
```

Example:
```
    Flight identifier,Departure difference,Arrival difference,Duration
    UA 30,3,10,322
    WN 100,XX,XX,XX
    UA 12,XX,XX,XX
    LH 491,XX,XX,XX
    F9 618,XX,XX,XX
    VA 1,XX,XX,XX
```


