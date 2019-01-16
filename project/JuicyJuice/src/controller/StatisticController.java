package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SellVO;
import model.StatisticVO;

public class StatisticController implements Initializable {
	@FXML
	TableView<SellVO> tableView = new TableView<>();
	@FXML
	TableView<StatisticVO> tableView2 = new TableView<>();
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnBarchart;
	/*@FXML
	private Button btnTotal;*/
	@FXML
	private DatePicker dpDate1;
	@FXML
	private DatePicker dpDate2;
	@FXML
	private TextField txtSearch;

	SellVO sell = new SellVO();
	StatisticVO statistic = new StatisticVO();

	ObservableList<SellVO> selldata = FXCollections.observableArrayList();// ���̺� ��
	ObservableList<SellVO> selectSell;

	ObservableList<StatisticVO> selldata2 = FXCollections.observableArrayList();// ���̺� ��

	boolean editDelete = false;

	int selectedIndex;

	int no;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TableColumn colSNo = new TableColumn<>("NO");
		colSNo.setMaxWidth(40);
		colSNo.setCellValueFactory(new PropertyValueFactory<>("sno"));

		TableColumn colSName = new TableColumn<>("�޴��̸�");
		colSName.setMaxWidth(100);
		colSName.setCellValueFactory(new PropertyValueFactory<>("sname"));

		TableColumn colSCount = new TableColumn<>("�� ����");
		colSCount.setMaxWidth(150);
		colSCount.setCellValueFactory(new PropertyValueFactory<>("scount"));

		TableColumn colPayment = new TableColumn<>("�������");
		colPayment.setMaxWidth(300);
		colPayment.setCellValueFactory(new PropertyValueFactory<>("spayment"));

		TableColumn colTotal = new TableColumn<>("�� ����");
		colTotal.setMaxWidth(300);
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		TableColumn colDate = new TableColumn<>("�Ǹ���");
		colDate.setMaxWidth(700);
		colDate.setCellValueFactory(new PropertyValueFactory<>("sdate"));

		tableView.setItems(selldata);
		tableView.getColumns().addAll(colSNo, colSName, colSCount, colPayment, colTotal, colDate);
		// -----------------------------------------------------------------------------------------------------

		TableColumn colSName2 = new TableColumn<>("�޴��̸�");
		colSName2.setMaxWidth(100);
		colSName2.setCellValueFactory(new PropertyValueFactory<>("sname"));

		TableColumn colSCount2 = new TableColumn<>("�� ����");
		colSCount2.setMaxWidth(150);
		colSCount2.setCellValueFactory(new PropertyValueFactory<>("totalcount"));

		TableColumn colTotal2 = new TableColumn<>("�� ����");
		colTotal2.setMaxWidth(1200);
		colTotal2.setCellValueFactory(new PropertyValueFactory<>("totalsell"));

		tableView2.setItems(selldata2);
		tableView2.getColumns().addAll(colSName2, colSCount2, colTotal2);

		totalList();

		btnExit.setOnAction(event -> handlerBtnExitAction(event));
		btnReturn.setOnAction(event -> handlerBtnReturnAction(event));
		btnSearch.setOnAction(event -> handlerBtnSearch2Action(event));
		btnBarchart.setOnAction(event -> handlerBtnBarChartAction(event));
		//btnTotal.setOnAction(event -> handlerBtnTotalAction(event));

	}

	// �������
	public void handlerBtnBarChartAction(ActionEvent event) {
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnBarchart.getScene().getWindow());
			dialog.setTitle("������Ȳ");

			Parent parent = FXMLLoader.load(getClass().getResource("/view/barchart.fxml"));

			BarChart barChart = (BarChart) parent.lookup("#barChart");

			// ���� ���
			XYChart.Series seriesAugust = new XYChart.Series();

			ObservableList AugustList = FXCollections.observableArrayList();
			for (int i = 0; i < selldata.size(); i++) {
				AugustList.add(new XYChart.Data(selldata.get(i).getSdate().toString().substring(5, 7) + "��",
						selldata.get(i).getTotal()));
			}
			seriesAugust.setData(AugustList);

			barChart.getData().add(seriesAugust);

			// ���� ��� ����
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();

		} catch (IOException e) {
		}
	}

	/*public void handlerBtnTotalAction(ActionEvent event) {
		selldata.removeAll(selldata);
		totalList();
	}
*/
	// ����â����
	public void handlerBtnReturnAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/administer.fxml"));
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

	// ���α׷� ����
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

	// �ǸŸ�� ����Ʈ
	public void totalList() {
		Object[][] totalData;

		StatisticDAO sDao = new StatisticDAO();
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

	// ����� ����Ʈ
	public void totalList2() {
		Object[][] totalData;

		StatisticDAO sDao = new StatisticDAO();
		StatisticVO sVo = null;
		ArrayList<String> title;
		ArrayList<StatisticVO> list;

		title = sDao.getColumnName2();
		int columnCount = title.size();

		list = sDao.getSellTotal2();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			selldata2.add(sVo);
		}

	}

	// �ǸŰ˻���ư
	private void handlerBtnSearch2Action(ActionEvent event) {
		selldata2.removeAll(selldata2);

		StatisticVO sVo = new StatisticVO();
		StatisticDAO sDao = null;

		String searchDate = "";
		String searchDate2 = "";

		boolean searchResult = false;
		
		try {
			
			
			searchDate = dpDate1.getValue().toString();
			searchDate2 = dpDate2.getValue().toString();

			sDao = new StatisticDAO();
			selldata2.addAll(sDao.getSellCheck(searchDate, searchDate2));

			
		}catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�Ǹ����� �˻�");
			alert.setHeaderText("�˻� ����");
			alert.setContentText("��¥�� ������ �ּ���");
			alert.showAndWait();
		
		}catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�Ǹ����� �˻�");
			alert.setHeaderText("�˻� ����");
			alert.setContentText("�ٽð˻��ϼ���");
			alert.showAndWait();
			System.out.println(e);
			
		}

	}
}
