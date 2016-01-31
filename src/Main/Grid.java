//package Main;
//
//import Cell.Cell;
//import javafx.scene.Group;
//
///**
// * Created by rhondusmithwick on 1/30/16.
// *
// * @author Rhondu Smithwick
// */
//public class Grid extends Group {
//
//    public Grid() {
//        super();
//    }
//
//    public void init(double width, double height) {
//        int numCellsPerRow = (int) (width * .1);
//        int numCellsPerColumn = (int) (height * .1);
//        for (int r = 0; r < numCellsPerRow; r++) {
//            for (int c = 0; c < numCellsPerColumn; c++) {
//                Cell myCell = new Cell(numCellsPerRow, numCellsPerColumn);
//                myCell.setX(r * numCellsPerRow);
//                myCell.setY(c * numCellsPerColumn);
//                myCell.init();
//                addCell(myCell);
//            }
//        }
//    }
//
//    public void addCell(Cell c) {
//        this.getChildren().add(c);
//    }
//
//}
