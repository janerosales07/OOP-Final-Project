import java.io.*;
import java.util.ArrayList;

public class Order {

    // Stores all orders
    static ArrayList<Order> orders = new ArrayList<>();

    String customer;
    String productName;
    int quantity;
    double total;
    double cost;
    String eventName;
    String eventDate;
    String eventTime;
    String eventLocation;

    // Constructor
    public Order(String customer, String productName, int quantity,
                double total, double cost, Event event) {

        this.customer = customer;
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.cost = cost;

        this.eventName = event.name;
        this.eventDate = event.date;
        this.eventTime = event.time;
        this.eventLocation = event.location;
    }

    // Place a pre-order
    public static void placeOrder(Product product, int quantity,
                                 String customer, Event event) {

        double total = product.price * quantity;
        double cost = product.cost * quantity;

        // Deduct stock after successful order
        product.stock -= quantity;

        orders.add(new Order(
                customer,
                product.name,
                quantity,
                total,
                cost,
                event
        ));

        // Update product inventory file
        Product.saveProducts();

        // Save order information
        saveOrders();

        System.out.println("\n===== ORDER SUMMARY =====");
        System.out.println("Customer: " + customer);
        System.out.println("Product: " + product.name);
        System.out.println("Quantity: " + quantity);
        System.out.println("Total payment: PHP " + total);
        System.out.println("Pickup event: " + event.name);
        System.out.println("Pickup date: " + event.date);
        System.out.println("Pickup time: " + event.time);
        System.out.println("Pickup location: " + event.location);
        System.out.println("\nOrder saved successfully!");
    }

    // Display all pre-orders
    public static void viewOrders() {
        System.out.println("\n===== PRE-ORDERS =====");

        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);

            System.out.println((i + 1) + ". " + o.customer
                    + " | Product: " + o.productName
                    + " | Quantity: " + o.quantity
                    + " | Total: PHP " + o.total
                    + " | Pickup: " + o.eventName
                    + " (" + o.eventDate + " " + o.eventTime + ")");
        }
    }

    // Save orders to orders.txt
    public static void saveOrders() {
        try (PrintWriter writer = new PrintWriter("orders.txt", java.nio.charset.StandardCharsets.UTF_8)) {

            for (Order o : orders) {
                writer.println(o.customer + "|" + o.productName + "|"
                        + o.quantity + "|" + o.total + "|" + o.cost + "|"
                        + o.eventName + "|" + o.eventDate + "|"
                        + o.eventTime + "|" + o.eventLocation);
            }

        } catch (IOException e) {
            System.out.println("Error saving orders.");
        }
    }

    // Load orders from orders.txt
    public static void loadOrders() {
        orders.clear();

        File file = new File("orders.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file, java.nio.charset.StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 9) {

                    // Rebuild pickup event information
                    Event event = new Event(
                            data[5],
                            data[6],
                            data[7],
                            data[8]
                    );

                    orders.add(new Order(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]),
                            Double.parseDouble(data[4]),
                            event
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("\nError loading orders.");
        }
    }
}