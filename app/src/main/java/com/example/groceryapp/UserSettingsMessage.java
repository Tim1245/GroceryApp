package com.example.groceryapp;

public class UserSettingsMessage {
    private boolean fav_WILLYS;
    public boolean IsWILLYSFavoured() {
        return fav_WILLYS;
    }

    private boolean fav_COOP;
    public boolean IsCOOPFavoured() {
        return fav_COOP;
    }

    private boolean fav_ICA;
    public boolean IsICAFavoured() {
        return fav_ICA;
    }

    private boolean fav_LIDL;
    public  boolean IsLIDLFavoured() {
        return fav_LIDL;
    }

    public UserSettingsMessage(boolean IsWILLYSFavoured, boolean IsCOOPFavoured, boolean IsICAFavoured, boolean IsLIDLFavoured) {
        this.fav_WILLYS = IsWILLYSFavoured;
        this.fav_COOP = IsCOOPFavoured;
        this.fav_ICA = IsICAFavoured;
        this.fav_LIDL = IsLIDLFavoured;
    }

    @Override
    public String toString() {
        return "UserSettingsMessage{" +
                "WILLYS=" + fav_WILLYS +
                ", COOP=" + fav_COOP +
                ", ICA=" + fav_ICA +
                ", LIDL=" + fav_LIDL +
                '}';
    }
}
