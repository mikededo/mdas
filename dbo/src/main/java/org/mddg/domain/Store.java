package org.mddg.domain;

public class Store {

    private final Integer id;

    public Store(Integer id) {
       this.id = id;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
