package edu.neu.numad21su_osmansubasi;

public class Movie {

    private final String name;
    private final String date;

    public Movie(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
