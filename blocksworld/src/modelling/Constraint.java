package modelling;

import java.util.*;

public interface Constraint{
    public Set<Variable> getScope();//ensemble des variables sur lesquelles portent la contrainte
    public boolean isSatisfiedBy(Map<Variable, Object> ensemblesVariables);//retourne un booleen selon que la contrainte est satisfaite ou non par l'instanciation donnée
}