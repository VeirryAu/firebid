package com.example.firebid.model;

public class Product {

    private String productId;
    private String productName;
    private String imageUrl;
    private String description;
    private String winner;
    private String highestBid;
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setHighestBid(String highestBid) {
        this.highestBid = highestBid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getWinner() {
        return winner;
    }

    public String getHighestBid() {
        return highestBid;
    }

    public Product(String productName, String imageUrl, String description, String winner,
                   String highestBid){
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.winner = winner;
        this.highestBid = highestBid;
        this.endTime = endTime;
    }

    public Product(){}
}
