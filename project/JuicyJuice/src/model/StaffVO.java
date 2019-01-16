package model;

public class StaffVO {
	private int s_no;
	private String s_name;
	private String s_birth;
	private String s_number;
	private String s_id;
	private String s_pw;

	public StaffVO() {
		super();
	}

	public StaffVO(int s_no, String s_name, String s_birth, String s_number, String s_id, String s_pw) {
		super();
		this.s_no = s_no;
		this.s_name = s_name;
		this.s_birth = s_birth;
		this.s_number = s_number;
		this.s_id = s_id;
		this.s_pw = s_pw;
	}

	public StaffVO(String s_name, String s_number, String s_id, String s_pw, String s_birth) {
		super();
		this.s_name = s_name;
		this.s_birth = s_birth;
		this.s_number = s_number;
		this.s_id = s_id;
		this.s_pw = s_pw;
	}


	public StaffVO(int s_no, String s_name, String s_number, String s_id) {
		super();
		this.s_no = s_no;
		this.s_name = s_name;
		this.s_number = s_number;
		this.s_id = s_id;
	}

	public StaffVO(String s_name, String s_number, String s_id) {
		super();
		this.s_name = s_name;
		this.s_number = s_number;
		this.s_id = s_id;
	}
	
	

	public StaffVO(String s_name, String s_birth, String s_number, String s_pw) {
		super();
		this.s_name = s_name;
		this.s_birth = s_birth;
		this.s_number = s_number;
		this.s_pw = s_pw;
	}

	public StaffVO(int s_no, String s_name, String s_number, String s_id, String s_pw) {
		super();
		this.s_no = s_no;
		this.s_name = s_name;
		this.s_number = s_number;
		this.s_id = s_id;
		this.s_pw = s_pw;
	}

	public int getS_no() {
		return s_no;
	}

	public void setS_no(int s_no) {
		this.s_no = s_no;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_birth() {
		return s_birth;
	}

	public void setS_birth(String s_birth) {
		this.s_birth = s_birth;
	}

	public String getS_number() {
		return s_number;
	}

	public void setS_number(String s_number) {
		this.s_number = s_number;
	}

	public String getS_id() {
		return s_id;
	}

	public void setS_id(String s_id) {
		this.s_id = s_id;
	}

	public String getS_pw() {
		return s_pw;
	}

	public void setS_pw(String s_pw) {
		this.s_pw = s_pw;
	}

}
