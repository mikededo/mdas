package org.mddg.domain;

import java.util.Date;

public class User {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Date createdDate;

    public User(
        String firstName,
        String lastName,
        String email,
        Date createdDate
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return firstName +
            " " +
            lastName +
            " (" +
            (email == null ? "no-email" : email) +
            ")" +
            " [" +
            createdDate.toString() +
            "]";
    }
}
