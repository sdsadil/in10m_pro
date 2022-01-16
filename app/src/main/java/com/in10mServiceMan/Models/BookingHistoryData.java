
package com.in10mServiceMan.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingHistoryData {

    @SerializedName("booking")
    @Expose
    private List<BookingArray> booking = null;

    public List<BookingArray> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingArray> booking) {
        this.booking = booking;
    }

}
