package blocksworld.executable;
import blocksworld.block.*;;


/**
 * Demo class to showcase Block World variables and constraints.
 */
public class Demo {
    public static void main(String[] args) {
        int nbBlocs = 4; // Number of blocks
        int nbPiles = 3; // Number of piles

        System.out.println("------------ Monde des Blocs avec " + nbBlocs + " blocs et " + nbPiles + " piles ----------");

        // Initialize variables and constraints
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(nbBlocs, nbPiles);
        BlockWorldConstraint blockWorldConstraint = new BlockWorldConstraint(nbBlocs, nbPiles);
        BlockWorldGrowingConstraint blockWorldGrowingConstraint = new BlockWorldGrowingConstraint(nbBlocs, nbPiles);
        BlockWorldRegularityConstraint blockWorldRegularityConstraint = new BlockWorldRegularityConstraint(nbBlocs, nbPiles);
        BlockWorldCombinedConstraint blockWorldCombinedConstraint = new BlockWorldCombinedConstraint(nbBlocs, nbPiles);

        // Display variables
        System.out.println("\n--- Affichage des Variables ---");
        System.out.println(blockWorldVariable);

        // Display difference constraints
        System.out.println("\n--- Contraintes de Différence ---");
        blockWorldConstraint.displayVariableDifferenceConstraints();

        // Display implication constraints
        System.out.println("\n--- Contraintes d'Implication ---");
        blockWorldConstraint.getAllConstraints().forEach(System.out::println);

        // Display regularity constraints
        System.out.println("\n--- Contraintes de Régularité ---");
        blockWorldRegularityConstraint.displayRegularityConstraints();

        // Display growing constraints
        System.out.println("\n--- Contraintes de Croissance ---");
        blockWorldGrowingConstraint.displayGrowingConstraints();

        // Display combined constraints (growing + regularity)
        System.out.println("\n--- Contraintes Combinées (Croissance & Régularité) ---");
        blockWorldCombinedConstraint.displayCombinedConstraints();
    }
}
