package com.keshav.facerecognition;

public class MainModel {
    String Name, UserID, Gender, Designation, Address, Entry, Exit, Count;

    MainModel()
    {

    }

    public MainModel(String name, String userID, String gender, String designation, String address, String entry, String exit, String count ) {
        Name = name;
        UserID = userID;
        Gender = gender;
        Designation = designation;
        Address = address;
        Entry = entry;
        Exit = exit;
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    public String getEntry() {
        return Entry;
    }

    public void setEntry(String entry) { Entry = entry; }
    public String getExit() {
        return Exit;
    }

    public void setExit(String exit) {
        Exit = exit;
    }
    public String getCount() { return Count; }

    public void setCount(String count) {
        Count = count;
    }
}
