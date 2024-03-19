package com;

	import javafx.application.Application;
	import javafx.geometry.Insets;
	import javafx.scene.Scene;
	import javafx.scene.control.*;
	import javafx.scene.layout.*;
	import javafx.stage.Stage;

	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.List;

	public class ToDoListApp extends Application {

	    private List<Task> tasks = new ArrayList<>();
	    private ListView<Task> taskListView;

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("To-Do List");

	        BorderPane borderPane = new BorderPane();
	        borderPane.setPadding(new Insets(10));

	        VBox vBox = new VBox(10);
	        vBox.setPadding(new Insets(10));
	        vBox.setPrefWidth(300);

	        taskListView = new ListView<>();
	        taskListView.setPrefHeight(400);
	        refreshTaskListView();

	        Button addButton = new Button("Add Task");
	        addButton.setOnAction(e -> showAddTaskDialog());

	        Button deleteButton = new Button("Delete Task");
	        deleteButton.setOnAction(e -> deleteSelectedTask());

	        vBox.getChildren().addAll(taskListView, addButton, deleteButton);

	        borderPane.setCenter(vBox);

	        Scene scene = new Scene(borderPane, 400, 500);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    private void showAddTaskDialog() {
	        Dialog<Task> dialog = new Dialog<>();
	        dialog.setTitle("Add Task");
	        dialog.setHeaderText("Enter task details:");

	        TextField taskNameField = new TextField();
	        DatePicker datePicker = new DatePicker();
	        ChoiceBox<String> priorityChoiceBox = new ChoiceBox<>();
	        priorityChoiceBox.getItems().addAll("High", "Medium", "Low");

	        GridPane grid = new GridPane();
	        grid.add(new Label("Task Name:"), 0, 0);
	        grid.add(taskNameField, 1, 0);
	        grid.add(new Label("Due Date:"), 0, 1);
	        grid.add(datePicker, 1, 1);
	        grid.add(new Label("Priority:"), 0, 2);
	        grid.add(priorityChoiceBox, 1, 2);

	        dialog.getDialogPane().setContent(grid);

	        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

	        dialog.setResultConverter(buttonType -> {
	            if (buttonType == addButton) {
	                String name = taskNameField.getText();
	                LocalDate dueDate = datePicker.getValue();
	                String priority = priorityChoiceBox.getValue();
	                Task task = new Task(name, dueDate, priority);
	                tasks.add(task);
	                refreshTaskListView();
	                return task;
	            }
	            return null;
	        });

	        dialog.showAndWait();
	    }

	    private void deleteSelectedTask() {
	        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
	        if (selectedTask != null) {
	            tasks.remove(selectedTask);
	            refreshTaskListView();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText("No task selected");
	            alert.setContentText("Please select a task to delete.");
	            alert.showAndWait();
	        }
	    }

	    private void refreshTaskListView() {
	        taskListView.getItems().clear();
	        taskListView.getItems().addAll(tasks);
	    }

	    private static class Task {
	        private String name;
	        private LocalDate dueDate;
	        private String priority;

	        public Task(String name, LocalDate dueDate, String priority) {
	            this.name = name;
	            this.dueDate = dueDate;
	            this.priority = priority;
	        }

	        @Override
	        public String toString() {
	            return name + " - Due: " + dueDate.toString() + " (Priority: " + priority + ")";
	        }
	    }
	}

	


