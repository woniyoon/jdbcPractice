package jdbc.day01.statement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest04_Select_Where {

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
			
			conn.setAutoCommit(false);	// default는 true라 따로 변경하지 않으면 auto commit이 됨
			
			stmt = conn.createStatement();
			boolean isSearching = true;
			
			
			
			do {
				
				String sql = " select no, name, msg\n"+
						"      ,to_char(writedate, 'yyyy-mm-dd hh24:mi:ss') as writeday\n"+
						"from jdbc_tbl_memo ";

				// where 절에 해당하는 메뉴 보여주기
				System.out.println("\n>>> 검색대상 선택 <<<"
								   + "\n1.글번호  2.글쓴이  3.글내용  4.전체조회  5.종료"
								   + "\n");
				System.out.print("▷ 선택 : ");
				String option = sc.nextLine();
				
				switch (option) {
				case "1":
					System.out.print("▷ 검색할 글번호 : ");
					String searchNo = sc.nextLine();
					sql += " where no = " + searchNo;
					
					break;
				case "2":
					System.out.print("▷ 검색할 글쓴이 : ");
					String name = sc.nextLine();
					sql += " where name = '" + name + "'";
					
					break;
				case "3":
					System.out.print("▷ 검색할 글내용 : ");
					String word = sc.nextLine();
					sql += " where msg like '%'|| '"+word+"' ||'%' ";					
					break;
				case "4":
					
					break;
				case "5":
					isSearching = false;
					break;
				default:
					System.out.println(">>> 유효하지 않은 입력값입니다.");
					break;
				}			

				if("1".equals(option) || "2".equals(option) || "3".equals(option) || "4".equals(option)) {
					sql += " order by 1 desc";
					stmt.executeQuery(sql);
					
					rs = stmt.executeQuery(sql);
					
					System.out.println(" --------------------------------------------------------------------");
					System.out.println(" |글번호 | 글쓴이 |			글내용		|	작성일자	|");
					System.out.println(" --------------------------------------------------------------------");
						
				
					while(rs.next()) {
						System.out.println(" | " + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | ");					
					}
					System.out.println(" --------------------------------------------------------------------");						
				}
				
			} while (isSearching);
					
			
			conn.setAutoCommit(true);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			// 사용했던 자원(Statement & Connection)은 무조건 반납해야함
							// 생성순서의 역순으로 반납
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