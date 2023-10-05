package com.example.roomdbproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    void insertHistory(History history);

    @Update
    void updateHistory(History history);

    @Delete
    void deleteHistory(History history);

    @Query("SELECT * FROM history WHERE userId=:id")
    List<History> getHistoryAtUser(int id);

    @Query("SELECT * FROM history")
    List<History> getAllHistory();
}
