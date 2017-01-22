package org.draggable.example;
import java.io.Serializable;

public class Fruit implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "";

    public Fruit(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
