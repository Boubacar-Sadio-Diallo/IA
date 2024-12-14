package modelling;

import java.util.*;
/**
 * Represente les contraintes de la forme v appartient S
 *  s est un sous-ensemble de son domaine
 */
public class UnaryConstraint implements Constraint{
    private Variable v;
    private Set<Object> s;

    public UnaryConstraint(Variable v, Set<Object> s){
        this.v = v;
        this.s = s;
    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> sousEns = new HashSet<Variable>();
        sousEns.add(v);
        return sousEns;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> ensemblesVariablesValeurs){
        
        for(Variable var : getScope()){
             if (!ensemblesVariablesValeurs.containsKey(var)) {
                throw new IllegalArgumentException("Les variables sont introuvables");

             }
        }
        Object valV = ensemblesVariablesValeurs.get(v);
        //Vérifie si la valeur assignée à v appartient au sous-ensemble
        return s.contains(valV);
    }
}