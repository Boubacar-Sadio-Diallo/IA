package blocksworld.executable;

import datamining.BooleanDatabase;
import datamining.Apriori;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;
import datamining.AssociationRule;

import java.util.Set;

import blocksworld.block.BlockWorldDataExtractor;


public class BlockWorldDataminingMain {

    // Constants for configuration
    private static final int NUMBER_OF_BLOCKS = 5; // Number of blocks in the world
    private static final int NUMBER_OF_PILES = 5;  // Number of piles in the world
    private static final int NB_TRANSACTIONS = 10000; // Number of transactions to generate for the database
    private static final float MIN_FREQUENCY = 2.0f / 3.0f;  // Minimum frequency (2/3) for frequent itemsets
    private static final float MIN_CONFIDENCE = 95.0f / 100.0f;  // Minimum confidence (95%) for association rules

    public static void main(String[] args) {
        try {
            // 1. Create the data extractor (BlockWorldDataExtractor)
            BlockWorldDataExtractor extractor = createDataExtractor();

            // 2. Generate the BooleanDatabase with the defined number of transactions
            BooleanDatabase database = createDatabase(extractor);

            // 3. Extract frequent itemsets from the database
            Set<Itemset> frequentItemsets = extractFrequentItemsets(database);

            // 4. Extract association rules from the frequent itemsets
            Set<AssociationRule> associationRules = extractAssociationRules(database);

            // 5. Display the results: frequent itemsets and association rules
            displayResults(frequentItemsets, associationRules);
        } catch (Exception e) {
            // Error handling: prints the error message and stack trace if any exception occurs
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates the BlockWorldDataExtractor instance using the specified number of blocks and piles.
     * 
     * @return A new BlockWorldDataExtractor object.
     */
    private static BlockWorldDataExtractor createDataExtractor() {
        return new BlockWorldDataExtractor(NUMBER_OF_BLOCKS, NUMBER_OF_PILES);
    }

    /**
     * Generates the BooleanDatabase using the data extractor and the specified number of transactions.
     * 
     * @param extractor The BlockWorldDataExtractor instance to extract data.
     * @return A BooleanDatabase containing the generated transactions.
     */
    private static BooleanDatabase createDatabase(BlockWorldDataExtractor extractor) {
        return extractor.createBooleanDatabase(NB_TRANSACTIONS);
    }

    /**
     * Extracts frequent itemsets from the BooleanDatabase based on the minimum frequency.
     * 
     * @param database The BooleanDatabase containing the transactions.
     * @return A set of frequent itemsets extracted from the database.
     */
    private static Set<Itemset> extractFrequentItemsets(BooleanDatabase database) {
        // Create an instance of the Apriori algorithm to extract frequent itemsets
        Apriori miner = new Apriori(database);
        // Return the frequent itemsets based on the minimum frequency threshold
        return miner.extract(MIN_FREQUENCY);
    }

    /**
     * Extracts association rules from the frequent itemsets based on the minimum frequency and confidence.
     * 
     * @param database The BooleanDatabase containing the transactions.
     * @return A set of association rules extracted from the frequent itemsets.
     */
    private static Set<AssociationRule> extractAssociationRules(BooleanDatabase database) {
        // Create an instance of the BruteForceAssociationRuleMiner to extract association rules
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(database);
        // Return the association rules based on the minimum frequency and confidence thresholds
        return ruleMiner.extract(MIN_FREQUENCY, MIN_CONFIDENCE);
    }

    /**
     * Displays the results: frequent itemsets and association rules.
     * 
     * @param frequentItemsets The frequent itemsets to display.
     * @param associationRules The association rules to display.
     */
    private static void displayResults(Set<Itemset> frequentItemsets, Set<AssociationRule> associationRules) {
        // Print the minimum frequency and confidence values
        System.out.println("MinFrequency: " + MIN_FREQUENCY + " ******* MinConfidence: " + MIN_CONFIDENCE);

        // Display the frequent itemsets
        System.out.println("\n********************* Frequent Itemsets: *******************");
        frequentItemsets.forEach(System.out::println);

        // Display the association rules
        System.out.println("\n**************** Association Rules: ****************");
        associationRules.forEach(System.out::println);
    }
}
