package blocksworld.block;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

public class BlockWorldConstraint extends BlockWorld {

    /** Set of all constraints in the block world. */
    public Set<Constraint> allConstraints = new HashSet<Constraint>();

    /** Set of difference constraints between variables. */
    public Set<Constraint> variableDifferenceConstraints = new HashSet<Constraint>();

    /** Set of implication constraints between variables. */
    public Set<Constraint> variableImplicationConstraints = new HashSet<Constraint>();

    /**
     * Constructor to initialize a block world with constraints.
     * 
     * @param blockCount The number of blocks in the world.
     * @param stackCount The number of stacks in the world.
     */
    public BlockWorldConstraint(int blockCount, int stackCount) {
        super(blockCount, stackCount);
        this.setupVariableDifferenceConstraints();
        this.setupVariableImplicationConstraints();
    }

    /**
     * Sets up the difference constraints between the "on" variables of the block world.
     */
    protected void setupVariableDifferenceConstraints() {
        Set<Variable> onBlockVariables = this.blockWorldVariables.getOnBlockVariables();
        for (Variable var1 : onBlockVariables) {
            for (Variable var2 : onBlockVariables) {
                if (Objects.equals(var1, var2)) continue;
                DifferenceConstraint diffConstraint = new DifferenceConstraint(var1, var2);
                this.allConstraints.add(diffConstraint);
                this.variableDifferenceConstraints.add(diffConstraint);
            }
        }
    }

    /**
     * Sets up the implication constraints between "on" and "fixed"/"free" variables.
     */
    public void setupVariableImplicationConstraints() {
        for (Variable onBlockVar : this.blockWorldVariables.getOnBlockVariables()) {
            int onBlockVarIndex = this.getVariableIndex(onBlockVar);
            for (Variable fixedVar : this.blockWorldVariables.getFixedBlockVariables()) {
                int fixedVarIndex = this.getVariableIndex(fixedVar);
                if (onBlockVarIndex == fixedVarIndex) continue;

                HashSet<Object> fixedVarIndexSet = new HashSet<Object>();
                HashSet<Object> trueSet = new HashSet<Object>();
                fixedVarIndexSet.add(fixedVarIndex);
                trueSet.add(true);
                
                Implication implication = new Implication(onBlockVar, fixedVarIndexSet, fixedVar, trueSet);
                this.allConstraints.add(implication);
                this.variableImplicationConstraints.add(implication);
            }
            for (Variable freeVar : this.blockWorldVariables.getFreePileVariables()) {
                int freeVarIndex = this.getVariableIndex(freeVar);
                
                HashSet<Object> freeVarIndexSet = new HashSet<>();
                HashSet<Object> falseSet = new HashSet<>();
                freeVarIndexSet.add(freeVarIndex);
                falseSet.add(false);
                
                Implication implication = new Implication(onBlockVar, freeVarIndexSet, freeVar, falseSet);
                this.allConstraints.add(implication);
                this.variableImplicationConstraints.add(implication);
            }
        }
    }

    /**
     * Returns the set of implication constraints.
     * 
     * @return A set of implication constraints.
     */
    public Set<Constraint> getVariableImplicationConstraints() {
        return this.variableImplicationConstraints;
    }

    /**
     * Returns the set of difference constraints.
     * 
     * @return A set of difference constraints.
     */
    public Set<Constraint> getVariableDifferenceConstraints() {
        return this.variableDifferenceConstraints;
    }

    /**
     * Displays all the difference constraints.
     */
    public void displayVariableDifferenceConstraints() {
        System.out.println("---- Variable Difference Constraints ----");
        for (Constraint constraint : this.variableDifferenceConstraints) {
            System.out.println(constraint);
        }
    }

    /**
     * Returns the set of all constraints in the block world.
     * 
     * @return A set of all constraints.
     */
    public Set<Constraint> getAllConstraints() {
        return this.allConstraints;
    }

    /**
     * Displays all the implication constraints.
     */
    public void displayVariableImplicationConstraints() {
        System.out.println("---- Variable Implication Constraints ----");
        for (Constraint constraint : this.variableImplicationConstraints) {
            System.out.println(constraint);
        }
    }
}
