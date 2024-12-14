package cp;
import modelling.*;
import java.util.*;

public interface Solver{
    /**
     * Resoudre les problemes de csp en utilisant les algo
     * @return: une solution 
     */
    public Map<Variable,Object> solve();
    
}