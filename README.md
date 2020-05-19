# database-test

Database test application, with models: `Customer`, `Product`, `Purchase`

Available commands: `help`, `add`, `search`, `stat`:
  * help: Prints usage
  * add: Adds entities to database from json
  * search: Searches customers based on criterias
  * stat: Prints statistics on customers

In [examples](examples) directory placed:
  * examples of input and output files 
  * postgres dump file `dump.sql`
 
To build ready to use application
1. Use `mvn install` in root project directory
2. Create db `store` in postgres database
3. Restore db state using:
    1. Database dump `psql store < examples/dump.sql`
    2. Command add `java ... add examples/add-input.json`

To run application use this command in target directory: `java -cp ./lib/*;program.jar org.example.database.test.Main command inputFile outputFile`
