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

	ObservableList<SellVO> selldata = FXCollections.observableArrayList();// 테이블 뷰
	ObservableList<SellVO> selectSell;

	ObservableList<StatisticVO> selldata2 = FXCollections.observableArrayList();// 테이블 뷰

	boolean editDelete = false;

	int selectedIndex;

	int no;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TableColumn colSNo = new TableColumn<>("NO");
		colSNo.setMaxWidth(40);
		colSNo.setCellValueFactory(new PropertyValueFactory<>("sno"));

		TableColumn colSName = new TableColumn<>("메뉴이름");
		colSName.setMaxWidth(100);
		colSName.setCellValueFactory(new PropertyValueFactory<>("sname"));

		TableColumn colSCount = new TableColumn<>("총 개수");
		colSCount.setMaxWidth(150);
		colSCount.setCellValueFactory(new PropertyValueFactory<>("scount"));

		TableColumn colPayment = new TableColumn<>("결제방법");
		colPayment.setMaxWidth(300);
		colPayment.setCellValueFactory(new PropertyValueFactory<>("spayment"));

		TableColumn colTotal = new TableColumn<>("총 가격");
		colTotal.setMaxWidth(300);
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		TableColumn colDate = new TableColumn<>("판매일");
		colDate.setMaxWidth(700);
		colDate.setCellValueFactory(new PropertyValueFactory<>("sdate"));

		tableView.setItems(selldata);
		tableView.getColumns().addAll(colSNo, colSName, colSCount, colPayment, colTotal, colDate);
		// -----------------------------------------------------------------------------------------------------

		TableColumn colSName2 = new TableColumn<>("메뉴이름");
		colSName2.setMaxWidth(100);
		colSName2.setCellValueFactory(new PropertyValueFactory<>("sname"));

		TableColumn colSCount2 = new TableColumn<>("총 개수");
		colSCount2.setMaxWidth(150);
		colSCount2.setCellValueFactory(new PropertyValueFactory<>("totalcount"));

		TableColumn colTotal2 = new TableColumn<>("총 가격");
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

	// 매출통계
	public void handlerBtnBarChartAction(ActionEvent event) {
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnBarchart.getScene().getWindow());
			dialog.setTitle("매출현황");

			Parent parent = FXMLLoader.load(getClass().getResource("/view/barchart.fxml"));

			BarChart barChart = (BarChart) parent.lookup("#barChart");

			// 월별 통계
			XYChart.Series seriesAugust = new XYChart.Series();

			ObservableList AugustList = FXCollections.observableArrayList();
			for (int i = 0; i < selldata.size(); i++) {
				AugustList.add(new XYChart.Data(selldata.get(i).getSdate().toString().substring(5, 7) + "월",
						selldata.get(i).getTotal()));
			}
			seriesAugust.setData(AugustList);

			barChart.getData().add(seriesAugust);

			// 매출 통계 종료
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
	// 이전창으로
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

	// 프로그램 종료
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

	// 판매목록 리스트
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

	// 통계목록 리스트
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

	// 판매검색버튼
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
			alert.setTitle("판매정보 검색");
			alert.setHeaderText("검색 오류");
			alert.setContentText("날짜를 선택해 주세요");
			alert.showAndWait();
		
		}catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("판매정보 검색");
			alert.setHeaderText("검색 오류");
			alert.setContentText("다시검색하세요");
			alert.showAndWait();
			System.out.println(e);
			
		}

	}
}
