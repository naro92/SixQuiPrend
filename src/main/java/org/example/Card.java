package org.example;
public class Card {
    private final int number;
    private final int points;

    public Card(int number, int points) {
        this.number = number;
        this.points = points;
    }

    public int getNumber() {
        return number;
    }

    public int getPoints() {
        return points;
    }
}