package blocksworld.executable;

import cp.*;
import modelling.Constraint;
import modelling.Variable;

import java.util.*;

import blocksworld.block.*;

public class CSPRegularConstraintMain {
    public static void main(String[] args) {
        // Initialize the block world parameters
        int numberOfBlocks = 6;
        int numberOfPiles = 2;

        // Create variables and regularity constraints
        BlockWorldConstraint bwConstraints = new BlockWorldRegularityConstraint(numberOfBlocks, numberOfPiles);
        Set<Variable> variables = bwConstraints.getBlockWorldVariables().getVariables();
        Set<Constraint> constraints = bwConstraints.getAllConstraints();

        // Execute solvers and measure execution time
        Solver backtrackSolver = new BacktrackSolver(variables, constraints);
        Map<Variable, Object> solutionBT = backtrackSolver.solve();

        Solver macSolver = new MACSolver(variables, constraints);
        Map<Variable, Object> solutionMAC = macSolver.solve();

        // Heuristics for the heuristic MAC solver
        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMacSolver = new HeuristicMACSolver(variables, constraints, varHeuristic, valHeuristic);
        Map<Variable, Object> solutionMACHeuristic = heuristicMacSolver.solve();

        // Execute and display results for each solver
        executeSolver(backtrackSolver, "BacktrackSolver");
        executeSolver(macSolver, "MACSolver");
        executeSolver(heuristicMacSolver, "HeuristicMACSolver");

        // Display solutions using StateView for visualization
        View stateViewBT = new View(bwConstraints.getBlockWorldVariables(), solutionBT, "Backtrack");
        View stateViewMAC = new View(bwConstraints.getBlockWorldVariables(), solutionMAC, "MACSolver");
        View stateViewMACHeuristic = new View(bwConstraints.getBlockWorldVariables(), solutionMACHeuristic, "HeuristicMAC");

        stateViewBT.display(numberOfBlocks, numberOfPiles);
        stateViewMAC.display(numberOfBlocks, numberOfPiles);
        stateViewMACHeuristic.display(numberOfBlocks, numberOfPiles);
    }

    /**
     * Executes a solver and prints the results with execution time.
     * 
     * @param solver      The solver to execute.
     * @param solverName  The name of the solver for display purposes.
     */
    private static void executeSolver(Solver solver, String solverName) {
        System.out.println("=== Executing " + solverName + " for a Regular Configuration ===");

        long startTime = System.currentTimeMillis();
        Map<Variable, Object> solution = solver.solve();
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;

        if (solution != null) {
            System.out.println("Solution found: " + solution);
        } else {
            System.out.println("No solution found.");
        }

        System.out.println("Execution time: " + timeElapsed + " ms");
        System.out.println("=====================");
    }
}
