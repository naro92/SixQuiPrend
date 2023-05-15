package org.example;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Card>> rows;

    public Board() {
        rows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rows.add(new ArrayList<>());
        }
    }

    public boolean addCard(Card card) {
        for (List<Card> row : rows) {
            if (row.isEmpty() || card.getNumber() > row.get(row.size() - 1).getNumber()) {
                row.add(card);
                if (row.size() > 5) {
                    removeCards(row);
                }
                return true;
            }
        }

        // Si aucune rangée ne peut recevoir la carte, elle est placée dans la rangée avec le total de points le plus bas
        // et cette rangée est vidée
        List<Card> rowToReplace = rows.get(0);
        int minPoints = getTotalPoints(rowToReplace);
        for (List<Card> row : rows) {
            int points = getTotalPoints(row);
            if (points < minPoints) {
                minPoints = points;
                rowToReplace = row;
            }
        }
        rowToReplace.clear();
        rowToReplace.add(card);
        return false;
    }

    private void removeCards(List<Card> row) {
        // Ici, vous pouvez gérer la suppression des cartes d'une rangée qui a plus de 5 cartes.
        // Par exemple, vous pouvez ajouter les points des cartes supprimées au score du joueur qui a posé la carte.
    }

    private int getTotalPoints(List<Card> row) {
        int total = 0;
        for (Card card : row) {
            total += card.getPoints();
        }
        return total;
    }
}