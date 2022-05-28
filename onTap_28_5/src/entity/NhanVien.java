package entity;

import java.util.Objects;

public class NhanVien {
	private String maNV;
	private String hoTen;
	private int tuoi;
	private PhongBan phonBan;
	private float tienLuong;

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public PhongBan getPhonBan() {
		return phonBan;
	}

	public void setPhonBan(PhongBan phonBan) {
		this.phonBan = phonBan;
	}

	public float getTienLuong() {
		return tienLuong;
	}

	public void setTienLuong(float tienLuong) {
		this.tienLuong = tienLuong;
	}

	public NhanVien(String maNV, String hoTen, int tuoi, PhongBan phonBan, float tienLuong) {
		super();
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.tuoi = tuoi;
		this.phonBan = phonBan;
		this.tienLuong = tienLuong;
	}

	public NhanVien() {
		super();
	}

	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", hoTen=" + hoTen + ", tuoi=" + tuoi + ", phonBan=" + phonBan
				+ ", tienLuong=" + tienLuong + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}

}
