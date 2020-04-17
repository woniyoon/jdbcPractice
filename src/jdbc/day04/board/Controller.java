package jdbc.day04.board;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import jdbc.connection.MyDBConnection;
import jdbc.util.MyUtil;

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
						
			do {
				System.out.println(">>> 회원가입을 정말로 하시겠습니까? [Y/N] ");
				String answer = sc.nextLine();
				
				result = confirmCommit(answer);
				
			} while (result == -1);
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

	public void fetchBoard() {
		List<BoardDTO> boardList = brdDAO.fetchBoard();
		
		if(boardList.size() > 0) {
			
			StringBuilder sb = new StringBuilder();
			
			for(int i=0;i<boardList.size();i++) {
				BoardDTO post = boardList.get(i);
				String boardno = String.valueOf(post.getBoardno());
				List<CommentDTO> comments = brdDAO.fetchComments(boardno);
				
				sb.append(post.listInfo(comments.size())+"\n");
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
		List<CommentDTO> comments = brdDAO.fetchComments(boardNo);

		if(brdDTO != null) {
			
			brdDTO.showPost();
			
			// comments.size()로 댓글유무 판단  
			if(comments.size() > 0) {		// 댓글 O 
				System.out.println("[댓글]");
				System.out.println("-------------------------------------------------");
				System.out.println(" 내용 \t 작성자 \t 작성일자");
				System.out.println("-------------------------------------------------");
				for(int i=0;i<comments.size();i++) {
					comments.get(i).showDetail();
				}
			} else {						// 댓글 X
				System.out.println("[댓글]");
				System.out.println("-------------------------------------------------");
				System.out.println(">>	댓글 내용없음	<<");	
			}
			
			if(!(brdDTO.getFk_userid().equals(loginUser.getUserid()))) {
				brdDAO.incrementViewCount(boardNo);
			}
			
		} else {
			System.out.println("\n>>> 존재하지 않는 글번호입니다! <<< ");
		}
	}
	

	public int editPost(MemberDTO loginUser, Scanner sc) {
		System.out.print("▷ 수정할 글번호 :");
		String boardNo = sc.nextLine();
		
		BoardDTO brdDTO = brdDAO.editPost(boardNo);
		Map<String, String> paraMap = new HashMap<String, String>();
		
		
		if(brdDTO == null) {
									//  존재하지 않는 글
			return -1;
		} else {
			if(brdDTO.getFk_userid().equals(loginUser.getUserid())) {
				
				System.out.print("▷ 글 비밀번호 : ");
				String postPassword = sc.nextLine();
				
				if(postPassword.equals(brdDTO.getBoardpasswd())) {
					brdDTO.showPost();					
					
					System.out.print("▷ 글제목 : ");
					String newSubject = sc.nextLine();
					System.out.print("▷ 글내용 : ");
					String newContents = sc.nextLine();
					
					paraMap.put("subject", newSubject);
					paraMap.put("contents", newContents);
					paraMap.put("boardno", boardNo);
					
					int result = brdDAO.editSubAndConts(paraMap);
					
					if (result == 1) {
						int flag = -1;
						do {
							System.out.print("▷ 정말로 수정하시겠습니까?[Y/N] ");
							String answer = sc.nextLine();
							
							flag = confirmCommit(answer);
							
						}while(flag == -1);
						
						
						return flag;
						
					} else {
						return -4;	// 수정 실패
					}
				} else {
					return -3;		// 암호 틀림
				}
			} else {
				return -2;			// 타인의 글 수정 X 
			}
		}
	}
	
	
	public int deletePost(MemberDTO loginUser, Scanner sc) {
		System.out.print("▷ 삭제할 글번호 :");
		String boardNo = sc.nextLine();
	
		Map<String, String> paramap = new HashMap<String, String>();
	
		BoardDTO brdDTO = brdDAO.showContent(boardNo);
		
		if(brdDTO == null) {
			return -1;			// 존재하지 않는 글
		} else if(!(brdDTO.getFk_userid().equals(loginUser.getUserid()))) {
			return -2;			// 다른 유저의 글 삭제 시도
		} else {
			System.out.print("▷ 글 비밀번호 :");
			String password = sc.nextLine();
			
			paramap.put("boardno", boardNo);
			paramap.put("boardpasswd", password);
			
			int result = brdDAO.deletePost(paramap);

			if(result == 0) {
				return -3;		// DB에 업데이트 된 행 개수 = 0, 비밀번호 틀림
			} else {
				int flag = -1;
				do {
					System.out.print("▷ 정말로 삭제하시겠습니까?[Y/N] ");
					String answer = sc.nextLine();
					
					flag = confirmCommit(answer);
					
				} while(flag == -1);
				
				return flag;
			}
		}
	}
	

	public int writeComment(MemberDTO loginUser, Scanner sc) {
		int result = 0;
		String contents = "";
		
		System.out.println("\n>>> 댓글쓰기 <<<");
		System.out.println("1. 작성자명 : " + loginUser.getName());
		
		System.out.print("2. 원글의 글번호 :");
		String boardno = sc.nextLine();

		do {

			System.out.print("3. 댓글내용 :");
			contents = sc.nextLine();
			
			if(contents == null || contents.trim().isEmpty()) {
				System.out.println(">>> 댓글 내용은 필수로 입력해야 합니다. <<<");
			} else {
				break;
			} 
			
		} while (true);
			
		CommentDTO cmtDTO = new CommentDTO();
		
		cmtDTO.setFk_boardno(boardno);
		cmtDTO.setContents(contents);
		cmtDTO.setFk_userid(loginUser.getUserid());
		
		result = brdDAO.writeComment(cmtDTO);
		
		return result;
	}
	

	public void getStatisticsThisWeek() {
		System.out.println("------- [최근 일주일간 일자별 게시글 작성건수] -------");
		
		String result = "전체\t";
		for(int i=0; i<7; i++) {
			result += MyUtil.getDate(-i) + "\t";
		}
			
		System.out.println(result);
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		
		Map<String, Integer> resultMap = brdDAO.getStatisticsThisWeek();

		resultMap.values().forEach((r) -> {
			System.out.print(r+"\t\t");
		});
		
//		System.out.println("\n");
//		resultMap.forEach((key, value)->{
//			
//			System.out.print(resultMap.get(key)+"\t\t");
//			System.out.print(value+"\t\t");
//
//		});
//		System.out.println("\n");

		System.out.println("\n--------------------------------------------------------------------------------------------------------------------------\n");

	}

	
	// 이번달 일자별 게시글 작성건수
	public void getStatisticsThisMonth() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월");
		String currentMonth = dateFormat.format(currentDate.getTime());
		
		List<Map<String,String>> mapList = brdDAO.getStatisticsThisMonth();
		
		System.out.println("------------- [" + currentMonth +" 일자별 게시글 작성건수] -------------");
		System.out.println(" 작성일자\t작성건수");
		System.out.println("\n---------------------------------------------------------------------\n");

		if(mapList.size() > 0) {
			StringBuilder sb = new StringBuilder();
			
			for(Map<String, String> map : mapList) {
				 sb.append(map.get("WRITEDAY") + "\t" + map.get("CNT") + "\n");
			}
			
			System.out.println(sb.toString());
			
		} else {
			System.out.println("		작성된 게시글이 없습니다.			");
		}

		System.out.println("\n---------------------------------------------------------------------\n");

	}


	public void selectAllMember() {
		List<MemberDTO> memberList = mbrDAO.selectAllMember();
		
		if(memberList.size() > 0) {
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" 회원아이디\t 암호\t\t회원명\t    연락처\t포인트\t등록일자\t\t탈퇴유무 ");
			System.out.println("---------------------------------------------------------------------------------------");
			
			for(MemberDTO member : memberList) {
				System.out.println(member.memberInfoForAdmin());
			}
		
			System.out.println("---------------------------------------------------------------------------------------\n");
		}
		
	}
	
	// 자원반납
	public void appExit() {
		MyDBConnection.closeConnection();
	}
	
	// commit & rollback 정하는 메소드
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

}
