package org.example;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for(int i=1; i<=104; i++) {
            int points = 1 + i%5; // Chaque carte a au minimum 1 point, et une carte supplémentaire tous les multiples de 5.
            cards.add(new Card(i, points));
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if(cards.isEmpty()) {
            return null; // ou vous pouvez gérer cette situation autrement si vous le souhaitez.
        }
        return cards.remove(cards.size()-1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}