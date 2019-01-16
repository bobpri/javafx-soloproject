package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffLoginDAO {
	public boolean getLogin(String loginId, String loginPassword) throws Exception {
		String sql = "select * from staff where S_ID = ? and S_PW = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean loginResult = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginPassword);
			System.out.println("asd"+rs);
			rs = pstmt.executeQuery();
			System.out.println("as" + rs);
			if(rs.next()) {
				System.out.println("¾È°¨");
				loginResult = true;
			}
		}catch (SQLException e) {
			System.out.println(e);
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				if (rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
			}catch (SQLException e) {
		}
	}
		return loginResult;
	}
}
