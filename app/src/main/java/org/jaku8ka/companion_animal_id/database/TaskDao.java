package org.jaku8ka.companion_animal_id.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM pet ORDER BY nameOfPet")
    LiveData<List<TaskEntry>> loadAllTasks();

    @Insert
    void insertTask(TaskEntry taskEntry);

    @Insert
    void insertTaskDate(TaskEntryDate taskEntryDate);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTaskDate(TaskEntryDate taskEntryDate);

    @Delete
    void deleteTask(TaskEntry taskEntry);

    @Delete
    void deleteTaskDate(TaskEntryDate taskEntryDate);

    @Query("SELECT * FROM pet WHERE id = :id")
    LiveData<TaskEntry> loadTaskById(int id);

    @Query("SELECT * FROM date WHERE dateId = :dateId")
    LiveData<TaskEntryDate> loadDateById(int dateId);

    @Query("SELECT * FROM date WHERE dateId = :dateId")
    List<TaskEntryDate> getDates(int dateId);
}
