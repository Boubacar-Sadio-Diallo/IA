package planning;
import modelling.*;
import java.util.*;

public class BasicGoal implements  Goal{
    private Map<Variable, Object> goal;
    
    public BasicGoal(Map<Variable, Object> goal){
        this.goal = goal;
    }

     /**
     * Verifie si un état permet d'atteindre un but
     * @param state: l'état actuel
     * @return : renvoie vrai si le but est satisfait par l'etat et faux sinon
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state){
        
        if(state == null) return false;
        for(Map.Entry<Variable, Object> entry : goal.entrySet()){
            Variable variable = entry.getKey();
            Object variableValor = entry.getValue();
            if(!Objects.equals(state.get(variable),variableValor )|| !state.containsKey(variable)){
                return false;
            }
        }
        return true;
    }
    
    public Map<Variable, Object> getGoal(){
        return this.goal;
    }

    public String toString(){
    return "Goal : " + goal + "\n";
    }
}

