package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Implication
implements Constraint {
    Variable v1;
    Variable v2;
    Set<Object> s1;
    Set<Object> s2;

    public Implication(Variable variable, Set<Object> set, Variable variable2, Set<Object> set2) {
        this.v1 = variable;
        this.s1 = set;
        this.v2 = variable2;
        this.s2 = set2;
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
        for (Variable object2 : this.getScope()) {
            if (map.containsKey(object2)) continue;
            throw new IllegalArgumentException("Les variables sont introuvables");
        }
        Object object3 = map.get(this.v1);//la valeur de v1
        Object object = map.get(this.v2);//la valeur de v2
        return this.s1.contains(object3) && this.s2.contains(object) || !this.s1.contains(object3);
    }

    public Variable getV1() {
        return this.v1;
    }

    public Variable getV2() {
        return this.v2;
    }

    public Set<Object> getS1() {
        return this.s1;
    }

    public Set<Object> getS2() {
        return this.s2;
    }
    
    public String toString() {
        return "Implication{v1=" + String.valueOf(this.v1) + ", s1=" + String.valueOf(this.s1) + ", v2=" + String.valueOf(this.v2) + ", s2=" + String.valueOf(this.s2) + "}";
    }
}