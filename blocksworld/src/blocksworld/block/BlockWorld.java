package blocksworld.block;

import java.util.Set;
import modelling.Variable;

/**
 * Abstract base class representing the structure of a Block World problem.
 * Manages variables for the blocks and piles and provides access to them.
 */
public abstract class BlockWorld {
    protected int numberOfBlocks; // Number of blocks in the problem
    protected int numberOfPiles;  // Number of piles in the problem
    protected BlockWorldVariable blockWorldVariables; // Encapsulation of all block-world-related variables

    /**
     * Constructor for the BlockWorld class.
     *
     * @param numberOfBlocks the number of blocks in the world.
     * @param numberOfPiles  the number of piles in the world.
     */
    public BlockWorld(int numberOfBlocks, int numberOfPiles) {
        this.numberOfBlocks = numberOfBlocks;
        this.numberOfPiles = numberOfPiles;
        this.blockWorldVariables = new BlockWorldVariable(numberOfBlocks, numberOfPiles);
    }

    /**
     * Gets all "on-block" variables (representing blocks being on top of other blocks or piles).
     *
     * @return a set of "on-block" variables.
     */
    public Set<Variable> getOnBlockVariables() {
        return this.blockWorldVariables.getOnBlockVariables();
    }

    /**
     * Gets all variables in the BlockWorld (on-block, free-pile, and fixed-block variables).
     *
     * @return a set of all variables.
     */
    public Set<Variable> getAllVariables() {
        return blockWorldVariables.getVariables();
    }

    /**
     * Gets all "fixed-block" variables (representing whether a block is fixed in its position).
     *
     * @return a set of "fixed-block" variables.
     */
    public Set<Variable> getFixedBlockVariables() {
        return this.blockWorldVariables.getFixedBlockVariables();
    }

    /**
     * Gets all "free-pile" variables (representing whether a pile is empty or not).
     *
     * @return a set of "free-pile" variables.
     */
    public Set<Variable> getFreePileVariables() {
        return this.blockWorldVariables.getFreePileVariables();
    }

    /**
     * Gets the BlockWorldVariable object that encapsulates all block world variables.
     *
     * @return the BlockWorldVariable object.
     */
    public BlockWorldVariable getBlockWorldVariables() {
        return this.blockWorldVariables;
    }

    /**
     * Gets the index associated with a given variable.
     *
     * @param variable the variable to look up.
     * @return the index of the variable, or -1 if not found.
     */
    public int getVariableIndex(Variable variable) {
        return this.blockWorldVariables.getIndex(variable);
    }
}
