package com.db.socgen;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Admin on 31-Oct-17.
 */
public class ProductDiscountCalculator {

    private static ProductDiscountCalculator discountCalculator = new ProductDiscountCalculator();
    private Map<String, BigDecimal> categoryDiscounts = new HashMap<>();
    private Map<String, BigDecimal> brandDiscounts = new HashMap<>();

    private ProductDiscountCalculator() {}

    public static void loadBrandDiscount(Brand brand, BigDecimal discountPercentage){
        discountCalculator.addToMap(brand.getId(), discountPercentage, discountCalculator.brandDiscounts);
    }

    public static void loadCategoryDiscount(Category category, BigDecimal discountPercentage){
        discountCalculator.addToMap(category.getId(), discountPercentage, discountCalculator.categoryDiscounts);
    }

    private void addToMap(String id, BigDecimal discount, Map<String, BigDecimal> discountMap) {
        if(discount != null && id != null)
            discountMap.put(id, discount);
    }

    static BigDecimal discountPrice(Product product) {
        return discountCalculator.price(product);
    }

    private BigDecimal price(Product product) {
        BigDecimal maxDiscount = maxDiscount(product.getBrand()).max(maxDiscount(product.getCategory()));
        return product.getPrice().subtract(product.getPrice().multiply(maxDiscount.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP)));
    }

    private BigDecimal maxDiscount(Category category) {
        if(category == null) return BigDecimal.ZERO;

        List<String> path = category.path();
        BigDecimal maxDiscount = BigDecimal.ZERO;
        BigDecimal discount;
        for (String id : path) {
            discount = categoryDiscounts.get(id);
            maxDiscount = maxDiscount.max(discount ==  null?BigDecimal.ZERO: discount);
        }
        return maxDiscount;
    }

    private BigDecimal maxDiscount(Brand brand) {
        return brand == null || brandDiscounts.get(brand.getId()) == null?BigDecimal.ZERO: brandDiscounts.get(brand.getId());
    }
}
