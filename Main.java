import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // ==============================
    // INPUT VALIDATION
    // Prevent invalid numbers and negative inputs
    // ==============================

    // Accepts whole numbers only
    static int readInt(String message, int min) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                int number = Integer.parseInt(input);

                if (number < min) {
                    System.out.println("\nInvalid input. Minimum value is " + min + ".");
                } else {
                    return number;
                }

            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a whole number.");
            }
        }
    }

    // Accepts decimal numbers and prevents negative values
    static double readDouble(String message, double min) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                double number = Double.parseDouble(input);

                if (!Double.isFinite(number) || number < min) {
                    System.out.println("\nInvalid input. Please enter a value of " + min + " or higher.");
                } else {
                    return number;
                }

            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
            }
        }
    }

    // Accepts non-empty text
    static String readText(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\nInvalid input. This field cannot be empty.");
            } else if (input.contains("|")) {
                System.out.println("\n Invalid input. Please do not use the | character.");
            } else {
                return input;
            }
        }
    }

    // Accepts and validates a date
    static String readDate(String message) {
        while (true) {
            String input = readText(message);

            try {
                LocalDate.parse(input);
                return input;
            } catch (Exception e) {
                System.out.println("\nInvalid date. Use YYYY-MM-DD.");
            }
        }
    }

    // Accepts and validates time
    static String readTime(String message) {
        while (true) {
            String input = readText(message);

            try {
                LocalTime.parse(input);
                return input;
            } catch (Exception e) {
                System.out.println("\nInvalid time. Use HH:MM (24-hour format).");
            }
        }
    }

    // ==============================
    // PRODUCT MENU FUNCTIONS
    // ==============================

    // Add product
    static void addProductMenu() {
        System.out.println("\n===== ADD PRODUCT =====");

        String name = readText("Product name: ");
        double price = readDouble("Selling price: PHP ", 0.01);
        double cost = readDouble("Product cost: PHP ", 0);
        int stock = readInt("Initial stock: ", 0);

        Product.addProduct(name, price, cost, stock);
    }

    // Restock product
    static void restockMenu() {
        if (Product.products.isEmpty()) {
            System.out.println("\nNo products available to restock.");
            return;
        }

        Product.viewProducts();

        int choice = readInt("Choose product number: ", 1);

        Product product = Product.getProduct(choice - 1);

        if (product == null) {
            System.out.println("\nInvalid product selection.");
            return;
        }

        int quantity = readInt("Quantity to add: ", 1);

        Product.restockProduct(choice - 1, quantity);
    }

    // ==============================
    // EVENT MENU FUNCTIONS
    // ==============================

    // Add a campus selling event
    static void addEventMenu() {
        System.out.println("\n===== SET SELLING EVENT =====");

        String name = readText("Event name: ");
        String date = readDate("Selling date (YYYY-MM-DD): ");
        String time = readTime("Selling/pickup time (HH:MM): ");
        String location = readText("Location: ");

        Event.addEvent(name, date, time, location);
    }

    // ==============================
    // ORDER MENU FUNCTIONS
    // ==============================

    // Place customer pre-order and select pickup event
    static void placeOrderMenu() {

        if (Product.products.isEmpty()) {
            System.out.println("\nPlease add products first.");
            return;
        }

        if (Event.events.isEmpty()) {
            System.out.println("\nPlease schedule a selling event first.");
            System.out.println("\nOrders need a pickup schedule.");
            return;
        }

        System.out.println("\n===== PLACE PRE-ORDER =====");

        // Show available products
        Product.viewProducts();

        int productChoice = readInt("\nChoose product number: ", 1);
        Product product = Product.getProduct(productChoice - 1);

        if (product == null) {
            System.out.println("\nInvalid product selection.");
            return;
        }

        if (product.stock <= 0) {
            System.out.println("\nThis product is out of stock.");
            return;
        }

        String customer = readText("Customer name: ");
        int quantity = readInt("Quantity: ", 1);

        if (quantity > product.stock) {
            System.out.println("\nNot enough stock available.");
            System.out.println("Available stock: " + product.stock);
            return;
        }

        // Show available pickup schedules
        System.out.println("\n===== AVAILABLE PICKUP EVENTS =====");
        Event.viewEvents();

        int eventChoice = readInt("Choose pickup event number: ", 1);
        Event event = Event.getEvent(eventChoice - 1);

        if (event == null) {
            System.out.println("\nInvalid event selection.");
            return;
        }

        // Save order with the selected event
        Order.placeOrder(product, quantity, customer, event);
    }

    // ==============================
    // MAIN MENU
    // ==============================

    static void mainMenu() {

        while (true) {
            System.out.println("\n================================");
            System.out.println("       POPUP & PROFIT HUB");
            System.out.println("================================");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Restock Product");
            System.out.println("4. Set Selling Event");
            System.out.println("5. View Selling Events");
            System.out.println("6. Place Pre-order");
            System.out.println("7. View Pre-orders");
            System.out.println("8. View Sales & Profit");
            System.out.println("9. Exit");

            int choice = readInt("\nChoose an option: ", 1);

            switch (choice) {

                case 1:
                    addProductMenu();
                    break;

                case 2:
                    Product.viewProducts();
                    break;

                case 3:
                    restockMenu();
                    break;

                case 4:
                    addEventMenu();
                    break;

                case 5:
                    Event.viewEvents();
                    break;

                case 6:
                    placeOrderMenu();
                    break;

                case 7:
                    Order.viewOrders();
                    break;

                case 8:
                    Profit.viewReport();
                    break;

                case 9:
                    System.out.println("\nData saved successfully.");
                    System.out.println("\nThank you for using PopUp & Profit!");
                    return;

                default:
                    System.out.println("\nInvalid option. Please choose from 1-9.");
            }
        }
    }

    // ==============================
    // PROGRAM START
    // Load saved data before showing the menu
    // ==============================

    public static void main(String[] args) {
        Product.loadProducts();
        Event.loadEvents();
        Order.loadOrders();

        System.out.println("\nWelcome to PopUp & Profit!");

        mainMenu();
    }
}