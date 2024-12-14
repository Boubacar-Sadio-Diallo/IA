package cp;
import java.util.*;
import modelling.*;

public interface VariableHeuristic{
    public Variable best(Set<Variable> ensembleVariable,Map<Variable, Set<Object>> setDomains);
}