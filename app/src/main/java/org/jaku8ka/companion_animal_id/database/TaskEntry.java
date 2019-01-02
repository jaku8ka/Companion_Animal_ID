package org.jaku8ka.companion_animal_id.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "pet")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameOfPet;
    private int petType;
    private String dateOfBirth;
    private int sex;
    private String species;
    private String colorOfPet;


    private String dateOfOdc;
    private String dateOfVac;
    private int nextOdc;
    private int nextVac;

    @Ignore
    public TaskEntry(String nameOfPet, int petType, String dateOfBirth, int sex, String species, String colorOfPet, String dateOfOdc, String dateOfVac, int nextOdc, int nextVac) {
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
        this.dateOfOdc = dateOfOdc;
        this.dateOfVac = dateOfVac;
        this.nextOdc = nextOdc;
        this.nextVac = nextVac;
    }

    public TaskEntry(int id, String nameOfPet, int petType, String dateOfBirth, int sex, String species, String colorOfPet, String dateOfOdc, String dateOfVac, int nextOdc, int nextVac) {
        this.id = id;
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
        this.dateOfOdc = dateOfOdc;
        this.dateOfVac = dateOfVac;
        this.nextOdc = nextOdc;
        this.nextVac = nextVac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfPet() {
        return nameOfPet;
    }

    public void setNameOfPet(String nameOfPet) {
        this.nameOfPet = nameOfPet;
    }

    public int getPetType() {
        return petType;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getColorOfPet() {
        return colorOfPet;
    }

    public void setColorOfPet(String colorOfPet) {
        this.colorOfPet = colorOfPet;
    }

    public String getDateOfOdc() {
        return dateOfOdc;
    }

    public void setDateOfOdc(String dateOfOdc) {
        this.dateOfOdc = dateOfOdc;
    }

    public String getDateOfVac() {
        return dateOfVac;
    }

    public void setDateOfVac(String dateOfVac) {
        this.dateOfVac = dateOfVac;
    }

    public int getNextOdc() {
        return nextOdc;
    }

    public void setNextOdc(int nextOdc) {
        this.nextOdc = nextOdc;
    }

    public int getNextVac() {
        return nextVac;
    }

    public void setNextVac(int nextVac) {
        this.nextVac = nextVac;
    }
}

