package blocksworld.block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import modelling.BooleanVariable;
import modelling.Variable;

/**
 * The BlockWorldVariable class is responsible for managing the variables related to the block world problem.
 * It handles the creation and management of different types of variables for blocks and piles, such as 
 * "on", "free", and "fixed" variables, and maps them to their respective domains and positions.
 */
public class BlockWorldVariable {
    
    // Fields for managing blocks and piles in the block world
    protected int nbBloc;
    private int nbPile;
    private Set<Variable> onb;
    private Set<Variable> free;
    private Set<Variable> fixed;
    private Set<Integer> piles = new HashSet<Integer>();
    private Map<Variable, Integer> varOfIndex = new HashMap<Variable, Integer>();
    private Map<Integer, Variable> indexOfVar = new HashMap<Integer, Variable>();
    private Map<String, Variable> varOfString = new HashMap<>();
    private Set<Object> domain;
    private Set<Variable> variables;
    //private Map<Variable, Variable> associations = new HashMap<>();

    /**
     * Constructor for creating a BlockWorldVariable instance.
     * It initializes the number of blocks and piles, and sets up the necessary variables.
     * 
     * @param n The number of blocks.
     * @param n2 The number of piles.
     */
    public BlockWorldVariable(int n, int n2) {
        this.nbBloc = n;
        this.nbPile = n2;
        this.onb = new HashSet<Variable>();
        this.free = new HashSet<Variable>();
        this.fixed = new HashSet<Variable>();
        this.variables = new HashSet<Variable>();
        this.domain = new HashSet<Object>();
        //assignBlockPositions();
        this.setVariables();
    }

    /**
     * Sets up all the required variables (on, free, and fixed) and adds them to the variables set.
     */
    public void setVariables() {
        this.ensembleOnBVariable();
        this.ensembleFreePVariable();
        this.ensembleFixedVariables();
        this.variables.addAll(this.onb);
        this.variables.addAll(this.free);
        this.variables.addAll(this.fixed);
    }

    /**
     * Creates and initializes "on" variables for each block, representing its position on a pile.
     * Each variable is added to the onb set, along with its corresponding domain.
     */
    public void ensembleOnBVariable() {
        for (int i = 0; i < this.nbBloc; ++i) {
            Variable variable = new Variable("on" + Integer.toString(i), this.domain);
            HashSet<Object> hashSet = new HashSet<Object>(this.domain);
            hashSet.remove(i);
            variable.setDomain(hashSet, this.nbBloc, this.nbPile, i);
            this.onb.add(variable);
            this.varOfIndex.put(variable, i);
            this.indexOfVar.put(i, variable);
            varOfString.put(variable.getName(), variable);
        }
    }

    /**
     * Creates and initializes "free" variables for each pile, indicating whether a pile is free to hold a block.
     * The variables are added to the free set.
     */
    public void ensembleFreePVariable() {
        for (int i = -this.nbPile; i < 0; ++i) {
            BooleanVariable booleanVariable = new BooleanVariable("free" + Integer.toString(i));
            this.free.add(booleanVariable);
            this.varOfIndex.put(booleanVariable, i);
            this.piles.add(i);
            varOfString.put(booleanVariable.getName(), booleanVariable);
        }
    }

    /**
     * Creates and initializes "fixed" variables for each block, indicating whether a block is fixed in position.
     * The variables are added to the fixed set.
     */
    public void ensembleFixedVariables() {
        for (int i = 0; i < this.nbBloc; ++i) {
            BooleanVariable booleanVariable = new BooleanVariable("fixed" + Integer.toString(i));
            this.fixed.add(booleanVariable);
            this.varOfIndex.put(booleanVariable, i);
            varOfString.put(booleanVariable.getName(), booleanVariable);
        }
    }

    /**
     * Returns all the variables used in the block world, including "on", "free", and "fixed" variables.
     * 
     * @return A set of all variables in the block world.
     */
    public Set<Variable> getVariables() {
        return this.variables;
    }

    /**
     * Returns all "on" variables, representing blocks placed on specific piles.
     * 
     * @return A set of "on" variables.
     */
    public Set<Variable> getOnBlockVariables() {
        return this.onb;
    }

    /**
     * Returns all "free" variables, representing whether a pile is free to hold a block.
     * 
     * @return A set of "free" variables.
     */
    public Set<Variable> getFreePileVariables() {
        return this.free;
    }

    /**
     * Returns all "fixed" variables, representing whether a block is fixed in position.
     * 
     * @return A set of "fixed" variables.
     */
    public Set<Variable> getFixedBlockVariables() {
        return this.fixed;
    }

    /**
     * Returns a map that associates variables with their respective indices.
     * 
     * @return A map where the key is a variable and the value is its index.
     */
    public Map<Variable, Integer> getVarToIndex() {
        return this.varOfIndex;
    }

    /**
     * Returns a map that associates indices with their respective variables.
     * 
     * @return A map where the key is an index and the value is a variable.
     */
    public Map<Integer, Variable> getIndexOfVar() {
        return this.indexOfVar;
    }

    /**
     * Returns the index of the specified variable, or -1 if the variable is not found.
     * 
     * @param variable The variable whose index is to be retrieved.
     * @return The index of the variable.
     */
    public int getIndex(Variable variable) {
        return this.varOfIndex != null ? this.getVarToIndex().get(variable) : -1;
    }

    /**
     * Returns the variable corresponding to the specified index, or null if not found.
     * 
     * @param n The index of the variable to retrieve.
     * @return The variable corresponding to the given index.
     */
    public Variable getVariable(int n) {
        return this.indexOfVar != null ? this.getIndexOfVar().get(n) : null;
    }

    /**
     * Returns a set of all pile indices.
     * 
     * @return A set of integers representing pile indices.
     */
    public Set<Integer> getPiles() {
        return this.piles;
    }

    /**
     * Sets the pile indices.
     * 
     * @param set A set of integers representing pile indices to be added.
     */
    public void setPile(Set<Integer> set) {
        this.piles.addAll(set);
    }

    /**
     * Returns a map that associates fixed blocks with their respective positions.
     * 
     * @return A map where the key is a fixed block variable and the value is its associated position.
     */
    /*public Map<Variable, Variable> getAssociations() {
        return this.associations;
    }*/

    /**
     * Associates each "fixed" block with a corresponding "on" position if their indices match.
     * This helps link the blocks to their valid positions in the block world.
     */
    /*private void assignBlockPositions() {
        for (Variable blockFixed : getFixedBlockVariables()) { // Iterates through fixed blocks
            for (Variable position : getOnBlockVariables()) { // Iterates through "on" positions
                int indexFixed = varOfIndex.get(blockFixed); // Retrieves the index of the "fixed" block
                int indexPosition = varOfIndex.get(position); // Retrieves the index of the "on" position
                if (indexFixed == indexPosition) { // If indices match, associate
                    associations.put(blockFixed, position);
                }
            }
        }
    }*/
    /**
     * Retrieves the variable corresponding to the specified value from a given state map.
     * 
     * @param value The value to look for.
     * @param state The map representing the state of variables.
     * @return The variable associated with the given value, or null if not found.
     */
    public Variable getVariableFromIndex(int value, Map<Variable, Integer> state) {
        for (Entry<Variable, Integer> entry : state.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Displays all the variables for debugging purposes.
     * It prints the variables categorized by type: "on", "free", and "fixed".
     */
    public void displayAllVariables() {
        System.out.println("OnB Variables:");
        for (Variable variable : this.onb) {
            System.out.println(variable);
        }
        System.out.println("Free Variables:");
        for (Variable variable : this.free) {
            System.out.println(variable);
        }
        System.out.println("Fixed Variables:");
        for (Variable variable : this.fixed) {
            System.out.println(variable);
        }
    }

    /**
     * Generates a state representation based on the given pile configuration.
     * The state is represented as a map of variables to their respective values.
     * 
     * @param state A 2D array representing the pile configuration.
     * @return A map where the key is a variable and the value is its state.
     */
    public Map<Variable, Object> generateStateFromPiles(int[][] state) {
        Map<Variable, Object> res = new HashMap<>();
        int nbPiles = state.length;
        //System.out.println(nbPiles);
        for (int i = 0; i < nbPiles; i++) {
            int nbBlocks = state[i].length;
            String str = "free" + Integer.toString(-i - 1);
            Variable free = varOfString.get(str);
            if (free == null) {
                System.out.println("Variable " + str + " is missing.");
            }
            if (nbBlocks == 0) {
                res.put(free, true);
            } else {
                res.put(free, false);
            }
            for (int j = 0; j < nbBlocks; j++) {
                String strOn = "on" + Integer.toString(state[i][j]);
                System.out.println(strOn == null ? "Variable " + strOn + " is missing." : strOn);
                String strfix = "fixed" + Integer.toString(state[i][j]);
                System.out.println(fixed == null ? "Variable " + strfix + " is missing." : strfix);

                Variable on = varOfString.get(strOn);
                Variable fixed = varOfString.get(strfix);
                if (j == 0) {
                    res.put(on, -i - 1);
                    res.put(fixed, true);
                }
                if (nbBlocks == 1) {
                    res.put(on, -i - 1);
                    res.put(fixed, false);
                }
                if (j > 0 && j == nbBlocks - 1) {
                    res.put(on, state[i][j - 1]);
                    res.put(fixed, false);
                }
                if (j > 0 && j < nbBlocks - 1) {
                    res.put(on, state[i][j - 1]);
                    res.put(fixed, true);
                }
            }
        }
        return res;
    }

    /**
     * Initializes variables based on the number of piles and blocks.
     * Ensures that all variables are present for each pile and block configuration.
     * 
     * @param nbPiles The number of piles.
     * @param maxBlocks The maximum number of blocks.
     */
    public void initializeVariables(int nbPiles, int maxBlocks) {
        for (int i = 0; i < nbPiles; i++) {
            String freeName = "free" + (-i - 1);
            if (!varOfString.containsKey(freeName)) {
                BooleanVariable freeVar = new BooleanVariable(freeName); // Boolean domain
                varOfString.put(freeName, freeVar);
            }
        }

        for (int block = 0; block < maxBlocks; block++) {
            String onName = "on" + block;
            String fixedName = "fixed" + block;

            if (!varOfString.containsKey(onName)) {
                Variable onVar = new Variable(onName, generateOnDomain(nbPiles, maxBlocks));
                varOfString.put(onName, onVar);
            }

            if (!varOfString.containsKey(fixedName)) {
                BooleanVariable fixedVar = new BooleanVariable(fixedName); // Boolean domain
                varOfString.put(fixedName, fixedVar);
            }
        }
    }

    /**
     * Generates the domain for the "on" variables, including pile indices and block indices.
     * 
     * @param nbPiles The number of piles.
     * @param maxBlocks The maximum number of blocks.
     * @return A set of objects representing the domain of "on" variables.
     */
    private Set<Object> generateOnDomain(int nbPiles, int maxBlocks) {
        Set<Object> domain = new HashSet<>();
        for (int i = -1; i >= -nbPiles; i--) { // Piles
            domain.add(i);
        }
        for (int i = 0; i < maxBlocks; i++) { // Blocks
            domain.add(i);
        }
        return domain;
    }

    /**
     * Returns a map of all string-based variables.
     * 
     * @return A map where the key is the variable name and the value is the variable object.
     */
    public Map<String, Variable> getVarOfString() {
        return this.varOfString;
    }

    @Override
    public String toString() {
        return "Variable : " + variables;
    }
}
