package blocksworld.block;

import java.util.Map;
import modelling.Variable;
import planning.Heuristic;
import planning.Goal;

/**
 * This heuristic estimates how far the current state is from the goal state.
 * It returns a value indicating whether the goal is satisfied. If the goal is not
 * satisfied, it increments the count by 1.
 */
public class BlockWorldHeuristique1 implements Heuristic {

    private Goal goal; // The goal to be achieved

    /**
     * Constructor for BlockWorldHeuristique2.
     *
     * @param goal The target goal to be reached (Goal object).
     */
    public BlockWorldHeuristique1(Goal goal) {
        this.goal = goal;
    }

    /**
     * Estimates how far the current state is from the goal state.
     *
     * @param currentState The current configuration of blocks (map of variables and their current values).
     * @return A float representing the heuristic estimate. It returns 0 if the goal is satisfied, or 1 if not.
     */
    @Override
    public float estimate(Map<Variable, Object> currentState) {
        // Variable to hold the heuristic value
        float n = 0;

        // Check if the goal is satisfied by the current state
        if (!goal.isSatisfiedBy(currentState)) {
            n += 1; // Goal is not satisfied, increment heuristic value by 1
        }

        // Return the heuristic value (1 if the goal is not satisfied, 0 otherwise)
        return n;
    }
}
