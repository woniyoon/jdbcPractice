package jdbc.day01.statement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest03_Select {

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

			// 4. SQL문 작성
			
			String sql = " select no, name, msg\n"+
					"      ,to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday\n"+
					"from jdbc_tbl_memo ";
			
			
			rs = stmt.executeQuery(sql);	// SELECT문만 사용할 수 있음
			
			System.out.println(" --------------------------------------------------------------------");
			System.out.println(" |글번호 | 글쓴이 |			글내용		|	작성일자	|");
			System.out.println(" --------------------------------------------------------------------");

			while(rs.next()) {
				System.out.println(" | " + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | ");
				
				// rs.next() : select된 결과물에서 커서의 위치(행의 위치)를 다음으로 옮긴 후, 행이 존재하면 true 없으면 false 리턴
			}
			System.out.println(" --------------------------------------------------------------------");

	         // stmt.executeQuery(sql); 에서 
	         // 파라미터인 sql 은 오로지 select문 들어온다.
	         // stmt.executeQuery(sql); 을 실행하면 select 되어진 결과물을 가져오는데 
	         // 그 타입은 ResultSet 으로 가져온다.
	         /*
	            === 인터페이스 ResultSet 의 주요한 메소드 ===
	            1. next()  : select 되어진 결과물에서 커서를 다음으로 옮겨주는 것		return boolean
	            2. first() : select 되어진 결과물에서 커서를 가장 처음으로 옮겨주는 것
	            3. last()  : select 되어진 결과물에서 커서를 가장 마지막으로 옮겨주는 것 
	            
	            == 커서가 위치한 행에서 컬럼의 값을 읽어들이는 메소드 ==
	            getInt(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
	                           파라미터 숫자는 컬럼의 위치값 
	                          
	            getInt(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
	                           파라미터 문자는 컬럼명 또는 alias명 
	                           
	            getLong(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
	                              파라미터 숫자는 컬럼의 위치값 
	                          
	            getLong(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
	                              파라미터 문자는 컬럼명 또는 alias명                
	            
	            getFloat(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
	                               파라미터 숫자는 컬럼의 위치값 
	                          
	            getFloat(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
	                               파라미터 문자는 컬럼명 또는 alias명 
	                               
	            getDouble(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
	                                 파라미터 숫자는 컬럼의 위치값 
	                          
	            getDouble(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
	                                 파라미터 문자는 컬럼명 또는 alias명    
	                                 
	            getString(숫자) : 컬럼의 타입이 문자열로 읽어들이때
	                                 파라미터 숫자는 컬럼의 위치값 
	                          
	            getString(문자) : 컬럼의 타입이 문자열로 읽어들이때
	                                 파라미터 문자는 컬럼명 또는 alias명                                                        
	         */
			

			
			
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







