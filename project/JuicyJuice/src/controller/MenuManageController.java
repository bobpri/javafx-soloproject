package controller;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MenuVO;

public class MenuManageController implements Initializable {

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSearch;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtStuff;
	@FXML
	private TextField txtStock;
	@FXML
	private Button btnInput;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnTotalList;
	@FXML
	private TableView<MenuVO> tableView = new TableView<>();

	MenuVO menu = new MenuVO();

	ObservableList<MenuVO> data = FXCollections.observableArrayList();// 테이블 뷰
	ObservableList<MenuVO> selectMenu;

	boolean editDelete = false;
	int selectedIndex;

	int no;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setEditable(false);

		TableColumn colNo = new TableColumn<>("NO");
		colNo.setMaxWidth(40);
		colNo.setCellValueFactory(new PropertyValueFactory<>("mno"));

		TableColumn colName = new TableColumn<>("메뉴이름");
		colName.setMaxWidth(100);
		colName.setCellValueFactory(new PropertyValueFactory<>("mname"));

		TableColumn colPrice = new TableColumn<>("가격");
		colPrice.setMaxWidth(300);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));

		TableColumn colStuff = new TableColumn<>("재료");
		colStuff.setMaxWidth(300);
		colStuff.setCellValueFactory(new PropertyValueFactory<>("mstuff"));

		TableColumn colStock = new TableColumn<>("재고");
		colStock.setMaxWidth(300);
		colStock.setCellValueFactory(new PropertyValueFactory<>("mstock"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colNo, colName, colPrice, colStuff, colStock);

		totalList();
		//입력
		btnInput.setOnAction(event -> {
			try {
				data.removeAll(data);
				MenuVO mVo = null;
				MenuManageDAO mDao = null;
				if (event.getSource().equals(btnInput)) {
					mVo = new MenuVO(txtName.getText(), Integer.parseInt(txtPrice.getText().trim()), txtStuff.getText(),
							Integer.parseInt(txtStock.getText()));
					mDao = new MenuManageDAO();
					mDao.getMenuregiste(mVo);
					
					if (mDao != null) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("메뉴 정보 입력");
						alert.setHeaderText("메뉴 정보가 성공적으로 추가 되었습니다.");
						alert.setContentText("다음 메뉴의 정보를 입력해 주세요");
						alert.showAndWait();
						totalList();
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
		btnReturn.setOnAction(event -> handlerBtnReturnAction(event));
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		btnEdit.setOnAction(event -> handlerBtnEditAction(event));

		// 테이블에 마우스 선택시 생기는 이벤트
		tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				selectMenu = tableView.getSelectionModel().getSelectedItems();
				selectedIndex = tableView.getSelectionModel().getSelectedIndex();

				try {
					txtName.setText(selectMenu.get(0).getMname());
					txtPrice.setText(selectMenu.get(0).getMprice() + "");
					txtStuff.setText(selectMenu.get(0).getMstuff());
					txtStock.setText(selectMenu.get(0).getMstock() + "");
					editDelete = true;
				} catch (Exception e) {
				}
			}
		});
	}

	public void clear() {
		txtName.clear();
		txtPrice.clear();
		txtStock.clear();
		txtStuff.clear();

	}

	// 수정버튼
	public void handlerBtnEditAction(ActionEvent event) {
		MenuVO svo = new MenuVO();
		MenuManageDAO sDao = new MenuManageDAO();

		try {
			svo = new MenuVO(tableView.getSelectionModel().getSelectedIndex(), txtName.getText(),
					Integer.parseInt(txtPrice.getText().trim()), txtStuff.getText(),
					Integer.parseInt(txtStock.getText()));

			sDao.getMenuUpdate(svo);

			System.out.println("?" + sDao);
			data.removeAll(data);
			totalList();

			txtName.setEditable(true);
			txtPrice.setEditable(true);
			txtStuff.setEditable(true);
			txtStock.setEditable(true);
			clear();
		} catch (Exception e) {
			System.out.println("?? :" + e);
		}
	}

	// 삭제버튼
	public void handlerBtnDeleteAction(ActionEvent event) {
		MenuManageDAO mDao = null;
		mDao = new MenuManageDAO();
		try {
			mDao.getStudentDelete(tableView.getSelectionModel().getSelectedItem().getMno());
			data.removeAll(data);

			totalList();

			btnDelete.setDisable(false);

			handlerBtnClearAction(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 검색버튼
	private void handlerBtnSearchAction(ActionEvent event) {
		MenuVO mVo = new MenuVO();
		MenuManageDAO mDao = null;

		Object[][] totalData = null;

		String searchName = "";
		boolean searchResult = false;
		try {
			searchName = txtSearch.getText().trim();
			mDao = new MenuManageDAO();
			mVo = mDao.getMenuCheck(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("메뉴 정보 검색");
				alert.setHeaderText("메뉴 이름 입력");
				alert.setContentText("메뉴이름을 입력해주세요");
				alert.showAndWait();
			}
			if (!searchName.equals("") && (mVo != null)) {
				ArrayList<String> title;
				ArrayList<MenuVO> list;

				title = mDao.getColumnName();
				int columnCount = title.size();

				list = mDao.getMenuTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];
				if (mVo.getMname().equals(searchName)) {
					txtSearch.clear();
					data.removeAll(data);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						mVo = list.get(index);
						if (mVo.getMname().equals(searchName)) {
							data.add(mVo);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) { // 리스트에 검색 이름이 없을시
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("메뉴정보 검색");
				alert.setHeaderText(searchName + "메뉴가 리스트에 없습니다");
				alert.setContentText("다시검색하세요");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("메뉴정보 검색");
			alert.setHeaderText("검색 오류");
			alert.setContentText("다시검색하세요");
			alert.showAndWait();
		}

	}

	// 이전 창으로 이동
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

	// 종료 메소드
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

	// 입력창 깨끗이 하는 메소드 수정 버튼
	public void handlerBtnClearAction(ActionEvent event) {
		clear();
	}

	public void totalList() {
		Object[][] totalData;

		MenuManageDAO mDao = new MenuManageDAO();
		MenuVO mVo = null;
		ArrayList<String> title;
		ArrayList<MenuVO> list;

		title = mDao.getColumnName();
		int columnCount = title.size();

		list = mDao.getMenuTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			mVo = list.get(index);
			data.add(mVo);
		}

	}
}
