package org.example;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for(int i=1; i<=104; i++) {
            int points = 1 + ((i%5 == 0) ? 1 : 0) + (2 * ((i%10 == 0) ? 1 : 0)); // Chaque carte a au minimum 1 point,+ 1 pour les multiples de 5, +2 pour les multiples de 10, +4 pour les nombres à chiffres identiques
            if (i > 10) {
                points += (4 * (String.valueOf(i).substring(0,1).equals(String.valueOf(i).substring(1)) ? 1 : 0));
            }
            cards.add(new Card(i, points));
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(cards.size()-1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}