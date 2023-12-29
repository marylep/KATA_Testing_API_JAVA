package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Pet {
    private int id;
    private String name;
    private String birthDate;
    private Type type;
    private PetsOwner owner;
    @JsonIgnore
    private ArrayList<Visit> visits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
    }

    public PetsOwner getOwner() {
        return owner;
    }

    public void setOwner(PetsOwner owner) {
        this.owner = owner;
    }

}
