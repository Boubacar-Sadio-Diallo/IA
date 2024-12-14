package planning;

import java.util.*;
import modelling.*;
/**
 * La Classe BFSPlanner implemente Planner pour résoudre des problèmes de planification en utilisant
 * l'algo de recherche en largeur(DFS) en empruntant le chemin le plus court pour atteindre un objectif 
 */
public class BFSPlanner implements Planner{
    private Map<Variable, Object> initState;
    private Set<Action> action;
    private Goal goal;
    private boolean activateCount;
    private int count;

    public BFSPlanner(Map<Variable, Object> initState,Set<Action> actions, Goal goal){
        this.initState = initState;
        this.action = actions;
        this.goal = goal;
        this.activateCount=false;
        this.count=0;
    }
    /**
     * renvoie un plan
     * @return une liste d'actions ou null s'il n'y en pas
     */
    @Override
    public List<Action> plan(){
        return bfs();
    }
    /**
     * Renvoie un plan en implémentant l'algo de BSF
     * @return le plan résultant ou null s'i n'y a pas 
     */
    public List<Action> bfs(){
        Set<Map<Variable, Object>> closed = new HashSet<>();//liste des états déjà explorés
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initState);
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        father.put(this.initState, null);
        Map<Variable, Object> instanciation = new HashMap<>();
        List<Action> empty = new ArrayList<>();
        if(goal.isSatisfiedBy(this.initState)){
            return empty;
        }
        while(!open.isEmpty()){
            instanciation = open.poll();
            closed.add(instanciation);
            for(Action actions : action){
                if(actions.isApplicable(instanciation)){
                    Map<Variable, Object> next = actions.successor(instanciation);
                    if(!closed.contains(next) && !open.contains(next)){
                        father.put(next, instanciation);
                        plan.put(next,actions);
                        count++;
                        if(goal.isSatisfiedBy(next)){
                            return get_bfs_plan(father,plan,next);//rebuilt the plan
                        }
                        else{
                            open.add(next);
                        }
                    }
                }
            }

        }
        return null;
    }    
    /**
     * Renvoie les actions à entreprendre pour atteindre l'état objectif
     * @param father: répresente les couples pères fils
     * @param plan: actions actuelles
     * @param goal: le but à atteindre
     * @return le plan résultant
     */
  static public List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
        Map<Map<Variable, Object>, Action> plan,
        Map<Variable,Object> goal){
        List<Action> bfs_plan = new ArrayList<>();
        while (goal != null && !goal.isEmpty()) {
            Action action = plan.get(goal);
            if (action == null) {
                break;
            }
            // Add the action to the plan
            bfs_plan.add(plan.get(goal));

            // Move to the parent state
            goal = father.get(goal);
        }
        // Reverse the order of the plan
        Collections.reverse(bfs_plan);
        return bfs_plan;                               
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