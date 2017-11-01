package com.donnybiddappa.socgen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

import static com.donnybiddappa.socgen.Utils.decimal;

/**
 * Fun at the Mall
 */
public class Main {

    public static void main(String... args) throws IOException, URISyntaxException {

        //Process input file.
        InputFileProcessor inputFileProcessor = new InputFileProcessor(args).process();
        Collection<String> inventoryEntries = inputFileProcessor.getInventoryEntries();
        Collection<String> billsEntries = inputFileProcessor.getBillsEntries();

        System.out.println("Processing "+inventoryEntries.size()+" inventory entries, and outputing "+billsEntries.size()+" bills");

        initSystemData();

        Inventory inventory = loadInventory(inventoryEntries);

        Collection<BigDecimal> billsTotals = calculateTotalsForBills(billsEntries, inventory);

        printBillsTotals(billsTotals);
    }

    private static void printBillsTotals(Collection<BigDecimal> billsTotals) {
        //output
        System.out.println("Output:");
        for (BigDecimal billsTotal : billsTotals) {
            System.out.println(Utils.scalePriceBigDecimal(billsTotal));
        }
    }

    private static Collection<BigDecimal> calculateTotalsForBills(Collection<String> billsEntries, Inventory inventory) {
        Billing billing = Billing.create(inventory);
        Collection<BigDecimal> billsTotals = new LinkedHashSet<>();
        for (String billsEntry : billsEntries) {
            System.out.println("Processing bill..."+billsEntry);
            billsTotals.add(billing.totalPrice(billsEntry.split(",")));
        }
        return billsTotals;
    }

    private static Inventory loadInventory(Collection<String> inventoryEntries) {
        Inventory inventory = Inventory.create();
        String[] tokens;
        for (String inventoryEntry : inventoryEntries) {
            System.out.println("loading inventory info :"+inventoryEntry);
            tokens = inventoryEntry.split(",");
            if(tokens.length != 4) throw new IllegalArgumentException("Malformed entry should be <id>, \"<brand id>, <category id>, <price>\" format only");
            inventory.addProduct(tokens[0], new Product(tokens[1], tokens[2], tokens[3]));
        }
        return inventory;
    }


    /*Assuming the following interpretation o data
    *   Category discounts:
    *   Men's wear
    *       |- Shirts
    *       |- Trousers
    *           |- Casuals (30% off)
    *           |- Jeans   (20% off)
    *
    *   Women's wear (50% off)
    *       |- Dresses
    *           |- Footwear
    *
    *
    *   Brands Discounts:
    *   Wrangler         10%
    *   Arrow            20%
    *   Vero Moda        60%
    *   UCB              None
    *   Adidas           5%
    *   Provogue         20%
    *
    */
    public static void initSystemData() {
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Wrangler", "Wrangler"), decimal(10));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Arrow", "Arrow"), decimal(20));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Vero Moda", "Vero Moda"), decimal(60));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("UCB", "UCB"), decimal(0));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Adidas", "Adidas"), decimal(5));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Provogue", "Provogue"), decimal(20));

        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Men's wear", "Men's wear", null), null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Shirts", "Shirts", "Men's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Trousers", "Trousers", "Men's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Casuals", "Casuals", "Trousers"), decimal(30));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Jeans", "Jeans", "Trousers"), decimal(20));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Women's wear", "Women's wear", null), decimal(50));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Dresses", "Dresses", "Women's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Footwear", "Footwear", "Dresses"),null);
    }

    private static class InputFileProcessor {
        private String[] args;
        private Collection<String> inventoryEntries;
        private Collection<String> billsEntries;

        public InputFileProcessor(String... args) {
            validateArguments(args);
            this.args = args;
        }

        public Collection<String> getInventoryEntries() {
            return inventoryEntries;
        }

        public Collection<String> getBillsEntries() {
            return billsEntries;
        }

        public InputFileProcessor process() throws IOException {
            RandomAccessFile inputFile;
            try {
                inputFile = new RandomAccessFile(args[0], "r");
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("Specify full path to file.", e);
            }
            String line = inputFile.readLine();
            String trimmedLine;
            int number;
            int inventoryEntriesSize = -1;
            int billsEntriesSize = -1;
            inventoryEntries = new LinkedHashSet<>();
            billsEntries = new LinkedHashSet<>();

            while(line != null){
                trimmedLine = line.trim();
                if(trimmedLine.length() > 0){
                    try {
                        number = Integer.parseInt(trimmedLine);

                        if(inventoryEntriesSize == -1){
                            inventoryEntriesSize = number;
                        }else{
                            billsEntriesSize = number;
                        }
                    } catch (NumberFormatException e) {
                        if(billsEntriesSize == -1 && inventoryEntriesSize > -1 && inventoryEntries.size() < inventoryEntriesSize){
                            inventoryEntries.add(trimmedLine);
                        }else if(billsEntriesSize > -1 && billsEntries.size() < billsEntriesSize){
                            billsEntries.add(trimmedLine);
                        }
                    }
                }
                line = inputFile.readLine();
            }

//            debugFileRead(inventoryEntriesSize, billsEntriesSize, inventoryEntries, billsEntries);

            if(inventoryEntries.size() < inventoryEntriesSize || billsEntries.size() < billsEntriesSize) {
                throw new IllegalArgumentException("Input file not proper!, indicated sizes and entries vary! ");
            }
            return this;
        }

        private  void validateArguments(String... args) {
            if(args == null || args.length == 0) throw new IllegalArgumentException("Please provide absolute path to input file as an argument");
        }

        private  void debugFileRead(int inventoryEntriesSize, int billsEntriesSize, Collection<String> inventoryEntries, Collection<String> billsEntries) {
            System.out.println("Collecting "+inventoryEntriesSize+" inventory entries..., found "+ inventoryEntries.size());
            for (String inventoryEntry : inventoryEntries) {
                System.out.println(inventoryEntry);
            }

            System.out.println("Collected "+billsEntriesSize+" bill entries..., found "+ billsEntries.size());
            for (String billsEntry : billsEntries) {
                System.out.println(billsEntry);
            }
        }

    }
}
