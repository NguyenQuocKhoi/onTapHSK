package entity;

import java.util.Objects;

public class PhongBan {
	private String maPhong;
	private String tenPhong;

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public PhongBan(String maPhong, String tenPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
	}

	public PhongBan() {
		super();
	}

	public PhongBan(String maPhong) {
		super();
		this.maPhong = maPhong;
	}

	@Override
	public String toString() {
		return "PhongBan [maPhong=" + maPhong + ", tenPhong=" + tenPhong + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhong);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhongBan other = (PhongBan) obj;
		return Objects.equals(maPhong, other.maPhong);
	}

}
