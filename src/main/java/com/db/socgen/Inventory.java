package com.db.socgen;

import java.util.HashMap;

/**
 * Created by Admin on 31-Oct-17.
 */
public class Inventory {
    private HashMap<String, Product> products = new HashMap<>();
    private static Inventory inventory = new Inventory();

    private Inventory() {}

    public static Inventory create() {
        return inventory;
    }

    public void addProduct(String id, Product product) {
        this.products.put(id, product);
    }

    public Product get(String idOfProduct) {
        return products.get(idOfProduct);
    }
}
