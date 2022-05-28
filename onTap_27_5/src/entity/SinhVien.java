package entity;

import java.util.Objects;

public class SinhVien {
	private String maSV;
	private String hoTen;
	private boolean phai;
	private int tuoi;
	private LopHoc lopHoc;
	private String email;

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public boolean isPhai() {
		return phai;
	}

	public void setPhai(boolean phai) {
		this.phai = phai;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public LopHoc getLopHoc() {
		return lopHoc;
	}

	public void setLopHoc(LopHoc lopHoc) {
		this.lopHoc = lopHoc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SinhVien(String maSV, String hoTen, boolean phai, int tuoi, LopHoc lopHoc, String email) {
		super();
		this.maSV = maSV;
		this.hoTen = hoTen;
		this.phai = phai;
		this.tuoi = tuoi;
		this.lopHoc = lopHoc;
		this.email = email;
	}

	public SinhVien() {
		super();
	}

	public SinhVien(String maSV) {
		super();
		this.maSV = maSV;
	}

	@Override
	public String toString() {
		return "SinhVien [maSV=" + maSV + ", hoTen=" + hoTen + ", phai=" + phai + ", tuoi=" + tuoi + ", lopHoc="
				+ lopHoc + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SinhVien other = (SinhVien) obj;
		return Objects.equals(maSV, other.maSV);
	}

}
