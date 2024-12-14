package blocksworld.executable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import blocksworld.block.*;
import modelling.Constraint;
import modelling.Variable;


/**
 * Main class to test the constraints in the Block World.
 * This class sets up various types of constraints for the Block World
 * and tests them with different initial and target states.
 */
public class VariablesConstraintsMain {

    /**
     * Main method to run the constraint tests.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Number of blocks and stacks in the world
        int numberOfBlocks = 6;
        int numberOfStacks = 4;

        // Create instances of BlockWorldConstraint and other constraint types
        BlockWorldConstraint blockWorldConstraint = new BlockWorldConstraint(numberOfBlocks, numberOfStacks);
        BlockWorldCombinedConstraint blockWorldCombinedConstraint = new BlockWorldCombinedConstraint(numberOfBlocks, numberOfStacks);
        BlockWorldRegularityConstraint blockWorldRegularityConstraint = new BlockWorldRegularityConstraint(numberOfBlocks, numberOfStacks);
        BlockWorldGrowingConstraint blockWorldGrowingConstraint = new BlockWorldGrowingConstraint(numberOfBlocks, numberOfStacks);

        // Retrieve the variables for the block world
        BlockWorldVariable blockWorldVariables = blockWorldConstraint.getBlockWorldVariables();
        
        // Maps to hold the states of the block world
        Map<Variable, Object> initialState = new HashMap<>();
        Map<Variable, Object> targetState = new HashMap<>();
        
        // Initial and target configurations of the block piles
        int[][] initialPiles = {
            {1, 4, 5},
            {2, 3},
            {},
            {0}
        };

        int[][] targetPiles = {
            {0, 1, 2, 3},
            {4, 5},
            {},
            {}
        };

        // Generate the states based on the pile configurations
        initialState = blockWorldVariables.generateStateFromPiles(initialPiles);
        targetState = blockWorldVariables.generateStateFromPiles(targetPiles);

        // Test the different constraints with the initial and target states
        testConstraints(blockWorldRegularityConstraint, blockWorldRegularityConstraint.getRegularityConstraints(), initialState, targetState);
        testConstraints(blockWorldGrowingConstraint, blockWorldGrowingConstraint.getGrowingConstraints(), initialState, targetState);
        testConstraints(blockWorldCombinedConstraint, blockWorldCombinedConstraint.getCombinedConstraints(), initialState, targetState);
    }

    /**
     * Tests the given constraints by checking if they are satisfied by both the
     * initial and target states of the Block World.
     * 
     * @param constraintInstance The instance of the BlockWorldConstraint to test.
     * @param constraints The set of constraints to be tested.
     * @param initialState The initial state of the block world.
     * @param targetState The target state of the block world.
     */
    public static void testConstraints(BlockWorldConstraint constraintInstance, Set<Constraint> constraints, Map<Variable, Object> initialState, Map<Variable, Object> targetState) {
        System.out.println("***************************  Test " + constraintInstance.getClass().getSimpleName() + " World ************************");
        //System.out.println("Initial State: " + initialState);
        System.out.println();
        //System.out.println("Target State: " + targetState);

        // Check if all constraints are satisfied by both states
        boolean allConstraintsSatisfied = true;
        for (Constraint constraint : constraints) {
            if (!constraint.isSatisfiedBy(initialState) || !constraint.isSatisfiedBy(targetState)) {
                allConstraintsSatisfied = false;
            }
        }

        // Affiche si toutes les contraintes sont satisfaites
    if (allConstraintsSatisfied) {
      System.out.println("All constraints are satisfied.");
  } else {
      System.out.println("Some constraints are not satisfied.");
  }
    }
}
