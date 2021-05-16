package cas.xb3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
    FileWriter log;

    public Logger() throws IOException {
        log = new FileWriter("log.txt");
        log.write("FoodName,Calories,Sugar,Sodium,Fat\n");
        log.flush();
    }

    // Takes given food name and adds it, plus it's nutrient info to a log
    public void add(String foodname) throws IOException {
        // Initialize string to put into file, and variables for each nutrition value
        String output = foodname + ",";
        String cal = "---";
        String sugar = "---";
        String sodium = "---";
        String fat = "---";

        // Get the nutrients of the given food name
        List<Pair<String, Double>> nutrients = new DBReader().getNutrients(foodname);

        // Get values of each nutrient
        for (Pair<String, Double> pair : nutrients) {
            if (pair.getFirst().equals("ENERGY (KILOCALORIES)"))
                cal = pair.getSecond().toString();
            else if (pair.getFirst().equals("\"SUGARS, TOTAL\""))
                sugar = pair.getSecond().toString();
            else if (pair.getFirst().equals("SODIUM"))
                sodium = pair.getSecond().toString();
            else if (pair.getFirst().equals("FAT (TOTAL LIPIDS)"))
                fat = pair.getSecond().toString();
        }
        // Add info to log
        log.write(output + cal + "," + sugar + "," + sodium + "," + fat + "\n");
        log.flush();
    }

    // Gets the contents of the log.txt, and puts it into an ArrayList
    public ArrayList<String> getLog() throws FileNotFoundException {
        // Initialize the output
        ArrayList<String> output = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream("log.txt"));
        String line;
        scanner.nextLine();

        // Add each line to the output
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            output.add(line);
        }

        return output;
    }

    // Reset the log
    public void clear() throws IOException {
        log = new FileWriter("log.txt");
        log.write("FoodName,Calories,Sugar,Sodium,Fat\n");
        log.flush();
    }
}
