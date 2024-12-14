package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {
    
    public BruteForceAssociationRuleMiner(BooleanDatabase booleanDB){
        super(booleanDB);
    }
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> rules = new HashSet<>();
        Apriori apriori = new Apriori(booleanDB);
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency); // Assume a method to get all frequent itemsets

        // Pour chaque itemset fréquent, on génère des règles candidates
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems();

            // Trouver toutes les prémisses candidates (sous-ensembles de l'itemset)
            Set<Set<BooleanVariable>> premises = allCandidatePremises(items);

            for (Set<BooleanVariable> premise : premises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                // Calculer la fréquence et la confiance
                float frequency = AbstractAssociationRuleMiner.frequency(items, frequentItemsets);
                float confidence = AbstractAssociationRuleMiner.confidence(premise, conclusion, frequentItemsets);

                // Vérifier si les seuils de fréquence et de confiance sont atteints
                if (frequency >= minFrequency && confidence >= minConfidence) {
                    rules.add(new AssociationRule(premise, conclusion, frequency, confidence));
                }
            }
        }

        return rules;
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> allSubsets = new HashSet<>();
        
        // Pour chaque élément de l'ensemble d'origine, on génère les sous-ensembles
        for (BooleanVariable item : items) {
            Set<Set<BooleanVariable>> newSubsets = new HashSet<>();
            
            for (Set<BooleanVariable> subset : allSubsets) {
                Set<BooleanVariable> extendedSubset = new HashSet<>(subset);
                extendedSubset.add(item);
                newSubsets.add(extendedSubset);
            }
            
            allSubsets.addAll(newSubsets);
            
            // Ajoute le sous-ensemble contenant uniquement l'élément actuel
            Set<BooleanVariable> singleItemSet = new HashSet<>();
            singleItemSet.add(item);
            allSubsets.add(singleItemSet);
        }
        
        // Supprime l'ensemble complet et l'ensemble vide
        allSubsets.remove(items);
        allSubsets.remove(new HashSet<>());
        
        return allSubsets;
    }

}
