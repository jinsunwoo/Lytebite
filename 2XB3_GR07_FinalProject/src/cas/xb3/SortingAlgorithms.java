package cas.xb3;

import java.util.ArrayList;
import java.util.List;

public class SortingAlgorithms {
    // Caller for the quicksort algorithm
    public static List<String> quickSort(List<String> input, int start, int end) {
        // Initialize variables
        List<String> unquotedinput = new ArrayList<>();
        List<String> output = new ArrayList<>();

        // Creates new list of names without quotations so the sorting algorithm doesn't
        // count quotations when sorting
        for (String food : input) {
            // If food name has quotation marks, remove them
            if (food.indexOf('"') >= 0)
                food = food.substring(1, food.length() - 1);
            unquotedinput.add(food);
        }

        // Sort the list
        quickSortLex(unquotedinput, start, end);

        // After sorting, put back any quotations that were removed from the names
        for (String food : unquotedinput) {
            // If food name had quotation marks, add them again
            if (food.contains(","))
                food = '"' + food + '"';
            output.add(food);

        }
        return output;
    }

    private static void quickSortLex(List<String> input, int lo, int hi) {
        //Pivot
        if (hi > lo) {
            int pivot = partition(input, lo, hi);
            quickSortLex(input, lo, pivot - 1);
            quickSortLex(input, pivot, hi);
        }
    }

    private static int partition(List<String> input, int lo, int hi) {
        String pivot = input.get(hi);
        int i = lo;
        int j = hi;
        // Continues to swap any word in the list that is greater than and to the left of the pivot word with
        // any words less than and to the right of the pivot word.
        // Results in all words less than the pivot word to be on the left side of the pivot, and all
        // words greater than the pivot word to be on the right side.
        while ((input.get(i).compareTo(pivot) <= 0) && (i < hi))
            i++;
        while ((input.get(j).compareTo(pivot) > 0) && (j > lo))
            j = j - 1;
        // Swap
        swap(input, i, j);

        while (i < j) {
            while ((input.get(i).compareTo(pivot) <= 0) && (i < hi))
                i++;
            while ((input.get(j).compareTo(pivot) > 0) && (j > lo))
                j = j - 1;
            // Swap
            swap(input, i, j);
        }
        // Returns the position where the left side crossed the right side
        return i;
    }

    private static void swap(List<String> input, int i, int j) {
        if(i < j) {
            String hold = input.get(i);
            input.set(i, input.get(j));
            input.set(j, hold);
        }
    }
}
