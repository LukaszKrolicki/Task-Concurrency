package com.example.concurrency;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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

        //service schedule task
        service.setPeriod(Duration.seconds(5));

        startBtn.setOnAction(o->{
            //normal task
//            Thread bgThread = new Thread(task);
//            bgThread.setDaemon(true);
//            bgThread.start();

            //service task
            if(started){
                service.restart();

            }else{
                service.start();
                started=true;
                startBtn.setText("Restart");
            }

        });

        cancelBtn.setOnAction(o->{
            //normal task
//            task.cancel();

            //service task
            service.cancel();
        });

        //service task
        resetBtn.setOnAction(o->{service.reset();});

        exitBtn.setOnAction(e->stage.close());

        GridPane pane = new WorkerUI(service); //task - if normal task
        HBox box = new HBox(5,startBtn, resetBtn,cancelBtn,exitBtn);
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

    Button resetBtn = new Button("Reset");

    //normal task
    //EvenNumTask task = new EvenNumTask(1,10,1000);

    //service task
//    Service<ObservableList<Integer>> service = new Service<>(){
//        @Override
//        protected Task<ObservableList<Integer>> createTask(){
//            return new EvenNumTask(1,10,1000);
//        }
//    };
    //serviceTask
    boolean started = false;

    //scheduled service
    ScheduledService<ObservableList<Integer>> service = new ScheduledService<ObservableList<Integer>>() {
        @Override
        protected Task<ObservableList<Integer>> createTask() {
            return new EvenNumTask(1,10,100);
        }
    };

    public static void main(String[] args) {
        launch();
    }

}