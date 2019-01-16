package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MenuVO;
import model.SellVO;

public class SellController implements Initializable {

	@FXML
	private TextField txtSName;
	@FXML
	private TextField txtSPrice;
	@FXML
	private TextField txtSCount;
	@FXML
	private TextField txtSTotal;
	@FXML
	private TextField txtStock;
	@FXML
	private TextField txtSTakeMoney;
	@FXML
	private TextField txtSGiveMoney;
	@FXML
	private TextField txtStuff;
	@FXML
	private TextField txtLastStock;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSInput;
	@FXML
	private Button btnSearch2;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnSClear;
	@FXML
	private Button btnSearch;
	@FXML
	private DatePicker dpDate;
	@FXML
	private DatePicker dpDate1;
	@FXML
	private DatePicker dpDate2;
	@FXML
	private Button btnExit;
	@FXML
	private ComboBox<String> cbHow;
	@FXML
	private TableView<MenuVO> tableView = new TableView<>();
	@FXML
	private TableView<SellVO> selltableView = new TableView<>();
	MenuVO menu = new MenuVO();
	SellVO sell = new SellVO();

	ObservableList<SellVO> selldata = FXCollections.observableArrayList();// ���̺� ��
	ObservableList<SellVO> selectSell;

	ObservableList<MenuVO> data = FXCollections.observableArrayList();// ���̺� ��
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

		TableColumn colName = new TableColumn<>("�޴��̸�");
		colName.setMaxWidth(100);
		colName.setCellValueFactory(new PropertyValueFactory<>("mname"));

		TableColumn colPrice = new TableColumn<>("����");
		colPrice.setMaxWidth(300);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));

		TableColumn colStuff = new TableColumn<>("���");
		colStuff.setMaxWidth(300);
		colStuff.setCellValueFactory(new PropertyValueFactory<>("mstuff"));

		TableColumn colStock = new TableColumn<>("���");
		colStock.setMaxWidth(300);
		colStock.setCellValueFactory(new PropertyValueFactory<>("mstock"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colNo, colName, colPrice, colStuff, colStock);

		menutotalList();
		totalList();

		selltableView.setEditable(false);

		TableColumn colSNo = new TableColumn<>("NO");
		colSNo.setMaxWidth(40);
		colSNo.setCellValueFactory(new PropertyValueFactory<>("sno"));

		TableColumn colSName = new TableColumn<>("�޴��̸�");
		colSName.setMaxWidth(100);
		colSName.setCellValueFactory(new PropertyValueFactory<>("sname"));

		TableColumn colSCount = new TableColumn<>("����");
		colSCount.setMaxWidth(150);
		colSCount.setCellValueFactory(new PropertyValueFactory<>("scount"));

		TableColumn colSPrice = new TableColumn<>("����");
		colSPrice.setMaxWidth(250);
		colSPrice.setCellValueFactory(new PropertyValueFactory<>("sprice"));

		TableColumn colPayment = new TableColumn<>("�������");
		colPayment.setMaxWidth(300);
		colPayment.setCellValueFactory(new PropertyValueFactory<>("spayment"));

		TableColumn colTotal = new TableColumn<>("�� ����");
		colTotal.setMaxWidth(300);
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		TableColumn colDate = new TableColumn<>("�Ǹ���");
		colDate.setMaxWidth(700);
		colDate.setCellValueFactory(new PropertyValueFactory<>("sdate"));

		selltableView.setItems(selldata);
		selltableView.getColumns().addAll(colSNo, colSName, colSCount, colSPrice, colPayment, colTotal, colDate);

		dpDate.setValue(LocalDate.now());
		dpDate.setDisable(false);
		cbHow.setItems(FXCollections.observableArrayList("����", "ī��"));
		txtSName.setDisable(true);
		txtSPrice.setDisable(true);
		txtStock.setDisable(true);
		txtLastStock.setDisable(true);
		txtStuff.setDisable(true);

		btnExit.setOnAction(event -> handlerBtnExitAction(event));
		btnReturn.setOnAction(event -> handlerBtnReturnAction(event));
		btnSInput.setOnAction(event -> handlerBtnInputAction(event));
		btnSClear.setOnAction(event -> handlerBtnClearAction(event));
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		btnSearch2.setOnAction(event -> handlerBtnSearch2Action(event));
		txtSCount.setOnKeyPressed(event -> handlerTxtTotalPressde(event));
		txtSTakeMoney.setOnKeyPressed(event -> handlerTxtGivePressde(event));
		cbHow.setOnAction(event -> handlerCbHowAction(event));

		// ���̺� ���콺 ���ý� ����� �̺�Ʈ
		tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {

				selectMenu = tableView.getSelectionModel().getSelectedItems();
				selectedIndex = tableView.getSelectionModel().getSelectedIndex();

				try {
					txtSName.setText(selectMenu.get(0).getMname());
					txtSPrice.setText(selectMenu.get(0).getMprice() + "");
					txtStock.setText(selectMenu.get(0).getMstock() + "");
					txtStuff.setText(selectMenu.get(0).getMstuff());
					editDelete = true;
				} catch (Exception e) {
				}
			}
		});
		
		//���� ��� �޼ҵ�
				dpDate.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						LocalDate date = dpDate.getValue();
					}
				});

	}

	// ��� �޼ҵ�
	public void stuff() {
		MenuVO svo = new MenuVO();
		SellDAO sDao = new SellDAO();

		try {
			svo = new MenuVO(tableView.getSelectionModel().getSelectedIndex(), txtSName.getText(),
					Integer.parseInt(txtSPrice.getText().trim()), txtStuff.getText(),
					Integer.parseInt(txtLastStock.getText()));

			sDao.getMenuUpdate(svo);

			System.out.println("sell ���" + sDao);
			data.removeAll(data);
			clear();
		} catch (Exception e) {
			System.out.println("sell ��� :" + e);
		}
	}

	// �˻���ư
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
				alert.setTitle("�޴� ���� �˻�");
				alert.setHeaderText("�޴� �̸� �Է�");
				alert.setContentText("�޴��̸��� �Է����ּ���");
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
			if (!searchResult) { // ����Ʈ�� �˻� �̸��� ������
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�޴����� �˻�");
				alert.setHeaderText(searchName + "�޴��� ����Ʈ�� �����ϴ�");
				alert.setContentText("�ٽð˻��ϼ���");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�޴����� �˻�");
			alert.setHeaderText("�˻� ����");
			alert.setContentText("�ٽð˻��ϼ���");
			alert.showAndWait();
		}

	}

	// �ǸŰ˻���ư
	private void handlerBtnSearch2Action(ActionEvent event) {
		selldata.removeAll(selldata);
		
		SellVO sVo = new SellVO();
		SellDAO sDao = null;

		Object[][] totalData = null;

		String searchDate = "";
		String searchDate2 = "";
		
		boolean searchResult = false;
		
	
		try {
			searchDate = dpDate1.getValue().toString();
			searchDate2 = dpDate2.getValue().toString();
			
			sDao = new SellDAO();
			selldata.addAll(sDao.getSellCheck(searchDate, searchDate2));
			
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�Ǹ����� �˻�");
			alert.setHeaderText("�Ǹ����� ��Ȯ�� �Է��ϼ���");
			alert.setContentText("�ٽð˻��ϼ���");
			alert.showAndWait();
			totalList();
			System.out.println(e);
		}

	}

	// ȭ�� �������ϴ� ��ư
	public void handlerBtnClearAction(ActionEvent event) {
		clear();
	}

	public void clear() {
		txtSName.clear();
		txtStuff.clear();
		txtStock.clear();
		txtLastStock.clear();
		txtSPrice.clear();
		txtSCount.clear();
		txtSGiveMoney.clear();
		txtSTakeMoney.clear();
		txtSTotal.clear();
	}

	public void handlerCbHowAction(ActionEvent event) {
		if (cbHow.getSelectionModel().getSelectedItem() == null) {

		} else if (cbHow.getValue().toString().equals("ī��")) {
			txtSGiveMoney.setDisable(true);
			txtSTakeMoney.setDisable(true);
		} else {
			txtSGiveMoney.setDisable(false);
			txtSTakeMoney.setDisable(false);
		}

	}

	private void handlerTxtGivePressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			try {
				int take = Integer.parseInt(txtSTakeMoney.getText());
				int total = Integer.parseInt(txtSTotal.getText());

				int give;

				give = take - total;

				if (give < 0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("�ݾ� ����");
					alert.setHeaderText("���� �ݾ��� ���� ���� ���� �����ϴ�");
					alert.showAndWait();
					btnSInput.setDisable(true);
				} else {
					btnSInput.setDisable(false);
				}
				sell.setSprice(Integer.parseInt(txtSTakeMoney.getText()));
				sell.setScount(Integer.parseInt(txtSTotal.getText()));
				sell.setGiveMoney(give);

				txtSGiveMoney.setText(sell.getGiveMoney() + "");
			} catch (Exception e) {
				System.out.println("asd" + e);
			}
		}

	}

	// ���� �Է½� �Ž����� ���
	private void handlerTxtTotalPressde(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			try {
				int price = Integer.parseInt(txtSPrice.getText());
				int count = Integer.parseInt(txtSCount.getText());

				int total;

				total = price * count;

				sell.setSprice(Integer.parseInt(txtSPrice.getText()));
				sell.setScount(Integer.parseInt(txtSCount.getText()));
				sell.setTotal(total);

				txtSTotal.setText(sell.getTotal() + "");

				int stock = Integer.parseInt(txtStock.getText());

				int lastStock;

				lastStock = stock - count;

				if (lastStock < 0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle(" ��� ����");
					alert.setContentText("��� �����մϴ�");
					alert.showAndWait();

					btnSInput.setDisable(true);
				} else {

					sell.setScount(Integer.parseInt(txtSCount.getText()));

					sell.setStock(lastStock);
					txtLastStock.setText(sell.getStock() + "");
					btnSInput.setDisable(false);
				}
			} catch (Exception e) {
			}
		}

	}

	// ���� â���� �̵�
	private void handlerBtnReturnAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("JuicyJuice");
			mainMtage.setResizable(false);
			mainMtage.setScene(scene);
			Stage oldStage = (Stage) btnReturn.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (Exception e) {
		}

	}

	// ���� ��ư
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

	// �Ǹ� �Է�
	public void handlerBtnInputAction(ActionEvent event) {
		try {
			selldata.removeAll(selldata);
			SellVO sVo = null;
			SellDAO sDao = null;
			if (event.getSource().equals(btnSInput)) {
				sVo = new SellVO(txtSName.getText(), Integer.parseInt(txtSCount.getText()),
						Integer.parseInt(txtSPrice.getText()), cbHow.getSelectionModel().getSelectedItem(),
						Integer.parseInt(txtSTotal.getText()), dpDate.getValue().toString());
				sDao = new SellDAO();
				sDao.getSellregiste(sVo);

				if (sDao != null) {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("�Ǹ� �Է�");
					alert.setHeaderText("�Ǹ� ������ ���������� �߰� �Ǿ����ϴ�.");
					alert.setContentText("���� �Ǹ��� ������ �Է��� �ּ���");
					alert.showAndWait();
					stuff();
					clear();
					totalList();
					menutotalList();
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("�Ǹ� ���� �Է�");
			alert.setHeaderText("�Ǹ� ������ ��Ȯ�� �Է��� �ּ���.");
			alert.setContentText("�Ǹ� ���� �Է� ����");
			alert.showAndWait();
			totalList();
		}
	}

	// �ǸŸ�� ����Ʈ
	public void totalList() {
		Object[][] totalData;

		SellDAO sDao = new SellDAO();
		SellVO sVo = null;
		ArrayList<String> title;
		ArrayList<SellVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getSellTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			selldata.add(sVo);
		}

	}

	// �޴� ��ü ����Ʈ
	public void menutotalList() {
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
