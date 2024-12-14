package blocksworld.block;

import java.util.HashSet;
import java.util.Set;
import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

/**
 * Class representing the regularity constraints in the Block World problem.
 * This class extends BlockWorldConstraint and generates implications between the variables of the Block World.
 */
public class BlockWorldRegularityConstraint extends BlockWorldConstraint {

    /**
     * Set of regularity constraints in the form of implications.
     */
    public Set<Constraint> regularityConstraints;

    /**
     * Constructor for the BlockWorldRegularityConstraint class.
     * Initializes the variables and generates the regularity constraints.
     *
     * @param numBlocks Number of blocks in the Block World.
     * @param numPiles  Number of piles in the Block World.
     */
    public BlockWorldRegularityConstraint(int numBlocks, int numPiles) {
        super(numBlocks, numPiles);
        this.regularityConstraints = new HashSet<>();
        this.createRegularityImplications();
    }

    /**
     * Returns the set of generated regularity constraints.
     *
     * @return A set of regularity constraints.
     */
    public Set<Constraint> getRegularityConstraints() {
        return this.regularityConstraints;
    }

    /**
     * Creates regularity constraints in the form of implications between the variables of the Block World.
     * For each pair of variables, an implication is created and added to the set of constraints.
     */
    private void createRegularityImplications() {
        // Assuming blockWorldVariables is an object containing the variables for the block positions and piles
        
        // Loop over the "on_i" and "on_j" variables in the Block World variables set.
        for (Variable blockPosition_i : blockWorldVariables.getOnBlockVariables()) {
            for (Variable blockPosition_j : blockWorldVariables.getOnBlockVariables()) {
                // Calculate the indices of the variables
                int index_i = getVariableIndex(blockPosition_i);
                int index_j = getVariableIndex(blockPosition_j);
                int index_k = index_j - (index_i - index_j);

                // Do not create a constraint for a variable on itself
                if (index_i != index_j) {
                    Set<Object> subDomainFor_i = new HashSet<>();
                    subDomainFor_i.add(index_j);

                    Set<Object> subDomainFor_j = new HashSet<>(blockWorldVariables.getPiles());
                    if (index_k >= 0 && index_k < numberOfBlocks) {
                        subDomainFor_j.add(index_k);
                    }

                    // Create the implication constraint
                    Constraint regularityConstraint = new Implication(blockPosition_i, subDomainFor_i, blockPosition_j, subDomainFor_j);
                    allConstraints.add(regularityConstraint); // Assuming allConstraints is accessible
                    regularityConstraints.add(regularityConstraint);
                }
            }
        }
    }

    /**
     * Displays all the regularity constraints in the form of implications.
     * This allows visualizing the constraints generated by the `createRegularityImplications` method.
     */
    public void displayRegularityConstraints() {
        System.out.println("---- Regularity Constraints ----");
        for (Constraint constraint : this.regularityConstraints) {
            // Cast the constraint to an Implication for display
            Implication regularityConstraint = (Implication) constraint;
            System.out.printf("Variable: %s, Sub-domain: %s => Variable: %s, Sub-domain: %s\n",
                    regularityConstraint.getV1().getName(),
                    regularityConstraint.getS1(),
                    regularityConstraint.getV2().getName(),
                    regularityConstraint.getS2());
        }
    }
}