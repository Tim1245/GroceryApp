package com.example.groceryapp;

public class UserSettingsMessage {
    private boolean fav_WILLYS;
    public boolean IsWillysFavourited() {
        return fav_WILLYS;
    }

    private boolean fav_COOP;
    public boolean IsCOOPFavourited() {
        return fav_COOP;
    }

    private boolean fav_ICA;
    public boolean IsICAFavourited() {
        return fav_ICA;
    }

    private boolean fav_LIDL;
    public boolean IsLIDLFavourited() {
        return fav_LIDL;
    }

    public UserSettingsMessage(boolean IsWillysFavourited, boolean IsCOOPFavourited, boolean IsICAFavourited, boolean IsLIDLFavourited) {
        this.fav_WILLYS = IsWillysFavourited;
        this.fav_COOP = IsCOOPFavourited;
        this.fav_ICA = IsICAFavourited;
        this.fav_LIDL = IsLIDLFavourited;
    }
}
