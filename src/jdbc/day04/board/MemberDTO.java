package jdbc.day04.board;

import java.sql.Date;

public class MemberDTO {

	private int userseq; 
	private String userid;
	private String passwd;
	private String name;
	private String mobile;
	private int point;
	private String registerday;
	private int status;
	
	private Date regDate;
	
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public int getUserseq() {
		return userseq;
	}
	
	public void setUserseq(int userseq) {
		this.userseq = userseq;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getRegisterday() {
		return registerday;
	}
	
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public String memberInfoForAdmin() {
//		String hiddenPasswd = passwd;
//		String result = String.format("%-"+passwd.length()+"s", hiddenPasswd.substring(0,4)).replace(' ', '*');
		String active = (status == 1) ? "가입" : "탈퇴"; 
		String memberId = userid.length() > 7 ? userid + "\t" : userid + "\t\t";
		String passwd = this.passwd.length() > 7 ? this.passwd + "\t" : this.passwd + "\t\t";
		return memberId + passwd + name + "\t" + mobile + "\t" + point + "\t" + registerday + "\t" + active;

//		return memberId + result + "\t" + name + "\t" + mobile + "\t" + point + "\t" + registerday + "\t" + active;
	}
	
}
