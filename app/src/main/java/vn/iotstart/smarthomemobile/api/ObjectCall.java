package vn.iotstart.smarthomemobile.api;

import java.io.Serializable;

import vn.iotstart.smarthomemobile.model.User;

public class ObjectCall implements Serializable {
    Boolean error;
    String message;

    User user;

    public ObjectCall(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ObjectCall{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
