
package com.github.angads25.roomretrorxdagger.architecture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("propertyListing")
    @Expose
    private List<PropertyListing> propertyListing = null;

    public List<PropertyListing> getPropertyListing() {
        return propertyListing;
    }

    public void setPropertyListing(List<PropertyListing> propertyListing) {
        this.propertyListing = propertyListing;
    }

}
