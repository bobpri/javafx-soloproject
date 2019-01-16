package model;

public class SellVO {
	private int sno; //�Ǹ� ��ȣ
	private String sname; //��ǰ�̸�
	private int scount; //�ǸŰ���
	private int sprice; //�Ǹ� ����
	private String spayment;//���� ���
	private int total; // �� �ǸŰ�
	private int giveMoney; // �Ž�����
	private int stock; // �������
	private String sdate; // �Ǹ���
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public SellVO() {
		super();
	}

	public SellVO(int sno, String sname, int scount, int sprice, String spayment, int total) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.scount = scount;
		this.sprice = sprice;
		this.spayment = spayment;
		this.total = total;
	}

	public SellVO(String sname, int scount, int sprice, String spayment, int total) {
		super();
		this.sname = sname;
		this.scount = scount;
		this.sprice = sprice;
		this.spayment = spayment;
		this.total = total;
	}
	
	

	public SellVO(String sname, int scount, int sprice, String spayment, int total, String sdate) {
		super();
		this.sname = sname;
		this.scount = scount;
		this.sprice = sprice;
		this.spayment = spayment;
		this.total = total;
		this.sdate = sdate;
	}

	public SellVO(String sname, int scount, int sprice, String spayment, int total, int giveMoney, int stock,
			String sdate) {
		super();
		this.sname = sname;
		this.scount = scount;
		this.sprice = sprice;
		this.spayment = spayment;
		this.total = total;
		this.giveMoney = giveMoney;
		this.stock = stock;
		this.sdate = sdate;
	}

	public SellVO(int sno, String sname, int scount, int sprice, String spayment, int total, int giveMoney, int stock,
			String sdate) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.scount = scount;
		this.sprice = sprice;
		this.spayment = spayment;
		this.total = total;
		this.giveMoney = giveMoney;
		this.stock = stock;
		this.sdate = sdate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}

	public int getSprice() {
		return sprice;
	}

	public void setSprice(int sprice) {
		this.sprice = sprice;
	}

	public String getSpayment() {
		return spayment;
	}

	public void setSpayment(String spayment) {
		this.spayment = spayment;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getGiveMoney() {
		return giveMoney;
	}

	public void setGiveMoney(int giveMoney) {
		this.giveMoney = giveMoney;
	}
	
	
}
