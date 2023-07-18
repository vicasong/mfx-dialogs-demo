package me.vicasong;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Demo dialog controller
 *
 */
public class DialogController {
    public MFXTextField txtCode;
    public Label txtMsg;

    private MFXStageDialog dialog;
    private volatile String result;

    public synchronized void openPrompt(Consumer<String> resultCallback) {
        long invokeTime = System.currentTimeMillis();
        if (dialog == null) {
            MFXGenericDialog content;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(c -> CurrentStage.dialogController);
                fxmlLoader.setLocation(this.getClass().getResource("/views/dialogs/test-dialog.fxml"));
                fxmlLoader.load();
                content = fxmlLoader.getRoot();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dialog = MFXGenericDialogBuilder.build(content)
                    .toStageDialogBuilder()
                    .initOwner(CurrentStage.stage)
                    .initModality(Modality.APPLICATION_MODAL)
                    .setDraggable(true)
                    .setTitle("Dialogs Preview")
                    .setOwnerNode((Pane) CurrentStage.stage.getScene().getRoot())
                    .setScrimPriority(ScrimPriority.WINDOW)
                    .setScrimStrength(0.45)
                    .setScrimOwner(true)
                    .get();
            System.out.println("init owner stage: " + dialog.getOwner());
            System.out.println("init owner node: " + dialog.getOwnerNode());
        }
        dialog.setOnHidden(e -> {
            if (resultCallback != null && result != null) {
                resultCallback.accept(result);
            }
            // clear input
            txtCode.setText("");
        });
        dialog.showDialog();
        long costTime = System.currentTimeMillis() - invokeTime;
        if (costTime > 1000) {
            System.err.println("show dialog timeout: " + costTime);
        }
    }

    public void onDialogClose(MouseEvent mouseEvent) {
        result = null;
        dialog.close();
    }

    public void onConfirm(ActionEvent actionEvent) {
        String input = txtCode.getText();
        if (input == null || input.isBlank()) {
            txtMsg.setVisible(true);
            txtMsg.setText("Input required.");
        } else {
            txtMsg.setVisible(false);
            result = input;
            dialog.close();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        result = null;
        dialog.close();
    }
}
