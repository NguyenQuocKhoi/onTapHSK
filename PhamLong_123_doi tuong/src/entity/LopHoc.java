package entity;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class LopHoc {
	private String maLop;
	private String tenLop;
	private GiaoVien giaoVien;
	private int siso;

	public LopHoc(String maLop, String tenLop, GiaoVien giaoVien, int siso) {
		this.maLop = maLop;
		this.tenLop = tenLop;
		this.giaoVien = giaoVien;
		this.siso = siso;
	}
	
	public LopHoc(String maLop) {
		super();
		this.maLop = maLop;
	}

	public LopHoc(GiaoVien giaoVien) {
		super();
		this.giaoVien = giaoVien;
	}

	public int getSiso() {
		return siso;
	}
	public void setSiso(int siso) {
		this.siso = siso;
	}
	public String getMaLop() {
		return maLop;
	}
	
	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}
	
	public String getTenLop() {
		return tenLop;
	}
	
	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	
	public GiaoVien getGiaoVien() {
		return giaoVien;
	}
	
	public void setGiaoVien(GiaoVien giaoVien) {
		this.giaoVien = giaoVien;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maLop == null) ? 0 : maLop.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LopHoc other = (LopHoc) obj;
		if (maLop == null) {
			if (other.maLop != null)
				return false;
		} else if (!maLop.equals(other.maLop))
			return false;
		return true;
	}
	
}