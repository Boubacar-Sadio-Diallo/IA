package cp;
import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver{
    private ValueHeuristic heuristicValeur;
    private VariableHeuristic heuristicVariable;

    public HeuristicMACSolver(Set<Variable> variable,Set<Constraint> constraint,VariableHeuristic heuristicVariable,ValueHeuristic heuristicValeur){
        super(variable,constraint);
        this.heuristicValeur = heuristicValeur;
        this.heuristicVariable = heuristicVariable;
    }

    @Override
    public Map<Variable,Object> solve(){
        Map<Variable, Set<Object>> domain = new HashMap<>();
        for(Variable variables : variable){
            domain.put(variables,variables.getDomain());
        }
        return mac(new HashMap<>(),variable,domain);
    }

    public Map<Variable,Object> mac(Map<Variable,Object> instantiation,Set<Variable> noInstantiation,
            Map<Variable, Set<Object>> setDomain){
            if(noInstantiation.isEmpty()) return instantiation;
            ArcConsistency arc = new ArcConsistency(super.constraint); 

            if(!arc.ac1(setDomain)) return null;

            Variable meilleurVariable = heuristicVariable.best(noInstantiation,setDomain);
            List<Object> domaineOrdonnee = heuristicValeur.ordering(meilleurVariable,setDomain.get(meilleurVariable));
            for(Object valeur : domaineOrdonnee){
                Map<Variable, Object> etatNouvel = new HashMap<>(instantiation);
                etatNouvel.put(meilleurVariable,valeur);
                Set<Variable> nouvelleVariable = new HashSet<>(noInstantiation);
                nouvelleVariable.remove(meilleurVariable);
                if(isConsistent(etatNouvel)){
                    Map<Variable,Object> sub_mac = new HashMap<>();
                    sub_mac = mac(etatNouvel,nouvelleVariable,setDomain);
                    if(sub_mac!=null){
                        return sub_mac;
                    }
                }
            }
            return null;
    }
 
}