package org.project.booking.ui.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class Item  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int HostelIndex,amount;

    private String userId,roomtype;


    public Item( String userId, int HostelIndex,int amount,String roomtype) {

        this.userId = userId;
        this.HostelIndex = HostelIndex;
        this.amount=amount;
        this.roomtype=roomtype;


    }


    // Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }

    public int getAmount() {
        return amount;
    }

    public int getHostelIndex() {
        return HostelIndex;
    }

    public String getRoomtype() {
        return roomtype;
    }
}
