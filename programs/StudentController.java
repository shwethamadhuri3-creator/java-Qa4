package controller;

import dao.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Student;

public class StudentController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField deptField;

    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> idCol;
    @FXML private TableColumn<Student, String> nameCol;
    @FXML private TableColumn<Student, String> emailCol;
    @FXML private TableColumn<Student, String> deptCol;

    private StudentDAO dao = new StudentDAO();
    private ObservableList<Student> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStudentId()).asObject());
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        deptCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDepartment()));

        loadStudents();
    }

    private void loadStudents() {
        list.setAll(dao.getAllStudents());
        table.setItems(list);
    }

    // CREATE
    @FXML
    public void addStudent() {
        dao.addStudent(new Student(
                nameField.getText(),
                emailField.getText(),
                deptField.getText()
        ));
        clearFields();
        loadStudents();
    }

    // UPDATE
    @FXML
    public void updateStudent() {
        Student s = table.getSelectionModel().getSelectedItem();
        if (s != null) {
            s.setName(nameField.getText());
            s.setEmail(emailField.getText());
            s.setDepartment(deptField.getText());
            dao.updateStudent(s);
            loadStudents();
        }
    }

    // DELETE
    @FXML
    public void deleteStudent() {
        Student s = table.getSelectionModel().getSelectedItem();
        if (s != null) {
            dao.deleteStudent(s.getStudentId());
            loadStudents();
        }
    }

    @FXML
    public void selectStudent() {
        Student s = table.getSelectionModel().getSelectedItem();
        if (s != null) {
            nameField.setText(s.getName());
            emailField.setText(s.getEmail());
            deptField.setText(s.getDepartment());
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        deptField.clear();
    }
}
