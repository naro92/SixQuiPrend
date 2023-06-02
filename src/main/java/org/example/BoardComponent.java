package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BoardComponent extends VBox {
    private List<HBox> rowComponents;

    public BoardComponent() {
        rowComponents = new ArrayList<>();
        setSpacing(10);
        for (int i = 0; i < 4; i++) {
            HBox rowComponent = new HBox();
            rowComponent.setAlignment(Pos.CENTER);
            rowComponent.setSpacing(10);
            rowComponent.setPadding(new Insets(10));
            rowComponents.add(rowComponent);
            getChildren().add(rowComponent);
        }
    }

    public void update(Game game) {
        List<List<Card>> rows = game.getBoard().getRows();
        for (int i = 0; i < rows.size(); i++) {
            List<Card> cards = rows.get(i);
            HBox rowComponent = rowComponents.get(i);
            rowComponent.getChildren().clear();

            for (Card card : cards) {
                CardComponent cardComponent = new CardComponent(false);
                cardComponent.setCardValue(card);
                rowComponent.getChildren().add(cardComponent);
            }
        }
    }
}
