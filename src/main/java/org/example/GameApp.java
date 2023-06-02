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

import java.awt.event.MouseEvent;
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
        List<String> playerNames = Arrays.asList("Arno", "Anthony");
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
        primaryStage.setTitle("6 Qui Prend!");
        primaryStage.show();

        new Thread(this::playGame).start();
    }

    private void playGame() {
        while (!game.isGameOver()) {
            Platform.runLater(this::updateGUI);
            for (Player player : game.getPlayers()) {
                chooseCard(player);
            }

            game.playRound();

            if (game.isGameOver()) {
                Player winner = game.determineWinner();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Partie Terminée !");
                    alert.setHeaderText("La Partie est Finie!");
                    alert.setContentText("Le gagnant est " + winner.getName() + " avec " + winner.getScore() + " points.");
                    alert.showAndWait();
                });
            }
        }
    }

    private void chooseCard(Player player) {
        latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Dialog<Card> dialog = new Dialog<>();
            dialog.setTitle("Choisissez une carte");
            dialog.setHeaderText("Joueur " + player.getName() + ", choisissez une carte à placer.");

            ButtonType playButtonType = new ButtonType("Play", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(playButtonType, ButtonType.CANCEL);
            dialog.getDialogPane().lookupButton(playButtonType).setVisible(false);
            dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

            HBox box = new HBox();
            box.setSpacing(10);

            for (Card card : player.getHand()) {
                Button button = new Button(Integer.toString(card.getNumber()));
                button.setOnAction(e -> handleCardChoice(card, dialog));
                box.getChildren().add(button);
                CardComponent cardComponent = new CardComponent(true);
                cardComponent.setCardValue(card);
                box.getChildren().add(cardComponent);
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

    private void handleCardChoice(Card card, Dialog<Card> dialog) {
        chosenCard = card;
        dialog.close();
        updateGUI();
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
