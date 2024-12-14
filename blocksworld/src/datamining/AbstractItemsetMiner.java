package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    protected BooleanDatabase baseDD;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
    
    public AbstractItemsetMiner(BooleanDatabase baseDD) {
        this.baseDD = baseDD;
    }

    public BooleanDatabase getBase() {
        return this.baseDD;
    }

    public float frequency(Set<BooleanVariable> items) {
        int compteur = 0;
        for (Set<BooleanVariable> transaction : baseDD.getTransactions()) {

            if (transaction.containsAll(items)) {
                compteur = compteur + 1;
            }
        }
        return compteur / (float)baseDD.getTransactions().size();
    }

    
}
