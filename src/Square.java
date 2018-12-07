import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Square {

    boolean isMine = false;
    int number = 0;
    int squareSize;

    Position position;

    Rectangle base;
    Rectangle cover;
    Text text;

    public Square(Position position, int squareSize){
        this.position = position;
        this.squareSize = squareSize;
    }

    public Position getPos(){
        return position;
    }

    public void setMine(boolean hasMine){
        isMine = hasMine;
    }

    public boolean hasMine(){
        return isMine;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public boolean hasNumber(){
        if(number < 1){
            return false;
        }
        return true;
    }

    public Rectangle getCover(){
        return cover;
    }

    public Rectangle getBase(){
        return base;
    }

    public StackPane getStack(Game game){
        StackPane stack = new StackPane();

        Color standard = Color.GREY;
        Color bg = Color.WHITE;
        Color hover = Color.LIGHTGREY;
        Color mine = Color.RED;

        base = new Rectangle(squareSize, squareSize, bg);
        base.setStroke(hover);
        base.setStrokeType(StrokeType.INSIDE);
        if(hasMine()){
            base.setFill(mine);
        }

        cover = new Rectangle(squareSize, squareSize, standard);
        cover.setStroke(hover);
        cover.setStrokeType(StrokeType.INSIDE);

        text = new Text();

        Color fill;
        switch (number) {
            case 1: fill = Color.BLUE;
                break;
            case 2: fill = Color.GREEN;
                break;
            case 3: fill = Color.RED;
                break;
            case 4: fill = Color.DARKBLUE;
                break;
            case 5: fill = Color.DARKRED;
                break;
            case 6: fill = Color.DARKCYAN;
                break;
            case 7: fill = Color.PURPLE;
                break;
            case 8: fill = Color.DARKGREY;
                break;
            default: fill = Color.BLACK;
                break;
        }

        text.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        text.setText(Integer.toString(number));
        text.setFill(fill);

        cover.setOnMouseEntered(e -> cover.setFill(hover));
        cover.setOnMouseExited(e -> cover.setFill(standard));
        cover.setOnMouseClicked(e -> {

            MouseButton button = e.getButton();

            if(button == MouseButton.PRIMARY){
                if(!(cover.getFill() == Color.LIGHTBLUE || cover.getFill() == Color.BLUE)){
                    game.reveal(this);
                }
            }

            if(button == MouseButton.SECONDARY){
                if(cover.getFill() == Color.LIGHTBLUE || cover.getFill() == Color.BLUE){
                    cover.setFill(standard);
                    cover.setOnMouseEntered(f -> cover.setFill(hover));
                    cover.setOnMouseExited(f -> cover.setFill(standard));
                } else {
                    cover.setFill(Color.BLUE);
                    cover.setOnMouseEntered(f -> cover.setFill(Color.LIGHTBLUE));
                    cover.setOnMouseExited(f -> cover.setFill(Color.BLUE));
                }
            }
        });


        if(hasNumber() && !hasMine()){
            stack.getChildren().addAll(base, text, cover);
        } else {
            stack.getChildren().addAll(base, cover);
        }

        return stack;
    }
}
