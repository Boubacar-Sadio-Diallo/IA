package cp;

import java.util.*;
import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic {
    Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    public List<Object> ordering(Variable variable, Set<Object> domain) {
        if (variable == null || domain.isEmpty()) {
            return null;
        }
        List<Object> listeMelangees = new ArrayList<>(domain);
        Collections.shuffle(listeMelangees, random);
        return listeMelangees;
    }

}