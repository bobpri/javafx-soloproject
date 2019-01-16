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
import model.SellVO;
import model.SellVO;

public class SellDAO {

	// �Ǹ� ���
		public SellVO getSellregiste(SellVO svo) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into Sell ");
			sql.append("values (Sell_seq.nextval, ?, ?, ?, ?, ?, ?)");
 
			Connection con = null;
			PreparedStatement pstmt = null;
			SellVO sVo = svo;

			try {
				con = DBUtil.getConnection();

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, sVo.getSname());
				pstmt.setInt(2, sVo.getScount());
				pstmt.setInt(3, sVo.getSprice());
				pstmt.setString(4, sVo.getSpayment());
				pstmt.setInt(5, sVo.getTotal());
				pstmt.setString(6, sVo.getSdate());

				pstmt.executeUpdate();
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
			return sVo;
		}
	
	//�Ǹ� �� ����Ʈ ��� db
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
		public ArrayList<SellVO> getSellCheck(String date, String date2) throws Exception {
			ArrayList<SellVO> list = new ArrayList<SellVO>();
			StringBuffer sql = new StringBuffer();
			String day;
			sql.append("select * from sell where sdate between");
			sql.append(" ? and ? order by sno");

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			SellVO mVo = null;
			
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, date);
				pstmt.setString(2, date2);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					mVo = new SellVO();
					mVo.setSno(rs.getInt("sno"));
					mVo.setSname(rs.getString("sname"));
					mVo.setScount(rs.getInt("scount"));
					mVo.setSprice(rs.getInt("sprice"));
					mVo.setSpayment(rs.getString("spayment"));
					mVo.setTotal(rs.getInt("stotal"));
					mVo.setSdate(rs.getString("sdate"));
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
		
		// �����Ѱ� ������Ʈ �ϴ� sql �޼���
		public MenuVO getMenuUpdate(MenuVO svo) {
			SellVO sellVO = new SellVO();
			
			String sql2 = "update menu set mname =? , mprice = ? , mstuff = ?, mstock = ? where mno = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			MenuVO retval = null;
			int i = 0;
			int index = svo.getMno()+1;

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
					retval = new MenuVO();
					System.out.println(retval);
				} else {
					
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
}
