# Transactions Converter

This repository contains code for a converter to convert a csv file to a SQLite database file. The header is read out and used as table columns in the table named MUTATIONS. All header names are imported as TEXT data type. 

## Install
Generate a java executable jar.
```
$ git clone https://github.com/Tim83/TransactionConverter.git
$ cd TransactionConverter.git
$ mvn package
```

## Usage
Run with:
```
$ java -jar TransactionConverter.jar /path/to/inputfile.csv
```
 
## Todo's
* set nummeric data in a column of data type nummeric/int/double
* make use of a data layout scheme. E.g. a XML describing the headers and their data type
* add parameter for output file(path)
* include templates for different banks (and their formats of the transactions export file)