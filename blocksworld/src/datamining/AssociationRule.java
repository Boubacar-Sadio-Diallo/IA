package datamining;

import modelling.BooleanVariable;
import java.util.Set;

public class AssociationRule {
    // Propriétés de la règle d'association
    private Set<BooleanVariable> premise;   // Prémisse de la règle
    private Set<BooleanVariable> conclusion; // Conclusion de la règle
    private float frequency;                  // Fréquence de la règle
    private float confidence;                 // Confiance de la règle

    /**
     * Constructeur pour créer une règle d'association.
     *
     * @param premise     La prémisse de la règle (un ensemble de variables booléennes).
     * @param conclusion  La conclusion de la règle (un ensemble de variables booléennes).
     * @param frequency   La fréquence de la règle.
     * @param confidence  La confiance de la règle.
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    /**
     * Retourne la prémisse de la règle.
     *
     * @return La prémisse de la règle.
     */
    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    /**
     * Retourne la conclusion de la règle.
     *
     * @return La conclusion de la règle.
     */
    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    /**
     * Retourne la fréquence de la règle.
     *
     * @return La fréquence de la règle.
     */
    public float getFrequency() {
        return frequency;
    }

    /**
     * Retourne la confiance de la règle.
     *
     * @return La confiance de la règle.
     */
    public float getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        return "AssociationRule{" +
           "premise=" + premise +
           ", conclusion=" + conclusion +
           ", frequency=" + frequency +
           ", confidence=" + confidence +
           '}';
    }

}
