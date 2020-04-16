package jdbc.day04.board;

public class BoardDTO {
	
	private int boardno;
	private String fk_userid;
	private String subject;
	private String contents;
	private String writeday;
	private int viewcount;
	private String boardpasswd;	// 글 비밀번호
	private MemberDTO member;
	
	
	// 게시판에 있는 글들을 보여주는 메소드
	public String listInfo(int numOfComments) {	//	글번호	글제목	글쓴이	작성일자	조회수
		if(numOfComments > 0 ) {
			return boardno+"\t"+subject+" [" + numOfComments + "] \t"+member.getName()+"\t"+writeday+"\t"+viewcount;			
		} else {
			return boardno+"\t"+subject+"\t"+member.getName()+"\t"+writeday+"\t"+viewcount;			
		}
	}
	
	// 글내용을 보여주는 메소드
	public void showPost() {
		System.out.println("-------------------------------------------------");		
		System.out.println("[" + this.getBoardno() + "번 글]");
		System.out.println("[글제목] : " + this.getSubject());
		System.out.println("[글내용] : " + this.getContents());
		System.out.println("-------------------------------------------------");		
	}
	
	
	// getter & setter
	public int getBoardno() {
		return boardno;
	}
	
	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}
	
	public String getFk_userid() {
		return fk_userid;
	}
	
	public void setFk_userid(String userid) {
		this.fk_userid = userid;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getWriteday() {
		return writeday;
	}
	
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	
	public int getViewcount() {
		return viewcount;
	}
	
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	
	public String getBoardpasswd() {
		return boardpasswd;
	}
	
	public void setBoardpasswd(String boardpasswd) {
		this.boardpasswd = boardpasswd;
	}
	
	public MemberDTO getMember() {
		return member;
	}

	public void setMember(MemberDTO member) {
		this.member = member;
	}
	
}


