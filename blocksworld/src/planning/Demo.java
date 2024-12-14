package planning;

import java.util.*;
import modelling.*;


public class Demo{
    public static void main(String[] args){
        Set<Object> domain = new HashSet<>(Arrays.asList(1,2,3,4));
        
        // Création de variables avec le nouveau domaine
        Variable x = new Variable("x", domain);
        Variable y = new Variable("y", domain);
        Variable z = new Variable("z", domain);
        Variable t = new Variable("t", domain);
        
       // Listes des actions
        Map<Variable,Object> precondition = new HashMap<>();
        precondition.put(x,1);
        precondition.put(t,4);
        precondition.put(y,1);
        precondition.put(z,2);
        Map<Variable,Object> effect = new HashMap<>();
        effect.put(x,3);
        effect.put(x,3);
        effect.put(y,3);
        effect.put(t,4);

        Set<Action> actions = new HashSet<>(Arrays.asList(
            new BasicAction(precondition, effect, 90)
        ));

         //l'état initial
        Map<Variable,Object> initialState = new HashMap<>();
        initialState.put(x,1);
        initialState.put(y,3);
        initialState.put(z,2);
        initialState.put(t,4);

        
        //le but 
        HashMap<Variable,Object> next = new HashMap<>();
        next.put(x,3);
        next.put(y,3);
        next.put(z,2);
        next.put(t,4);
        BasicGoal goals = new BasicGoal(next);
        

       

        //Instanciation de mon BFSPlanner
        System.out.println("BFSPlanner");
        BFSPlanner bfs = new BFSPlanner(initialState,actions,goals);
        System.out.print("Plan trouvé: " );
        System.out.println(bfs.plan()!=null ?  bfs.plan():"Aucun plan trouvé");
        bfs.activateNodeCount(true);//Active le compteur de noeud explorés
        System.out.println("Le nombre de noeuds explorés (bfs):"+bfs.count());

        System.out.println("DFSPlanner");
        DFSPlanner dfs = new DFSPlanner(initialState,actions,goals);
        System.out.print("Plan trouvé: " );
        System.out.println(dfs.plan()!=null ? dfs.plan():"Aucun plan trouvé");
        dfs.activateNodeCount(true);//Active le compteur de noeud explorés
        System.out.println("Le nombre de noeuds explorés (dfs):"+dfs.count());

        System.out.println("DIJSKTRAPlanner");
        DijkstraPlanner dijktra = new DijkstraPlanner(initialState,actions,goals);
        System.out.print("Plan trouvé: " );
        System.out.println(dijktra.plan()!=null ? dijktra.plan():"Aucun plan trouvé");
        dijktra.activateNodeCount(true);//Active le compteur de noeud explorés
        System.out.println("Le nombre de noeuds explorés (dijkstra):"+dijktra.count());
        
        
    }
}
