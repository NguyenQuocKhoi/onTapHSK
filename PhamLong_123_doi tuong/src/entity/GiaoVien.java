package entity;

public class GiaoVien {
	private String magv;
	private String tengv;
	
	public GiaoVien(String magv) {
		super();
		this.magv = magv;
	}

	public GiaoVien(String magv, String tengv) {
		super();
		this.magv = magv;
		this.tengv = tengv;
	}
	public String getMagv() {
		return magv;
	}

	public void setMagv(String magv) {
		this.magv = magv;
	}

	public String getTengv() {
		return tengv;
	}

	public void setTengv(String tengv) {
		this.tengv = tengv;
	}
	

	@Override
	public String toString() {
		return  magv  ;
	}

	

}
