package com.konarktimes.konark.Model;

public class Languages {
    String name;
    String code;
    int id;

    public Languages(String name, String code, int id) {
        this.name = name;
        this.code = code;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return name;
    }
}
