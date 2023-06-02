package org.example;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlayerComponent extends HBox {
    private Player player;
    private Label nameLabel;
    private Label scoreLabel;

    public PlayerComponent(Player player) {
        this.player = player;
        this.nameLabel = new Label();
        this.scoreLabel = new Label();

        this.getChildren().addAll(nameLabel, scoreLabel);
    }

    public void update(Game game) {
        nameLabel.setText(player.getName());
        scoreLabel.setText(" Score : " + player.getScore());
    }
}