package com.onwhichtvchannel.Model;

import java.util.ArrayList;

/**
 * Created by vinove on 4/11/16.
 */

public class DataModel {

    String header;
    ArrayList<ItemModel> itemModelArrayList;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setItemModelArrayList(ArrayList<ItemModel> itemModelArrayList) {
        this.itemModelArrayList = itemModelArrayList;
    }

    public ArrayList<ItemModel> getItemModelArrayList() {
        return itemModelArrayList;
    }

    public String getHeader() {
        return header;
    }
}
