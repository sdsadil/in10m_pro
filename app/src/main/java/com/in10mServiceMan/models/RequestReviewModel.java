
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestReviewModel {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("service_provider_id")
    @Expose
    private String serviceProviderId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("overall_rating")
    @Expose
    private String overallRating;
    @SerializedName("price_rating")
    @Expose
    private String priceRating;
    @SerializedName("knowledge_rating")
    @Expose
    private String knowledgeRating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service_id")
    @Expose
    private String serviceId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public String getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(String priceRating) {
        this.priceRating = priceRating;
    }

    public String getKnowledgeRating() {
        return knowledgeRating;
    }

    public void setKnowledgeRating(String knowledgeRating) {
        this.knowledgeRating = knowledgeRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
