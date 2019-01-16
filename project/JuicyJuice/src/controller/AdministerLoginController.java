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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AdministerLoginController implements Initializable {
	@FXML
	private TextField txtAdministerID;
	@FXML
	private PasswordField txtAdministerPW;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtAdministerID.setOnKeyPressed(event -> handlerTxtAdminiterIDPressde(event));
		txtAdministerPW.setOnKeyPressed(event -> handlerTxtAdminiterPWPressde(event));
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

	public void handlerTxtAdminiterPWPressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	public void handlerTxtAdminiterIDPressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	public void login() {
		AdministerLoginDAO login = new AdministerLoginDAO();

		boolean sucess = false;

		try {
			sucess = login.getLogin(txtAdministerID.getText(), txtAdministerPW.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Alert alert;

		if (txtAdministerID.getText().equals("") || txtAdministerPW.getText().equals("")) {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디 비밀번호 미 입력");
			alert.setContentText("아이디,비밀번호를 제대로 입력해 주세요");
			alert.setResizable(false);
			alert.showAndWait();
		}

		if (sucess) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/administer.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainMtage = new Stage();
				mainMtage.setTitle("관리자");
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

			txtAdministerID.clear();
			txtAdministerPW.clear();
		}
	}
}
