package com.db.socgen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 31-Oct-17.
 */
public class Brand {
    private static Map<String, Brand> registry = new HashMap<>();
    private String id;
    private String name;

    private Brand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Brand get(String id) {
        return registry.get(Utils.validateId(id));
    }

    public String getName() {
        return name;
    }

    public static Brand create(String id, String name) {
        return findOrCreate(name, Utils.validateId(id));
    }

    private static Brand findOrCreate(String name, String validId) {
        Brand brand = Brand.get(validId);
        if(brand == null) {
            brand = new Brand(validId, name);
            registry.put(validId, brand);
        }
        return brand;
    }

    public String getId() {
        return id;
    }
}
