package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GameApp extends Application {

    private Game game;
    private CountDownLatch latch;
    private Card chosenCard;
    BorderPane root;
    VBox playerComponents;
    BoardComponent boardComponent;

    @Override
    public void start(Stage primaryStage) {
        // Create your game here
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        game = new Game(playerNames);

        // Create your GUI components here and add them to the root
        root = new BorderPane();

        boardComponent = new BoardComponent();
        root.setCenter(boardComponent);

        playerComponents = new VBox();
        for (Player player : game.getPlayers()) {
            PlayerComponent playerComponent = new PlayerComponent(player);
            playerComponents.getChildren().add(playerComponent);
        }
        root.setTop(playerComponents);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("6 nimmt!");
        primaryStage.show();

        new Thread(this::playGame).start();
    }

    private void playGame() {
        while (!game.isGameOver()) {
            for (Player player : game.getPlayers()) {
                chooseCard(player);
            }

            Platform.runLater(this::updateGUI);



            if (game.isGameOver()) {
                Player winner = game.determineWinner();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("The game is over!");
                    alert.setContentText("The winner is " + winner.getName() + " with " + winner.getScore() + " points.");
                    alert.showAndWait();
                });
            }
        }
    }

    private void chooseCard(Player player) {
        latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Dialog<Card> dialog = new Dialog<>();
            dialog.setTitle("Choose a card");
            dialog.setHeaderText("Player " + player.getName() + ", choose a card to play");

            ButtonType playButtonType = new ButtonType("Play", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(playButtonType, ButtonType.CANCEL);

            HBox box = new HBox();
            box.setSpacing(10);

            for (Card card : player.getHand()) {
                Button button = new Button(card.toString());
                button.setOnAction(e -> handleCardChoice(card));
                box.getChildren().add(button);
            }

            dialog.getDialogPane().setContent(box);

            dialog.showAndWait();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        player.chooseCard(chosenCard);
    }

    private void handleCardChoice(Card card) {
        chosenCard = card;
        latch.countDown();
    }

    private void updateGUI() {
        boardComponent.update(game);
        for (Node node : playerComponents.getChildren()) {
            ((PlayerComponent)node).update(game);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
