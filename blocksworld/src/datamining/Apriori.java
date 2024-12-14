package datamining;
import modelling.BooleanVariable;
import java.util.*;

public class Apriori extends AbstractItemsetMiner {
    /**
     * Représente les extracteurs fonctionnant sur le principe de l'algorithme Apriori
     */
    public Apriori(BooleanDatabase baseDD) {
        super(baseDD);
    }

@Override
public Set<Itemset> extract(float frequenceMin) {
    // Initialiser les singletons fréquents
    List<SortedSet<BooleanVariable>> currentLevelFrequent = new ArrayList<>();
    Set<Itemset> frequentSingleton = frequentSingletons(frequenceMin);
    
    for (Itemset itemset : frequentSingleton) {
        SortedSet<BooleanVariable> sortedSet = new TreeSet<>(COMPARATOR);
        sortedSet.addAll(itemset.getItems());
        currentLevelFrequent.add(sortedSet);
    }

    // Générer les itemsets de niveau k jusqu'à épuisement
    while (!currentLevelFrequent.isEmpty()) {
        List<SortedSet<BooleanVariable>> nextLevelFrequent = new ArrayList<>();

        // Génération des candidats de longueur k+1
        for (int i = 0; i < currentLevelFrequent.size(); i++) {
            for (int j = i + 1; j < currentLevelFrequent.size(); j++) {
                SortedSet<BooleanVariable> combined = combine(currentLevelFrequent.get(i), currentLevelFrequent.get(j));
                if (combined != null && allSubsetsFrequent(combined, currentLevelFrequent)) {
                    float freq = frequency(combined);
                    if (freq >= frequenceMin) {
                        frequentSingleton.add(new Itemset(combined, freq)); // Ajouter ici à allFrequentItemsets
                        nextLevelFrequent.add(combined);
                    }
                }
            }
        }
        // Passer au niveau suivant
        currentLevelFrequent = nextLevelFrequent;
    }
    return frequentSingleton;
}


    public static boolean allSubsetsFrequent(Set<BooleanVariable> itemset, Collection<SortedSet<BooleanVariable>> frequentItemsets) {
        for (BooleanVariable item : itemset) {
            Set<BooleanVariable> copySubSet = new HashSet<>(itemset);
            copySubSet.remove(item);
            if (!frequentItemsets.contains(copySubSet)) return false;
        }
        return true;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2) {
        if ((set1.size() != set2.size() || set1.isEmpty()) ) return null;
        
        BooleanVariable[] items1 = set1.toArray(new BooleanVariable[0]);
        BooleanVariable[] items2 = set2.toArray(new BooleanVariable[0]);
        
        for (int i = 0; i < items1.length - 1; i++) {
            if (!items1[i].equals(items2[i])) return null;
        }
        if (set1.last().equals(set2.last())) return null;  
        SortedSet<BooleanVariable> combined = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        combined.addAll(set1);
        combined.add(items2[items2.length - 1]);
        return combined;
        
        
    }

    /**
     * Méthode pour obtenir les items singletons fréquents
     */
    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> singletons = new HashSet<>();
        for (BooleanVariable item : baseDD.getItems()) {
            SortedSet<BooleanVariable> singletonSet = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            singletonSet.add(item);
            float freq = frequency(singletonSet);
            if (freq >= minFrequency) {
                singletons.add(new Itemset(singletonSet, freq));
            }
        }
        return singletons;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return baseDD;
    }
}
