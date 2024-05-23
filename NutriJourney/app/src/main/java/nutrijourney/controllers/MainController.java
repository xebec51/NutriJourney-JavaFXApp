package nutrijourney.controllers;

import javafx.scene.layout.VBox;

public class MainController {
    private VBox view;

    public MainController() {
        view = new VBox();
        // Tambahkan komponen UI ke view
    }

    public VBox getView() {
        return view;
    }
}