package planning;
import modelling.Variable;

import java.util.*;
public interface Goal{
    /**
     * Verifie si un état permet d'atteindre un but
     * @param state: l'état actuel
     * @return : renvoie vrai si le but est satisfait par l'etat et faux sinon
     */
    public boolean isSatisfiedBy(Map<Variable, Object> state);
    
}