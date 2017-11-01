package com.db.socgen;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Admin on 01-Nov-17.
 */
public class ProductDiscountCalculatorTest {

    @BeforeClass
    public static void setUp() throws Exception {
        ProductDiscountCalculatorFixture.loadProblemStatementData();
    }

    @Test
    public void shouldDiscountPriceWhenOnlyBrandDiscounted() throws Exception {
        assertDiscount("640.00", "Arrow", "Shirts", "800");
    }

    @Test
    public void shouldDiscountPriceWhenBothBrandAndParentCategoryDiscounted() throws Exception {
        assertDiscount("560.00", "Vero Moda", "Dresses", "1400");
    }

    @Test
    public void shouldDiscountPriceWhenBrandAndAncestorCategoryDiscounted() throws Exception {
        assertDiscount("900.00", "Provogue", "Footwear", "1800");
    }

    @Test
    public void shouldDiscountPriceWhenBrandAndCategoryDiscounted() throws Exception {
        assertDiscount("1760.00", "Wrangler", "Jeans", "2200");
    }


    /*Monkey tests follow*/
    @Test
    public void shouldDiscountPriceInDecimals() throws Exception {
        assertDiscount("1111.10", "Wrangler", "Shirts", "1234.56");
    }

    @Test
    public void shouldDiscountPriceInLargeNumber() throws Exception {
        assertDiscount("11107100009117110810810711071107110810711711711711071171107110711071107110720990711071107110711071107110711071107110711711071107110711881107110711071107110711071107110711079207110711071107110808281081081081080.98", "Wrangler", "Shirts", "12341222232352345345345234523452345345235235235234523523452345234523452345245545234523452345234523452345234523452345235234523452345235423452345234523452345234523452345234532452345234523452345342534534534534534.4234233333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333356");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowErrorForIllegalArguments() throws Exception {
        assertDiscount("2.98", "Wrangler", "Shirts", "adfa");
    }


    //Altered setup data tests...
    @Test
    public void shouldDiscountPriceWhenBrandAndCategoryDiscountedAndIgnoreCase() throws Exception {
        assertDiscount("1760.00", "wrAngLer", "jEaNs", "2200");
    }

    @Test
    public void shouldDiscountPriceWhenBrandAndCategoryAndAncestorDiscountedWithMaxDiscountOnAncestor() throws Exception {
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Frocks", "Dresses", "Women's wear"),new BigDecimal(40));
        assertDiscount("500.00", "Arrow", "Frocks", "1000");
    }

    @Test
    public void shouldDiscountPriceWhenBrandAndCategoryAndAncestorDiscountedWithMaxDiscountOnBrand() throws Exception {
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Frocks", "Dresses", "Women's wear"),new BigDecimal(40));
        assertDiscount("400.00", "Vero Moda", "Frocks", "1000");
    }

    @Test
    public void shouldDiscountPriceWhenBrandAndCategoryAndAncestorDiscountedWithMaxDiscountOnCategory() throws Exception {
        ProductDiscountCalculator.loadCategoryDiscount(Category.create("Frocks", "Dresses", "Women's wear"),new BigDecimal(70));
        assertDiscount("300.00", "Vero Moda", "Frocks", "1000");
    }

    private void assertDiscount(String expected, String brandId, String categoryId, String price) {
        BigDecimal actualDiscountPrice = ProductDiscountCalculator.discountPrice(new Product(brandId, categoryId, price));
        Assert.assertEquals(expected, Utils.scalePriceBigDecimal(actualDiscountPrice).toPlainString());
    }

}