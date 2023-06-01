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

    private List<Card> chooseRow(Card card) {
        int chosenRowIndex = 4;
        int minOffset = 0;

        for (List<Card> row : rows) {
            if (card.getNumber() > row.get(row.size() - 1).getNumber()) {
                int offset = card.getNumber() - row.get(row.size() - 1).getNumber();
                if (minOffset == 0 || offset < minOffset) {
                    minOffset = offset;
                    chosenRowIndex = rows.indexOf(row);
                }
            }
        }

        return rows.get(chosenRowIndex);
    }

    public int addCard(Card card) {
        int pointsTaken = 0;
        try {
            List<Card> chosenRow = chooseRow(card);
            if (chosenRow.size() < 5) {
                chosenRow.add(card);
            } else {
                // Si la rangée choisie possède 5 cartes, le joueur récupère les points de la rangée
                // et cette rangée est vidée
                pointsTaken = getTotalPoints(chosenRow);
                chosenRow.clear();
                chosenRow.add(card);
            }

        } catch(Exception e) {
            // Si aucune rangée ne peut recevoir la carte, elle est placée dans la rangée avec le total de points le plus bas
            // et cette rangée est vidée
            List<Card> rowToReplace = getLeastPointRow();
            pointsTaken = getTotalPoints(rowToReplace);
            rowToReplace.clear();
            rowToReplace.add(card);
        }

        return pointsTaken;
    }

    public List<List<Card>> getRows() {
        return rows;
    }

    private int getTotalPoints(List<Card> row) {
        int total = 0;
        for (Card card : row) {
            total += card.getPoints();
        }
        return total;
    }

    private List<Card> getLeastPointRow() {
        int returnedRowIndex = 0;
        int minPoints = 0;

        for (List<Card> row : rows) {
            int rowPoints = getTotalPoints(row);
            if (minPoints == 0 || rowPoints < minPoints) {
                minPoints = rowPoints;
                returnedRowIndex = rows.indexOf(row);
            }
        }

        return rows.get(returnedRowIndex);
    }
}