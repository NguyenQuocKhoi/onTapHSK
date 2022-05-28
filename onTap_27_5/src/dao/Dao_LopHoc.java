package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LopHoc;

public class Dao_LopHoc {
	ArrayList<LopHoc> dslh;

	public Dao_LopHoc() {
		dslh = new ArrayList<LopHoc>();
	}

	public ArrayList<LopHoc> getAllLopHoc() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("Select * from LopHoc");
			while (rs.next()) {
				String maLop = rs.getString(1);
				String tenLop = rs.getString(2);
				LopHoc lh = new LopHoc(maLop, tenLop);
				dslh.add(lh);
			}
		} catch (Exception e) {

		}
		return dslh;
	}

}
