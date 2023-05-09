package vn.iotstart.smarthomemobile.model;

import java.util.List;

public class Product {

    private Integer productId;
    private String name;
    private String description;
    private List<ProductImage> images;
    private Long price;
    private int quantity;
    private Category category;

//    private List<OrderItem> orderItems;

    public Product() {
    }

    public Product(Integer productId, String name, String description, List<ProductImage> images, Long price, int quantity, Category category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.images = images;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
