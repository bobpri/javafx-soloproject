package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StaffVO;

public class StaffManageDAO {
	//����Ʈ�� ���� �Է��ϴ� �޼���
	public boolean getStaffregiste(StaffVO svo) throws Exception {
		String day = svo.getS_birth();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into staff ");
		sql.append("values (staff_seq.nextval, ?, ?, ?, ?, to_date('" + day + "','yyyy-mm-dd'))");
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean staffSucess = false;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getS_name());
			pstmt.setString(2, svo.getS_number());
			pstmt.setString(3, svo.getS_id());
			pstmt.setString(4, svo.getS_pw());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ���");
				alert.setHeaderText("���� ��� �Ϸ�");
				alert.setContentText("���� ��� ����!");
				alert.showAndWait();

				staffSucess = true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ���");
				alert.setHeaderText("���� ��� ����");
				alert.setContentText("���� ��� ����!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("������sql���� : " + e);
		} catch (Exception e) {
			System.out.println("������ db�������" + e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return staffSucess;
	}
	//���̵� �ߺ� Ȯ�� �޼ҵ�
	public boolean getIdOverlap(String idOverlap) throws Exception {
		String sql = "select * from staff where s_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean idOverlapResult = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idOverlap);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idOverlapResult = true;
			}
		} catch (SQLException e) {
			System.out.println("�ߺ�Ȯ�� ��������" + e);
		} catch (Exception e) {
			System.out.println("�ߺ�Ȯ�� ����" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return idOverlapResult;
	}
	//�����Ѱ� ������Ʈ �ϴ� sql �޼���
	public StaffVO getStaffUpdate(StaffVO svo) {
		String day = svo.getS_birth();
		String sql2 = "update staff set s_name =? , s_number = ? , s_id = ?, s_pw = ?, s_birth = ? where s_no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		StaffVO retval = null;
		int i = 0;
		int index = svo.getS_no() + 1;
		
		System.out.println(svo.getS_no());
		
		try {
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, svo.getS_name());
			pstmt.setString(2, svo.getS_number());
			pstmt.setString(3, svo.getS_id());
			pstmt.setString(4, svo.getS_pw());
			pstmt.setString(5, svo.getS_birth().toString());
			pstmt.setInt(6, index);
			
			
			i = pstmt.executeUpdate();
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�������� �Է�");
				alert.setHeaderText("���� ���� ���� �Ϸ�");
				alert.setContentText("���� ���� ���� ����!!");
				alert.showAndWait();
				// �����ϸ� vo ����
				retval = new StaffVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���� ���� ����");
				alert.setHeaderText("���� ���� ���� ����");
				alert.setContentText("���� ���� ���� ����!!");
				alert.showAndWait();
				System.out.println(i);
				// �����ϸ� vo ����
			}
		} catch (SQLException e) {
			System.out.println("�������� ��������" + e);
		} catch (Exception e) {
			System.out.println("�������� ����" + e);
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
	//���� ��ư���� �����Ѱ��� ��� �����ϴ� �޼���
	public void getStudentDelete(int no) throws Exception {
		String sql = "delete from staff where s_no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();
			System.out.println("1231");
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ���� ����");
				alert.setHeaderText("���� ���� ���� �Ϸ�");
				alert.setContentText("���� ���� ���� ����!!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���� ���� ����");
				alert.setHeaderText("���� ���� ���� ����");
				alert.setContentText("���� ���� ���� ����!!");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("�������� ������ ����" + e);
		} catch (Exception e) {
			System.out.println("���� ���� �ڵ� ����" + e);
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
	//�� ����Ʈ ���̺� �信 ����ϴ� �޼��� 
	public ArrayList<StaffVO> getStaffTotal() {
		ArrayList<StaffVO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from staff order by s_no");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StaffVO sVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sVo = new StaffVO();
				sVo.setS_no(rs.getInt("s_no"));
				sVo.setS_name(rs.getString("s_name"));
				sVo.setS_birth(rs.getString("s_birth"));
				sVo.setS_number(rs.getString("s_number"));
				sVo.setS_id(rs.getString("s_id"));
				sVo.setS_pw(rs.getString("s_pw"));
				list.add(sVo);
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
	//Į���̸� �Է��ϴ� �޼���
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from staff");
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
	//�˻� ��ư �޼���
	public StaffVO getStaffCheck(String name) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from staff where s_name like");
		sql.append("? order by s_no desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StaffVO sVo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sVo = new StaffVO();
				sVo.setS_no(rs.getInt("s_no"));
				sVo.setS_name(rs.getString("s_name"));
				sVo.setS_birth(rs.getString("s_birth"));
				sVo.setS_number(rs.getString("s_number"));
				sVo.setS_id(rs.getString("s_id"));
				sVo.setS_pw(rs.getString("s_pw"));
			}
		} catch (SQLException e) {
			System.out.println("�˻�sql����" + e);
		} catch (Exception e) {
			System.out.println("�˻��ڵ�����" + e);
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
		return sVo;

	}

}
