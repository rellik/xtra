package com.donnybiddappa.socgen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.DoubleBuffer;

/**
 * Created by Admin on 31-Oct-17.
 */
public class Product {
    private final Brand brand;
    private final Category category;
    private final BigDecimal price;

    public Product(String brandId, String categoryId, String price) {
        this.brand = Brand.get(brandId);
        this.category = Category.get(categoryId);
        try {
            this.price = Utils.scalePriceBigDecimal(new BigDecimal(price));
        } catch (Exception e) {
            throw new IllegalArgumentException("Accepting decimal decimal in format ####.## , but got:"+price);
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Brand getBrand() {
        return brand;
    }

    public Category getCategory() {
        return category;
    }
}
