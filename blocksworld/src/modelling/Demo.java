package modelling;

import java.util.*;
public class Demo{
    public static void main(String [] args){
        // VAriables & Domaines
        Variable entree, plat, dessert;
        Set<Object> domaineEntree = new HashSet<Object>();
        Set<Object> domainePlat = new HashSet<Object>();
        Set<Object> domaineDessert = new HashSet<Object>();

        domaineEntree.add("Oeufs");domaineEntree.add("Legumes");domaineEntree.add("Rien");
        entree = new Variable("Entrée",domaineEntree);

        domainePlat.add("Viande");domainePlat.add("Poisson");
        plat = new Variable("Plat",domainePlat);

        domaineDessert.add("Tarte");domaineDessert.add("Glace");domaineDessert.add("Fruits");
        dessert = new Variable("dessert",domaineDessert);

        System.out.println(entree);
        System.out.println(plat);
        System.out.println(dessert);

        //Vérification de contraintes
        DifferenceConstraint constraintes = new DifferenceConstraint(entree,dessert);
        System.out.println(constraintes.toString());
        
        //Instanciation & Difference de Contraintes & Implication & UniryContraintes
        Map<Variable, Object> instance;
        instance = new HashMap<Variable, Object>();
        instance.put(entree,domaineEntree);
        instance.put(dessert,domaineDessert);

        //Difference de Contraintes
        System.out.println(instance);
        System.out.println(constraintes.isSatisfiedBy(instance));

        //Implication
        Implication implique = new Implication(entree,domaineEntree,plat,domaineDessert);
        System.out.println(implique.isSatisfiedBy(instance));

    }
}