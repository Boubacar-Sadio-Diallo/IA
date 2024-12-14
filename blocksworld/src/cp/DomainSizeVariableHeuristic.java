package cp;
import java.util.*;
import modelling.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic{
    private boolean plusmoinsDomaines;

    public DomainSizeVariableHeuristic(boolean plusmoinsDomaines){
        
        this.plusmoinsDomaines = plusmoinsDomaines;
    }

    @Override
    public Variable best(Set<Variable> setVariable, Map<Variable, Set<Object>> setDomains) {
    if (setVariable.isEmpty()) {
        return null;
    }

    // On crée une ArrayList de l'ensemble de nos variables
    List<Variable> variablesTriees = new ArrayList<>(setVariable);

    // On trie les variables selon la longueur de leur domaine
    variablesTriees.sort((v1, v2) -> {
        int longueurDomaineV1 = setDomains.get(v1).size();
        int longueurDomaineV2 = setDomains.get(v2).size();
        return Integer.compare(longueurDomaineV1, longueurDomaineV2);
    });

    // Utiliser une variable d'instance pour indiquer la préférence
    return plusmoinsDomaines ? variablesTriees.get(variablesTriees.size() - 1) : variablesTriees.get(0);
}


}