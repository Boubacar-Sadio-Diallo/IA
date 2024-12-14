package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DifferenceConstraint
implements Constraint {
    private Variable v1;
    private Variable v2;

    public DifferenceConstraint(Variable variable, Variable variable2) {
        this.v1 = variable;
        this.v2 = variable2;
    }

    @Override
    public Set<Variable> getScope() {
        HashSet<Variable> hashSet = new HashSet<Variable>();
        hashSet.add(this.v1);
        hashSet.add(this.v2);
        return hashSet;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> map) {
        for (Variable variable : this.getScope()) {
            if (map.containsKey(variable)) continue;
            throw new IllegalArgumentException("Les variables sont introuvables");
        }
        return map.get(this.v1) != map.get(this.v2);
    }

    public String toString() {
        return "DifferenceConstraint{v1=" + String.valueOf(this.v1) + ", v2=" + String.valueOf(this.v2) + "}";
    }
}