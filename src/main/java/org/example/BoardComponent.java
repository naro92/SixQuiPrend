package org.example;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BoardComponent extends VBox {
    private List<Label> rowLabels;

    public BoardComponent() {
        rowLabels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Label label = new Label();
            this.getChildren().add(label);
            rowLabels.add(label);
        }
    }

    public void update(Game game) {
        List<List<Card>> rows = game.getBoard().getRows();
        for (int i = 0; i < rows.size(); i++) {
            List<Card> row = rows.get(i);
            StringBuilder sb = new StringBuilder("Rangée " + (i + 1) + ": ");
            for (Card card : row) {
                sb.append(card.getNumber()).append(" ").append("(").append(card.getPoints()).append(" Points)").append(" ");
            }
            rowLabels.get(i).setText(sb.toString());
        }
    }
}