package model;

public class MenuVO {
	private int mno; //둥록번호
	private String mname; // 메뉴이름
	private int mprice; //메뉴 가격
	private String mstuff; // 메뉴 재료
	private int mstock; // 메뉴 남은개수
	
	public MenuVO() {
		super();
	}

	public MenuVO(int mno, String mname, int mprice, String mstuff, int mstock) {
		super();
		this.mno = mno;
		this.mname = mname;
		this.mprice = mprice;
		this.mstuff = mstuff;
		this.mstock = mstock;
	}
	
	

	public MenuVO(int mno, String mname, int mprice, int mstock) {
		super();
		this.mno = mno;
		this.mname = mname;
		this.mprice = mprice;
		this.mstock = mstock;
	}

	public MenuVO(String mname, int mprice, String mstuff, int mstock) {
		super();
		this.mname = mname;
		this.mprice = mprice;
		this.mstuff = mstuff;
		this.mstock = mstock;
	}
	
	


	public MenuVO(int mno, int mstock) {
		super();
		this.mno = mno;
		this.mstock = mstock;
	}

	public MenuVO(String mname, int mprice) {
		super();
		this.mname = mname;
		this.mprice = mprice;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getMprice() {
		return mprice;
	}

	public void setMprice(int mprice) {
		this.mprice = mprice;
	}

	public String getMstuff() {
		return mstuff;
	}

	public void setMstuff(String mstuff) {
		this.mstuff = mstuff;
	}

	public int getMstock() {
		return mstock;
	}

	public void setMstock(int mstock) {
		this.mstock = mstock;
	}
	
	
	
}