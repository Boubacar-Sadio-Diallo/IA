package blocksworld.block;

import java.util.*;
import bwgeneratordemo.Demo;
import datamining.BooleanDatabase;
import modelling.BooleanVariable;


/**
 * Class to instantiate boolean variables to represent the block world with a certain number of blocks and stacks.
 * Extends BlockWorld.
 */
public class BlockWorldDataExtractor extends BlockWorld {

    private Set<BooleanVariable> booleanVariables = new HashSet<>();
    private Map<String, BooleanVariable> variableMapping = new HashMap<>();

    /**
     * Constructor to initialize the block world with the number of blocks and stacks.
     *
     * @param numberOfBlocks The number of blocks
     * @param numberOfPiles  The number of stacks
     */
    public BlockWorldDataExtractor(int numberOfBlocks, int numberOfPiles) {
        super(numberOfBlocks, numberOfPiles);
        initializeVariables();
    }

    /**
     * Initializes all variables related to block-world interactions.
     * Generates both "on block" and "on table" variables.
     */
    private void initializeVariables() {
        initializeOnBlockVariables();
        initializeOnTableVariables();
    }

    /**
     * Generates variables to check if one block is on top of another.
     */
    private void initializeOnBlockVariables() {
        getOnBlockVariables().forEach(blockA -> 
            getFixedBlockVariables().forEach(blockB -> {
                int i = getVariableIndex(blockA);
                int j = getVariableIndex(blockB);
                if (i != j) {
                    String name = i + "_" + j;
                    addBooleanVariable("on_" + name, name);
                    addBooleanVariable(String.valueOf(j), String.valueOf(j));
                }
            })
        );
    }

    /**
     * Generates variables indicating if a block is on the table in a specific stack.
     */
    private void initializeOnTableVariables() {
        getOnBlockVariables().forEach(block -> 
            getFreePileVariables().forEach(pile -> {
                int blockIndex = getVariableIndex(block);
                int pileIndex = getVariableIndex(pile);
                String name = blockIndex + "_" + pileIndex;
                addBooleanVariable("onTable_" + name, name);
                addBooleanVariable(String.valueOf(pileIndex), String.valueOf(pileIndex));
            })
        );
    }

    /**
     * Helper method to add a boolean variable and its corresponding mapping.
     *
     * @param variableName The name of the variable
     * @param key          The key for the variable in the mapping
     */
    private void addBooleanVariable(String variableName, String key) {
        BooleanVariable variable = new BooleanVariable(variableName);
        booleanVariables.add(variable);
        variableMapping.putIfAbsent(key, variable);
    }

    /**
     * Retrieves the boolean variables that have a true value in a transaction.
     *
     * @param state The state representation of the block world (transaction)
     * @return Set of active boolean variables in this state.
     */
    public Set<BooleanVariable> extractActiveVariables(List<List<Integer>> state) {
        Set<BooleanVariable> activeVariables = new HashSet<>();
        
        for (int pileIndex = 0; pileIndex < state.size(); pileIndex++) {
            List<Integer> pile = state.get(pileIndex);
            handleEmptyPile(pileIndex, activeVariables, pile);

            for (int pos = 0; pos < pile.size(); pos++) {
                int currentBlock = pile.get(pos);
                handleBlockPosition(pileIndex, activeVariables, pile, pos, currentBlock);
            }
        }
        return activeVariables;
    }

    /**
     * Handles the case when a pile is empty and adds the corresponding "free" variable.
     *
     * @param pileIndex       The index of the pile
     * @param activeVariables The set of active variables to update
     * @param pile            The pile (list of blocks)
     */
    private void handleEmptyPile(int pileIndex, Set<BooleanVariable> activeVariables, List<Integer> pile) {
        if (pile.isEmpty()) {
            String name = Integer.toString(-pileIndex - 1);
            activeVariables.add(retrieveOrCreateVariable("free_" + name, name));
        }
    }

    /**
     * Handles the positions of blocks within a pile and adds the corresponding "on" and "fixed" variables.
     *
     * @param pileIndex       The index of the pile
     * @param activeVariables The set of active variables to update
     * @param pile            The pile (list of blocks)
     * @param pos             The current position of the block in the pile
     * @param currentBlock    The block at the current position
     */
    private void handleBlockPosition(int pileIndex, Set<BooleanVariable> activeVariables, List<Integer> pile, int pos, int currentBlock) {
        if (pos == 0) {
            String name = currentBlock + "_" + (-pileIndex - 1);
            activeVariables.add(retrieveOrCreateVariable("onTable_" + name, name));
        }

        if (pos < pile.size() - 1) {
            int aboveBlock = pile.get(pos + 1);
            String nameOn = aboveBlock + "_" + currentBlock;
            String nameFixed = String.valueOf(currentBlock);
            activeVariables.add(retrieveOrCreateVariable("on_" + nameOn, nameOn));
            activeVariables.add(retrieveOrCreateVariable("fixed_" + nameFixed, nameFixed));
        }
    }

    /**
     * Generates a boolean database using the BWGenerator library.
     *
     * @param transactionCount The number of transactions to generate
     * @return Boolean database with the generated transactions.
     */
    public BooleanDatabase createBooleanDatabase(int transactionCount) {
        BooleanDatabase database = new BooleanDatabase(booleanVariables);
        for (int i = 0; i < transactionCount; i++) {
            List<List<Integer>> state = Demo.getState(new Random());
            Set<BooleanVariable> instance = extractActiveVariables(state);
            database.add(instance);
        }
        return database;
    }

    /**
     * Checks if the key already exists in the map or creates a new variable if necessary.
     *
     * @param variableName The name of the variable
     * @param key          The key of the variable in the map
     * @return The existing or newly created boolean variable
     */
    private BooleanVariable retrieveOrCreateVariable(String variableName, String key) {
        return variableMapping.computeIfAbsent(key, k -> new BooleanVariable(variableName));
    }

    /**
     * Returns the set of all boolean variables.
     *
     * @return Set of all boolean variables.
     */
    public Set<BooleanVariable> getBooleanVariables() {
        return booleanVariables;
    }

    /**
     * Returns the map of name-variable mappings.
     *
     * @return Map of name-variable mappings.
     */
    public Map<String, BooleanVariable> getVariableMapping() {
        return variableMapping;
    }

    @Override
    public String toString() {
        return "BlockWorldDataExtractor{" +
                " booleanVariables=" + getBooleanVariables() +
                " }";
    }
}
