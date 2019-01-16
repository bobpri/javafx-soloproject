package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private Button btnAdminister;
	@FXML
	private Button btnStaff;
	@FXML
	private Button btnExit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAdminister.setOnAction(event -> handlerBtnAdministerAction(event));
		btnStaff.setOnAction(event -> handlerBtnStaffAction(event));
		btnExit.setOnAction(event -> handlerBtnExitAction(event));

	}

	private void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

	public void handlerBtnStaffAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/staffLogin.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("직원로그인");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnStaff.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnAdministerAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/administerLogin.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("관리자 로그인");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnStaff.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
