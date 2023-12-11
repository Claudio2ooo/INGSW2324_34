package it.unina.dietideals24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table
public class DietiUser {

    private String name;
    private String surname;
    @Id
    private String email;
    private String password;
    private List<String> links;
    private String geographicalArea;

    public DietiUser(){};

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getLinks() {
        return links;
    }

    public String getGeographicalArea() {
        return geographicalArea;
    }

    public DietiUser(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString(){
        return "DietiUser{" +
                "name="+'\''+name+'\''+
                ", surname="+'\''+surname+'\''+
                ", email="+'\''+email+'\''+
                '}';
    }
}
