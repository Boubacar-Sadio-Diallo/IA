package datamining;
import java.util.Set;

public interface AssociationRuleMiner {

    /**
     * Retourne l'instance de la base de données booléenne utilisée par le mineur de règles d'association.
     *
     * @return Une instance de BooleanDatabase.
     */
    BooleanDatabase getDatabase();

    /**
     * Extrait les règles d'association dont la fréquence et la confiance sont supérieures aux seuils donnés.
     *
     * @param minFrequency  La fréquence minimale des règles d'association.
     * @param minConfidence La confiance minimale des règles d'association.
     * @return Un ensemble de règles d'association qui satisfont les critères de fréquence et de confiance.
     */
    Set<AssociationRule> extract(float minFrequency, float minConfidence);
}

