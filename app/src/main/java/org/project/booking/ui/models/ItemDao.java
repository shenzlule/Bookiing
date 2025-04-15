package org.project.booking.ui.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);


    @Delete
    void delete(Item item);


    @Query("SELECT * FROM items WHERE userId = :userId")
    List<Item> getItemsByUserId(String userId);

    @Query("SELECT * FROM items WHERE userId = :userId AND hostelIndex = :hostelIndex LIMIT 1")
    Item getItemByUserIdAndHostelIndex(String userId, int hostelIndex);



    @Query("SELECT * FROM items")
    List<Item> getAllItems();
}
