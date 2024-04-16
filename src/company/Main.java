package company;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
                Map<String, List<String>> laptopSales = new HashMap<>();

                try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(" ");
                        int year = Integer.parseInt(parts[0]);
                        String brand = parts[1];
                        double price = Double.parseDouble(parts[2]);
                        String city = parts[3];
                        String clientName = parts[4];

                        if (price < 2000 && year >= 2015 && year <= 2020 && (city.equals("Sofia") || city.equals("Plovdiv") || city.equals("Varna"))) {
                            String key = brand + "_" + year + "_" + city;
                            laptopSales.putIfAbsent(key, new ArrayList<>());
                            laptopSales.get(key).add(clientName);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<Integer, List<String>> sortedSales = new TreeMap<>(Collections.reverseOrder());

                for (String key : laptopSales.keySet()) {
                    int salesCount = laptopSales.get(key).size();
                    sortedSales.putIfAbsent(salesCount, new ArrayList<>());
                    sortedSales.get(salesCount).add(key);
                }

                try (PrintWriter writer = new PrintWriter(new FileWriter("top_laptops.txt"))) {
                    int count = 0;
                    for (Map.Entry<Integer, List<String>> entry : sortedSales.entrySet()) {
                        for (String sale : entry.getValue()) {
                            String[] parts = sale.split("_");
                            String brand = parts[0];
                            String year = parts[1];
                            String city = parts[2];
                            writer.print("Laptop: " + brand + ": ");
                            writer.println(String.join(", ", laptopSales.get(sale)));
                            count++;
                            if (count == 3) break;
                        }
                        if (count == 3) break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

