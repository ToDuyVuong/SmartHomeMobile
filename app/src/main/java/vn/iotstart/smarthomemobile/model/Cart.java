package vn.iotstart.smarthomemobile.model;

public class Cart {

    private Integer cartId;
    private int quantity;
    private User user;
    private Product product;
    private boolean selected;

    public Cart() {
    }

    public Cart(Integer cartId, int quantity, User user, Product product, boolean selected) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
        this.selected = selected;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
