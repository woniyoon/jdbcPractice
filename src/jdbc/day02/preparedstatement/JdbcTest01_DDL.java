package jdbc.day02.preparedstatement;

import java.sql.*;
import java.util.Scanner;

public class JdbcTest01_DDL {
												// IP:portnumber  => Socket
	static final String URL = "jdbc:oracle:thin:@192.168.150.144:1521:xe ";
	static final String USER = "myorauser";
	static final String PASSWORD = "eclass";
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		Connection conn = null;
		// Connection conn : 오라클 DB서버와 연결을 맺어주는 객체, 어떤 오라클 DB서버 
		
		PreparedStatement pstmt = null; 	// SQL문이 편지라면, Statement는 우편배달부같은 역할 
				
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

			
			// >>>> 3. SQL문을 작성	
			//				**** SQL문 맨 뒤에 세미콜론 X
			//				**** 띄어쓰기 주의
			String createTblSql = " create table jdbc_tbl_memo2 "
						  + " (no			number(4) not null "
						  + " ,name			varchar2(20) "
						  + " ,msg			varchar2(200) "
						  + " ,writeDate		date default sysdate "
						  + " ,constraint 	PK_jdbc_tbl_memo2 primary key(no) "
						  + ") ";
			
			String createSeqSql = " create sequence jdbc_seq_memo2 "
						  + " start with 1 "
						  + " increment by 1 "
						  + " nomaxvalue"
						  + " nominvalue"
						  + " nocycle"
						  + " nocache ";
			
			String insertRowSql = " insert into jdbc_tbl_memo2(no, name, msg) "
						  + " values(jdbc_seq_memo2.nextval, '윤재원', '주말이라 개행복') ";
			
			
			String dropTblSql = " drop table jdbc_tbl_memo2 purge ";

			String dropSeqSql = " drop sequence jdbc_seq_memo2 ";
			
			// >>>> 4. 연결할 오라클서버에 SQL문 전달할 PreparedStatement객체 생성

			
			pstmt = conn.prepareStatement(createTblSql);

			
			// >>>> 5. PreparedStatement 객체가 작성된 SQL문을 전달해 실행되도록 함
			// 실행되어질 sql문이 DDL문(create, alter, drop, truncate) => 리턴값이 0 이 나온다.
	        // 실행되어질 sql문이 DML문(insert, update, delete) => 리턴값이 적용된 행의갯수가 나온다.
		

			
			//FIXME: try catch 사용시, sql문 문법이 틀릴 때도 Exception을 던지기 때문에 사용 X 
			//TODO : 수정할 것 
			
			try {
				int n = pstmt.executeUpdate();
				
				System.out.println("sql1 DDL문 (테이블 생성)  : " + n);
				
				pstmt = conn.prepareStatement(createSeqSql);
				n = pstmt.executeUpdate();
				System.out.println("sql2 DDL문 (sequence 생성)  : " + n);				
				
				pstmt = conn.prepareStatement(insertRowSql);
				n = pstmt.executeUpdate();
				System.out.println("sql3 DML문 (행 삽입)  : " + n);

			} catch (SQLSyntaxErrorException e) {

				System.out.println(e.getMessage());
				
				System.out.print(">>> 테이블과 시퀀스가 이미 존재합니다. 삭제를 원하면 Y를 입력하세요.");
				String option = sc.nextLine();
				
				if("Y".equalsIgnoreCase(option)) {
					int tbl_n = pstmt.executeUpdate(dropTblSql);
					int seq_n = pstmt.executeUpdate(dropSeqSql);
					
					if(tbl_n == 0 && seq_n == 0) {
						System.out.println("\n>>> 테이블과 시퀀스를 삭제합니다.");
					} else {
						System.out.println("\n>>> 다시 시도해주세요.");
					}					
				} else {
					System.out.println(">>> 프로그램을 종료합니다.");
				}
				
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println(">>> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 되지 않았습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			// 사용했던 자원(Statement & Connection)은 무조건 반납해야함
						// 생성순서의 역순으로 반납
			try {
//			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}	
		}
		
	}

}


		
/*
		===== Statement 와 PreparedStatement =====
		Statement 	VS	 PreparedStatement
		 차이점:  캐시 사용유무
		이들은 쿼리문장(SQL문)을 분석([==파싱 parsing] 문법검사, 인덱스 사용유무)하고 컴파일 후 실행된다.
		Statement는 매번 쿼리문장(SQL문)을 수행할때 마다 모든 단계(파싱 parsing)를 거치지만
		
		
		PreparedStatement 는 처음 한번만 모든 단계(파싱 parsing)를 수행한 후 캐시에 담아 재사용한다.
		그러므로 동일한 쿼리문장(SQL문)을 수행시 PreparedStatement가 DB에 훨씬 적은 부하를 주므로 성능이 좋아진다. 
		   또한  Statement 는 사용자가 입력한 단어(검색어 또는 입력단어)들이 보여지지만
		PreparedStatement 는 위치홀더(?)를 사용하므로 입력한 단어(검색어 또는 입력단어)들이 보여지지 않으므로 
		Statement 보다 PreparedStatement 가 보안성이 높으므로 PreparedStatement 를 주로 사용한다.       
*/







