
package planning;
import modelling.Variable;

import java.util.*;
public interface Action{
    /**
     * Vérifie si une telle action est applicable dans un état ou pas
     * @param etat l'état actuel
     * @return true si l'action est applicable et faux sinon
     */
    public boolean isApplicable(Map<Variable, Object> etat);
    /**
     * renvoie l'état suivant contenant les effets
     * @param etat l'état actuel
     */
    public Map<Variable, Object> successor(Map<Variable, Object> etat);
    /**
     * renvie le coût d'une action
     * @return un entier qui represente le coût
     */
    public int getCost();
    
}