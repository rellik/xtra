package com.donnybiddappa.socgen;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by Admin on 31-Oct-17.
 */
public class BillingTest {


    @Test
    public void shouldOutputTotalProvidedAInventoryOfProductsAndDiscounts() throws Exception {

        ProductDiscountCalculatorFixture.load2Brands2Categories();
        Inventory inventory = Inventory.create();
        inventory.addProduct("1", new Product("Arrow", "Shirts", "60.25"));
        inventory.addProduct("2", new Product("Vero Moda", "Dresses", "39.75"));
        Billing billing = Billing.create(inventory);
        BigDecimal actualPrice = billing.totalPrice("1", "2");

        Assert.assertEquals("90.00", Utils.scalePriceBigDecimal(actualPrice).toPlainString());
    }

}