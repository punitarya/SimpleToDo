package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Created by puarya on 2/14/17.
 * for class for - Task details
 */
public class TaskItem implements Serializable{

    private String itemName;
    private String itemDate;
    private String itemNotes;

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNotes() {
        return itemNotes;
    }

    public void setItemNotes(String itemNotes) {
        this.itemNotes = itemNotes;
    }
}
