package jdbc.connection;

import java.sql.*;

public class MyDBConnection {
		// 1. 해당 클래스 타입의 private static 변수 만듦
		private static Connection conn = null;

		private final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe ";
		private final static String USER = "myorauser";
		private final static String PASSWORD = "eclass";
		
		
		// 2. static 초기화 블럭에서 conn 초기화
		static {
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				conn.setAutoCommit(false);	// 수동커밋으로 전환
				
			} catch (ClassNotFoundException e) {
				System.out.println(">>> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 되지 않았습니다.");
				e.printStackTrace();
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}

		}
		
		// 3. Connection conn 객체를 외부에서 접근하게 해주는 static 메소드
		public static Connection getConn() {
			return conn;
		}
		
		// 4. 외부에서 생성자에 접근하지 못하게 private으로 선언
		private MyDBConnection() {
			
		}
		
		// 5. Connection conn객체 자원 반납하기 
		public static void closeConnection() {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				
			}
		}
		
}
