package algorithms;

import java.util.ArrayList;
import java.util.List;

public class Algorithms {
    public List<Integer> aboveAverage(List<Integer> input){
        double average = 0;
        for (Integer i: input){
            average += i;
        }
        average = average / input.size();

        List<Integer> result = new ArrayList<>();
        for (Integer i: input) {
            if (i>average){
                result.add(i);
            }
        }
        return result;
    }
}
