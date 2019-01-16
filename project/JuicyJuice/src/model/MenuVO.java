package model;

public class MenuVO {
	private int mno; //�շϹ�ȣ
	private String mname; // �޴��̸�
	private int mprice; //�޴� ����
	private String mstuff; // �޴� ���
	private int mstock; // �޴� ��������
	
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