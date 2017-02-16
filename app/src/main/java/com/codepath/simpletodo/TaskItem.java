package com.codepath.simpletodo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by puarya on 2/14/17.
 * for class for - Task details
 */
public class TaskItem implements Serializable{

    private String itemName;
    private String itemDate;
    private String itemNotes;
    private String itemPriority;

    public TaskItem(String itemName, String itemDate, String itemNotes, String itemPriority){
        this.itemName = itemName;
        this.itemDate = itemDate;
        this.itemNotes = itemNotes;
        this.itemPriority = itemPriority;
    }

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

    public String getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(String itemPriority) {
        this.itemPriority = itemPriority;
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.deepEquals(this, obj)) {
            return true;
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{itemName, itemDate, itemNotes, itemPriority});
    }
}
