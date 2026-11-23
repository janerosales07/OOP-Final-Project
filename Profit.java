public class Profit {

    // Calculate total sales
    public static double getTotalSales() {
        double sales = 0;

        for (Order o : Order.orders) {
            sales += o.total;
        }

        return sales;
    }

    // Calculate total product costs
    public static double getTotalCost() {
        double cost = 0;

        for (Order o : Order.orders) {
            cost += o.cost;
        }

        return cost;
    }

    // Calculate profit
    public static double getProfit() {
        return getTotalSales() - getTotalCost();
    }

    // Display financial report
    public static void viewReport() {
        System.out.println("\n===== SALES & PROFIT REPORT =====");

        System.out.println("Total Orders: " + Order.orders.size());
        System.out.printf("Total Sales: ₱%.2f%n", getTotalSales());
        System.out.printf("Total Product Cost: ₱%.2f%n", getTotalCost());
        System.out.printf("Total Profit: ₱%.2f%n", getProfit());
    }
}