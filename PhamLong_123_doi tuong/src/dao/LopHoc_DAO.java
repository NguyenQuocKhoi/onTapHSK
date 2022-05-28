package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import connectDB.Database;
import entity.GiaoVien;
import entity.LopHoc;

public class LopHoc_DAO {
	ArrayList<LopHoc> dslop;
	LopHoc lh;
	public LopHoc_DAO(){
		dslop= new ArrayList<LopHoc>();	
		
	}	
	public  ArrayList<LopHoc> docTuBang()  {
		
			
		try {
		Connection con = Database.getInstance().getConnection();
		 String sql = "Select * from lophoc";
		 Statement statement = con.createStatement();
	      // Thực thi câu lệnh SQL trả v�? đối tượng ResultSet.
	      ResultSet rs = statement.executeQuery(sql);
	      // Duyệt trên kết quả trả v�?.
	      while (rs.next()) {// Di chuyển con tr�? xuống bản ghi kế tiếp.
	          String ma = rs.getString(1);
	          String ten= rs.getString(2);
	          String gv = rs.getString(3);
	          int siso = rs.getInt(4);
	          LopHoc s = new LopHoc(ma,ten,new GiaoVien(gv),siso);
			  dslop.add(s);
	      }
		} catch (SQLException e) {
			e.printStackTrace();
	      // �?óng kết nối
		}
	return dslop;
 }
	public ArrayList<LopHoc> getLopTheoMaLop(String mlop) {
		
		Connection con = Database.getInstance().getConnection();
		PreparedStatement stmt =null;
		try {						
			String sql = "Select * from lophoc where maLop = ?";
			//String sql = "Select * from lophoc";
			stmt=con.prepareStatement(sql);
			stmt.setString(1, mlop);
			// Thá»±c thi cÃ¢u lá»‡nh SQL tráº£ vá»� Ä‘á»‘i tÆ°á»£ng ResultSet.
			ResultSet rs = stmt.executeQuery();
			// Duyá»‡t trÃªn káº¿t quáº£ tráº£ vá»�.
			while (rs.next()) {// Di chuyá»ƒn con trá»� xuá»‘ng báº£n ghi káº¿ tiáº¿p.
				String ma = rs.getString(1);
				System.out.println(ma);
		          String ten= rs.getString(2);
		          String gv = rs.getString(3);
		          int siso = rs.getInt(4);
		          LopHoc s = new LopHoc(ma,ten,new GiaoVien(gv),siso);
				  dslop.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();				
		}
		
		return dslop;
	}
	public boolean cresate(LopHoc p) {
		Connection con = Database.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into LopHoc values(?, ?, ?, ?)");
			stmt.setString(1,p.getMaLop());
			stmt.setString(2,p.getTenLop());
			stmt.setString(3,p.getGiaoVien().getMagv());
			stmt.setInt(4,p.getSiso());
			n = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0; 
	}
	//U: Update
		public boolean update(LopHoc p) {
			Connection con = Database.getInstance().getConnection();
			PreparedStatement stmt = null;
			int n = 0;
			try {
				stmt = con.prepareStatement("update LopHoc set tenLop = ?, maGiaoVien = ?, siSo = ? where maLop = ?");
				stmt.setString(1, p.getTenLop());
				stmt.setString(2, p.getGiaoVien().getMagv());
				stmt.setInt(3,p.getSiso());
				stmt.setString(4, p.getMaLop());
				
				n = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return n > 0; 
		}
		
		//D: Delete
		public boolean delete(String mlop) {
			Connection con = Database.getInstance().getConnection();
			PreparedStatement stmt = null;
			int n = 0;
			try {
				stmt = con.prepareStatement("delete from LopHoc where maLop = ?");
				stmt.setString(1, mlop);
				n = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return n > 0; 
		}	
}
