package sample;

public class Teacher {
    private int id;
    private String name;
    private  String surename;
    private  String email;

    public Teacher(int id, String name, String surename, String email) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.email = email;
    }


    public String toString(){return name + " " + surename;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSurename() {return surename;}

    public void setSurename(String surename) {this.surename = surename;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
