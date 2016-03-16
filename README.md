# Transactions Converter

This repository contains code for a converter to convert a csv file to a SQLite database file. The header is read out and used as table columns in the table named MUTATIONS. All header names are imported as TEXT data type. 

## Features
* converts comma separated file to a SQLite database
* parameters for input file and output file
* usage description printed when parameters are incorrect
* usage of configuration file to select and set the columns

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
$ java -jar TransactionConverter.jar -i /path/to/inputfile.csv -o /path/to/outputfile.db
```
 
## Todo's
* make use of a data layout scheme. E.g. a XML describing the headers and their data type
* include templates for different banks (and their formats of the transactions export file)