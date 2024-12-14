package modelling;

import java.util.*;

public class BooleanVariable extends Variable{
    //Initialise le domaine à {true,false}
    public BooleanVariable(String nom){
        super(nom,new HashSet<>(Arrays.asList(true,false)));
    }
}