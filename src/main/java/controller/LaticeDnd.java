package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import model.GameManager;


public class LaticeDnd {
    public static void manageSourceDragAndDrop(ImageView source, Integer indexInRack, Integer player) {

        source.setOnDragDetected(event -> {
            if (player.equals(GameManager.getInstance().getActualPlayerIndex())) {

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                db.setDragView(source.getImage());

                ClipboardContent content = new ClipboardContent();
                content.putString(indexInRack.toString());
                content.putImage(source.getImage());
                db.setContent(content);

                event.consume();
            }
        });

    }

    public static void manageTargetDragAndDrop(ImageView target, int x, int y, LaticeController controller) {
        Image baseImage = target.getImage();

        target.setOnDragOver(event -> {
            if (event.getGestureSource() != target &&
                    event.getGestureSource() instanceof ImageView) {

                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        target.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            if (event.getGestureSource() instanceof ImageView && db.hasString()) {
                target.setImage(db.getImage());
            }

            event.consume();
        });
        target.setOnDragExited(event -> {
            if (!event.isDropCompleted())
                target.setImage(baseImage);

            event.consume();
        });

        target.setOnDragDropped(event -> {
            boolean success = false;
            Dragboard db = event.getDragboard();
            GameManager manager = GameManager.getInstance();
            if (event.getGestureSource() instanceof ImageView && db.hasString()) {
                if (manager.getActualPlayer().putTile(Integer.parseInt(db.getString()), x, y)) {
                    target.setImage(db.getImage());
                    controller.actualizeRacks();
                    success = true;
                     stopDnd(target);
                }

            }

            event.setDropCompleted(success);

            event.consume();
        });
    }

    private static void stopDnd(ImageView target){
        target.setOnDragDropped(null);
        target.setOnDragOver(null);
        target.setOnDragEntered(null);
        target.setOnDragExited(null);
    }
}
