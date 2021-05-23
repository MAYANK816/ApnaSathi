package com.example.apnasathi;

public class queryModel {
    String Name,Number,Query;

    public queryModel(String name, String number, String query) {
        Name = name;
        Number = number;
        Query = query;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }
}
