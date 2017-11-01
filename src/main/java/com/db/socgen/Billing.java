package com.db.socgen;

import java.math.BigDecimal;

/**
 * Created by Admin on 31-Oct-17.
 */
public class Billing {
    private static Billing billing = new Billing();
    private Inventory inventory;

    private Billing() {}

    public static Billing create(Inventory inventory) {
        billing.inventory = inventory;
        return billing;
    }

    public BigDecimal totalPrice(String... listOfProductIds) {
        BigDecimal sum = BigDecimal.ZERO;

        for (String idsOfProduct : listOfProductIds) {
            sum = sum.add(ProductDiscountCalculator.discountPrice(inventory.get(idsOfProduct)));
        }

        return Utils.scalePriceBigDecimal(sum);
    }
}
