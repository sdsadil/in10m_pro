
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fdata {

    @SerializedName("favorite_data")
    @Expose
    private FavoriteData favoriteData;

    public FavoriteData getFavoriteData() {
        return favoriteData;
    }

    public void setFavoriteData(FavoriteData favoriteData) {
        this.favoriteData = favoriteData;
    }

}
