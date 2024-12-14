package planning;

import java.util.*;
import modelling.*;
/**
 * La Classe DFSPlanner implemente Planner pour résoudre des problèmes de planification en utilisant
 * l'algo de recherche en profondeur(DFS) 
 */
public class DFSPlanner implements Planner{
    private Map<Variable, Object> initState;
    private Set<Action> action;
    private Goal goal;
    private int count;
    private boolean activateCount;

    public DFSPlanner(Map<Variable, Object> initState,Set<Action> actions, Goal goal){
        this.initState = initState;
        this.action = actions;
        this.goal = goal;
        this.count=0;
        this.activateCount=false;
    }
    /**
     * renvoie un plan
     * @return une liste d'actions ou null s'il n'y en pas
     */
    @Override
    public List<Action> plan(){
        return dfs(this.initState,new Stack<Action>(),new HashSet<Map<Variable, Object>>() );
    }

    /**
     * Renvoie un plan en implémantant l'algo de DSF
     * @param instanciation: l'etat actuel
     * @param plan: actions actuelles
     * @param closed: états déja explorés
     * @return un plan ou nul s'il y'a pas
     */
    public List<Action> dfs(Map<Variable, Object> instanciation,Stack<Action> plan, Set<Map<Variable, Object>> closed){
        if (goal.isSatisfiedBy(instanciation)){ 
            return plan;
        }
        else{
            for(Action actions : this.action){
                if(actions.isApplicable(instanciation)){
                   Map<Variable, Object> next = actions.successor(instanciation);
                    if(!closed.contains(next)){
                        plan.push(actions);
                        closed.add(next);
                        //On incremente le compteur de noeuds(noeuds dejà explorés)
                        count++;
                        List<Action> subPlan = dfs(next,plan,closed);
                        if(subPlan != null){
                            return subPlan;
                        }
                        plan.pop();
                        
                    }
                }
            }
        }
        return null;
    }
     /**
     * Permet d'obtenir le nbre de noeud explorés lors de la planification si activeNodeCount==true
     * @return le nombre de neouds explorés
     */
    @Override
    public int count(){
        return this.activateCount ? count : 0;
    }
     /**
     * Permet  d'activer le comptage des noeuds explorés
     * @param active: active(true) ou désactive(false) le comptage
     */
    @Override
    public void activateNodeCount(boolean activate){
        this.activateCount = activate;
    }

    /**
     * Renvoie l'état initial
     * @return un état
     */
    @Override
    public Map<Variable, Object> getInitialState(){
        return this.initState;
    }

     /**
     * Renvoie la liste des actions appliquées sur une instanciation d'un état
     * @return renvoie un ensemble d'actions
     */
    @Override
    public Set<Action> getActions(){
        return this.action;
    }

    /**
     * Renvoie le but à atteindre
     * @return un objet Goal
     */
    @Override
    public Goal getGoal(){
        return this.goal;
    }
}