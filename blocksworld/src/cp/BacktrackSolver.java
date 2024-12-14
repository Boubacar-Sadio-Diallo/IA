package cp;
import modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver{

    public BacktrackSolver(Set<Variable> setVariables, Set<Constraint> setConstraints){
        super(setVariables,setConstraints);
    }

    public Map<Variable,Object> solve(){
        return backtrack(new HashMap<Variable,Object>(),new LinkedList<>(super.variable));
    }
    /**
     * Méthode récursive de backtracking qui tente d'assigner des valeurs aux variables
     * et vérifier la validité de chaque instanciation partielle
     * @param instantiation: l'instanciation partielle actuelle des variables
     * @param noInstantiation listes des variables restantes à assigner
     * @return une solution 
     */
    public Map<Variable, Object> backtrack(Map<Variable,Object> instantiation,LinkedList<Variable> noInstantiation){
        if(noInstantiation.isEmpty()){
            return instantiation;
        }
        Variable noInstantied = noInstantiation.poll();
        Set<Object> domain = noInstantied.getDomain();
        Map<Variable,Object> sub_bt = new HashMap<>();
        for(Object  valeur : domain){
            Map<Variable, Object> etatNouvel = new HashMap<>(instantiation);
            etatNouvel.put(noInstantied,valeur);
            if(isConsistent(etatNouvel)){
                sub_bt = backtrack(etatNouvel,noInstantiation);
                if(sub_bt!=null){
                    return sub_bt;
                }
            }
        }
        noInstantiation.add(noInstantied);
        return null;
    }
}