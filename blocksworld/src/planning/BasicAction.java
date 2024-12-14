package planning;
import java.util.*;
import modelling.Variable;

public class BasicAction implements Action{
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effect;
    private int cost;
    /**
     * Constructeur de la classe Basic Action
     * @param precondition: précondition des actions
     * @param effect: les effets de l'action
     * @param cost: le cout de l'action
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost){
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    /**
     * Vérifie si une telle action est applicable dans un état ou pas
     * @param etat l'état actuel
     * @return true si l'action est applicable et faux sinon
     */
    @Override
    public boolean isApplicable(Map<Variable, Object> state){
        for(Map.Entry<Variable, Object> entry : precondition.entrySet()){
            Variable variable = entry.getKey();
            Object variableValor = entry.getValue();
            if(!Objects.equals(state.get(variable) , (variableValor))){
                return false;
            }
        }
        return true;
    }

    /**
     * renvoie l'état suivant contenant les effets
     * @param etat l'état actuel
     */
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state){
        Map<Variable, Object> result = new HashMap<Variable, Object>(state);

        if(isApplicable(state)){
            for(Map.Entry<Variable, Object> effects : effect.entrySet()){
                result.put(effects.getKey(), effects.getValue());
            }
            for(Map.Entry<Variable, Object> s : state.entrySet()){
                if(!result.containsKey(s.getKey())){
                 result.put(s.getKey(), s.getValue());
                }
               
            }
            return result;
        }
        return null;
    }
    /**
     * renvoie de l'action
     */
    @Override 
    public int getCost(){
        return cost;
    }

public String toString(){
    return "\nprecondition : " + precondition + ", effet : " + effect + "cout : " + cost + "\n";
    }
}