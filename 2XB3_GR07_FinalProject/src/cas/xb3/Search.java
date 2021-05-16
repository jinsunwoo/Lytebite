package cas.xb3;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    public static List<String> match(String keyword) throws FileNotFoundException {
        DBReader dbReader = new DBReader();
        int keywordSize = keyword.length();
        List<String> foodsInOrder = new ArrayList<String>();
        // ArrayList of all the foodNames sorted in alphabetical order.
        foodsInOrder = (ArrayList<String>) SortingAlgorithms.quickSort(dbReader.getFoods(), 0, dbReader.getFoods().size() - 1);
        // Cut the length of each element of the ArrayList to the length of the inputted keyword.
        for (int i=0; i<foodsInOrder.size();i++) {
            if (foodsInOrder.get(i).length() > keywordSize) {
                foodsInOrder.set(i,foodsInOrder.get(i).substring(1,1+keywordSize));
            }
        }
        // Using binary search method in collections, get the index of the element that matches with the keyword inputted.
        int res = Collections.binarySearch(foodsInOrder,keyword);
        // If there is a match, output an ArrayList with first 10 elements that matches with the inputted keyword.
        if (res >=0) {
            List<String> foodsMatched = new ArrayList<String>();
            foodsMatched = (ArrayList<String>) SortingAlgorithms.quickSort(dbReader.getFoods(), 0, dbReader.getFoods().size() - 1);
            List<String> FoodMatched2 = new ArrayList<String>();
            return foodsMatched.subList(res,res+10);
        } else {
            return null;
        }
    }
}
