package org.jaku8ka.companion_animal_id.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "date")
public class TaskEntryDate {
    private int id;
    private Date vaccine;
    private Date parasite;

    @ForeignKey(entity = TaskEntry.class, parentColumns = "id", childColumns = "dateId", onDelete = CASCADE)
    @PrimaryKey(autoGenerate = true)
    private int dateId;

    public TaskEntryDate(int id, Date vaccine, Date parasite, int dateId) {
        this.id = id;
        this.vaccine = vaccine;
        this.parasite = parasite;
        this.dateId = dateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVaccine() {
        return vaccine;
    }

    public void setVaccine(Date vaccine) {
        this.vaccine = vaccine;
    }

    public Date getParasite() {
        return parasite;
    }

    public void setParasite(Date parasite) {
        this.parasite = parasite;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }
}
