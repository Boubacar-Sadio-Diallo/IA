package planning;
import modelling.*;
import java.util.*;
/**
 * La Classe DijkstraPlanner implemente Planner pour résoudre des problèmes de planification en utilisant
 * l'algo de Dijkstra qui essaye de trouver le chemin le plus court et le coût minimum pour atteindre un but 
 */

public class DijkstraPlanner implements Planner{
    private Map<Variable, Object> initState;
    private Set<Action> actions;
    private Goal goal;
    private boolean activateCount;
    private int count;

    public DijkstraPlanner(Map<Variable, Object> initState,Set<Action> actions, Goal goal){
        this.initState = initState;
        this.actions = actions;
        this.goal = goal;
        this.activateCount = false;
        this.count = 0;
    }
    /**
     * renvoie un plan
     * @return une liste d'actions ou null s'il n'y en pas
     */
    @Override
    public List<Action> plan(){
        return dijsktra();
    }
    
    public List<Action> dijsktra(){
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue<>(new MyComparator(distance));
        Map<Variable, Object> next =  new HashMap<>();
        Map<Variable, Object> instanciation =  new HashMap<>();
        father.put(this.initState,null);
        distance.put(this.initState,0f);
        open.add(this.initState);
     
        while(!open.isEmpty()){
            
            instanciation=open.peek();
            open.remove();
            if(goal.isSatisfiedBy(instanciation)){
                goals.add(instanciation);
            }
            for(Action action : this.actions){
                
                if(action.isApplicable(instanciation)){
                  next  = action.successor(instanciation);
                    if(!(distance.containsKey(next))){
                        distance.put(next,Float.MAX_VALUE);
                    }
                    Float cost = (distance.get(instanciation)+ action.getCost()); 

                    if( distance.get(next) > cost ){
                        distance.put(next,cost);
                        father.put(next, instanciation);
                        plan.put(next,action);
                        open.add(next);
                        count++;
                    }
                }
            }
        }
        if(goals.isEmpty())
            return null;
        
        return get_dijkstra_plan(father,plan,goals,distance);
    }
    /**
     * Renvoie les actions à entreprendre pour atteindre l'état objectif
     * @param father: répresente les couples <pères fils>
     * @param plan: actions actuelles
     * @param goals: ensemble des états objectifs atteints
     * @param distance: la distance entre les états
     * @return le plan résultant
     */
    @SuppressWarnings("unchecked")
    public List<Action> get_dijkstra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
                            Map<Map<Variable, Object>, Action> plan,
                            Set<Map<Variable,Object>> goals,
                            Map<Map<Variable, Object>, Float> distance){
        Queue<Action> dij_plan = new LinkedList<>();
        //Trier ordre croissant les états explorés à partir de leur distance
        PriorityQueue<Map<Variable,Object>> goal = new PriorityQueue<>(new MyComparator(distance));
        Map<Variable, Object> goal_state;
        for(Map<Variable,Object> elem : goals){
            goal.add(elem);
        }
        goal_state = goal.poll();
        while(father.get(goal_state)!=null){//tant que l'etat précedent n'est pas null
            dij_plan.add(plan.get(goal_state));
            goal_state=father.get(goal_state);
        }
        Collections.reverse((List<Action>) dij_plan);
        return ((List<Action>)dij_plan);
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
        return this.actions;
    }

    /**
     * Renvoie le but à atteindre
     * @return un objet Goal
     */
    @Override
    public Goal getGoal(){
        return this.goal;
    }

     public String toString(){
    return "\nGoal : " + goal + ", initial : " + initState + "\n";
    }

}