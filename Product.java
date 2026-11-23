import java.io.*;
import java.util.ArrayList;

public class Product {

    // Stores all products
    static ArrayList<Product> products = new ArrayList<>();

    // Product information
    String name;
    double price;
    double cost;
    int stock;

    // Constructor
    public Product(String name, double price, double cost, int stock) {
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.stock = stock;
    }

    // Add a new product
    public static void addProduct(String name, double price, double cost, int stock) {
        products.add(new Product(name, price, cost, stock));
        saveProducts();
        System.out.println("\nProduct added successfully!");
    }

    // Display all products
    public static void viewProducts() {
        System.out.println("\n===== PRODUCT LIST =====");

        if (products.isEmpty()) {
            System.out.println("\nNo products available.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);

            System.out.println((i + 1) + ". " + p.name
                    + " | Price: PHP " + p.price
                    + " | Cost: PHP " + p.cost
                    + " | Stock: " + p.stock);
        }
    }

    // Restock an existing product
    public static void restockProduct(int index, int quantity) {
        Product p = products.get(index);
        p.stock += quantity;

        saveProducts();

        System.out.println("Product restocked successfully!");
        System.out.println("New stock: " + p.stock);
    }

    // Find product by index
    public static Product getProduct(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        }

        return null;
    }

    // Save products to products.txt
    public static void saveProducts() {
        try (PrintWriter writer = new PrintWriter("products.txt")) {

            for (Product p : products) {
                writer.println(p.name + "|" + p.price + "|"
                        + p.cost + "|" + p.stock);
            }

        } catch (IOException e) {
            System.out.println("\nError saving products.");
        }
    }

    // Load products from products.txt
    public static void loadProducts() {
        products.clear();

        File file = new File("products.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 4) {
                    products.add(new Product(
                            data[0],
                            Double.parseDouble(data[1]),
                            Double.parseDouble(data[2]),
                            Integer.parseInt(data[3])
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("\nError loading products.");
        }
    }
}