package vn.iotstart.smarthomemobile.model;

public class ProductImage {
    private Integer productImageId;
    private String image;
    private Product product;

    public ProductImage(Integer productImageId, String image, Product product) {
        this.productImageId = productImageId;
        this.image = image;
        this.product = product;
    }

    public ProductImage() {
    }

    public Integer getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
