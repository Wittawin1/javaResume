package domain;

import java.io.Serializable;

public class Patient implements Serializable {
    private String id;
    private String name;
    private int age;
    private String address;

    public Patient(String id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public void updateDetails(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient : " + "id : " + id + " , " + "name : " + name + " , "
                + "age : " + age + " , " + "address : " + address;
    }
}
