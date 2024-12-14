package cp;
import modelling.*;
import java.util.*;

public class ArcConsistency{
    private Set<Constraint> contraintesUnaires;
    private Set<Constraint> contraintesBinaires;
    

    public ArcConsistency(Set<Constraint> ensembleConstraintes){
        init(ensembleConstraintes);
    }
    /**
     * Vérifie si les contraintes sont unaires ou binaires
     * @param ensConst: l'ensemble desc ontraintes définies dans le problème
     */
    public void init(Set<Constraint> ensConst){
        contraintesUnaires = new HashSet<>();
        contraintesBinaires = new HashSet<>();
        for(Constraint contraintes : ensConst){
            Set<Variable> scope = contraintes.getScope();
            if (scope.size()==1) {
                contraintesUnaires.add(contraintes);
            } 
            else if (scope.size()==2) {
                    contraintesBinaires.add(contraintes);
                    
                } else{
                    throw new IllegalArgumentException("Ni unaire ni binaire");
                }
        }
    }
    /**
     * Supprime les valeurs non valides d'un domaine de variables
     * @param setDomain: represente l'ensemble des domaines des variables
     * @return: true si toutes les valeurs du domaine s'elles satisfont toutes les contraintes unaires
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> setDomain){
        for(Variable variable : setDomain.keySet()){
            Set<Object> domainVariable = setDomain.get(variable);
            Set<Object> variableSupp = new HashSet<>();
            for(Object valeur : new HashSet<>(domainVariable)){
                for(Constraint constraint : contraintesUnaires){
                    if(constraint.getScope().contains(variable)){
                        Map<Variable,Object> etatActuel = new HashMap<>();
                        etatActuel.put(variable,valeur);
                        if(!constraint.isSatisfiedBy(etatActuel)){
                            variableSupp.add(valeur);
                            break;// On passe à la valeur suivante après avoir trouvé une contrainte insatisfaisante
                        }
                    }
                }
            }
            domainVariable.removeAll(variableSupp);
        }
            for(Variable variable : setDomain.keySet()){
                Set<Object> domainVariable = setDomain.get(variable);

                if(domainVariable.isEmpty()){
                    return false;//indique aumoins qu'un domaine est vide
                }
            }
            return true;
    }
    

    /**
     * Revise un arc entre deux variables pour appliquer les contraintes binaires et 
     * on supprime les valeurs non satisfaites  d'un des domaines
     * @param v1
     * @param domaine1
     * @param v2
     * @param domaine2
     * @return true si certaines valeurs de domaine1 ont été supprimées
     */
    public boolean revise(Variable v1,Set<Object> domaine1,Variable v2,Set<Object> domaine2){
        boolean supprime = false;
        Set<Object> variableSupp = new HashSet<>();
        for(Object valeurV1 : domaine1){
                boolean viable = false;
            for(Object valeurV2 : domaine2){
                boolean toutSatisfait = true;
                for(Constraint contrainte : contraintesBinaires){
                    if(contrainte.getScope().contains(v1) && contrainte.getScope().contains(v2)){
                        Map<Variable,Object> etat = new HashMap<>();
                        etat.put(v1,valeurV1);
                        etat.put(v2,valeurV2);
                        if(!contrainte.isSatisfiedBy(etat)){
                            toutSatisfait=false;
                            break;
                        }
                    }
                }
                if(toutSatisfait){
                    viable=true;
                    break;
                }

            }
            if(!viable){
                variableSupp.add(valeurV1);
                supprime=true;
            }
        }
        domaine1.removeAll(variableSupp);
        return supprime;
    }

    /**
     * 
     * @param setDomain ensemble de domaines
     * @return false si au moins un domain a été vidé et true si tous les domaines sont valides 
     */
    public boolean ac1(Map<Variable,Set<Object>> setDomain){
        if(!enforceNodeConsistency(setDomain)){
            return false;
        }
        boolean change = false;
        do{
            change= false;
            for(Variable v1 : setDomain.keySet()){
                for(Variable v2 : setDomain.keySet()){
                    if(!Objects.equals(v1,v2)){
                        if(revise(v1,v1.getDomain(),v2,v2.getDomain())){
                            change = true;
                        }
                    }
                }
            }
        }while(change);
        for(Variable variable : setDomain.keySet()){
            if(variable.getDomain().isEmpty()){
                return false;
            }
        }
        return true;
    }
}
            
