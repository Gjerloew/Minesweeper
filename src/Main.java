import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Minesweeper?");

        //Container
        BorderPane root = new BorderPane();

        //Top bar - HBox
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(5, 5, 5, 5));
        topBar.setSpacing(5);
        root.setTop(topBar);

        //Center - GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        root.setCenter(grid);

        //Button
        Button generate = new Button();
        generate.setText("Generate grid");
        generate.setMaxWidth(100);
        topBar.getChildren().addAll(generate);

        int textBoxWidth = 80;

        //Width field
        TextField width = new TextField();
        width.setPromptText("Width");
        width.setMaxWidth(textBoxWidth);

        //Height field
        TextField height = new TextField();
        height.setPromptText("Height");
        height.setMaxWidth(textBoxWidth);

        //Minecount field
        TextField minecount = new TextField();
        minecount.setPromptText("# of mines");
        minecount.setMaxWidth(textBoxWidth);

        //Add textfields
        topBar.getChildren().addAll(width, height, minecount);

        //Button action
        generate.setOnAction(e -> generateMinefield(window, grid, width, height, minecount));

        //Tie it together
        Scene scene = new Scene(root, 355,300);
        window.setScene(scene);
        window.show();

        generateMinefield(window, grid, width, height, minecount);
    }

    private void generateMinefield(Stage stage, GridPane grid, TextField width, TextField height, TextField minecount) {

        //Clear the grid to avoid stacking squares
        grid.getChildren().clear();

        //Default values
        int w = 23;
        int h = 23;
        int mc = 69;

        //Validate the inputs
        try {
            w = Integer.parseInt(width.getText());
        } catch (NumberFormatException e) {}

        try {
            h = Integer.parseInt(height.getText());
        } catch (NumberFormatException e) {}

        try {
            mc = Integer.parseInt(minecount.getText());
        } catch (NumberFormatException e) {}

        Game game = new Game(stage, grid, w, h, mc);
        game.generate();
        game.resize();
    }
}
