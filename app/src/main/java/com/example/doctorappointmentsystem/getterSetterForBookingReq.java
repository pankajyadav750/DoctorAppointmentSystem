package com.example.doctorappointmentsystem;

public class getterSetterForBookingReq
{
    public String name,shedule,image;

    public getterSetterForBookingReq()
    {

    }

    public getterSetterForBookingReq(String name, String shedule, String image) {
        this.name = name;
        this.shedule = shedule;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShedule() {
        return shedule;
    }

    public void setShedule(String shedule) {
        this.shedule = shedule;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
