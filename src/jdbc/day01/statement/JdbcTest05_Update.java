package jdbc.day01.statement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest05_Update {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.print("▷ 연결할 오라클 서버의 IP 주소 : ");
			String ip = sc.nextLine();

			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe ", "myorauser", "eclass");
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();

			
			boolean isEditing = true;
		
			do {
				String selectSql = " select no, name, msg\n"+
						"      ,to_char(writedate, 'yyyy-mm-dd hh24:mi:ss') as writeday\n"+
						"from jdbc_tbl_memo ";
				
				String editSql = "update jdbc_tbl_memo set msg =";

				rs = stmt.executeQuery(selectSql);
				
			
				System.out.println(" --------------------------------------------------------------------");
				System.out.println(" |글번호 | 글쓴이 |			글내용		|	작성일자	|");
				System.out.println(" --------------------------------------------------------------------");

				while(rs.next()) {
					System.out.println(" | " + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | ");				
				}

				System.out.println(" --------------------------------------------------------------------\n");	
				
				
				System.out.println("\n======== 글내용 수정하기 =========");
				System.out.println(" 1. 변경대상 글번호	 	2. 종료 ");
				System.out.println("==============================");
				System.out.print("▷ 번호를 골라주세요 : ");
				String option = sc.nextLine();
				
				if("1".equals(option)) {
					System.out.print("▷ 변경대상 글 번호 입력 : ");
					String no = sc.nextLine();
					
					selectSql += " where no= '" + no + "' ";
					
					rs = stmt.executeQuery(selectSql);
					
					if(rs.getRow() == 0) {
						System.out.println("없는 번호입니다.");
						break;
						
					} else {
						
						System.out.print("▷ 변경할 글내용 입력 : ");
						String msg = sc.nextLine();
				
						
						editSql +=  " '"+msg+"' where no = '" +no+"' ";
						
						int n = stmt.executeUpdate(editSql);
						
						if(n == 1) {
							
							do {
								System.out.print("▷ 정말로 수정하시겠습니까? [Y/N]");
								String answer = sc.nextLine();
								
								if("y".equalsIgnoreCase(answer)) {
									conn.commit();
									System.out.println(">>> 데이터 수정 성공 <<<");
									break;
								} else if("n".equalsIgnoreCase(answer)){
									conn.rollback();
									System.out.println(">>> 데이터 수정 취소 <<<");
									break;
								}
							} while (true);
							
						} else {
							System.out.println(">>> 다시 시도해주세요! ");
						}
						
					}
					
								
				} else if("2".equals(option)) {
					isEditing = false;
				} else {
					System.out.println(">>> 유효하지 않은 입력값입니다! ");
				}
				
			} while (isEditing);
		
			
			
			
			
			
			

			conn.setAutoCommit(true);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
		}
		

		System.out.println(">>> 프로그램이 종료됐습니다.");
		sc.close();

	}
	
	

}






