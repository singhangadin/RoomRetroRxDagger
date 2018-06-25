package com.github.angads25.roomretrorxdagger.retrofit.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "PropertyData")
public class PropertyListing {

    @PrimaryKey(autoGenerate = true)
    private long propertyId;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("reviewCount")
    @Expose
    private Integer reviewCount;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("ratedText")
    @Expose
    private String ratedText;

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRatedText() {
        return ratedText;
    }

    public void setRatedText(String ratedText) {
        this.ratedText = ratedText;
    }

}
