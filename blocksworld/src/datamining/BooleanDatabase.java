package datamining;
import modelling.*;
import java.util.*;
/**
 * Cette classe représente des bases de données transactionnelles
 */
public class BooleanDatabase{
    
    private Set<BooleanVariable> setItems;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> setItems){
        this.setItems = setItems;
        transactions = new ArrayList<>();
    }

    public void add(Set<BooleanVariable> transaction){
        transactions.add(transaction);
    }

    public Set<BooleanVariable> getItems(){
        return this.setItems;
    }

    public List<Set<BooleanVariable>> getTransactions(){
        return transactions;
    }


}