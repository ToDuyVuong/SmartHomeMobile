package vn.iotstart.smarthomemobile.model;

import java.util.List;

public class Category {
    private Integer categoryId;
    private String name;
    private String description;
    private String image;
    private List<Product> products;

    public Category() {
    }

    public Category(Integer categoryId, String name, String description, String image, List<Product> products) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.products = products;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
