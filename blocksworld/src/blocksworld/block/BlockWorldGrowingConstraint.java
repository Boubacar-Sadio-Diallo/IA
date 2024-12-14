package blocksworld.block;

import java.util.HashSet;
import java.util.Set;
import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

public class BlockWorldGrowingConstraint extends BlockWorldConstraint {

    // Stores all growing constraints
    private final Set<Constraint> growingConstraints;

    /**
     * Constructor: Initializes the block world with growing constraints.
     *
     * @param numberOfBlocks   Total number of blocks.
     * @param numberOfStacks   Total number of stacks.
     */
    public BlockWorldGrowingConstraint(int numberOfBlocks, int numberOfStacks) {
        super(numberOfBlocks, numberOfStacks);
        this.growingConstraints = new HashSet<>();
        this.createGrowingConstraints();
    }

    /**
     * Creates growing constraints for block arrangement.
     * Ensures that a block (variable1) can only be placed above another block (variable2)
     * based on specific index conditions.
     */
    private void createGrowingConstraints() {
        Set<Variable> onBlockVariables = this.blockWorldVariables.getOnBlockVariables();

        for (Variable variable1 : onBlockVariables) {
            for (Variable variable2 : onBlockVariables) {
                int index1 = this.getVariableIndex(variable1);
                int index2 = this.getVariableIndex(variable2);

                // Skip if variable1's index is not less than variable2's index
                if (index1 >= index2) continue;

                // Define subdomains for the implication constraint
                HashSet<Object> allowedDomainForVariable1 = new HashSet<>();
                HashSet<Object> allowedDomainForVariable2 = new HashSet<>();
                allowedDomainForVariable1.add(index2);
                allowedDomainForVariable2.add(true);

                // Create and store the implication constraint
                Implication growingConstraint = new Implication(
                    variable1,
                    allowedDomainForVariable1,
                    variable2,
                    allowedDomainForVariable2
                );

                this.allConstraints.add(growingConstraint);
                this.growingConstraints.add(growingConstraint);
            }
        }
    }

    /**
     * Returns the set of growing constraints.
     *
     * @return A set of growing constraints.
     */
    public Set<Constraint> getGrowingConstraints() {
        return this.growingConstraints;
    }

    /**
     * Displays all growing constraints in a readable format.
     */
    public void displayGrowingConstraints() {
        System.out.println("---- Growing Constraints ----");
        for (Constraint constraint : this.growingConstraints) {
            Implication growingConstraint = (Implication) constraint;
            System.out.printf(
                "Variable: %s, Subdomain: %s => Variable: %s, Subdomain: %s%n",
                growingConstraint.getV1().getName(),
                growingConstraint.getS1(),
                growingConstraint.getV2().getName(),
                growingConstraint.getS2()
            );
        }
    }
}
