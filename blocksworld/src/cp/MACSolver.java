package cp;
import java.util.*;
import modelling.*;

public class MACSolver extends AbstractSolver{

     public MACSolver(Set<Variable> setVariables, Set<Constraint> setConstraints){
        super(setVariables,setConstraints);
    }
    
    public Map<Variable,Object> solve(){
        return mac(new HashMap<Variable,Object>(),new LinkedList<Variable>(super.variable),new HashMap<Variable,Set<Object>>());
    }
    /**
     * Explore l'arbre de recherche en maintenant la cohérence d'arc
     * @param instantiation: les variables instanciées
     * @param noInstantiation: les variables non instanciées
     * @param setDomain
     * @return retourne une solution
     */
    public Map<Variable,Object> mac(Map<Variable,Object> instantiation,LinkedList<Variable> noInstantiation,Map<Variable, Set<Object>> setDomain){
        if(noInstantiation.isEmpty()){
            return instantiation;
        }
        else{
            ArcConsistency arc = new ArcConsistency(super.constraint); 
            if(!arc.ac1(setDomain)){
                return null;
            }
            Variable noInstantied = noInstantiation.poll();
            Map<Variable,Object> sub_mac = new HashMap<>();
            for(Object valeur : noInstantied.getDomain()){
                Map<Variable, Object> etatNouvel = new HashMap<>(instantiation);
                etatNouvel.put(noInstantied,valeur);
                if(isConsistent(etatNouvel)){
                    sub_mac = mac(etatNouvel,noInstantiation,setDomain);
                    if(sub_mac!=null){
                        return sub_mac;
                    }
                }
            }
            noInstantiation.add(noInstantied);
            return null;
        }
    }
 
}