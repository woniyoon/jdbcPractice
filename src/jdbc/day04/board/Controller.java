package jdbc.day04.board;

import java.util.*;
import java.sql.*;
import jdbc.connection.MyDBConnection;

public class Controller {

	InterMemberDAO mbrDAO = new MemberDAO();
	InterBoardDAO brdDAO = new BoardDAO();
	
	public int signUp(Scanner sc) {
		int result=0;
		
		System.out.println("\n >>> --- 회원가입 --- <<<");
		
		System.out.print("1. 아이디 : ");
		String userid = sc.nextLine();
		System.out.print("2. 암호 : ");
		String passwd = sc.nextLine();
		System.out.print("3. 회원명 : ");
		String name = sc.nextLine();
		System.out.print("4. 연락처 : ");
		String mobile = sc.nextLine();
	
		MemberDTO member = new MemberDTO();
		member.setUserid(userid);
		member.setPasswd(passwd);
		member.setName(name);
		member.setMobile(mobile);
				
		result = mbrDAO.register(member);	
		
		if(result == 1) {
			
			Connection conn = MyDBConnection.getConn();
			
			do {
				System.out.println(">>> 회원가입을 정말로 하시겠습니까? [Y/N] ");
				String answer = sc.nextLine();
				
				try {
					if("Y".equalsIgnoreCase(answer)) {
						conn.commit();
						break;
					} else if("N".equalsIgnoreCase(answer)) {
						conn.rollback();
						result = -1;
						break;
					} else {
						System.out.println(">>> Y나 N 둘 중 하나만 입력해");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} while (true);
		}
		return result;
	}
	
	
	public MemberDTO login(Scanner sc) {
		MemberDTO member = null;
		
		System.out.print("▷ 아이디 : ");
		String userid = sc.nextLine();
		System.out.print("▷ 암호 : ");
		String passwd = sc.nextLine();
				
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userid", userid);
		paraMap.put("passwd", passwd);
		
		member = mbrDAO.login(paraMap);
		
		return member;
	}

	
	public int updatePoint(String userid) {
		int result = 0;
		
		result = mbrDAO.updatePoint(userid);
		return result;
	}


	public int writePost(MemberDTO loginUser, Scanner sc) {
		int result = 0;
		
		System.out.println("\n>>> 글쓰기 <<<");
		System.out.println("1. 작성자명 : " + loginUser.getName());
		System.out.print("2. 글제목 : ");
		String subject = sc.nextLine();
		System.out.print("3. 글내용 : ");
		String contents = sc.nextLine();		
		System.out.print("4. 글암호 : ");
		String boardpasswd = sc.nextLine();		
		
		BoardDTO brdDTO = new BoardDTO();
		
		brdDTO.setFk_userid(loginUser.getUserid());
		brdDTO.setSubject(subject);
		brdDTO.setContents(contents);
		brdDTO.setBoardpasswd(boardpasswd);
		
		result = brdDAO.writePost(brdDTO);
		
		return result;
	}
	
	public int confirmCommit(String answer) {
		int result = 0;
		Connection conn = MyDBConnection.getConn();
		
			try {
				if("Y".equalsIgnoreCase(answer)) {
					conn.commit();
					result = 1;
				} else if("N".equalsIgnoreCase(answer)) {
					conn.rollback();
					result = 0;
				} else {
					System.out.println(">>> Y나 N 둘 중 하나만 입력해");
					result = -1;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		
		return result;
	}


	public void fetchBoard() {
		List<BoardDTO> boardList = brdDAO.fetchBoard();
		
		if(boardList.size() > 0) {
			
			StringBuilder sb = new StringBuilder();
			
			for(int i=0;i<boardList.size();i++) {
			
				sb.append(boardList.get(i).listInfo()+"\n");
			}
			
			System.out.println("\n-------------------------- [게시글 목록] ---------------------------");
			System.out.println("\n글번호\t글제목\t\t작성자\t작성일\t조회수\t");
			System.out.println("\n-----------------------------------------------------------------");
			System.out.println(sb.toString());
			
		} else {
			System.out.println(">>> 글목록이 없습니다. <<<");
		}
	
	}
	

	public void showContent(MemberDTO loginUser, Scanner sc) {
		System.out.println("\n>>> 글내용 보기 <<<");
		System.out.print("▷ 글번호 : ");
		String boardNo = sc.nextLine();
		
		BoardDTO brdDTO = brdDAO.showContent(boardNo);
		
		if(brdDTO != null) {
			
			System.out.println("---------------------[" +boardNo + "번 글]--------------------");
			System.out.println("|\t[글내용]\t\t\t[유저아이디]\t|");
			System.out.println("|\t" + brdDTO.getContents() + "\t\t| " + brdDTO.getFk_userid() + "\t|");
			System.out.println("-------------------------------------------------");

			if(!(brdDTO.getFk_userid().equals(loginUser.getUserid()))) {
				brdDAO.incrementViewCount(boardNo);
			}
			
		} else {
			System.out.println("\n>>> 존재하지 않는 글번호입니다! <<< ");
		}
	}
	
	// 자원반납
	public void appExit() {
		MyDBConnection.closeConnection();
	}


}
