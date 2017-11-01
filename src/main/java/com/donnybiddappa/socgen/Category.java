package com.donnybiddappa.socgen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 31-Oct-17.
 */
public class Category {
    private static HashMap<String, Category> registry = new HashMap<>();
    private final String id;
    private String name;
    private Category parent;

    private Category(String id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public List<String> path() {
        LinkedList<String> path = new LinkedList<>();
        if(parent != null) path.addAll(parent.path());
        path.add(id);
        return path;
    }

    public static Category get(String id) {
        return registry.get(Utils.validateId(id));
    }

    public static Category create(String id, String name, String parentId) {
        Category parent = getParentCategory(parentId);
        String validId = Utils.validateId(id);
        return findOrCreate(validId, name, parent);
    }

    private static Category findOrCreate(String validId, String name, Category parent) {
        Category category = registry.get(validId);

        if(category == null) {
            category = new Category(validId, name, parent);
            registry.put(validId, category);
        }
        return category;
    }

    private static Category getParentCategory(String parentId) {
        if(parentId == null || parentId.trim().length() == 0) return null;
        Category parent = registry.get(Utils.validateId(parentId));

        if(parent == null)throw new IllegalArgumentException("Parent category does not exist :"+parentId);
        return parent;
    }

    public String getId() {
        return id;
    }
}
