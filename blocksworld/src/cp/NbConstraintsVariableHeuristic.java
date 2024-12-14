package cp;
import java.util.*;
import modelling.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{
    private Set<Constraint> setConstraints;
    private boolean dansPlusContraintes;

    public NbConstraintsVariableHeuristic(Set<Constraint> setConstraints,boolean dansPlusContraintes){
        this.setConstraints = setConstraints;
        this.dansPlusContraintes = dansPlusContraintes;
    }

    @Override
    public Variable best(Set<Variable> setVariable,Map<Variable, Set<Object>> setDomains){
        //vérifier avant tout que mes contraintes ou mes varibales n'est pas vide
        if(setVariable.isEmpty() || setConstraints.isEmpty()){
            return null;
        }
        int minmaxCompteur = dansPlusContraintes ? -1 : Integer.MAX_VALUE;
        Variable meilleurVariable = null; 
        //Est ce que les contraintes impliquent les variables
        for(Variable variable : setVariable){
            int compteur=0;
            for(Constraint constraintes : setConstraints){
                if(constraintes.getScope().contains(variable)){
                    compteur=compteur+1;
                }
            }

            if(dansPlusContraintes){
                //Variable dans le plus de contraintes
                if(compteur > (minmaxCompteur)){
                    minmaxCompteur = compteur;
                    meilleurVariable = variable;
                }
            }else{
                //variable dans le moins de contrainte
                if(compteur < minmaxCompteur){
                    minmaxCompteur = compteur;
                    meilleurVariable = variable;
                }
            }
        }
        return meilleurVariable;
    }

}