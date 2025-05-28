package com.example.holleworld.DATA.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PeopleDao {

    @Insert
    long insert(People people);

    @Insert
    long[] inserts(List<People> people);

    @Query("SELECT * FROM peoples")
    List<People> getAllPeoples();

    @Query("DELETE FROM PEOPLES")
    void clearTable();

    @Update
    void updataPeople(People people);

    @Delete
    void deletePeople(People people);
}
