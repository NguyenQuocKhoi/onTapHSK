package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.PhongBan;

public class Dao_NhanVien {

	ArrayList<NhanVien> dsnv;
	NhanVien nv;

	public Dao_NhanVien() {
		dsnv = new ArrayList<NhanVien>();
	}

	public ArrayList<NhanVien> getALLNhanVien() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from NhanVien");
			while (rs.next()) {
				String ma = rs.getString(1);
				String hoTen = rs.getString(2);
				int tuoi = rs.getInt(3);
				String maPhong = rs.getString(4);
				float tienLuong = rs.getFloat(5);
				nv = new NhanVien(ma, hoTen, tuoi, new PhongBan(maPhong), tienLuong);
				dsnv.add(nv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsnv;
	}

	public boolean themNhanVien(NhanVien nv) {
		PreparedStatement stmt = null;
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			stmt = con.prepareStatement("insert into NhanVien values (?,?,?,?,?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getHoTen());
			stmt.setInt(3, nv.getTuoi());
			stmt.setString(4, nv.getPhonBan().getMaPhong());
			stmt.setFloat(5, nv.getTienLuong());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean xoaNhanVien(String ma) {
		PreparedStatement stmt = null;
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			stmt = con.prepareStatement("delete from NhanVien where maNV = ?");
			stmt.setString(1, ma);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean suaNhanVien(NhanVien nv) {
		PreparedStatement stmt = null;
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			stmt = con.prepareStatement(
					"Update NhanVien set hoten = ?, tuoi = ?, maPhong = ?, tienLuong = ? where maNV = ?");
			stmt.setString(1, nv.getHoTen());
			stmt.setInt(2, nv.getTuoi());
			stmt.setString(3, nv.getPhonBan().getMaPhong());
			stmt.setFloat(4, nv.getTienLuong());
			stmt.setString(5, nv.getMaNV());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public ArrayList<NhanVien> getNhanVienByMa(String ma) {
		PreparedStatement stmt;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			stmt = con.prepareStatement("Select * from NhanVien where maNV = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String hoTen = rs.getString(2);
				int tuoi = rs.getInt(3);
				String maPhong = rs.getString(4);
				float tienLuong = rs.getFloat(5);
				nv = new NhanVien(maNV, hoTen, tuoi, new PhongBan(maPhong), tienLuong);
				dsnv.add(nv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsnv;
	}

}
