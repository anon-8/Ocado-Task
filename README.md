# Basket Splitter

Basket Splitter is a Java application designed to split a basket of items into different delivery groups based on a configuration file.

## Prerequisites
- Java 21 or higher

## Project Structure

**Key directories:**


- **Main**
    - Contains the core application logic and entry points.
        - `BasketSplitter.java`: Splits a basket of items into different delivery groups based on a configuration file.
        - `ConfigLoader.java`: Parses the configuration file and retrieves delivery options for each item.
        - `JsonLoader.java`: Loads JSON files and provides methods for parsing them.

- **Test**
    - Contains Tests that ensures functionality works as expected.


- **Test Resources**
    - Contains Json Files needed to perform tests.


**Additional Comments**

-  "Taken given <Program ma podzielić produkty znajdujące się w koszyku. Zostaną one przekazane do API
   biblioteki w formie listy z nazwami produktów> which didn't contain enough information application returns doubled product in group with doubled item list entry"

