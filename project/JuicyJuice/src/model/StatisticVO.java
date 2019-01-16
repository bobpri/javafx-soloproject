package model;

public class StatisticVO {
	private String sname;
	private int totalcount;
	private int totalsell;

	public StatisticVO() {
		super();
	}

	public StatisticVO(String sname, int totalcount, int totalsell) {
		super();
		this.sname = sname;
		this.totalcount = totalcount;
		this.totalsell = totalsell;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getTotalsell() {
		return totalsell;
	}

	public void setTotalsell(int totalsell) {
		this.totalsell = totalsell;
	}

}
