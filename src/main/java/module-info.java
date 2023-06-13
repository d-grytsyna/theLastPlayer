module com.example.cardgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.cardgame to javafx.fxml;
    exports com.example.cardgame;
}