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
	// ��ǰ ���
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
			System.out.println("��ǰ ��� sql ���� :" + e);
		} catch (Exception e) {
			System.out.println("��ǰ��� �ڵ� ���� : + e");
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

	// �����Ѱ� ������Ʈ �ϴ� sql �޼���
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
				alert.setTitle("�޴� ����");
				alert.setHeaderText(svo.getMname().toString() + " �޴� ���� ���� �Ϸ�");
				alert.showAndWait();
				retval = new MenuVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�޴� ���� ����");
				alert.setHeaderText("�޴� ���� ���� ����");
				alert.showAndWait();
				System.out.println(i);
			}
		} catch (SQLException e) {
			System.out.println("�޴����� ��������" + e);
		} catch (Exception e) {
			System.out.println("�޴����� ����" + e);
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

	// ���� ��ư���� �����Ѱ��� ��� �����ϴ� �޼���
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
				alert.setTitle("�޴� ����");
				alert.setHeaderText("�޴� ���� �Ϸ�");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�޴� ����");
				alert.setHeaderText("�޴� ���� ����");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("�޴����� ������ ����" + e);
		} catch (Exception e) {
			System.out.println("�޴� ���� �ڵ� ����" + e);
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

	// ��ǰ �˻�
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
			System.out.println("�˻�sql ���� :" + e);
		} catch (Exception e) {
			System.out.println("�˻� �ڵ� ���� : " + e);
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

	// Į������ db����
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
			System.out.println("Į�� ��������" + e);
		} catch (Exception e) {
			System.out.println("Į��  �ڵ� ����" + e);
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

	// �� ����Ʈ ��� db
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
			System.out.println("����Ʈ ������ ����" + e);
		} catch (Exception e) {
			System.out.println("����Ʈ ����" + e);
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
