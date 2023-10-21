package com.example.concurrency;

import javafx.beans.Observable;
import javafx.beans.binding.When;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class WorkerUI extends GridPane {
    private final Label title=new Label("");
    private final Label message=new Label("");

    private final Label running = new Label("");

    private final Label state = new Label("");
    private final Label totalWork = new Label("");
    private final Label workDone = new Label("");
    private final Label progress = new Label("");

    private final ProgressBar progressBar   = new ProgressBar();
    private final TextArea value = new TextArea();
    private final TextArea exception=new TextArea();

    public WorkerUI(Worker<ObservableList<Integer>> worker) {
        addUI();
        bindToWorker(worker);
    }

    private void addUI() {
        value.setPrefHeight(20);
        value.setPrefRowCount(4);
        value.setWrapText(true);
        exception.setPrefColumnCount(20);
        exception.setPrefRowCount(5);
        this.setHgap(10);
        this.setVgap(10);
        addRow(0, new Label("Title: "),title);
        addRow(1, new Label("Message: "),message);
        addRow(2, new Label("Running: "),running);
        addRow(3, new Label("State: "),state);
        addRow(4, new Label("Total work: "),totalWork);
        addRow(5, new Label("Work done: "),workDone);
        addRow(6, new Label("Proggress: "),new HBox(progressBar,progress));
        addRow(7, new Label("Value"),value);
        addRow(8, new Label("Exception: "),exception);
    }

    private void bindToWorker(Worker<ObservableList<Integer>> worker){
        title.textProperty().bind(worker.titleProperty());
        message.textProperty().bind(worker.messageProperty());
        running.textProperty().bind(worker.runningProperty().asString());
        state.textProperty().bind(worker.stateProperty().asString());

        totalWork.textProperty().bind(new When(worker.totalWorkProperty().isEqualTo(-1))
                .then("Unknown")
                .otherwise((worker.totalWorkProperty().asString())));
        workDone.textProperty().bind(new When(worker.workDoneProperty().isEqualTo(-1))
                .then("Unknown")
                .otherwise((worker.workDoneProperty().asString())));
        progress.textProperty().bind(new When(worker.progressProperty().isEqualTo(-1))
                .then("Unknown")
                .otherwise(worker.progressProperty().multiply(100).asString()));

        progressBar.progressProperty().bind(worker.progressProperty());
        value.textProperty().bind(worker.valueProperty().asString());
        worker.exceptionProperty().addListener((obs,oldVal,newVal)->
        {
            if(newVal!=null){
                exception.setText(newVal.getMessage());
            }else{
                exception.setText("");
            }
        });

    }

}
