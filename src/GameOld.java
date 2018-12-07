import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collections;

public class GameOld {

    int width,height;

    public GameOld(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void placeMines(GridPane grid, int w, int h, int mc){

        //if there are less than 0 mines
        if(mc < 0){
            mc = w+h;
        }

        //If minecount is too high, limit it
        if(mc > w*h){
            mc=w*h;
        }

        //Pull unique random locations for mines
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<w*h; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i=0; i<mc; i++) {

            //Place the mines on the locations
            StackPane pane = (StackPane) grid.getChildren().get(list.get(i));
            Rectangle rect = (Rectangle) getByUserData(pane, "base");
            rect.setFill(Color.RED);
        }

        //put numbers on the field
        for (int i = 0; i<w*h; i++){
            updateNumbers(grid, i, w, h);
        }
    }

    private Node getByUserData(Pane pane, Object data) {
        for (Node n : pane.getChildren()) {
            if (data.equals(n.getUserData())) {
                return n;
            }
        }
        return null;
    }

    private void updateNumbers(GridPane grid, int index, int width, int height){

        //directions
        int n = -width;
        int ne = -width+1;
        int e = 1;
        int se = width+1;
        int s = width;
        int sw = width-1;
        int w = -1;
        int nw = -width-1;

        ArrayList<Integer> positions = new ArrayList<>();

        positions.add(n);
        positions.add(s);

        //if on an edge don't search past the edge
        if((index % (width)) != 0){
            positions.add(w);
            positions.add(nw);
            positions.add(sw);
        }

        //if on an edge don't search past the edge
        if(((index+1) % (width)) != 0){
            positions.add(se);
            positions.add(ne);
            positions.add(e);
        }

        int number = 0;

        //search for mines and icrement number if mine is found
        for (int dir : positions) {

            //if out of bounds
            if(index+dir >= width*height || index+dir < 0){
                continue;
            }

            StackPane pane = (StackPane) grid.getChildren().get(index+dir);
            Rectangle rect = (Rectangle) getByUserData(pane, "base");

            if(rect.getFill() == Color.RED){
                number++;
            }
        }

        //if there are 0 mines don't print a 0
        Text text = new Text(Integer.toString(number));
        if (number == 0) {
            text.setText("");
        }

        //text formatting
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        text.setUserData("number");

        //set number color based on value
        Color fill = Color.BLACK;
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
        }

        text.setFill(fill);

        //text.setVisible(false);

        StackPane pane = (StackPane) grid.getChildren().get(index);
        Rectangle rect = (Rectangle) getByUserData(pane, "base");

        //don't put numbers on mines
        if(rect.getFill() != Color.RED){
            pane.getChildren().add(text);
        }
    }

    public void reveal(GridPane grid, Rectangle rect){

        rect.setVisible(false);
        int index = Integer.parseInt(rect.getId());

        //directions
        int n = -width;
        int ne = -width+1;
        int e = 1;
        int se = width+1;
        int s = width;
        int sw = width-1;
        int w = -1;
        int nw = -width-1;

        ArrayList<Integer> positions = new ArrayList<>();

        positions.add(n);
        positions.add(s);

        //if on an edge don't search past the edge
        if((index % (width)) != 0){
            positions.add(w);
            positions.add(nw);
            positions.add(sw);
        }

        //if on an edge don't search past the edge
        if(((index+1) % (width)) != 0){
            positions.add(se);
            positions.add(ne);
            positions.add(e);
        }

        //search for mines and icrement number if mine is found
        for (int dir : positions) {

            //if out of bounds
            if(index+dir >= width*height || index+dir < 0){
                continue;
            }

            StackPane pane = (StackPane) grid.getChildren().get(index+dir);
            Rectangle rectangle = (Rectangle) getByUserData(pane, "cover");

            //if the cover has been opened; continue
            if(!rectangle.isVisible()){
                continue;
            }

            reveal(grid, rectangle);
        }

    }
}
