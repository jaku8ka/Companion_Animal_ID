package org.jaku8ka.companion_animal_id.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "pet")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameOfPet;
    private String petType;
    private String dateOfBirth;
    private String sex;
    private String species;
    private String colorOfPet;

    @Ignore
    public TaskEntry(String nameOfPet, String petType, String dateOfBirth, String sex, String species, String colorOfPet) {
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
    }

    public TaskEntry(int id, String nameOfPet, String petType, String dateOfBirth, String sex, String species, String colorOfPet) {
        this.id = id;
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
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

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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
}
