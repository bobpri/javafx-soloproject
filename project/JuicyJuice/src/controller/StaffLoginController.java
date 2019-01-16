package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class StaffLoginController implements Initializable {
	@FXML
	private TextField txtStaffID;
	@FXML
	private TextField txtStaffPW;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtStaffID.setOnKeyPressed(event -> handlerTxtStaffIDPressde(event));
		txtStaffPW.setOnKeyPressed(event -> handlerTxtStaffPWPressde(event));
		btnLogin.setOnAction(event -> handlerBtnLoginAction(event));
		btnCancle.setOnAction(event -> handlerBtnCancleAction(event));
	}

	public void handlerBtnCancleAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);

			Stage mainMtage = new Stage();
			mainMtage.setTitle("JuicyJuice");
			mainMtage.setResizable(false);
			mainMtage.setScene(scene);
			Stage oldStage = (Stage) btnCancle.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	public void handlerBtnLoginAction(ActionEvent event) {
		login();
	}

	public void handlerTxtStaffPWPressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	public void handlerTxtStaffIDPressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	public void login() {
		StaffLoginDAO login = new StaffLoginDAO();

		boolean sucess = false;

		try {
			sucess = login.getLogin(txtStaffID.getText(), txtStaffPW.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Alert alert;

		if (txtStaffID.getText().equals("") || txtStaffPW.getText().equals("")) {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디 비밀번호 미 입력");
			alert.setContentText("아이디,비밀번호를 제대로 입력해 주세요");
			alert.setResizable(false);
			alert.showAndWait();
		}

		if (sucess) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sell.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainMtage = new Stage();
				mainMtage.setTitle("직원");
				mainMtage.setResizable(false);
				mainMtage.setScene(scene);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				mainMtage.show();
			} catch (IOException e) {
				System.out.println("오류1" + e);
			}
		} else {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디 비밀번호 미 일치");
			alert.setContentText("아이디,비밀번호를 제대로 입력해 주세요");
			alert.setResizable(false);
			alert.showAndWait();

			txtStaffID.clear();
			txtStaffPW.clear();
		}
	}
}
