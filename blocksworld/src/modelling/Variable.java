package modelling;

import java.util.Objects;
import java.util.Set;

public class Variable {
    private String nom; 
    private Set<Object> domain;

    public Variable(String string, Set<Object> set) {
        this.nom = string;
        this.domain = set;
    }

    public String getName() {
        return this.nom;
    }

    public Set<Object> getDomain() {
        return this.domain;
    }

    public void setDomain(Set<Object> set, int n, int n2, int n3) {
        for (int i = -n2; i < n; ++i) {
            set.add(i);
        }
        set.remove(n3);
        this.domain = set;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable variable = (Variable)object;
        return Objects.equals(variable.getName(), this.nom);
    }

    public int hashCode() {
        return this.nom.hashCode();
    }

    public String toString() {
        return "nom : " + nom + ", domain : " + String.valueOf(this.domain);
    }
}
