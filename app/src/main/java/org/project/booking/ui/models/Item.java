package org.project.booking.ui.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class Item  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int HostelIndex;

    private String userId;


    public Item( String userId, int HostelIndex) {

        this.userId = userId;
        this.HostelIndex = HostelIndex;

    }


    // Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }


    public int getHostelIndex() { return HostelIndex; }

}
