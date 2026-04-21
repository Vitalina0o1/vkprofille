package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class VKProfileController {

    @FXML
    private Button addFriendButton;
    @FXML
    private HBox friendsFlow;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView coverImage;
    @FXML
    private Button uploadPhotoBtn;
    @FXML
    private HBox photosFlow;
    @FXML
    private Button themeButton;
    @FXML
    private BorderPane rootPane;
    @FXML
    private HBox profileHeader;
    @FXML
    private VBox leftColumn;
    @FXML
    private VBox rightColumn;
    @FXML
    private VBox photosCard;
    @FXML
    private VBox mutualFriendsCard;
    @FXML
    private VBox friendsCard;
    @FXML
    private StackPane coverContainer;
    @FXML
    private StackPane avatarContainer;
    @FXML
    private Label nameLabel;
    @FXML
    private Button editNameBtn;

    private boolean darkTheme = false;

    private Button changeCoverBtn;
    private Button changeAvatarBtn;

    private List<ImageView> uploadedPhotos = new ArrayList<>();

    private static final String VK_DARK_BG = "#141414";
    private static final String VK_DARK_CONTENT = "#222222";
    private static final String VK_DARK_SECONDARY = "#333333";
    private static final String VK_DARK_BORDER = "#3c3c3c";
    private static final String VK_DARK_TEXT_PRIMARY = "#e1e3e6";
    private static final String VK_DARK_TEXT_SECONDARY = "#a1a5aa";

    @FXML
    public void initialize() {
        avatar.setImage(new Image("https://floors.kz/wp-content/uploads/2022/05/no-name.png"));
        coverImage.setImage(new Image("https://via.placeholder.com/900x160/C4C9D1/FFFFFF?text=Обложка"));


        addFriendButton.setOnAction(e -> {
        });
        changeCoverBtn = createCoverButton();
        if (coverContainer != null) {
            coverContainer.getChildren().add(changeCoverBtn);
            StackPane.setAlignment(changeCoverBtn, Pos.TOP_RIGHT);

            coverContainer.setOnMouseEntered(e -> changeCoverBtn.setVisible(true));
            coverContainer.setOnMouseExited(e -> changeCoverBtn.setVisible(false));
            changeCoverBtn.setVisible(false);
        }

        changeAvatarBtn = createAvatarButton();
        if (avatarContainer != null) {
            avatarContainer.getChildren().add(changeAvatarBtn);
            StackPane.setAlignment(changeAvatarBtn, Pos.BOTTOM_RIGHT);

            avatarContainer.setOnMouseEntered(e -> changeAvatarBtn.setVisible(true));
            avatarContainer.setOnMouseExited(e -> changeAvatarBtn.setVisible(false));
            changeAvatarBtn.setVisible(false);
        }

        friendsFlow.getChildren().addAll(
                createFriend("Александр", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeiA1kdvFeN5RdmFzMry7msSZYxYnAGzKxEw&s"),
                createFriend("Владилен", "data:image/jpeg;base64,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"),
                createFriend("Макс", "data:image/jpeg;base64,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"),
                createFriend("Екатерина", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRp4tJ0abY9FIIx3BB5mMnpQH3KL67l8mIQcw&s"),
                createFriend("Дарья", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTUoL9TgvqJT_aiFdsXHMvg6eydq_mtvqWTw&s")
        );

        changeCoverBtn.setOnAction(e -> loadImage(coverImage, "Выберите обложку"));
        changeAvatarBtn.setOnAction(e -> loadImage(avatar, "Выберите аватар"));
        uploadPhotoBtn.setOnAction(e -> uploadPhoto());

        editNameBtn.setOnAction(e -> editName());

        themeButton.setOnAction(e -> toggleTheme());
    }

    private Button createCoverButton() {
        Button btn = new Button("Сменить обложку");
        btn.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 4; -fx-cursor: hand;");
        return btn;
    }

    private Button createAvatarButton() {
        Button btn = new Button("Сменить");
        btn.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 4; -fx-cursor: hand;");
        return btn;
    }

    private void editName() {
        TextInputDialog dialog = new TextInputDialog(nameLabel.getText());
        dialog.setTitle("Редактирование имени");
        dialog.setHeaderText("Изменить имя и фамилию");
        dialog.setContentText("Введите новое имя:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            if (!newName.trim().isEmpty()) {
                nameLabel.setText(newName.trim());
            }
        });
    }

    private void loadImage(ImageView imageView, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    private void uploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите фото");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if (files != null) {
            for (File file : files) {
                Image image = new Image(file.toURI().toString());
                ImageView photoView = new ImageView(image);
                photoView.setFitWidth(100);
                photoView.setFitHeight(100);
                photoView.setPreserveRatio(true);
                photosFlow.getChildren().add(photoView);
                uploadedPhotos.add(photoView);
            }
        }
    }

    private void toggleTheme() {
        darkTheme = !darkTheme;
        if (darkTheme) {
            applyDarkTheme();
            themeButton.setText("☀ Светлая тема");
            themeButton.setStyle("-fx-background-color: #e1e3e6; -fx-text-fill: #141414; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        } else {
            applyLightTheme();
            themeButton.setText("🌙 Тёмная тема");
            themeButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        }
    }

    private void applyDarkTheme() {
        rootPane.setStyle("-fx-background-color: " + VK_DARK_BG + ";");
        HBox contentBox = (HBox) rootPane.getCenter();
        if (contentBox != null) contentBox.setStyle("-fx-background-color: " + VK_DARK_BG + ";");

        if (profileHeader != null) {
            profileHeader.setStyle("-fx-background-color: " + VK_DARK_CONTENT + "; -fx-border-color: " + VK_DARK_BORDER + "; -fx-border-width: 0 0 1 0;");
        }

        String cardStyle = "-fx-background-color: " + VK_DARK_CONTENT + "; -fx-border-color: " + VK_DARK_BORDER + "; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 12; -fx-spacing: 8;";
        applyCardStyle(photosCard, cardStyle);
        applyCardStyle(mutualFriendsCard, cardStyle);
        applyCardStyle(friendsCard, cardStyle);


        Node themeCard = themeButton.getParent();
        if (themeCard instanceof VBox) {
            VBox card = (VBox) themeCard;
            card.setStyle(
                    "-fx-background-color: " + VK_DARK_CONTENT + ";" +
                            "-fx-border-color: " + VK_DARK_BORDER + ";" +
                            "-fx-border-radius: 4;" +
                            "-fx-background-radius: 4;" +
                            "-fx-padding: 12;" +
                            "-fx-spacing: 8;"
            );

            for (Node node : card.getChildren()) {
                if (node instanceof Label && "Оформление".equals(((Label) node).getText())) {
                    ((Label) node).setTextFill(Color.web(VK_DARK_TEXT_PRIMARY));
                    break;
                }
            }
        }

        setTextColorForCard(photosCard, Color.web(VK_DARK_TEXT_PRIMARY), Color.web(VK_DARK_TEXT_SECONDARY));
        setTextColorForCard(mutualFriendsCard, Color.web(VK_DARK_TEXT_PRIMARY), Color.web(VK_DARK_TEXT_SECONDARY));
        setTextColorForCard(friendsCard, Color.web(VK_DARK_TEXT_PRIMARY), Color.web(VK_DARK_TEXT_SECONDARY));

        if (profileHeader != null) {
            for (Node node : profileHeader.getChildren()) {
                if (node instanceof VBox) {
                    VBox infoBox = (VBox) node;
                    for (Node child : infoBox.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            if (label == nameLabel) {
                                label.setTextFill(Color.BLACK);
                            } else {
                                label.setTextFill(Color.web(VK_DARK_TEXT_SECONDARY));
                            }
                        } else if (child instanceof HBox) {
                            for (Node labelNode : ((HBox) child).getChildren()) {
                                if (labelNode instanceof Label) {
                                    ((Label) labelNode).setTextFill(Color.web(VK_DARK_TEXT_SECONDARY));
                                }
                            }
                        }
                    }
                }
            }
        }


        Node top = rootPane.getTop();
        if (top instanceof VBox) {
            VBox topVBox = (VBox) top;
            for (Node node : topVBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox buttonsBox = null;
                    for (Node child : ((HBox) node).getChildren()) {
                        if (child instanceof HBox && ((HBox) child).getChildren().size() > 0 && ((HBox) child).getChildren().get(0) instanceof Button) {
                            buttonsBox = (HBox) child;
                            break;
                        }
                    }
                    if (buttonsBox != null) {
                        for (Node btn : buttonsBox.getChildren()) {
                            if (btn instanceof Button && btn != addFriendButton) {
                                ((Button) btn).setStyle("-fx-background-color: " + VK_DARK_SECONDARY + "; -fx-text-fill: " + VK_DARK_TEXT_PRIMARY + "; -fx-padding: 8 16; -fx-background-radius: 4;");
                            }
                        }
                    }
                }
            }
        }

        for (Node friend : friendsFlow.getChildren()) {
            if (friend instanceof VBox) {
                for (Node child : ((VBox) friend).getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setTextFill(Color.web(VK_DARK_TEXT_SECONDARY));
                    }
                }
            }
        }


        editNameBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + VK_DARK_TEXT_SECONDARY + "; -fx-font-size: 16px;");
    }

    private void applyLightTheme() {
        rootPane.setStyle("-fx-background-color: #EDEEF0;");
        HBox contentBox = (HBox) rootPane.getCenter();
        if (contentBox != null) contentBox.setStyle("-fx-background-color: #EDEEF0;");

        if (profileHeader != null) {
            profileHeader.setStyle("-fx-background-color: white; -fx-border-color: #D3D9E0; -fx-border-width: 0 0 1 0;");
        }

        String cardStyle = "-fx-background-color: white; -fx-border-color: #D3D9E0; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 12; -fx-spacing: 8;";
        applyCardStyle(photosCard, cardStyle);
        applyCardStyle(mutualFriendsCard, cardStyle);
        applyCardStyle(friendsCard, cardStyle);

        Node themeCard = themeButton.getParent();
        if (themeCard instanceof VBox) {
            VBox card = (VBox) themeCard;
            card.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-border-color: #D3D9E0;" +
                            "-fx-border-radius: 4;" +
                            "-fx-background-radius: 4;" +
                            "-fx-padding: 12;" +
                            "-fx-spacing: 8;"
            );
            for (Node node : card.getChildren()) {
                if (node instanceof Label && "Оформление".equals(((Label) node).getText())) {
                    ((Label) node).setTextFill(Color.BLACK);
                    break;
                }
            }
        }

        setTextColorForCard(photosCard, Color.BLACK, Color.GRAY);
        setTextColorForCard(mutualFriendsCard, Color.BLACK, Color.GRAY);
        setTextColorForCard(friendsCard, Color.BLACK, Color.GRAY);

        if (profileHeader != null) {
            for (Node node : profileHeader.getChildren()) {
                if (node instanceof VBox) {
                    VBox infoBox = (VBox) node;
                    for (Node child : infoBox.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            if (label == nameLabel) {
                                label.setTextFill(Color.BLACK);
                            } else {
                                label.setTextFill(Color.GRAY);
                            }
                        } else if (child instanceof HBox) {
                            for (Node labelNode : ((HBox) child).getChildren()) {
                                if (labelNode instanceof Label) {
                                    ((Label) labelNode).setTextFill(Color.GRAY);
                                }
                            }
                        }
                    }
                }
            }
        }

        Node top = rootPane.getTop();
        if (top instanceof VBox) {
            VBox topVBox = (VBox) top;
            for (Node node : topVBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox buttonsBox = null;
                    for (Node child : ((HBox) node).getChildren()) {
                        if (child instanceof HBox && ((HBox) child).getChildren().size() > 0 && ((HBox) child).getChildren().get(0) instanceof Button) {
                            buttonsBox = (HBox) child;
                            break;
                        }
                    }
                    if (buttonsBox != null) {
                        for (Node btn : buttonsBox.getChildren()) {
                            if (btn instanceof Button && btn != addFriendButton) {
                                ((Button) btn).setStyle("-fx-background-color: #F0F2F5; -fx-text-fill: #2B5876; -fx-padding: 8 16; -fx-background-radius: 4;");
                            }
                        }
                    }
                }
            }
        }

        for (Node friend : friendsFlow.getChildren()) {
            if (friend instanceof VBox) {
                for (Node child : ((VBox) friend).getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setTextFill(Color.BLACK);
                    }
                }
            }
        }

        editNameBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4682B4; -fx-font-size: 16px;");
    }

    private void applyCardStyle(VBox card, String style) {
        if (card != null) {
            card.setStyle(style);
        }
    }

    private void setTextColorForCard(VBox card, Color titleColor, Color subtextColor) {
        if (card == null) return;
        for (Node node : card.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String text = label.getText();
                if (text.equals("Фотографии") || text.equals("Общие друзья 3") ||
                        text.equals("Друзья 105") || text.equals("Оформление")) {
                    label.setTextFill(titleColor);
                } else {
                    label.setTextFill(subtextColor);
                }
            }
        }
    }

    private VBox createFriend(String name, String imageUrl) {
        VBox item = new VBox(5);
        item.setAlignment(Pos.CENTER);
        ImageView av = new ImageView(imageUrl);
        av.setFitWidth(50);
        av.setFitHeight(50);
        Circle clip = new Circle(25, 25, 25);
        av.setClip(clip);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 11px;");
        item.getChildren().addAll(av, nameLabel);
        return item;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}