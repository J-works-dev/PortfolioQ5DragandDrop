/**
 * Portfolio Question 5
 * SangJoon Lee
 * 30024165
 * 30/04/2021
 */

package portfolioq5draganddrop;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PortfolioQ5DragandDrop extends Application {

    ListView<String> toDoList = new ListView<>();
    ListView<String> doneList = new ListView<>();
    static int toDoCount = 0;
    static int doneCount = 0;
    
    private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("J-works To Do List");
        
        final SplitPane splitPane = new SplitPane();
        BorderPane leftBorder = new BorderPane(toDoList);
        BorderPane rightBorder = new BorderPane(doneList);
        
        toDoList.getItems().add("To Do List");
        doneList.getItems().add("Done List");
        splitPane.getItems().addAll(leftBorder, rightBorder);

        Scene scene = new Scene(splitPane, 800, 500);

        TextField getList = new TextField();
        Button buttonAdd = new Button("ADD");
        Text delete = new Text("Drag out to Delete List");
        HBox hbox = new HBox();
        hbox.getChildren().add(getList);
        hbox.getChildren().add(buttonAdd);
        hbox.getChildren().add(delete);
        leftBorder.setBottom(hbox);
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (toDoCount > 15) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("To Do List is Full!");
                    alert.setContentText("Add after finish task");
                    alert.showAndWait();
                } else {
                    String list = getList.getText();
                    toDoList.getItems().add(list);
                    toDoCount++;
                    getList.setText("");
                }
            }
        });
        
        toDoList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnDragDetected(event -> {
                    if (!(cell.getItem()).equals("To Do List")) {
                        if (!cell.isEmpty()) {
                            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent cc = new ClipboardContent();
                            cc.putString(cell.getItem());
                            db.setContent(cc);
                            dragSource.set(cell);
                        }
                    }
            });

            cell.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            });

            cell.setOnDragDone(event -> {
                toDoList.getItems().remove(cell.getItem());
                toDoCount--;
            });
            
            cell.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString() && dragSource.get() != null) {
                    ListCell<String> dragSourceCell = dragSource.get();
                    toDoList.getItems().add(dragSourceCell.getItem());
                    toDoCount++;
                    event.setDropCompleted(true);
                    dragSource.set(null);
                } else {
                    event.setDropCompleted(false);
                }
            });
            return cell;
        });
        
        doneList.setCellFactory(lv -> {
            ListCell<String> cell2 = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell2.setOnDragDetected(event -> {
                    if (!(cell2.getItem()).equals("Done List")) {
                        if (!cell2.isEmpty()) {
                            Dragboard db = cell2.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent cc = new ClipboardContent();
                            cc.putString(cell2.getItem());
                            db.setContent(cc);
                            dragSource.set(cell2);
                        }
                    }
            });

            cell2.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            });

            cell2.setOnDragDone(event -> {
                doneList.getItems().remove(cell2.getItem());
                doneCount--;
            });

            cell2.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString() && dragSource.get() != null) {
                    ListCell<String> dragSourceCell = dragSource.get();
                    doneList.getItems().add(dragSourceCell.getItem());
                    doneCount++;
                    event.setDropCompleted(true);
                    dragSource.set(null);
                } else {
                    event.setDropCompleted(false);
                }
            });
            return cell2;
        });

        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
