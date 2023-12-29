package com.keshav.facerecognition;

public class LogModel {
    String Name, Entry, Exit, Count;

    LogModel()
    {

    }

    public LogModel(String name, String entry, String exit, String count ) {
        Name = name;
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
