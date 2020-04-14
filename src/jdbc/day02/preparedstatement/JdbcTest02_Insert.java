package jdbc.day02.preparedstatement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest02_Insert {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.print("▷ 연결할 오라클 서버의 IP 주소 : ");
			String ip = sc.nextLine();
		
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe ", "myorauser", "eclass");
			
			conn.setAutoCommit(false);	// default는 true라 따로 변경하지 않으면 auto commit이 됨
			

			// 3. SQL문 작성
			
			System.out.print("▷ 글쓴이 : ");
			String name = sc.nextLine();			
			
			System.out.print("▷ 글내용 : ");
			String msg = sc.nextLine();
			
//			 또한  Statement 는 사용자가 입력한 단어(검색어 또는 입력단어)들이 보여지지만
//			PreparedStatement 는 위치홀더(?)를 사용하므로 입력한 단어(검색어 또는 입력단어)들이 보여지지 않으므로 
//			Statement 보다 PreparedStatement 가 보안성이 높으므로 PreparedStatement 를 주로 사용한다.       
						// 매번 검사하지않고, 처음에만 확인 & 캐시에 저장해서 재사용
			String sql = " insert into jdbc_tbl_memo2(no, name, msg) "
					  + " values(jdbc_seq_memo2.nextval, ?, ?) ";
		
						
			
			// >>>> 4. PreparedStatement객체 생성

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);	// 첫 번째 물음표에 name 대입
			pstmt.setString(2, msg);	// 두 번째 물음표에 msg 대입
			
			System.out.println("SQL : "+ sql);

			
			// >>>> 5. PreparedStatement 객체가 작성된 SQL문을 전달해 실행되도록 함
			// 실행되어질 sql문이 DDL문(create, alter, drop, truncate) => 리턴값이 0 이 나온다.
	        // 실행되어질 sql문이 DML문(insert, update, delete) => 리턴값이 적용된 행의갯수가 나온다.
			
			int n = pstmt.executeUpdate();

			
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
			
			conn.setAutoCommit(true);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			// 사용했던 자원(PreparedStatement & Connection)은 무조건 반납해야함
							// 생성순서의 역순으로 반납
			try {
				if(conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
		}
		
		sc.close();

	}

}






