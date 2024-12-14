package blocksworld.block;

import java.util.Map;
import java.util.Objects;

import modelling.Variable;
import planning.Heuristic;
import planning.BasicGoal;

/**
 * This heuristic estimates the number of moves needed by counting
 * how many blocks need to be moved to reach a final configuration
 * where each block is placed on the correct pile or position.
 */
public class BlockWorldHeuristique2 extends BasicGoal implements Heuristic {

    /**
     * Constructor for BlockWorldHeuristic1. Initializes the goal map.
     *
     * @param goal The target configuration to be achieved (map of variables and their goal values).
     */
    public BlockWorldHeuristique2(Map<Variable, Object> goal) {
        super(goal); // Calls the constructor of BasicGoal to initialize the goal.
    }

    /**
     * Estimates the number of moves required to reach the goal state from the current state.
     *
     * @param currentState The current configuration of the blocks (map of variables and their current values).
     * @return A float representing the number of moves needed.
     */
    @Override
    public float estimate(Map<Variable, Object> currentState) {
        int movesNeeded = 0;

        // Iterate over each goal variable to compare its current state value with the goal value
        for (Variable onb : getGoal().keySet()) {
            Object goalValue = getGoal().get(onb); // The target value for the variable
            Object currentValue = currentState.get(onb); // The current value for the variable

            // Only process if the values are integers (as assumed for block positions)
            if (goalValue instanceof Integer && currentValue instanceof Integer) {
                int currentIntValue = (Integer) currentValue;

                // Check if the current position differs from the goal position
                if (!Objects.equals(currentState.get(onb), getGoal().get(onb))) {
                    // Increment the move counter if the block is not in its correct position
                    movesNeeded += (currentIntValue < 0) ? 1 : 2; // Adds 1 for each incorrect position
                }
            }
        }

        // Return the estimated number of moves as a float
        return movesNeeded;
    }
}
