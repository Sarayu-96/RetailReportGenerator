package com.example.retailreportgenerator.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.retailreportgenerator.model.Transaction;

public class ReportGenerator {
        private final List<Transaction> transactions;

        public ReportGenerator(List<Transaction> transactionList) {// Method to generate retail report
                this.transactions = transactionList;
        }

        public void getCustomerRetailReport(String customerId) {
                // filtering the transaction that belongs to the given Customer
                List<Transaction> customerTransactions = transactions.stream()
                                .filter(t -> customerId.equals(t.getCustomerId()))
                                .collect(Collectors.toList());
                // If no transaction found ,display message and stop
                if (customerTransactions.isEmpty()) {
                        System.out.println("No transactions for Customer ID :" + customerId);
                        return;
                }
                long totalVisits = customerTransactions.size(); // total transaction by this customer

                double averageSpend = customerTransactions.stream().mapToDouble(Transaction::getTotalAmount).average()
                                .orElse(0); // average spend per visist

                String topProductCategory = customerTransactions.stream()
                                .collect(Collectors.groupingBy(Transaction::getProductCategory,
                                                Collectors.summingDouble(Transaction::getTotalAmount)))
                                .entrySet().stream().max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("None");// Top product category by spend

                double totalDiscount = customerTransactions.stream()
                                .mapToDouble(t -> (t.getPrice() * t.getQuantity() * t.getDiscountApplied()) / 100)
                                .sum(); // total discount recieved

                String mostPreferredPayment = customerTransactions.stream()
                                .collect(Collectors.groupingBy(Transaction::getPaymentMethod, Collectors.counting()))
                                .entrySet().stream().max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("None");// Most preffered payment method
                // count and gross amount for each method
                Map<String, String> paymentSummary = customerTransactions.stream()
                                .collect(Collectors.groupingBy(Transaction::getPaymentMethod,
                                                Collectors.collectingAndThen(Collectors.toList(), list -> "Count = "
                                                                + list.size()
                                                                + ", Gross = "
                                                                + list.stream().mapToDouble(Transaction::getTotalAmount)
                                                                                .sum())));
                // Top store by revenue
                String topStoreByRevenue = customerTransactions.stream()
                                .collect(Collectors.groupingBy(Transaction::getStoreLocation,
                                                Collectors.summingDouble(Transaction::getTotalAmount)))
                                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                                .orElse("None");

                String topStoreToDisplay = topStoreByRevenue.replace("\n", ", ");
                // print retail report
                System.out.println("------- Customer Report for " + customerId + "-------");
                System.out.printf("%-30s : %d%n", "Total Number of Visits", totalVisits);
                System.out.printf("%-30s : %.2f%n", "Average Spend per Visit", averageSpend);
                System.out.printf("%-30s : %s%n", "Top product Category", topProductCategory);
                System.out.printf("%-30s : %.2f%n", "Total Discount Received", totalDiscount);
                System.out.printf("%-30s : %s%n", "Most preferred payment method", mostPreferredPayment);
                System.out.printf("%-30s : %-50s%n", "Top store by revenue", topStoreToDisplay);
                System.out.println("Payment count and gross amount By Payment Mode:");
                System.out.printf("%-15s | %-10s | %-10s%n", "Payment Method", "Count", "Gross");
                System.out.println("--------------------------------------------------------------");

                paymentSummary.forEach((method, summary) -> {
                        String[] countAndGross = summary.split(",");
                        int count = Integer.parseInt(countAndGross[0].split("=")[1].trim());
                        double gross = Double.parseDouble(countAndGross[1].split("=")[1].trim());
                        System.out.printf("%-15s | %-10d | %-10.2f%n", method, count, gross);
                });

                System.out.println("--------------------------------------------------------------");

        }

}
