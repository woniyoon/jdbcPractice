package jdbc.day02.preparedstatement;

/*
   ===== Statement 와 PreparedStatement =====
   Statement 와 PreparedStatement 의 가장 큰 차이점은 캐시 사용유무 이다. 
   이들은 쿼리문장(SQL문)을 분석([==파싱 parsing] 문법검사, 인덱스 사용유무)하고 컴파일 후 실행된다.
   Statement는 매번 쿼리문장(SQL문)을 수행할때 마다 모든 단계(파싱 parsing)를 거치지만
   PreparedStatement 는 처음 한번만 모든 단계(파싱 parsing)를 수행한 후 캐시에 담아 재사용한다.
   그러므로 동일한 쿼리문장(SQL문)을 수행시 PreparedStatement가 DB에 훨씬 적은 부하를 주므로 성능이 좋아진다. 
      또한  Statement 는 사용자가 입력한 단어(검색어 또는 입력단어)들이 보여지지만
   PreparedStatement 는 위치홀더(?)를 사용하므로 입력한 단어(검색어 또는 입력단어)들이 보여지지 않으므로 
   Statement 보다 PreparedStatement 가 보안성이 높으므로 PreparedStatement 를 주로 사용한다.       
*/

import java.sql.*;

public class JdbcTest01_DDL2 {

   public static void main(String[] args) {
      
      Connection conn = null;
   //  Connection conn 은 오라클 데이터베이스 서버와 연결을 맺어주는 객체이다. 
      
      PreparedStatement pstmt = null;
   /*
           개발자가 SQL문(편지)을 작성했는데,
           작성한 SQL문을 어느 오라클 서버에서 실행을 해야할지 결정해야 한다.
           이때 어느 오라클 서버인지는  Connection conn 에서 알고,
       Connection conn 에 전송할 SQL문(편지)은 PreparedStatement pstmt(우편배달부)를 통해 전송된다.       
    */
      
      ResultSet rs = null;
    // select 되어진 결과물을 저장하는 용도에 쓰임.    
      
      try {
         // >>> 1. 오라클 드라이버 로딩 <<< 
         /*
           === OracleDriver(오라클 드라이버)의 역할 ===
           1). OracleDriver 를 메모리에 로딩시켜준다.
           2). OracleDriver 객체를 생성해준다.
           3). OracleDriver 객체를 DriverManager에 등록시켜준다. 
               --> DriverManager 는 여러 드라이버들을 Vector에 저장하여 관리해주는 클래스이다. 
           */
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         
         // >>> 2. 어떤 오라클 서버에 연결을 할래? <<< 
         conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "myorauser", "eclass");    
         
         
         // ********  테이블 생성하기  ******** //  
         // >>> 3. SQL문(편지)을 작성한다. 
         String sql = " select * " + 
                    " from user_tables " + 
                    " where table_name = ?";
         
         // >>> 4. 연결한 오라클서버(conn)에 SQL문(편지)을 전달할  PreparedStatement 객체(우편배달부) 생성하기 <<< 
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "JDBC_TBL_MEMO2");
         
         // >>> 5. PreparedStatement pstmt 객체(우편배달부)가 작성된 SQL문(편지)을 오라클서버에 보내서 실행이 되어지도록 한다.
         rs = pstmt.executeQuery();
                  
         if(rs.next()) {
            // JDBC_TBL_MEMO2 테이블이 존재한다라면 
            // 먼저 JDBC_TBL_MEMO2 테이블을 drop 한다.
            pstmt.close();
            
            sql = " drop table JDBC_TBL_MEMO2 purge ";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.executeUpdate();
            // 실행되어질 sql문이 DDL문(create, alter, drop, truncate)이라면 리턴값이 0 이 나온다.
            
            rs.close();
         }
         
         sql = " create table jdbc_tbl_memo2 "
            + " (no          number(4) not null "
             + " ,name        varchar2(20) "
             + " ,msg         varchar2(200) "
             + " ,writeday    date default sysdate "
             + " ,constraint  PK_jdbc_tbl_memo2 primary key(no) "
             + ") ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.executeUpdate();
         // 실행되어질 sql문이 DDL문(create, alter, drop, truncate)이라면 리턴값이 0 이 나온다.
 
         
         // ********  시퀀스 생성하기  ******** //  
         sql = " select * " + 
              " from user_sequences " + 
              " where sequence_name = ? ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "JDBC_SEQ_MEMO2");
         
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            // JDBC_SEQ_MEMO2 시퀀스가 존재한다라면 
            // 먼저 JDBC_SEQ_MEMO2 시퀀스를 drop 한다.
            pstmt.close();
            
            sql = " drop sequence JDBC_SEQ_MEMO2 ";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.executeUpdate();
            // 실행되어질 sql문이 DDL문(create, alter, drop, truncate)이라면 리턴값이 0 이 나온다.
         }
         
         sql = " create sequence jdbc_seq_memo2 "
             + " start with 1 " 
            + " increment by 1 "
            + " nomaxvalue "
            + " nominvalue "
            + " nocycle "
            + " nocache ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.executeUpdate();
         
         
         sql = " insert into jdbc_tbl_memo2(no, name, msg) "
            + " values(jdbc_seq_memo2.nextval, '이순신', '안녕하세요? 이순신 인사드립니다' ) ";
         
         pstmt = conn.prepareStatement(sql);
         int n = pstmt.executeUpdate();
         // 실행되어질 sql문이 DML문(insert, update, delete)이라면 리턴값이 적용된 행의갯수가 나온다. 
         
         System.out.println(n+"개 행이 입력되었습니다.");
         
      } catch (ClassNotFoundException e) {
         System.out.println(">> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 안 되었습니다.");
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         // >>> 6. 사용하였던 자원반납하기 <<< 
         // 반납의 순서는 생성순서의 역순으로 한다.
         
         try {
              if(rs != null)   rs.close();  
              if(pstmt != null) pstmt.close();
              if(conn != null) conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      

   }// end of main()----------------------------------

}




