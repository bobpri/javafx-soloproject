package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SellVO;
import model.StatisticVO;

public class StatisticDAO {
	// �Ǹ� �� ����Ʈ ��� db
	public ArrayList<SellVO> getSellTotal() {
		ArrayList<SellVO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sell order by sno");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SellVO sVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sVo = new SellVO();
				sVo.setSno(rs.getInt("sno"));
				sVo.setSname(rs.getString("sname"));
				sVo.setScount(rs.getInt("scount"));
				sVo.setSprice(rs.getInt("sprice"));
				sVo.setSpayment(rs.getString("spayment"));
				sVo.setTotal(rs.getInt("stotal"));
				sVo.setSdate(rs.getString("sdate"));

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
	
	// ��� �� ����Ʈ ��� db
	public ArrayList<StatisticVO> getSellTotal2() {
		ArrayList<StatisticVO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select sname, sum(scount) totalcount, sum(stotal) totalsell from sell");
		sql.append(" group by sname");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StatisticVO sVo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sVo = new StatisticVO();
				sVo.setSname(rs.getString("sname"));
				sVo.setTotalcount(rs.getInt("totalcount"));
				sVo.setTotalsell(rs.getInt("totalsell"));
				
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
	
	// Į������ db����
		public ArrayList<String> getColumnName2() {
			ArrayList<String> columnName = new ArrayList<>();
			StringBuffer sql = new StringBuffer();
			sql.append("select sname, sum(scount) totalcount, sum(stotal) totalsell from sell");
			sql.append(" group by sname");
			
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

	// Į������ db����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sell");
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

	// �Ǹ� �˻�
	public ArrayList<StatisticVO> getSellCheck(String date, String date2) throws Exception {
		ArrayList<StatisticVO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select sname, sum(scount) totalcount, sum(stotal) totalsell from sell where sdate between");
		sql.append(" ? and ? group by sname");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StatisticVO mVo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date);
			pstmt.setString(2, date2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mVo = new StatisticVO();
				mVo.setSname(rs.getString("sname"));
				mVo.setTotalcount(rs.getInt("totalcount"));
				mVo.setTotalsell(rs.getInt("totalsell"));
				list.add(mVo);
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
		return list;
	}

	// �� ���� , �ݾ�

	public int[] setTextCalculation(String date) {
		String sql = "select sum(scount), sum(sprice) from sell where substr(sdate,1,5) = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] sum = null;

		System.out.println(date);

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sum = new int[] { rs.getInt(1), rs.getInt(2) };
			}
		} catch (SQLException e) {
			System.out.println("���� ���� sql����" + e);
		} catch (Exception e) {
			System.out.println("���� ���� �ڵ� ���� " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return sum;
	}

}
