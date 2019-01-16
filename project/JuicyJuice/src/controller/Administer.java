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

public class Administer implements Initializable{
	@FXML
	private Button btnStaffManage; 
	@FXML
	private Button btnMenuManage;
	@FXML
	private Button btnStatic;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnExit;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(event -> handlerBtnExitAction(event));
		btnReturn.setOnAction(event -> handlerBtnReturnAction(event));
		btnStaffManage.setOnAction(event -> handlerBtnStaffManageAction(event));
        btnStatic.setOnAction(event -> handlerBtnStaticManageAction(event));
		btnMenuManage.setOnAction(event -> handlerBtnMenuManageAction(event));
	}

	public void handlerBtnStaticManageAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/statistic.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("烹拌");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnStatic.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void handlerBtnMenuManageAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/menumanage.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("皋春包府");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnMenuManage.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void handlerBtnStaffManageAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/staffmanage.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("流盔包府");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnStaffManage.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void handlerBtnReturnAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();

			primaryStage.setTitle("JuicyJuice");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Stage oldStage = (Stage) btnReturn.getScene().getWindow();
			oldStage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
		
	}

}
