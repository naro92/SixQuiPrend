package org.example;
import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> hand;

    private int score;
    private Card cardToPlay;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public void draw(Deck deck) {
        Card drawnCard = deck.drawCard();
        hand.add(drawnCard);
    }

    public void chooseCard(Card card) {
        cardToPlay = hand.remove(hand.indexOf(card));
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getCardToPlay() {
        return cardToPlay;
    }

    public int getCardToPlayValue() { return cardToPlay.getNumber(); }

    public int getScore() {
        return score;
    }
}