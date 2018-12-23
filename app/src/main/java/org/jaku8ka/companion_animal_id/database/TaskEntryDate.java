package org.jaku8ka.companion_animal_id.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@ForeignKey(entity = TaskEntry.class, parentColumns = "id", childColumns = "dateId", onDelete = CASCADE)
@Entity(tableName = "date")
public class TaskEntryDate {
    @PrimaryKey
    private int id;
    private Date vaccine;
    private Date parasite;
    private long dateId;

    public TaskEntryDate(int id, Date vaccine, Date parasite, long dateId) {
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

    public long getDateId() {
        return dateId;
    }

    public void setDateId(long dateId) {
        this.dateId = dateId;
    }
}
