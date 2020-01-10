package sample;

public class Klassen {
    private int id;
    private  String classname;

    public Klassen(int id, String classname) {
        this.id = id;
        this.classname = classname;
    }
    public String toString(){return classname;}

    public int getId() {
        return id;
    }

    public String getClassname() {
        return classname;
    }
}
