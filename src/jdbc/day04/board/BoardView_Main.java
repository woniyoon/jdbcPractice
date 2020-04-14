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
		
		do {
			System.out.println("\n----------------------------- 게시판메뉴["+ loginUser.getName() +"님] -----------------------------");
			System.out.println(" 1.글목록 보기		2.글내용 보기		3.글쓰기		4.댓글쓰기\n"
							 + " 5.글수정하기		6.글삭제하기		9.로그아웃");
			System.out.println("-------------------------------------------------------------------------------------------------\n");
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
				
				break;
			case "5":	// 글수정
				controller.editPost(loginUser, sc);
				break;
			case "6":	// 글삭제
				controller.deletePost(loginUser, sc);
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
//					System.out.println("확인용 : "+loginUser);
				} else {
					System.out.println("\n>>> 로그인 실패 ! ");
				}
				
				break;
			case "2":
//				signUp();
				int n = controller.signUp(sc);
				
				if(n == 1) {
					System.out.println("\n >>> 회원가입 축하 !");
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
