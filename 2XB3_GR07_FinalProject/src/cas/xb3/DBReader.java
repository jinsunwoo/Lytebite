package cas.xb3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBReader {

    public DBReader() {}

    public List<String> getFoods() throws FileNotFoundException {
        List<String> output = new ArrayList<>();
        // Create scanner from foodName.csv file.
        Scanner scanner = new Scanner(new FileInputStream("database/foodName.csv"));
        String line;
        // Skip first line that contains legend.
        scanner.nextLine();

        while (scanner.hasNext()) {
            // Add all names of foods into output. Regex ignores commas within quotation marks.
            line = scanner.nextLine();
            output.add(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[4]);
        }
        return output;
    }

    public List<String> getFoods(String category) throws FileNotFoundException {
        List<String> output = new ArrayList<>();
        Scanner scannerGroup = new Scanner(new FileInputStream("database/foodGroup.csv"));
        Scanner scannerName = new Scanner(new FileInputStream("database/foodName.csv"));

        String[] line = null;
        int groupID = 0;
        scannerName.nextLine();
        scannerGroup.nextLine();

        // Find ID of given category
        while (scannerGroup.hasNext()) {
            line = scannerGroup.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (line[2].equals(category)) {
                groupID = Integer.parseInt(line[0]);
                break;
            }
        }

        // Add every food that is part of given category
        while (scannerName.hasNext()) {
            line = scannerName.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (Integer.parseInt(line[2]) == groupID)
                output.add(line[4]);
        }

        return output;
    }

    // get the name of the food group that matches with the input food name
    public String getCategory(String input) throws FileNotFoundException {
        // Create scanner that reads from foodName.csv file.
        Scanner scanner1 = new Scanner(new FileInputStream("database/foodName.csv"));
        scanner1.nextLine();

        String[] token = new String[2];
        while (scanner1.hasNext()) {
            String line = scanner1.nextLine();
            String foodName = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[4];
            if (foodName.equals(input)) {
                String foodGroupID = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2];
                token[0]=foodGroupID;
                break;
            }
        }

        // Create scanner that reads from foodGroup.csv.
        Scanner scanner2 = new Scanner(new FileInputStream("database/foodGroup.csv"));
        scanner2.nextLine();

        while (scanner2.hasNext()) {
            String line = scanner2.nextLine();
            String foodGroup = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0];
            if (foodGroup.equals(token[0])) {
                token[1] = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2];
                break;
            }
        }
        return token[1];
    }

    // get the name of all the food group as an output
    public List<String> getCategories() throws FileNotFoundException {
        List<String> output = new ArrayList<>();
        // Create scanner that reads from foodGroup.csv file.
        Scanner scanner = new Scanner(new FileInputStream("database/foodGroup.csv"));
        String line;
        scanner.nextLine();

        while(scanner.hasNext()) {
            line = scanner.nextLine();
            output.add(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2]);
        }
        return output;
    }

    //This method takes an inputted food, and scans though multiple database files in order to match the food name with
    //the nutrients that the food contains
    public List<Pair<String, Double>> getNutrients(String input) throws FileNotFoundException {
        //List<Pair<String, Integer>> is [(Nutrient Name, Nutrient Amount)]
        List<Pair<String, Double>> output = new ArrayList<>();
        int foodID = -1;
        String[] tokens;

        Scanner scanner = new Scanner(new FileInputStream("database/foodName.csv"));
        scanner.nextLine();

        while(scanner.hasNextLine()) {
            tokens = scanner.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if(tokens[4].equals(input)) {
                foodID = Integer.parseInt(tokens[0]); break; }
        }

        if(foodID == -1)
            return null;

        scanner = new Scanner(new FileInputStream("database/nutrientAmount.csv"));
        scanner.nextLine();


        while(scanner.hasNextLine()) {
            tokens = scanner.nextLine().split(",", -1);
            if(foodID == Integer.parseInt(tokens[0])) {
                Double amount = Double.parseDouble(tokens[2]);
                int nutrientID = Integer.parseInt(tokens[1]);

                Scanner scanner1 = new Scanner(new FileInputStream("database/nutrientName.csv"));
                scanner1.nextLine();

                while(scanner1.hasNextLine()) {
                    tokens = scanner1.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if(Integer.parseInt(tokens[0]) == nutrientID) {
                        output.add(new Pair<>(tokens[4], amount)); break; }
                }
            }
        }
        return output;
    }
}
