package com.example.retailreportgenerator.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.retailreportgenerator.model.Transaction;

public class TransactionCsvReader {
    // Method to read all transactions from a csv file
    public List<Transaction> getTransactions(String csvFile) throws FileNotFoundException {
        List<Transaction> transactionList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;// Flag to skip header
            StringBuilder sb = new StringBuilder();
            boolean insideQuotes = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // skipping header
                    continue;
                }

                sb.append(line);

                // Count quotes in the current line to detect multiline fields
                int quoteCount = line.length() - line.replace("\"", "").length();
                if (quoteCount % 2 != 0) {
                    insideQuotes = !insideQuotes;
                }

                if (!insideQuotes) {

                    String[] values = parseCsvLine(sb.toString());
                    if (values.length >= 10) {
                        // Set values for each field in transaction
                        Transaction transaction = new Transaction();

                        transaction.setCustomerId(values[0]);
                        transaction.setProductId(values[1]);
                        try {
                            transaction.setQuantity(Integer.parseInt(values[2]));
                        } catch (NumberFormatException e) {
                            transaction.setQuantity(0);
                        }

                        try {
                            transaction.setPrice(Double.parseDouble(values[3]));
                        } catch (NumberFormatException e) {
                            transaction.setPrice(0.0);
                        }

                        transaction.setTransactionDate(values[4]);
                        transaction.setPaymentMethod(values[5]);
                        transaction.setStoreLocation(values[6]);
                        transaction.setProductCategory(values[7]);

                        try {
                            transaction.setDiscountApplied(Double.parseDouble(values[8]));
                        } catch (NumberFormatException e) {
                            transaction.setDiscountApplied(0.0);
                        }

                        try {
                            transaction.setTotalAmount(Double.parseDouble(values[9]));
                        } catch (NumberFormatException e) {
                            transaction.setTotalAmount(0.0);
                        }
                        // Add transactions to the list
                        transactionList.add(transaction);
                    } else {
                        System.out.println("Skipping invalid row: " + sb.toString());
                    }

                    sb.setLength(0); // reset for next line
                } else {
                    sb.append("\n"); // preserve newline inside quoted field
                }
            }

        } catch (

        FileNotFoundException e) {
            throw e;
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

        return transactionList;
    }

    //CSV parser to handle quoted fields and commas
    private String[] parseCsvLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                columns.add(sb.toString().trim().replaceAll("^\"|\"$", ""));
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        columns.add(sb.toString().trim().replaceAll("^\"|\"$", "")); // last column
        return columns.toArray(new String[0]);
    }
}
