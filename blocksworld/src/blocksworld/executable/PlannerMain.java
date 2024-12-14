package blocksworld.executable;

import planning.*;
import modelling.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.block.*;

/**
 * Main class to demonstrate the use of planning algorithms in a Block World problem.
 */
public class PlannerMain {
    public static void main(String[] args) {
        // Initialize the BlockWorldActions with 5 blocks and 3 piles
        BlockWorldActions blockWorldActions = new BlockWorldActions(5, 3);
        BlockWorldVariable blockWorldVariable = blockWorldActions.getBlockWorldVariables();

        // Example initial state represented as an array of piles
        int[][] initialState = {
            { 1,3 ,4  },
            {},
            { 0,2  }
        };

        // Example goal state
        int[][] goalState = {
            { 3,1 ,4  },
            { 2,0  },
            {}
        };

        // Convert piles to variable-based states
        Map<Variable, Object> initState = blockWorldVariable.generateStateFromPiles(initialState);
        Map<Variable, Object> goal = blockWorldVariable.generateStateFromPiles(goalState);

        // Create the goal and retrieve all possible actions
        Goal goals = new BasicGoal(goal);
        Set<Action> actions = blockWorldActions.getAllActions();

        // Test BFS planner and display the resulting plan
        BFSPlanner bfs = new BFSPlanner(initState, actions, goals);
        List<Action> plan = bfs.plan();

        // Execute all available planners
        executePlanner(new DijkstraPlanner(initState, actions, goals));
        executePlanner(new DFSPlanner(initState, actions, goals));
        executePlanner(new BFSPlanner(initState, actions, goals));

        // Initialize heuristics and test A* planner
        BlockWorldHeuristique2 heuristic1 = new BlockWorldHeuristique2(goal);
        BlockWorldHeuristique1 heuristic2 = new BlockWorldHeuristique1(goals);

        // Display heuristic estimates
        System.out.println("Estimate of how far the current state is from the goal state: " + heuristic1.estimate(initState));
        System.out.println("Number of blocks needing to move: " + heuristic2.estimate(initState));

        // Execute A* planner
        executePlanner(new AStarPlanner(initState, actions, goals, heuristic1));

        // Visualize the plan
        View view = new View(blockWorldVariable, initState);
        view.displayPlan(plan);
    }

    /**
     * Executes a given planner, displays the results, and tracks performance metrics.
     *
     * @param planner the planning algorithm to execute.
     */
    private static void executePlanner(Planner planner) {
        // Enable node count tracking for the planner
        planner.activateNodeCount(true);

        // Display the planner name
        System.out.println("Executing planner: " + planner.getClass().getSimpleName());

        // Measure execution time
        long startTime = System.nanoTime();
        List<Action> plan = planner.plan();
        long endTime = System.nanoTime();

        System.out.println("Computation time: " + (endTime - startTime) / 1_000_000 + " ms");

        // Display results
        if (plan != null) {
            System.out.println("Plan found:");
            for (Action action : plan) {
                System.out.println(action);
            }
        } else {
            System.out.println("No plan found.");
        }
        System.out.println("------------------------");
    }
}
