package planning;
import modelling.*;
/**
 * Définit une méthode pour estimer le coût heuristique d'un état donné.
 * Les classes implementant cette interface doivent fournir 
 * une estimation du cout restant pour atteindre l'objectif à partir de l'état fourni
 */
import java.util.*;
public interface Heuristic{
    /**
     * Estime le cout heuristique du coût restant d'un noeud au noeud objectif
     *@param heuristique: l'état pour lequel estimer le cout heuristique
     *@return une estimation du cout heuristique de l'etat.
     */
    float estimate(Map<Variable,Object> heuristique);
}