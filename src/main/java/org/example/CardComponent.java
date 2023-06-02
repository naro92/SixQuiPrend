package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CardComponent extends StackPane {
    private ImageView imageView;
    private boolean clickable;
    private Card card;

    private Runnable onClickCallback;

    public CardComponent(boolean clickable) {
        imageView = new ImageView();
        getChildren().add(imageView);
        setClickable(clickable);
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setCardValue(Card card) {
        this.card = card;
        String imagePath = getImagePath(card.getNumber()); // Get the image path based on the card value
        Image cardImage = new Image(imagePath);
        imageView.setImage(cardImage);
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        if (clickable) {
            setOnMouseClicked(this::handleMouseClick);
        } else {
            setOnMouseClicked(null);
        }
    }

    public void setOnClickCallback(Runnable callback) {
        onClickCallback = callback;
    }

    private void handleMouseClick(MouseEvent event) {
        // Handle the click event here
        if (onClickCallback != null) {
            onClickCallback.run();
        }
    }

    private String getImagePath(int value) {
        return "C:\\Users\\tenso\\Desktop\\Ingé\\ISEP\\2022-2023\\Java\\SixQuiPrend\\image\\" + value + ".png";
    }
}