package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.MenuVO;

public class MenuManageDAO {
	// 제품 등록
	public MenuVO getMenuregiste(MenuVO mvo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into menu ");
		sql.append("values (menu_seq.nextval, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		MenuVO mVo = mvo;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, mVo.getMname());
			pstmt.setInt(2, mVo.getMprice());
			pstmt.setString(3, mVo.getMstuff());
			pstmt.setInt(4, mVo.getMstock());

			int i = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("제품 등록 sql 오류 :" + e);
		} catch (Exception e) {
			System.out.println("제품등록 코딩 오류 : + e");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return mVo;
	}

	// 수정한것 업데이트 하는 sql 메서드
	public MenuVO getMenuUpdate(MenuVO svo) {
		String sql2 = "update menu set mname =? , mprice = ? , mstuff = ?, mstock = ? where mno = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		MenuVO retval = null;
		int i = 0;
		int index = svo.getMno() + 1;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, svo.getMname());
			pstmt.setInt(2, svo.getMprice());
			pstmt.setString(3, svo.getMstuff());
			pstmt.setInt(4, svo.getMstock());
			pstmt.setInt(5, index);

			i = pstmt.executeUpdate();
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("메뉴 수정");
				alert.setHeaderText(svo.getMname().toString() + " 메뉴 정보 수정 완료");
				alert.showAndWait();
				retval = new MenuVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("메뉴 정보 수정");
				alert.setHeaderText("메뉴 정보 수정 실패");
				alert.showAndWait();
				System.out.println(i);
			}
		} catch (SQLException e) {
			System.out.println("메뉴수정 쿼리오류" + e);
		} catch (Exception e) {
			System.out.println("메뉴수정 오류" + e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return retval;
	}

	// 삭제 버튼으로 삭제한것을 디비에 저장하는 메서드
	public void getStudentDelete(int no) throws Exception {
		String sql = "delete from menu where mno = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("메뉴 삭제");
				alert.setHeaderText("메뉴 삭제 완료");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("메뉴 삭제");
				alert.setHeaderText("메뉴 삭제 실패");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("메뉴삭제 쿼리문 오류" + e);
		} catch (Exception e) {
			System.out.println("메뉴 삭제 코딩 오류" + e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
	}

	// 제품 검색
	public MenuVO getMenuCheck(String name) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from menu where mname like ");
		sql.append("? order by mno desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MenuVO mVo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mVo = new MenuVO();
				mVo.setMno(rs.getInt("mno"));
				mVo.setMname(rs.getString("mname"));
				mVo.setMprice(rs.getInt("mno"));
				mVo.setMstuff(rs.getString("mstuff"));
				mVo.setMstock(rs.getInt("mstock"));
			}
		} catch (SQLException e) {
			System.out.println("검색sql 오류 :" + e);
		} catch (Exception e) {
			System.out.println("검색 코딩 오류 : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return mVo;
	}

	// 칼럼내용 db저장
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from menu");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			System.out.println("칼럼 쿼리오류" + e);
		} catch (Exception e) {
			System.out.println("칼럼  코딩 오류" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return columnName;

	}

	// 총 리스트 출력 db
	public ArrayList<MenuVO> getMenuTotal() {
		ArrayList<MenuVO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from menu order by mno");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MenuVO mVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mVo = new MenuVO();
				mVo.setMno(rs.getInt("mno"));
				mVo.setMname(rs.getString("mname"));
				mVo.setMprice(rs.getInt("mprice"));
				mVo.setMstuff(rs.getString("mstuff"));
				mVo.setMstock(rs.getInt("mstock"));
				list.add(mVo);
			}
		} catch (SQLException e) {
			System.out.println("리스트 쿼리문 오류" + e);
		} catch (Exception e) {
			System.out.println("리스트 오류" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

}
