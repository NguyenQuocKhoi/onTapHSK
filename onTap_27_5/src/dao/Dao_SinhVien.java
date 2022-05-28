package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LopHoc;
import entity.SinhVien;

public class Dao_SinhVien {
	ArrayList<SinhVien> dssv;
	SinhVien sv;

	public Dao_SinhVien() {
		dssv = new ArrayList<SinhVien>();
	}

	public ArrayList<SinhVien> getAllSinhVien() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("Select * from SinhVien");
			while (rs.next()) {
				String maSV = rs.getString(1);
				String hoTen = rs.getString(2);
				boolean phai = rs.getBoolean(3);
				int tuoi = rs.getInt(4);
				String gV = rs.getString(5);
				String email = rs.getString(6);
				sv = new SinhVien(maSV, hoTen, phai, tuoi, new LopHoc(gV), email);
				dssv.add(sv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dssv;
	}

	public boolean themSinhVien(SinhVien sv) {
		int n = 0;
		PreparedStatement pr = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			pr = con.prepareStatement("insert into SinhVien values(?,?,?,?,?,?)");
			pr.setString(1, sv.getMaSV());
			pr.setString(2, sv.getHoTen());
			pr.setBoolean(3, sv.isPhai());
			pr.setInt(4, sv.getTuoi());
			pr.setString(5, sv.getLopHoc().getMaLop());
			pr.setString(6, sv.getEmail());
			n = pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean xoaSinhVien(String ma) {
		int n = 0;
		PreparedStatement pr = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			pr = con.prepareStatement("Delete from SinhVien where maSV = ?");
			pr.setString(1, ma);
			n = pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean suaSinhVien(SinhVien sv) {
		int n = 0;
		PreparedStatement pr = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			pr = con.prepareStatement(
					"update SinhVien set hoTen = ?, phai = ?, tuoi = ?, maLop = ?, email = ? where masv = ?");
			pr.setString(1, sv.getHoTen());
			pr.setBoolean(2, sv.isPhai());
			pr.setInt(3, sv.getTuoi());
			pr.setString(4, sv.getLopHoc().getMaLop());
			pr.setString(5, sv.getEmail());
			pr.setString(6, sv.getMaSV());
			n = pr.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public ArrayList<SinhVien> getSinhVienByMa(String ma) {
		PreparedStatement pr = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			pr = con.prepareStatement("Select * from SinhVien where masv = ?");
			pr.setString(1, ma);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				String maSV = rs.getString(1);
				String hoTen = rs.getString(2);
				boolean phai = rs.getBoolean(3);
				int tuoi = rs.getInt(4);
				String gV = rs.getString(5);
				String email = rs.getString(6);
				sv = new SinhVien(maSV, hoTen, phai, tuoi, new LopHoc(gV), email);
				dssv.add(sv);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dssv;
	}

}
