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
    private String fur;
    private String differences;
    private String chipDate;
    private String chipNumber;

    @Ignore
    public TaskEntry(String nameOfPet, String petType, String dateOfBirth, String sex, String species, String colorOfPet,
                     String fur, String differences, String chipDate, String chipNumber) {
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
        this.fur = fur;
        this.differences = differences;
        this.chipDate = chipDate;
        this.chipNumber = chipNumber;
    }

    public TaskEntry(int id, String nameOfPet, String petType, String dateOfBirth, String sex, String species, String colorOfPet,
                     String fur, String differences, String chipDate, String chipNumber) {
        this.id = id;
        this.nameOfPet = nameOfPet;
        this.petType = petType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.species = species;
        this.colorOfPet = colorOfPet;
        this.fur = fur;
        this.differences = differences;
        this.chipDate = chipDate;
        this.chipNumber = chipNumber;
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

    public String getFur() {
        return fur;
    }

    public void setFur(String fur) {
        this.fur = fur;
    }

    public String getDifferences() {
        return differences;
    }

    public void setDifferences(String differences) {
        this.differences = differences;
    }

    public String getChipDate() {
        return chipDate;
    }

    public void setChipDate(String chipDate) {
        this.chipDate = chipDate;
    }

    public String getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }
}
