package planning;

import modelling.*;
import java.util.*;

public class AStarPlanner implements Planner,Heuristic{
    private Map<Variable, Object> initState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristique;
    private boolean activateCount;
    private int count;

    public AStarPlanner(Map<Variable, Object> initState, Set<Action> actions,Goal goal,Heuristic heuristique){
        this.initState = initState;
        this.actions = actions;
        this.goal = goal;
        this.heuristique=heuristique;
        this.activateCount=false;
        this.count = 0;
    }

    @Override
    public List<Action> plan(){
        return astar(this);
    }

    public List<Action> astar(AStarPlanner problem){
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue<>(new MyComparator(value));

        Map<Variable, Object> instanciation = new HashMap<>();
        open.add(initState);
        father.put(initState,null);
        distance.put(initState,0f);
        value.put(initState,heuristique.estimate(initState));
        
        while(!open.isEmpty()){
            instanciation=open.peek();
            if(goal.isSatisfiedBy(instanciation)){
                return get_bfs_plan(father,plan,instanciation);
            }
            else{
                open.remove();
                for(Action action : actions){
                    if(action.isApplicable(instanciation)){
                        Map<Variable, Object> next = action.successor(instanciation);
                        if(!distance.containsKey(next)){
                            distance.put(next,Float.MAX_VALUE);
                        }
                        if(distance.get(next)>(distance.get(instanciation)+action.getCost())){
                            distance.put(next,distance.get(instanciation)+action.getCost());
                            value.put(next,distance.get(next)+heuristique.estimate(next));
                            father.put(next, instanciation);
                            plan.put(next,action);
                            count++;
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;

    }
    /**
     * Estime le cout heuristique du coût restant d'un noeud au noeud objectif
     *@param heuristique: l'état pour lequel estimer le cout heuristique
     *@return une estimation du cout heuristique de l'etat.
     */
    @Override
    public float estimate(Map<Variable,Object> heuristique){
        return 0f;
    }


    public List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan,Map<Variable, Object> goal){
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

    @Override
    public int count(){
        return this.activateCount ? count : 0;
    }
    //On active la sonde
    @Override
    public void activateNodeCount(boolean activate){
        this.activateCount = activate;
    }

    //On desactive la sonde
    public void desactiveCount(){
        if (activateCount) count=0;
    }



    @Override
    public Map<Variable, Object> getInitialState(){
        return initState;
    }
    @Override
    public Set<Action> getActions(){
        return actions;
    }
    @Override
    public Goal getGoal(){
        return goal;
    }
}