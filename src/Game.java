import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    final int SQUARESIZE = 15;
    Stage stage;
    GridPane grid;
    int w;
    int h;
    int mc;
    Square[][] board;
    int revealed;

    public Game(Stage stage, GridPane grid, int width, int height, int minecount){

        this.stage = stage;
        this.grid = grid;
        w = width;
        h = height;
        mc = minecount;
        board = new Square[w][h];
    }

    public void generate(){
        ArrayList<Position> list = new ArrayList<>();
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                Position pos = new Position(i, j);
                board[i][j] = new Square(pos, SQUARESIZE);
                list.add(pos);
            }
        }

        //Pull unique random locations for mines
        Collections.shuffle(list);
        for (int i=0; i<mc; i++) {
            Position pos = list.get(i);
            board[pos.getX()][pos.getY()].setMine(true);
        }

        //Set numbers
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                int number = 0;

                for(Position pos : getSurroundingPositions(new Position(i, j))){
                    if(board[pos.getX()][pos.getY()].hasMine()){
                        number++;
                    }
                }
                board[i][j].setNumber(number);
            }
        }

        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                grid.add(board[i][j].getStack(this), i, j);
            }
        }
    }

    public void resize(){
        if(stage.getWidth() < SQUARESIZE * w + 25){
            stage.setWidth(SQUARESIZE * w + 25);
        }
        if(stage.getHeight() < SQUARESIZE * h + 84){
            stage.setHeight(SQUARESIZE*h + 84);
        }
    }

    private ArrayList<Position> getSurroundingPositions(Position pos){
        ArrayList<Position> list = new ArrayList<>();

        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){
                if(i == 0 && j == 0){
                    continue;
                }

                if(pos.getX() + i < w && pos.getX() + i >= 0 && pos.getY() + j < h && pos.getY() + j >= 0){
                    list.add(new Position(pos.getX()+i, pos.getY()+j));
                }
            }
        }
        return list;
    }

    private ArrayList<Position> getCardinalPositions(Position pos){
        ArrayList<Position> list = new ArrayList<>();

        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){

                if(i == 0 && j == 0){
                    continue;
                }

                if(i == 0 || j == 0){
                    if(pos.getX() + i < w && pos.getX() + i >= 0 && pos.getY() + j < h && pos.getY() + j >= 0){
                        list.add(new Position(pos.getX()+i, pos.getY()+j));
                    }
                }
            }
        }
        return list;
    }

    public boolean reveal(Square square){

        square.getCover().setVisible(false);
        revealed++;

        if(square.getBase().getFill() == Color.RED){
            gameOver();
            return false;
        }

        if (w*h-revealed == mc) {
            victory();
            return true;
        }

        if(!square.hasNumber() && !square.hasMine()){
            for(Position pos : getCardinalPositions(square.getPos())){
                Square sq = board[pos.getX()][pos.getY()];
                if(sq.getCover().isVisible()){
                    if(!(sq.getCover().getFill() == Color.BLUE || sq.getCover().getFill() == Color.LIGHTBLUE)){
                        reveal(sq);
                    }
                }
            }
        }
        return true;
    }

    public void gameOver(){
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                Square sq = board[i][j];
                sq.getCover().setVisible(false);
            }
        }
    }

    public void victory(){
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                Square sq = board[i][j];
                sq.getCover().setVisible(false);
                if(sq.getBase().getFill() != Color.RED){
                    sq.getBase().setFill(Color.LIGHTGREEN);
                }
            }
        }
    }
}
