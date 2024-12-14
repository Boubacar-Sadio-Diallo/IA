package planning;
import modelling.*;
import java.util.*;

/**
    * L'interface Planner définit les méthodes nécessaires pour planifier des actions
    * afin d'atteindre un objectif  aprtir de l'état initial
*/
public interface Planner{

    /**
     * renvoie un plan
     * @return une liste d'actions ou null s'il n'y en pas
     */
    public List<Action> plan();
    /**
     * Renvoie l'état initial
     * @return un état
     */
    public Map<Variable, Object> getInitialState();
    /**
     * Renvoie la liste des actions appliquées sur une instanciation d'un état
     * @return renvoie un ensemble d'actions
     */
    public Set<Action> getActions();
    /**
     * Renvoie le but à atteindre
     * @return un objet Goal
     */
    public Goal getGoal();
    /**
     * Permet  d'activer le comptage des noeuds explorés
     * @param active: active(true) ou désactive(false) le comptage
     */
    public void activateNodeCount(boolean activate);
    /**
     * Permet d'obtenir le nbre de noeud explorés lors de la planification si activeNodeCount==true
     * @return le nombre de neouds explorés
     */
    public int count();
    
}