package sample;

public class Student {
    private int id;
    private String name;
    private  String surename;
    private  String email;
    private  int classID;

    public Student(int id, String name, String surename, String email, int classID) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.email = email;
        this.classID = classID;

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

    public int getClassID() {return classID;}

    public void setClassID(int classID) {this.classID = classID;}
}
