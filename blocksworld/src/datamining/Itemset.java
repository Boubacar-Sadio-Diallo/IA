package datamining;
import modelling.*;
import java.util.*;

public class Itemset{
    private Set<BooleanVariable> setItems;
    private float frequence;

    public Itemset(Set<BooleanVariable> setItems,float frequence){
        
        this.setItems = setItems;
        this.frequence = frequence;
    }

    public Set<BooleanVariable> getItems(){
        return this.setItems;
    }

    public float getFrequency(){
        return this.frequence;
    }
    @Override
    public String toString() {
        return setItems + " (" + frequence + ")";
    }

}