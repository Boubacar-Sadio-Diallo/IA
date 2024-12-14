package planning;

import java.util.Comparator;
import java.util.Map;
import modelling.Variable;

public class MyComparator implements Comparator<Map<Variable,Object>>{
    private Map<Map<Variable,Object>, Float> distance;

    public MyComparator(Map<Map<Variable,Object>, Float> distance){
        this.distance=distance;
    }

    public int compare(Map<Variable,Object> o1,Map<Variable,Object> o2){
        return Float.compare(this.distance.get(o1),this.distance.get(o2));
    }
}