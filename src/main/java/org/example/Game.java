package org.example;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private final Board board;
    private final Deck deck;
    private boolean gameOver;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        board = new Board();
        deck = new Deck();
        dealCards();
    }

    public void playRound() {
        if (gameOver) {
            return;
        }
        for (Player player : players) {
            Card card = player.playCard(0); // Ici, vous pouvez implémenter une logique pour permettre aux joueurs de choisir la carte qu'ils veulent jouer.
            boolean success = board.addCard(card);
            if (!success) {
                player.addScore(card.getPoints());
            }
        }
        if (deck.isEmpty() && players.get(0).getHand().isEmpty()) {
            gameOver = true;
            Player winner = determineWinner();
            System.out.println("Le gagnant est " + winner.getName() + " avec " + winner.getScore() + " points.");
        } else {
            dealCards();
        }
    }

    private void dealCards() {
        for (Player player : players) {
            while (player.getHand().size() < 10 && !deck.isEmpty()) {
                player.draw(deck);
            }
        }
    }

    private Player determineWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() < winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }
}