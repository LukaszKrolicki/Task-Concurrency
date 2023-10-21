package com.example.concurrency;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void  start(Stage stage) throws Exception {
        //example of how threads works
//        VBox root = new VBox();
//        root.setAlignment(Pos.CENTER);
//        root.setSpacing(10);
//        Button stop = new Button("Stop UI");
//        Button exit = new Button("Exit");
//        stop.setOnAction(o->{
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        exit.setOnAction(o->{
//            Platform.exit();
//        });
//        root.getChildren().addAll(stop,exit);
//        Scene scene = new Scene(root,500,500);
//        stage.setScene(scene);
//        stage.show();



        //example2 of tasks

        startBtn.setOnAction(o->{
            Thread bgThread = new Thread(task);
            bgThread.setDaemon(true);
            bgThread.start();
        });

        cancelBtn.setOnAction(o->{
            task.cancel();
        });

        exitBtn.setOnAction(e->stage.close());

        GridPane pane = new WorkerUI(task);
        HBox box = new HBox(5,startBtn,cancelBtn,exitBtn);
        BorderPane root = new BorderPane();
        root.setCenter(pane);
        root.setBottom(box);

        Scene scene= new Scene(root,800,800);
        stage.setScene(scene);
        stage.show();

    }


    Button startBtn = new Button("Start ");
    Button cancelBtn = new Button("Cancel");

    Button exitBtn = new Button("Exit");

    EvenNumTask task = new EvenNumTask(1,10,1000);

    public static void main(String[] args) {
        launch();
    }

}