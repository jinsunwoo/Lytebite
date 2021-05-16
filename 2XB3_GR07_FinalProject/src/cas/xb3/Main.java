package cas.xb3;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //Testing code here
        //TreeBuilder is not necessary here because this is the CLI version. In the actual application, TreeBuilder is
        //Necessary to speed up the user experience and prevent unnecessary delays
        DBReader dbReader = new DBReader();
        Logger log = new Logger();

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Categories, Listed Alphabetically:");
            System.out.println(SortingAlgorithms.quickSort(dbReader.getCategories(), 0, dbReader.getCategories().size() - 1));
            System.out.println("Select Category: ");
            String category = scanner.nextLine();
            System.out.println("Foods in Category, Listed Alphabetically:");
            System.out.println(SortingAlgorithms.quickSort(dbReader.getFoods(category), 0, dbReader.getFoods(category).size() - 1).toString());
            System.out.println("Select Food: ");
            String food = scanner.nextLine();
            System.out.println("Nutritional Information:");
            double cal = 0;
            double sugar = 0;
            double na = 0;
            double fat = 0;
            for (Pair<String, Double> pair : dbReader.getNutrients(food)) {
                if (pair.getFirst().equals("ENERGY (KILOCALORIES)"))
                    cal = pair.getSecond();
                else if (pair.getFirst().equals("\"SUGARS, TOTAL\""))
                    sugar = pair.getSecond();
                if (pair.getFirst().equals("SODIUM"))
                    na = pair.getSecond();
                if (pair.getFirst().equals("FAT (TOTAL LIPIDS)"))
                    fat = pair.getSecond();
            }
            System.out.println("Calories: " + cal);
            System.out.println("Sugar: " + sugar + "g");
            System.out.println("Sodium: " + na + "g");
            System.out.println("Fat: " + fat + "g");

            System.out.println("Log? (Y/N)");
            if(scanner.nextLine().equals("Y"))
                log.add(food);
            for(int i = 0; i != log.getLog().size(); i ++) {
	            System.out.println("Food: " + log.getLog().get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0]);
	            System.out.println("Cal: " + log.getLog().get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[1]);
	            System.out.println("Sugar: " + log.getLog().get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2]);
	            System.out.println("Sodium: " + log.getLog().get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[3]);
	            System.out.println("Fat: " + log.getLog().get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[4]);
                System.out.println();
            }

            System.out.println("Continue? (Y/N)");
            if(scanner.nextLine().equals("N"))
                break;
            System.out.println();
            System.out.println();
        }
    }
}