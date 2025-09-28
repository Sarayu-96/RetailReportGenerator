# Retail Report Generator

## Description
This project generates retail reports from Transaction data.
It reads a CSV file of retail transactions and generates reports for the customer, including total visits, average spend, top product category, discounts, payment summary, and top store by revenue.

## Features
- Calculate total number of visits.
- Average spend per visit
- Top product category by total spend
- Total discount received to date
- Most preferred payment method
- Payment count and gross amount by payment mode
- Top store by revenue

## How to run

### Option 1: Run the jar file(Make sure csv file in the same folder).

    java -jar RetailReportGenerator.jar

### Option 2: Using Command Line

1. Open the project folder in VS Code
2. Open **RetailReportGeneratorMain.java**.
3. Click the **Run** button at the top right.
4. The program will automatically compile and run.

### Option 3: Using Command Line

1. Open a terminal in the project root folder.
2. Compile all jave files:
    
    javac -d bin src/com/example/retailreportgenerator/*.java src/com/example/retailreportgenerator/io/*.java src/com/example/retailreportgenerator/model/*.java src/com/example/retailreportgenerator/service/*.java

3. Run the main class:

    java -cp bin com.example.retailreportgenerator.RetailReportGeneratorMain


## Sample Input
The program ask for a **Customer ID** when it runs.
For example, you can enter:

    Enter the Customer ID :916450

The program will then generate the report for that customer using the CSV file.

## Sample Output
For Customer ID "916450" , the report may look like:

    ------- Customer Report for 916450-------
    Total Number of Visits         : 1
    Average Spend per Visit        : 590.49
    Top product Category           : Electronics
    Total Discount Received        : 97.13
    Most preferred payment method  : Debit Card
    Top store by revenue           : 865 Kyle Oval, Mcintyretown, AK 92585
    Payment count and gross amount By Payment Mode:
    Payment Method  | Count      | Gross
    --------------------------------------------------------------
    Debit Card      | 1          | 590.49
    --------------------------------------------------------------


### Notes
- Make sure the `Retail_Transaction_Dataset.csv` file is in the **project root**.  
- The report only includes transactions for the **Customer ID entered**.  
- You can enter any valid Customer ID present in the CSV file.


