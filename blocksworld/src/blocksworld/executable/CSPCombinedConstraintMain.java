package blocksworld.executable;

import cp.*;

import modelling.Constraint;
import modelling.Variable;

import java.util.*;

import blocksworld.block.*;

public class CSPCombinedConstraintMain {
    public static void main(String[] args) {
        // Initialize block world parameters
        int numberOfBlocks = 8;
        int numberOfStacks = 4;

        // Create variables and constraints for the combined configuration
        BlockWorldCombinedConstraint bwConstraints = new BlockWorldCombinedConstraint(numberOfBlocks, numberOfStacks);
        Set<Variable> variables = bwConstraints.getBlockWorldVariables().getVariables();
        Set<Constraint> constraints = bwConstraints.getAllConstraints();

        // Solve using different algorithms and measure execution time
        Solver backtrackSolver = new BacktrackSolver(variables, constraints);
        Map<Variable, Object> solutionBT = backtrackSolver.solve();

        Solver macSolver = new MACSolver(variables, constraints);
        Map<Variable, Object> solutionMAC = macSolver.solve();

        // Define heuristics for the heuristic-based MAC solver
        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(true);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMacSolver = new HeuristicMACSolver(variables, constraints, variableHeuristic, valueHeuristic);
        Map<Variable, Object> solutionMACHeuristic = heuristicMacSolver.solve();

        // Execute solvers and display results
        executeSolver(backtrackSolver, "BacktrackSolver");
        executeSolver(macSolver, "MACSolver");
        executeSolver(heuristicMacSolver, "HeuristicMACSolver");

        // Display solutions visually
        View stateViewBT = new View(bwConstraints.getBlockWorldVariables(), solutionBT, "BacktrackSolver");
        View stateViewMAC = new View(bwConstraints.getBlockWorldVariables(), solutionMAC, "MACSolver");
        View stateViewMACHeuristic = new View(bwConstraints.getBlockWorldVariables(), solutionMACHeuristic, "HeuristicMACSolver");

        stateViewBT.display(0, 0);
        stateViewMAC.display(500, 0);
        stateViewMACHeuristic.display(1707, 0);
    }

    /**
     * Executes a given solver and measures its performance.
     *
     * @param solver     The solver instance to execute.
     * @param solverName The name of the solver (for logging purposes).
     */
    private static void executeSolver(Solver solver, String solverName) {
        System.out.println("=== Executing " + solverName + " for Combined Configuration ===");

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
