
package com.in10mServiceMan.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBooking {

    @SerializedName("booking")
    @Expose
    private List<BookingListItem> booking = null;

    public List<BookingListItem> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingListItem> booking) {
        this.booking = booking;
    }

}
