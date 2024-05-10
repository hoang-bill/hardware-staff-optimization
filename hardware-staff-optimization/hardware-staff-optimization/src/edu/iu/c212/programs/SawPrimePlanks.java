package edu.iu.c212.programs;

import java.util.ArrayList;
import java.util.List;

public class SawPrimePlanks {

    public List<Integer> sawPlank(int plankLength) {
        List<Integer> result = new ArrayList<>();
        if (plankLength <= 1) {
            return result;  // Return an empty list for non-valid plank lengths
        }
        recursivelySawPlank(plankLength, result);
        return result;
    }

    private void recursivelySawPlank(int plankLength, List<Integer> result) {
        if (isPrime(plankLength)) {
            result.add(plankLength);  // Add prime plank length to the result
        } else {
            int factor = smallestPrimeFactor(plankLength);
            int newLength = plankLength / factor;
            for (int i = 0; i < factor; i++) {
                recursivelySawPlank(newLength, result);  // Recur for the new length
            }
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    private int smallestPrimeFactor(int number) {
        if (number % 2 == 0) return 2;
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return i;
        }
        return number;  // Return the number itself if no smaller prime factor found
    }
}
