package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import connectDB.Database;
import entity.GiaoVien;


public class GiaoVien_DAO {
	List<entity.GiaoVien> dsgv;
	public GiaoVien_DAO(){
		dsgv= new ArrayList<entity.GiaoVien>();	
		
	}	
	public  List<entity.GiaoVien> docTuBang()  {
			
		try {
		Connection con = Database.getInstance().getConnection();
		 String sql = "Select * from giaovien";
		 Statement statement = con.createStatement();
	      // Thực thi câu lệnh SQL trả v�? đối tượng ResultSet.
	      ResultSet rs = statement.executeQuery(sql);
	      // Duyệt trên kết quả trả v�?.
	      while (rs.next()) {// Di chuyển con tr�? xuống bản ghi kế tiếp.
	          String ma = rs.getString("maGiaoVien");
	          String ten= rs.getString(2);
	         
	          GiaoVien s = new GiaoVien(ma,ten);
	         
			  dsgv.add(s);
	      }
		} catch (SQLException e) {
			e.printStackTrace();
	      // �?óng kết nối
		}
	return dsgv;
 }

}
