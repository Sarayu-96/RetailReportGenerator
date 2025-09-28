package com.example.retailreportgenerator;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.example.retailreportgenerator.io.TransactionCsvReader;
import com.example.retailreportgenerator.model.Transaction;
import com.example.retailreportgenerator.service.ReportGenerator;

public class RetailReportGeneratorMain {
    public static void main(String[] args) throws FileNotFoundException {
        // Read input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Customer ID :  ");
        String customerId = scanner.nextLine();
        // Name of the csv file containing transaction data
        String csvFile = "Retail_Transaction_Dataset.csv";
        // read transactions from csv file
        TransactionCsvReader csvReader = new TransactionCsvReader();
        List<Transaction> transactionList = csvReader.getTransactions(csvFile);
        // create ReportGenerator object with the list of transactions
        ReportGenerator reportGenerator = new ReportGenerator(transactionList);
        // Generate report for the given Customer ID
        reportGenerator.getCustomerRetailReport(customerId);
        scanner.close();
    }

}
