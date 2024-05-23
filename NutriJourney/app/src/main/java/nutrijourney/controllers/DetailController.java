package nutrijourney.controllers;

import javafx.scene.layout.VBox;

public class DetailController {
    private VBox view;

    public DetailController() {
        view = new VBox();
        // Tambahkan komponen UI ke view
    }

    public VBox getView() {
        return view;
    }
}