package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FrameType;
import model.GameManager;
import model.TableBoard;
import model.Tile;

public class LaticeController {

    public static final int SIDE_TILE_SIZE = 75;
    @FXML
    public Label nbPtsLeft;
    @FXML
    public Label nbPtsRight;
    @FXML
    public Label currentPlayer;
    @FXML
    private GridPane rackLeft;

    @FXML
    private GridPane rackRight;

    @FXML
    private GridPane board;


    @FXML
    private Label nbTileLeftLeft;


    @FXML
    private Label nbTilesLeftRight;

    private void setRack(int player, Tile[] rack) {
        GridPane pane = player == 0 ? rackLeft : rackRight;

        pane.getChildren().clear();
        for (int i = 0; i < rack.length; i++) {
            if (rack[i] == null) {
                continue;
            }
            String url = "/images/" + rack[i].getImageName();

            ImageView image = new ImageView(new Image(url, SIDE_TILE_SIZE, SIDE_TILE_SIZE, true, true));
            image.setFitHeight(SIDE_TILE_SIZE);
            image.setFitWidth(SIDE_TILE_SIZE);

            actualizeNbPoints();
            actualizeNbTilesLeft();
            LaticeDnd.manageSourceDragAndDrop(image, i, player);
            pane.add(image, 0, i);
        }
    }

    private void actualizeNbPoints() {
        GameManager gameManager = GameManager.getInstance();
        nbPtsLeft.setText("Nb points : " + gameManager.getPlayer(0).getPoints());
        nbPtsRight.setText("Nb points : " + gameManager.getPlayer(1).getPoints());
    }

    public void actualizeNbTilesLeft() {
        GameManager manager = GameManager.getInstance();
        for (int i = 0; i < 2; i++) {
            Label label = i == 0 ? nbTileLeftLeft : nbTilesLeftRight;
            label.setText("Tiles left : " + manager.getPlayer(i).getTileLeftInStack());
        }
    }

    public void actualizeRacks() {
        for (int player = 0; player < 2; player++) {
            Tile[] rack = GameManager.getInstance().getPlayer(player).getRack();
            setRack(player, rack);
        }
    }

    @FXML
    private void initialize() {
        setRack(0, GameManager.getInstance().getPlayer(0).getRack());
        setRack(1, GameManager.getInstance().getPlayer(1).getRack());


        TableBoard gameBoard = TableBoard.getInstance();
        board.getChildren().clear();
        ImageView image = new ImageView();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                FrameType type = gameBoard.getFrameType(i, j);
                switch (type) {
                    case None:
                        image = new ImageView("/images/bg_sea.png");
                        break;
                    case Sun:
                        image = new ImageView("/images/bg_sun.png");
                        break;
                    case Moon:
                        image = new ImageView("/images/bg_moon.png");
                        break;
                }

                image.setFitHeight(SIDE_TILE_SIZE);
                image.setFitWidth(SIDE_TILE_SIZE);
                LaticeDnd.manageTargetDragAndDrop(image, i, j, this);
                board.add(image, i, j);
            }

        }
        currentPlayer.setText("Player Left is playing");
        rackRight.setVisible(false);
    }


    public void changeTiles(ActionEvent ignored) {
        GameManager manager = GameManager.getInstance();
        if (manager.getActualPlayer().changeRack()) {

            finishTurn(ignored);
        }

    }

    public void finishTurn(ActionEvent ignored) {
        GameManager manager = GameManager.getInstance();

        manager.passTurn();

        actualizeRacks();

        displayWhoPlays(manager);

        if (GameManager.getInstance().isGameOver()) {
            displayWinner();
        }
    }

    private void displayWhoPlays(GameManager manager) {
        if (manager.getActualPlayerIndex() == 0) {
            currentPlayer.setText("Player Left is playing");
            rackRight.setVisible(false);
            rackLeft.setVisible(true);
        } else {
            currentPlayer.setText("Player Right is playing");
            rackRight.setVisible(true);
            rackLeft.setVisible(false);
        }
    }

    private void displayWinner() {
        int playerLeftTiles = GameManager.getInstance().getPlayer(0).getTileLeftInStack();
        int playerRightTiles = GameManager.getInstance().getPlayer(1).getTileLeftInStack();
        Label secondLabel = new Label();

        if (playerLeftTiles > playerRightTiles) {
            secondLabel.setText("Player Right wins!");
        } else if (playerLeftTiles < playerRightTiles) {
            secondLabel.setText("Player Left wins!");
        } else {
            secondLabel.setText("Draw!");
        }

        Button buttonQuit = new Button("Quit");
        buttonQuit.setOnAction(event -> Platform.exit());

        VBox secondaryLayout = new VBox();
        secondaryLayout.setAlignment(Pos.TOP_CENTER);
        secondaryLayout.setSpacing(30);
        secondaryLayout.getChildren().addAll(secondLabel, buttonQuit);

        Scene scene = new Scene(secondaryLayout, 300, 100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Game Over");
        newWindow.setScene(scene);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        newWindow.show();
    }
}
