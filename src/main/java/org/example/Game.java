package org.example;
import java.util.ArrayList;
import java.util.Comparator;
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
        setupBoard();
        dealCards();
    }

    public void playRound() {
        List<Player> orderedPlayers = determineOrder();

        for (Player player : orderedPlayers) {
            player.addScore(board.addCard(player.getCardToPlay()));
        }

        if (players.get(0).getHand().isEmpty()) {
            if (deck.isEmpty()) {
                gameOver = true;
            } else {
                dealCards();
            }
        }
    }

    private void dealCards() {
        for (Player player : players) {
            while (player.getHand().size() < 10 && !deck.isEmpty()) {
                player.draw(deck);
            }
        }
    }

    public Player determineWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() < winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    private List<Player> determineOrder() {
        List<Player> orderedList = new ArrayList<>(players);
        orderedList.sort(Comparator.comparingInt(Player::getCardToPlayValue));

        return orderedList;
    }

    private void setupBoard() {
        for (List<Card> row : board.getRows()) {
            row.add(deck.drawCard());
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}