package org.mddg.domain;

public class Actor {

    private final String firstName;
    private final String lastName;
    private final Integer movieCount;

    public Actor(String firstName, String lastName, Integer movieCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movieCount = movieCount;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getMovieCount() {
        return "(" + movieCount.toString() + ")";
    }
}
