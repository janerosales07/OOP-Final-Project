import java.io.*;
import java.util.ArrayList;

public class Event {

    // Stores scheduled selling events
    static ArrayList<Event> events = new ArrayList<>();

    String name;
    String date;
    String time;
    String location;

    // Constructor
    public Event(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    // Add a selling event
    public static void addEvent(String name, String date, String time, String location) {
        events.add(new Event(name, date, time, location));
        saveEvents();

        System.out.println("\nSelling event added successfully!");
    }

    // Display all selling events
    public static void viewEvents() {
        System.out.println("\n===== SELLING EVENTS =====");

        if (events.isEmpty()) {
            System.out.println("No selling events scheduled.");
            return;
        }

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);

            System.out.println((i + 1) + ". " + e.name
                    + " | Date: " + e.date
                    + " | Time: " + e.time
                    + " | Location: " + e.location);
        }
    }

    // Get selected event
    public static Event getEvent(int index) {
        if (index >= 0 && index < events.size()) {
            return events.get(index);
        }

        return null;
    }

    // Save events to events.txt
    public static void saveEvents() {
        try (PrintWriter writer = new PrintWriter("events.txt")) {

            for (Event e : events) {
                writer.println(e.name + "|" + e.date + "|"
                        + e.time + "|" + e.location);
            }

        } catch (IOException e) {
            System.out.println("Error saving events.");
        }
    }

    // Load events from events.txt
    public static void loadEvents() {
        events.clear();

        File file = new File("events.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 4) {
                    events.add(new Event(
                            data[0],
                            data[1],
                            data[2],
                            data[3]
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading events.");
        }
    }
}