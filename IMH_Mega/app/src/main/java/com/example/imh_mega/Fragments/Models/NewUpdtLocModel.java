package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewUpdtLocModel {

    @SerializedName("0")

    private ArrayList<UpdtLocModel> lowcat;

    public ArrayList<UpdtLocModel> getLowcat() {
        return lowcat;
    }

    public void setLowcat(ArrayList<UpdtLocModel> lowcat) {
        this.lowcat = lowcat;
    }
}
