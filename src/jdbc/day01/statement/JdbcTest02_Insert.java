package jdbc.day01.statement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest02_Insert {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.print("▷ 연결할 오라클 서버의 IP 주소 : ");
			String ip = sc.nextLine();
			
//			System.out.print("▷ 오라클 사용자명 : ");
//			String user = sc.nextLine();			
//			
//			System.out.print("▷ 암호 : ");
//			String password = sc.nextLine();
		
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe ", "myorauser", "eclass");
			
			conn.setAutoCommit(false);	// default는 true라 따로 변경하지 않으면 auto commit이 됨
			
			stmt = conn.createStatement();

			// 4. SQL문 작성
			
			System.out.print("▷ 글쓴이 : ");
			String name = sc.nextLine();			
			
			System.out.print("▷ 글내용 : ");
			String msg = sc.nextLine();
			
			String sql = " insert into jdbc_tbl_memo(no, name, msg) "
					  + " values(jdbc_seq_memo.nextval, '" + name + "', '"+ msg + "') ";
			
			
			System.out.println("SQL : "+ sql);
			
			int n = stmt.executeUpdate(sql);

			
			if(n==1) {

				do {
					System.out.println("정말로 입력하겠습니까?[Y/N]");
					String yn = sc.nextLine();
					
					if("y".equalsIgnoreCase(yn)) {
						conn.commit();
						System.out.println(">> 데이터 입력 성공!!\n");
						break;
					} else if("n".equalsIgnoreCase(yn)) {
						conn.rollback();
						System.out.println(">> 데이터 입력 취소!!\n");
						break;
					} else {
						System.out.println(">> Y 또는 N만 입력하세요\n");
					}
					
				} while (true);
				
			} else {
				System.out.println(n+ "개의 행이 입력됐습니다.\n");			
			}
						
//			String sql2 = "select * from jdbc_tbl_memo";
//			Object test = stmt.executeQuery(sql2);

			
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
		
		sc.close();

	}

}


// line 42            stmt.executeUpdate(String sql) -> int 
//
// 파라미터의 sql은 오로지 DML(insert, update, delete)문만 사용가능
// sql 이 insert 이라면 insert 되어진 행의 갯수를 돌려주고,
// sql 이 update 이라면 update 되어진 행의 갯수를 돌려주고,
// sql 이 delete 이라면 delete 되어진 행의 갯수를 돌려준다.







