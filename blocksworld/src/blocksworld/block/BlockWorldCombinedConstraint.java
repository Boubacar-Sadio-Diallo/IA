package blocksworld.block;

import java.util.HashSet;
import java.util.Set;
import modelling.Constraint;

/**
 * This class combines the growing and regularity constraints in the Block World problem.
 * It extends the BlockWorldConstraint class and adds both types of constraints to a combined set.
 */
public class BlockWorldCombinedConstraint extends BlockWorldConstraint {

    /**
     * Growing constraints for the Block World problem.
     */
    private BlockWorldGrowingConstraint growingConstraint;

    /**
     * Regularity constraints for the Block World problem.
     */
    private BlockWorldRegularityConstraint regularityConstraint;

    /**
     * Set of combined constraints that includes both growing and regularity constraints.
     */
    private Set<Constraint> combinedConstraints;

    /**
     * Constructor for the BlockWorldCombinedConstraint class.
     * Initializes the growing and regularity constraints and combines them.
     *
     * @param numBlocks Number of blocks in the Block World.
     * @param numPiles  Number of piles in the Block World.
     */
    public BlockWorldCombinedConstraint(int numBlocks, int numPiles) {
        super(numBlocks, numPiles);
        this.growingConstraint = new BlockWorldGrowingConstraint(numBlocks, numPiles);
        this.regularityConstraint = new BlockWorldRegularityConstraint(numBlocks, numPiles);
        this.combinedConstraints = new HashSet<>();
        this.createCombinedConstraints();
    }

    /**
     * Combines the growing and regularity constraints into a single set of constraints.
     * Adds all constraints from both the growing and regularity constraints sets.
     */
    private void createCombinedConstraints() {
        // Add all growing constraints
        combinedConstraints.addAll(growingConstraint.getGrowingConstraints());
        // Add all regularity constraints
        combinedConstraints.addAll(regularityConstraint.getRegularityConstraints());
        // Add all combined constraints to the main list of constraints
        allConstraints.addAll(combinedConstraints);
    }

    /**
     * Returns the set of combined constraints, which includes both growing and regularity constraints.
     *
     * @return A set of combined constraints.
     */
    public Set<Constraint> getCombinedConstraints() {
        return this.combinedConstraints;
    }

    /**
     * Displays all the combined constraints, including both growing and regularity constraints.
     * This method will print out the constraints added in the `createCombinedConstraints` method.
     */
    public void displayCombinedConstraints() {
        System.out.println("---- Growing and Regularity Constraints ----");
        for (Constraint constraint : this.combinedConstraints) {
            System.out.println(constraint);
        }
    }
}
