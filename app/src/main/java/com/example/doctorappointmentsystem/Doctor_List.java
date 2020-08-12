package com.example.doctorappointmentsystem;

public class Doctor_List {
    public String name, specilist_in, image, uid;

    public Doctor_List() {

    }

    public Doctor_List(String name, String specilist_in, String image) {
        this.name = name;
        this.specilist_in = specilist_in;
        this.image = image;
    }

    public String getDoctor_name() {
        return name;
    }

    public void setDoctor_name(String name) {
        this.name = name;
    }

    public String getProfesion() {
        return specilist_in;
    }

    public void setProfesion(String status) {
        this.specilist_in = specilist_in;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() { return uid;}

    public void setUid(String uid) {
        this.uid = uid;
    }
}
