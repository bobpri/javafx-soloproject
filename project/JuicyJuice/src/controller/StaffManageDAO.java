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
	//리스트에 정보 입력하는 메서드
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
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 완료");
				alert.setContentText("직원 등록 성공!");
				alert.showAndWait();

				staffSucess = true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("직원 등록 실패!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("스테프sql오류 : " + e);
		} catch (Exception e) {
			System.out.println("스테프 db연결오류" + e);
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
	//아이디 중복 확인 메소드
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
			System.out.println("중복확인 쿼리오류" + e);
		} catch (Exception e) {
			System.out.println("중복확인 오류" + e);
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
	//수정한것 업데이트 하는 sql 메서드
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
				alert.setTitle("직원정보 입력");
				alert.setHeaderText("직원 정보 수정 완료");
				alert.setContentText("직원 정보 수정 성공!!");
				alert.showAndWait();
				// 성공하면 vo 생성
				retval = new StaffVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 정보 수정 실패");
				alert.setContentText("직원 정보 수정 실패!!");
				alert.showAndWait();
				System.out.println(i);
				// 성공하면 vo 생성
			}
		} catch (SQLException e) {
			System.out.println("직원수정 쿼리오류" + e);
		} catch (Exception e) {
			System.out.println("직원수정 오류" + e);
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
	//삭제 버튼으로 삭제한것을 디비에 저장하는 메서드
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
				alert.setTitle("직원 정보 삭제");
				alert.setHeaderText("직원 정보 삭제 완료");
				alert.setContentText("직원 정보 삭제 성공!!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 삭제");
				alert.setHeaderText("직원 정보 삭제 실패");
				alert.setContentText("직원 정보 삭제 실패!!");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("직원삭제 쿼리문 오류" + e);
		} catch (Exception e) {
			System.out.println("직원 삭제 코딩 오류" + e);
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
	//총 리스트 테이블 뷰에 출력하는 메서드 
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
	//칼럼이름 입력하는 메서드
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
	//검색 버튼 메서드
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
			System.out.println("검색sql오류" + e);
		} catch (Exception e) {
			System.out.println("검색코딩오류" + e);
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
