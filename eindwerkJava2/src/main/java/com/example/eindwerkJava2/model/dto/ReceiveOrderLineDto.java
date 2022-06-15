package com.example.eindwerkJava2.model.dto;

public class ReceiveOrderLineDto {
    private final String id;
    private final String receivedQuantity;
    private final String location;
    private final String user;

    public ReceiveOrderLineDto(String id, String receivedQuantity, String location, String user) {
        this.id = id;
        this.receivedQuantity = receivedQuantity;
        this.location = location;
        this.user = user;
    }

    public Long getId() {
        return Long.valueOf(id);
    }

    public Double getReceivedQuantity() {
        return Double.valueOf(receivedQuantity);
    }

    public Long getLocation() {
        return Long.valueOf(location);
    }

    public Long getUser() {
        return Long.valueOf(user);
    }
}
