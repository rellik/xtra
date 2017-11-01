package com.db.socgen;

/**
 * Created by Admin on 01-Nov-17.
 */
public class ProductDiscountCalculatorFixture {
    public static void load2Brands2Categories() {
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Arrow", "Arrow"), Utils.decimal(10));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Vero Moda", "Vero Moda"), Utils.decimal(10));
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
    public static void loadProblemStatementData() {
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Wrangler", "Wrangler"), Utils.decimal(10));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Arrow", "Arrow"), Utils.decimal(20));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Vero Moda", "Vero Moda"), Utils.decimal(60));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("UCB", "UCB"), Utils.decimal(0));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Adidas", "Adidas"), Utils.decimal(5));
        ProductDiscountCalculator.loadBrandDiscount(Brand.create("Provogue", "Provogue"), Utils.decimal(20));

        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Men's wear", "Men's wear", null), null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Shirts", "Shirts", "Men's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Trousers", "Trousers", "Men's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Casuals", "Casuals", "Trousers"), Utils.decimal(30));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Jeans", "Jeans", "Trousers"), Utils.decimal(20));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Women's wear", "Women's wear", null), Utils.decimal(50));
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Dresses", "Dresses", "Women's wear"),null);
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Footwear", "Footwear", "Dresses"),null);
    }

}
