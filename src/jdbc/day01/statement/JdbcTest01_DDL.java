package jdbc.day01.statement;

import java.sql.*;

public class JdbcTest01_DDL {
												// IP:portnumber  => Socket
	static final String URL = "jdbc:oracle:thin:@192.168.150.144:1521:xe ";
	static final String USER = "myorauser";
	static final String PASSWORD = "eclass";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = null;
		// Connection conn : 오라클 DB서버와 연결을 맺어주는 객체, 어떤 오라클 DB서버 
		
		Statement stmt = null; 	// SQL문이 편지라면, Statement는 우편배달부같은 역할 
				
		try {
			/*
			   >>>> 1. 오라클 드라이버 로딩
		        === OracleDriver(오라클 드라이버)의 역할 ===
		        
		        Class.forName("oracle.jdbc.driver.OracleDriver");가 의미하는 바 
		        
		        1). OracleDriver 를 메모리에 로딩시켜준다.
		        2). OracleDriver 객체를 생성해준다.
		        3). OracleDriver 객체를 DriverManager에 등록시켜준다. 
		            --> DriverManager 는 여러 드라이버들을 Vector에 저장하여 관리해주는 클래스이다. 
			*/
		
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			// >>>> 2. 어떤 오라클 서버에 연결할지 설정
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			System.out.println(conn);
			
			// >>>> 3. 연결할 오라클서버에 SQL문 전달할 Statement객체 생성
			stmt = conn.createStatement();
			
			// >>>> 4. SQL문을 작성	
			//				**** SQL문 맨 뒤에 세미콜론 X
			//				**** 띄어쓰기 주의
			String sql1 = " create table jdbc_tbl_memo "
						  + " (no			number(4) not null "
						  + " ,name			varchar2(20) "
						  + " ,msg			varchar2(200) "
						  + " ,writeDate		date default sysdate "
						  + " ,constraint 	PK_jdbc_tbl_memo primary key(no) "
						  + ") ";
			
			String sql2 = " create sequence jdbc_seq_memo "
						  + " start with 1 "
						  + " increment by 1 "
						  + " nomaxvalue"
						  + " nominvalue"
						  + " nocycle"
						  + " nocache ";
			
			String sql3 = " insert into jdbc_tbl_memo(no, name, msg) "
						  + " values(jdbc_seq_memo.nextval, '윤재원', '오늘의 점심 메뉴는 닭곱새, 겁나 맛있겠다!') ";
			
			// >>>> 5. Statement 객체가 작성된 SQL문을 전달해 실행되도록 함
			boolean isSQL1 = stmt.execute(sql1);
			boolean isSQL2 = stmt.execute(sql2);
			boolean isSQL3 = stmt.execute(sql3);
			
			
			/*
	            stmt.execute(sql1) => true or false
	            파라미터의 SQL == select 
	            						=> true (정상적으로 성공시)
	            			== DDL문(create, alter, drop, truncate) || DML문(insert, update, delete)일 경우에는 
	            						=> false (성공여부 관계없이)
			*/
			
			
			
			// 모두 DDL문이라 false값만 출력될것으로 예상
			System.out.println("확인용 isSQL1 => " + isSQL1);
			System.out.println("확인용 isSQL2 => " + isSQL2);
			System.out.println("확인용 isSQL3 => " + isSQL3);
			
			
		} catch (ClassNotFoundException e) {
			System.out.println(">>> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 되지 않았습니다.");
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
		
	}

}


		
		/*
		개발자가 SQL문(편지)를 작성했는데, 작성한 SQL문을 어느 오라클 서버에서 실행해야할지 결정해야함
		어느 오라클 서버인지는 Connection이 알고
		Connection conn에 전송할 SQL문은 Statement stmt(우편배달부)이 전달해줌
		
		*/

