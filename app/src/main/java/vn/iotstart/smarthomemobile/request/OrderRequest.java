package vn.iotstart.smarthomemobile.request;

import java.util.List;

import vn.iotstart.smarthomemobile.model.Order;
import vn.iotstart.smarthomemobile.model.OrderItem;

public class OrderRequest {
    private Order order;
    private List<OrderItem> orderItemList;

    public OrderRequest(Order order, List<OrderItem> orderItemList) {
        this.order = order;
        this.orderItemList = orderItemList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

}