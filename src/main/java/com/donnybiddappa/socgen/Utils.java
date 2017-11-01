package com.donnybiddappa.socgen;

import java.math.BigDecimal;

/**
 * Created by Admin on 01-Nov-17.
 */
public class Utils {
    public static String validateId(String id) {
        if(id == null || id.trim().length() == 0) throw new IllegalArgumentException("Id found null or empty! for Category:"+ id);
        return id.toLowerCase().trim();
    }

    public static BigDecimal scalePriceBigDecimal(BigDecimal price) {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    static BigDecimal decimal(int val) {
        return new BigDecimal(val);
    }
}
