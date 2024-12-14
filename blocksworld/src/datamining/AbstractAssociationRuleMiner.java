package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase booleanDB;

    /**
     * Constructeur pour la classe AbstractAssociationRuleMiner.
     *
     * @param booleanDB L'instance de la base de données booléenne à utiliser.
     */
    public AbstractAssociationRuleMiner(BooleanDatabase booleanDB) {
        this.booleanDB = booleanDB;
    }

    /**
     * Accesseur pour obtenir la base de données.
     *
     * @return La base de données booléenne utilisée par ce mineur de règles d'association.
     */
    @Override
    public BooleanDatabase getDatabase() {
        return this.booleanDB; 
    }
    
    /**
     * Méthode statique pour calculer la fréquence d'un ensemble d'items.
     */
    public static float frequency(Set<BooleanVariable> items,Set<Itemset> itemsets){
        for(Itemset itemset : itemsets){
            if(itemset.getItems().equals(items)){
                return itemset.getFrequency();
            }
        } 
        throw new IllegalArgumentException("L'ensemble d'items n'est pas présent dans les itemsets fréquents.");
    }

    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion,Set<Itemset> itemset){
        // Calculer la fréquence de la prémisse
        float premisseFrequency = frequency(premisse, itemset);
        // Créer un nouvel ensemble d'items pour la combinaison prémisse + conclusion
        Set<BooleanVariable> combined = new HashSet<>(premisse);
        combined.addAll(conclusion);
        float combinedFrequency = frequency(combined, itemset);
        // Calculer la confiance comme le ratio de la fréquence combinée sur la fréquence de la prémisse
        return combinedFrequency / premisseFrequency; 

    }
}

