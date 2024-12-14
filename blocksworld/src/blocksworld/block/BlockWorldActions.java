package blocksworld.block;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import planning.Action;
import planning.BasicAction;
import modelling.Variable;

/**
 * Class instantiating all the possibles actions of the block world
 * The actions have a precondition and an effect
 * Extends BlockWorld
 */
public class BlockWorldActions extends BlockWorld {

  private Set<Action> setOfActions;

  /**
   * Constructor
   *
   * @param numberOfBlocks : the number of blocks
   * @param numberOfPiles  : the number of piles
   */
  public BlockWorldActions(int nbBlocs, int nbPiles) {
    super(nbBlocs, nbPiles);
    setOfActions = new HashSet<>();
    generateActions();
  }

  /**
   * Getter
   *
   * @return return a set containing all the actions
   */
  public Set<Action> getAllActions() {
    return setOfActions;
  }

  /**
   * Method to generate actions moving block B1 from on top of B2 to on top of B3
   */
  private void moveB1FromB2ToB3() {
    for (Variable fixed1 : getFixedBlockVariables()) {
      for (Variable fixed2 : getFixedBlockVariables()) {
        int i1 = getVariableIndex(fixed1);
        int i2 = getVariableIndex(fixed2);
        if (i1 == i2)
          continue;
        for (Variable fixed3 : getFixedBlockVariables()) {
          int i3 = getVariableIndex(fixed3);
          if ((i1 == i3) || (i2 == i3))
            continue;
          Variable onb1 = blockWorldVariables.getVariable(i1);
          generateOneAction(onb1, fixed1, fixed2, fixed3, i2, false, true, false, i3, false, false, true);
        }
      }
    }
  }

  /**
   * Methode to generate actions moving block B1 from on top of B2 to on top of a
   * Pile
   */
  private void moveB1FromB2ToP() {
    for (Variable fixed1 : getFixedBlockVariables()) {
      for (Variable fixed2 : getFixedBlockVariables()) {
        int i1 = getVariableIndex(fixed1);
        int i2 = getVariableIndex(fixed2);
        if (i1 == i2)
          continue;
        for (Variable freep : getFreePileVariables()) {
          int i3 = getVariableIndex(freep);
          Variable onb1 = blockWorldVariables.getVariable(i1);

          //Variable onb1 = getAssociations().get(fixed1);
          generateOneAction(onb1, fixed1, fixed2, freep, i2, false, true, true, i3, false, false, false);
        }
      }
    }
  }

  /**
   * Methode to generate actions moving block B1 from on top of a Pile to on top
   * of a B2
   */
  private void moveB1FromPToB2() {
    for (Variable fixed1 : getFixedBlockVariables()) {
      for (Variable fixed2 : getFixedBlockVariables()) {
        int i1 = getVariableIndex(fixed1);
        int i2 = getVariableIndex(fixed2);
        if (i1 == i2)
          continue;
        for (Variable freep : getFreePileVariables()) {
          int i3 = getVariableIndex(freep);
          Variable onb1 = blockWorldVariables.getVariable(i1);

          //Variable onb1 = getAssociations().get(fixed1);
          generateOneAction(fixed1, onb1, fixed2, freep, false, i3, false, false, false, i2, true, true);
        }
      }
    }
  }

  /**
   * Method to generate actions moving block B1 from of top
   * of a pile to on top of a another pile
   */
  private void moveB1FromP1ToP2() {
    for (Variable fixed : getFixedBlockVariables()) {
      for (Variable free1 : getFreePileVariables()) {
        for (Variable free2 : getFreePileVariables()) {
          int i1 = getVariableIndex(free1);
          int i2 = getVariableIndex(free2);
          if (i1 == i2)
            continue;
          Variable onb1 = blockWorldVariables.getVariable(i1);

          //Variable onb = getAssociations().get(fixed);
          generateOneAction(fixed, onb1, free1, free2, false, i1, false, true, 
                                                    false, i2, true, false);
        }
      }
    }
  }

  /**
   * Method to assign values to variables
   *
   * @param pre  : map<Variable, Object>
   * @param var1 : first variable
   * @param var2 : second variable
   * @param var3 : third variable
   * @param var4 : fourth variable
   * @param val1 : value of var1
   * @param val2 : value of var2
   * @param val3 : value of var3
   * @param Val4 : value of var4
   */
  private void prePost(Map<Variable, Object> pre, Variable var1, Variable var2, Variable var3, Variable var4,
      Object val1,
      Object val2, Object val3, Object val4) {
      pre.put(var1, val1);
      pre.put(var2, val2);
      pre.put(var3, val3);
      pre.put(var4, val4);
  }

  /**
   * Method generating one action
   *
   * @param v1      : first variable
   * @param v2      : second variable
   * @param v3      : third variable
   * @param v4      : fourth variable
   * @param pre1    : value of var1 in the precondition
   * @param pre2    : value of var2 in the precondition
   * @param pre3    : value of var3 in the precondition
   * @param pre4    : value of var4 in the precondition
   * @param effect1 : value of var1 in the effect
   * @param effect2 : value of var2 in the effect
   * @param effect3 : value of var3 in the effect
   * @param effect4 : value of var4 in the effect
   */
  private void generateOneAction(Variable v1, Variable v2, Variable v3, Variable v4, Object pre1, Object pre2,
      Object pre3, Object pre4, Object effect1, Object effect2, Object effect3, Object effect4) {
    Map<Variable, Object> pre = new HashMap<>();
    Map<Variable, Object> effect = new HashMap<>();
    prePost(pre, v1, v2, v3, v4, pre1, pre2, pre3, pre4);
    prePost(effect, v1, v2, v3, v4, effect1, effect2, effect3, effect4);
    Action action = new BasicAction(pre, effect, 1);
    setOfActions.add(action);

  }

  /**
   * Private method to generate all the actions
   */
  private void generateActions() {
    if (numberOfBlocks < 2)
      return;
    moveB1FromB2ToB3();
    moveB1FromB2ToP();
    moveB1FromPToB2();
    moveB1FromP1ToP2();
  }

  /**
   * Getter
   *
   * @return all the actions
   */
  public Set<Action> getSetOfActions() {
    return setOfActions;
  }

  @Override
  public String toString() {
    return "{" +
        " setOfActions='" + getSetOfActions() + "'" +
        "}";
  }

}