package jdbc.day03.board;

import java.sql.*;
import java.util.Map;

public class MemberDAO implements InterMemberDAO {

	final String URL = "jdbc:oracle:thin:@192.168.150.144:1521:xe ";
	final String USER = "myorauser";
	final String PASSWORD = "eclass";


	@Override
	public int register(MemberDTO	member) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");


			// >>>> 2. 어떤 오라클 서버에 연결할지 설정
			conn = DriverManager.getConnection(URL, USER, PASSWORD);


			String sql = " insert into jdbc_member (userseq, userid, passwd, name, mobile)"
					+ " values(user_seq.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMobile());

			result = pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println(">>> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 되지 않았습니다.");
			e.printStackTrace();
		} catch(SQLIntegrityConstraintViolationException e){
			System.out.println("에러메시지 : " + e.getMessage());
			System.out.println("에러코드번호 : " + e.getErrorCode());
			System.out.println(">>> 중복된 아이디입니다. 새로운 아이디를 입력하세요! ");
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}


	@Override
	public MemberDTO login(Map<String, String> paraMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO member = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");


			// >>>> 2. 어떤 오라클 서버에 연결할지 설정
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = "select userseq, userid, passwd, name, mobile, point, to_char(registerday, 'yyyy-mm-dd') AS registerday, status\n"+
					"from jdbc_member\n"+
					"where status = 1 AND userid = ? AND passwd = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("passwd"));

			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				member = new MemberDTO();
				
				member.setUserseq(rs.getInt(1));
				member.setUserid(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setName(rs.getString(4));
				member.setMobile(rs.getString(5));
				member.setPoint(rs.getInt(6));
				member.setRegisterday(rs.getString(7));
				member.setStatus(rs.getInt(8));
				
			}

		} catch (ClassNotFoundException e) {
			System.out.println(">>> ojdbc6.jar 파일이 없거나 라이브러리에 등록이 되지 않았습니다.");
			e.printStackTrace();
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
		return member;
	}




}
