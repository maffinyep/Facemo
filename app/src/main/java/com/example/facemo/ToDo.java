package com.example.facemo;

import java.util.ArrayList;

public class ToDo {
    public ToDo(String testo) {
        this.testo = testo;
    }

    private String testo;
    private int expectedSbatti;
    private int actualSbatti;

    public int getActualSbatti() {
        return actualSbatti;
    }
    public void setActualSbatti(int actualSbatti) {
        this.actualSbatti = actualSbatti;
    }
    public String getTesto() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }
    public int getExpectedSbatti() {
        return expectedSbatti;
    }
    public void setExpectedSbatti(int expectedSbatti) {
        this.expectedSbatti = expectedSbatti;
    }

    public static ArrayList<ToDo> rawToBody(String raw){
        ArrayList<ToDo> body = new ArrayList<>();
        for(String i : raw.split("\\r?\\n"))
            body.add(new ToDo(i));
        return body;
    }

    public static String bodyToRaw(ArrayList<ToDo> body) {
        String raw = "";
        for(ToDo i : body){
            raw += "\n"+i.getTesto();
        }
        return raw.substring(1);
    }

}
