# Converter
_Console utility that works with csv and parquet files_

## Features
____
- csv to parquet conversion mode
- parquet to csv conversion mode
- getting file schema (list of attributes and their types)

## Tech
____
Converter uses the following additional libraries:

- [pandas](https://pandas.pydata.org/) - pandas is a fast, powerful, flexible and easy to use open source data analysis and manipulation tool, built on top of the Python programming language.
- [pyarrow](https://arrow.apache.org/docs/python/index.html) - PyArrow is regularly built and tested on Windows, macOS and various Linux distributions
- [fastparquet](https://fastparquet.readthedocs.io/en/latest/) - a Python interface to the Parquet file format.

## Installation
____
Install all the necessary libraries, follow the instructions from the project folder:

```sh
pip install -r requirements.txt
```

## Usage
____
**Script workflow:**
```sh
converter.py [-h] [[--parquet2csv input_parquet_file output_csv_file | --csv2parquet input_csv_file output_parquet_file] | --get-schema input_parquet_file]
```
- **-\-parquet2csv** - the conversion from a file with the csv extension to a file with the parquet extension
- **-\-csv2parquet** - the conversion from a file with the parquet extension to a file with the сsv extension
- **-\-get-schema** - prints the parquet file scheme to the console, in case of an error -an error message

#### Windows
```sh
python converter.py <arguments>
```
    
#### Linux
```sh
python3 converter.py <arguments>
```