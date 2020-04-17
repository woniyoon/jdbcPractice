package jdbc.day04.board;

import java.util.*;

public class BoardView_Main {
	//	게시판의 내용(메뉴, 결과물,...)을 보여주는 곳
	//  무엇인가 입력화면이나 출력결과물을 보여주는 곳 :  View단
	//	요청 & 결과물을 출력
	
	static Scanner sc = new Scanner(System.in);
	
	//	시작메뉴
	static void showMenu() {
		System.out.println("\n------------------ 시작메뉴 ------------------");
		System.out.println(" 1.로그인		2.회원가입		3.프로그램 종료");
		System.out.println("--------------------------------------------\n");
		System.out.print("▷ 메뉴 선택 : ");
	}
	
	//	게시판메뉴
	static MemberDTO showBoardMenu(MemberDTO loginUser, Controller controller) {
		String menuOption;
		String adminMenu = "admin".equals(loginUser.getUserid()) ? "\t10. 모든 회원 정보 조회" : "";
		
		do {
			System.out.println("\n----------------------------- 게시판메뉴["+ loginUser.getName() +"님] -----------------------------");
			System.out.print(" 1.글목록 보기		2.글내용 보기		3.글쓰기		4.댓글쓰기\n"
							 + " 5.글수정하기		6.글삭제하기		7.최근1주일간 일자별 게시글 작성건수\n"
							 + " 8.이번달 일자별 게시글 작성건수		9.로그아웃\t" + adminMenu);
			System.out.println("\n---------------------------------------------------------------------------\n");
			System.out.print("▷ 메뉴 선택 : ");
			
			menuOption = sc.nextLine();
			
			switch (menuOption) {
			case "1":	// 글목록 보기 
				controller.fetchBoard();
				break;
			case "2":	// 글내용 보기
				controller.showContent(loginUser, sc);
				break;
			case "3":	// 글쓰기
				int n_write = controller.writePost(loginUser, sc);
				int n_updatePoint = controller.updatePoint(loginUser.getUserid());
				
				if(n_write == 1 && n_updatePoint == 1) {
					
					int result = 0;
					do {
						System.out.println(">>> 글을 업로드하겠습니까? [Y/N] ");
						String answer = sc.nextLine();
						
						result = controller.confirmCommit(answer);
			
						if(result == 1 || result == 0) break; 
					} while (true);

					if(result == 1) {
						System.out.println(">>> 글이 성공적으로 업로드됐습니다!");
					} else if (result == 0){
						System.out.println(">>> 글 업로드가 취소됐습니다!");
					}
				} else {
					System.out.println("\n>> 글쓰기 실패! <<\n");
				}
				
				break;
			case "4":	// 댓글쓰기
				int commentResult = controller.writeComment(loginUser, sc);
				
				if (commentResult == 1) {
					System.out.println(">> 댓글 달기 성공!! << ");
				} else {
					System.out.println("\n>>> 댓글달기 실패! <<< ");	
				} 
				
				break;
			case "5":	// 글수정
				int editResult = controller.editPost(loginUser, sc);
				
				if (editResult == 0) {
					System.out.println(">> 수정 취소!! << ");
				} else if (editResult == -1) {
					System.out.println("\n>>> 존재하지 않는 글번호입니다! <<< ");	
				} else if (editResult == -2) {
					System.out.println("\n>> 다른 사용자의 글은 수정불가 합니다!! << ");
				} else if (editResult == -3) {
					System.out.println("\n>> 글암호가 올바르지 않습니다 << ");
				} else if (editResult == -4) {
					System.out.println(">> 수정 실패!! <<");
				} else if (editResult == 1) {
					System.out.println(">> 수정 성공!! << ");
				}
				
				break;
			case "6":	// 글삭제
				int n = controller.deletePost(loginUser, sc);
				
				if (n == 0) {
					System.out.println(">> 삭제 취소!! << ");
				} else if (n == -1) {
					System.out.println("\n>>> 존재하지 않는 글번호입니다! <<< ");	
				} else if (n == -2) {
					System.out.println("\n>> 다른 사용자의 글은 삭제불가 합니다!! << ");
				} else if (n == -3) {
					System.out.println("\n>>> 비밀번호가 틀렸습니다! <<<");
				} else if (n == 1) {
					System.out.println(">> 삭제 성공!! << ");
				}
				break;
			case "7":
				controller.getStatisticsThisWeek();
				break;
			case "8":
				controller.getStatisticsThisMonth();
				break;
			case "9":	// 로그아웃
//				loginUser = null;
				System.out.println(">> 로그아웃됐습니다. <<\n");
				break;

			default:
				System.out.println(">> 메뉴에 없는 번호를 선택하셨습니다. <<");
				break;
			}
			
		} while(!"9".equals(menuOption));
		
		return null;
	}
	
	
	public static void main(String[] args) {
		String mainMenuNo;
		Controller controller = new Controller();
		
		do {
			showMenu();
			
			mainMenuNo = sc.nextLine();
			
			switch (mainMenuNo) {
			case "1":
				MemberDTO loginUser = controller.login(sc);
				
				if(loginUser != null) {
					System.out.println("\n>>> 로그인 성공 ! ");
					loginUser = showBoardMenu(loginUser, controller);
				} else {
					System.out.println("\n>>> 로그인 실패 ! ");
				}
				
				break;
			case "2":
				int n = controller.signUp(sc);
				
				if(n == 1) {
					System.out.println("\n >>> 회원가입 축하 !");
				} else if(n == 0) {
					System.out.println("\n >>> 회원가입 취소 !");
				} else {
					System.out.println("\n >>> 회원가입 실패 !");
				}
				
				break;
			case "3":
				controller.appExit();
				
				break;

			default:
				System.out.println(">> 시작 메뉴에 없는 번호임");
				break;
			}
			
		} while(!"3".equals(mainMenuNo));
		
		
		System.out.println(">>>> 프로그램 종료! ");
		sc.close();
		
	}

}
