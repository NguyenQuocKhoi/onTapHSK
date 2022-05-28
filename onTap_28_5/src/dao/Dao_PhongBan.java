package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.PhongBan;

public class Dao_PhongBan {
	ArrayList<PhongBan> dspb;

	public Dao_PhongBan() {
		dspb = new ArrayList<PhongBan>();
	}

	public ArrayList<PhongBan> getAllPhongBan() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from PhongBan");
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				PhongBan pb = new PhongBan(maPhong, tenPhong);
				dspb.add(pb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dspb;
	}
}
