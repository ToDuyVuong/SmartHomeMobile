package vn.iotstart.smarthomemobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Integer orderId;
    private String date;
    private String shipTo;
    private Long totalPrice;
    private String note;
    private String address;
    private String phone;
    private Integer totalQuantity;
    private Status status;
//    private List<OrderItem> orderItems;
    private User user;

    public Order() {
    }

    public Order(Integer orderId, String date, String shipTo, Long totalPrice, String note, String address, String phone, Integer totalQuantity, Status status, User user) {
        this.orderId = orderId;
        this.date = date;
        this.shipTo = shipTo;
        this.totalPrice = totalPrice;
        this.note = note;
        this.address = address;
        this.phone = phone;
        this.totalQuantity = totalQuantity;
        this.status = status;
        this.user = user;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItem> orderItems) {
//        this.orderItems = orderItems;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public enum Status {
        PENDING(0), PROCESSING(1), DELIVERING(2), DELIVERED(3), CANCELED(4);

        private int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
