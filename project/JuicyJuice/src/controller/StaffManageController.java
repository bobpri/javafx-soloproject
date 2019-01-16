package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.StaffVO;

public class StaffManageController implements Initializable {
	@FXML
	private TextField txtSName;
	@FXML
	private TextField txtSNumber;
	@FXML
	private TextField txtSID;
	@FXML
	private Button btnSIDcheck;
	@FXML
	private Button btnInput;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnClear;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnTotalList;
	@FXML
	private TextField txtSearch;
	@FXML
	private TextField txtSPW;
	@FXML
	private TableView<StaffVO> tableView = new TableView<>();

	StaffVO staff = new StaffVO();
	
	ObservableList<StaffVO> data = FXCollections.observableArrayList();//테이블 뷰 에 들어가는 데이터
	ObservableList<StaffVO> selectStaff;

	boolean editDelete = false;
	int selectedIndex;

	int no;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setEditable(false);
		//칼럼 이름 설정
		TableColumn colNo = new TableColumn<>("NO");
		colNo.setMaxWidth(40);
		colNo.setCellValueFactory(new PropertyValueFactory<>("s_no"));

		TableColumn colName = new TableColumn<>("이름");
		colName.setMaxWidth(70);
		colName.setCellValueFactory(new PropertyValueFactory<>("s_name"));
		
		TableColumn colNumber = new TableColumn<>("전화번호");
		colNumber.setMaxWidth(300);
		colNumber.setCellValueFactory(new PropertyValueFactory<>("s_number"));

		TableColumn colID = new TableColumn<>("ID");
		colID.setMaxWidth(300);
		colID.setCellValueFactory(new PropertyValueFactory<>("s_id"));
		
		TableColumn colBirth = new TableColumn<>("생일");
		colBirth.setMaxWidth(150);
		colBirth.setCellValueFactory(new PropertyValueFactory<>("s_birth"));
		
		tableView.setItems(data);
		tableView.getColumns().addAll(colNo, colName, colBirth, colNumber, colID);

		totalList(); //총 리스트 출력
		//입력한 직원 내용 저장 액션
		btnInput.setOnAction(event -> {
			try {
				data.removeAll(data);
				StaffVO sVo = null;
				StaffManageDAO sDao = null;
				if (event.getSource().equals(btnInput)) {
					sVo = new StaffVO(txtSName.getText(), txtSNumber.getText().trim(),
							txtSID.getText(), txtSPW.getText(),dpDate.getValue().toString());
					sDao = new StaffManageDAO();
					sDao.getStaffregiste(sVo);

					if (sDao != null) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("직원 정보 입력");
						alert.setHeaderText("직원 정보가 성공적으로 추가 되었습니다.");
						alert.setContentText("다음 직원의 정보를 입력해 주세요");
						alert.showAndWait();
						totalList();
						txtSID.setEditable(true);
						handlerBtnClearAction(event);
					}
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 정보 입력");
				alert.setHeaderText("직원 정보를 정확히 입력해 주세요.");
				alert.setContentText("정보 입력 실패");
				alert.showAndWait();
				totalList();
			}
		});
		//날자 등록 메소드
		dpDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = dpDate.getValue();
			}
		});
		
		btnTotalList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					totalList();
				} catch (Exception e) {
				}
			}
		});
		
		
		
		btnClear.setOnAction(event -> handlerBtnClearAction(event));
		btnExit.setOnAction(event -> handlerBtnExitAction(event));
		btnSIDcheck.setOnAction(event -> handlerBtnOverlapAction(event));
		btnReturn.setOnAction(event -> handlerBtnReturnAction(event));
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		//테이블에 마우스 선택시 생기는 이벤트
		tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				selectStaff = tableView.getSelectionModel().getSelectedItems();
				selectedIndex = tableView.getSelectionModel().getSelectedIndex();

				try {
					txtSName.setText(selectStaff.get(0).getS_name());
					
					txtSNumber.setText(selectStaff.get(0).getS_number());
					txtSID.setText(selectStaff.get(0).getS_id());
					txtSPW.setText(selectStaff.get(0).getS_pw());

					editDelete = true;
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("정보입력");
					alert.setHeaderText("직원의 정보를 정확히  선택해주세요");
					alert.setContentText("다음에는 주의해 주세요!");
					alert.showAndWait();
				}
			}
		});
	}
	//수정버튼
	public void handlerBtnEditAction(ActionEvent event) {
		StaffVO svo = new StaffVO();
		StaffManageDAO sDao = new StaffManageDAO();

		try {
			svo = new StaffVO(tableView.getSelectionModel().getSelectedIndex(), txtSName.getText(), dpDate.getValue().toString(), txtSNumber.getText(), txtSID.getText(), txtSPW.getText());
			
			sDao.getStaffUpdate(svo);

			System.out.println("?" + sDao);
			data.removeAll(data);
			totalList();

			txtSName.setEditable(true);
			txtSNumber.setEditable(true);
			txtSID.setEditable(true);
			txtSPW.setEditable(true);
		} catch (Exception e) {
			System.out.println("?? :" + e);
		}
	}
	//삭제버튼
	public void handlerBtnDeleteAction(ActionEvent event) {
		StaffManageDAO sDao = null;
		sDao = new StaffManageDAO();
		try {
			sDao.getStudentDelete(tableView.getSelectionModel().getSelectedItem().getS_no());
			data.removeAll(data);

			totalList();

			btnDelete.setDisable(false);

			handlerBtnClearAction(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//전체 리스트 출력
	public void totalList() {
		Object[][] totalData;

		StaffManageDAO sDao = new StaffManageDAO();
		StaffVO sVo = null;
		ArrayList<String> title;
		ArrayList<StaffVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getStaffTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			data.add(sVo);
		}

	}
	//검색버튼
	private void handlerBtnSearchAction(ActionEvent event) {
		StaffVO sVo = new StaffVO();
		StaffManageDAO sDao = null;

		Object[][] totalData = null;

		String searchName = "";
		boolean searchResult = false;
		try {
			searchName = txtSearch.getText().trim();
			sDao = new StaffManageDAO();
			sVo = sDao.getStaffCheck(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 검색");
				alert.setHeaderText("직원 이름 입력");
				alert.setContentText("직원이름을 입력해주세요");
				alert.showAndWait();
			}
			if (!searchName.equals("") && (sVo != null)) {
				ArrayList<String> title;
				ArrayList<StaffVO> list;

				title = sDao.getColumnName();
				int columnCount = title.size();

				list = sDao.getStaffTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];
				if (sVo.getS_name().equals(searchName)) {
					txtSearch.clear();
					data.removeAll(data);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						sVo = list.get(index);
						if (sVo.getS_name().equals(searchName)) {
							data.add(sVo);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) { //리스트에 검색 이름이 없을시 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원정보 검색");
				alert.setHeaderText(searchName + "직원이 리스트에 없습니다");
				alert.setContentText("다시검색하세요");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("직원정보 검색");
			alert.setHeaderText("검색 오류");
			alert.setContentText("다시검색하세요");
			alert.showAndWait();
		}

	}
	//이전 창으로 이동 
	private void handlerBtnReturnAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/administer.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("관리자");
			mainMtage.setResizable(false);
			mainMtage.setScene(scene);
			Stage oldStage = (Stage) btnReturn.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (Exception e) {
		}

	}
	//아이디 중복검사 메소드
	private void handlerBtnOverlapAction(ActionEvent event) {
		StaffManageDAO sDao = null;
		String searchId = "";
		boolean searchResult = true;

		try {
			searchId = txtSID.getText().trim();
			sDao = new StaffManageDAO();
			searchResult = (boolean) sDao.getIdOverlap(searchId);

			if (!searchResult && !searchId.equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchId + "를 사용할수 있습니다.");
				alert.setContentText("비밀번호를 입력해 주세요");
				alert.showAndWait();
				txtSID.setEditable(true);
			} else if (searchId.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText("아이디를 입력하세요");
				alert.setContentText("등록할 아이디를 입력하세요");
				alert.showAndWait();
			} else {
				txtSID.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchId + "를 사용할수 없습니다.");
				alert.setContentText("다른 아이디를 입력하세요");
				alert.showAndWait();

				txtSID.requestFocus();
			}
		} catch (Exception e) {
		}
	}
	//종료 메소드
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}
	//입력창 깨끗이 하는 메소드 수정 버튼
	public void handlerBtnClearAction(ActionEvent event) {
		txtSID.clear();
		txtSName.clear();
		txtSNumber.clear();
		txtSPW.clear();
	}

}
