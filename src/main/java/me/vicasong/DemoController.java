package me.vicasong;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Demo main controller
 *
 */
public class DemoController {

    @FXML
    public MFXButton btnOpen;
    @FXML
    public Label txtCode;

    @FXML
    public void onOpenAction(ActionEvent actionEvent) {
        CurrentStage.dialogController.openPrompt((code) -> {
            txtCode.setText(code);
            txtCode.setVisible(true);
        });
    }
}
