package cp;
import modelling.*;
import java.util.*;

public abstract class AbstractSolver implements Solver{
    protected Set<Variable> variable;
    protected Set<Constraint> constraint;

    public AbstractSolver(Set<Variable> variable,Set<Constraint> constraint){
            this.variable=variable;
            this.constraint=constraint;
    }

    /**
     * @param instantiation: l'instanciation partielle
     * @return un boolean selon que l'instanciation partielle satisfait toutes les contraintes
     */
    public boolean isConsistent(Map<Variable,Object> instantiation){
        Set<Variable> keys = instantiation.keySet();
        for(Constraint contraintes : constraint){
            if(keys.containsAll(contraintes.getScope())){
                if(!contraintes.isSatisfiedBy(instantiation)){
                    return false;
                }
            }
        }
        return true; 
    }
}