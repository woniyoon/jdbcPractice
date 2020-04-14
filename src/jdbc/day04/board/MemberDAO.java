package jdbc.day04.board;

import java.sql.*;
import java.util.Map;

import jdbc.connection.MyDBConnection;

public class MemberDAO implements InterMemberDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	private void close() {
		try {
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int register(MemberDTO member) {

		int result = 0;

		try {
			
			conn = MyDBConnection.getConn();
			
			String sql = " insert into jdbc_member (userseq, userid, passwd, name, mobile)"
					+ " values(user_seq.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMobile());

			result = pstmt.executeUpdate();

		} catch(SQLIntegrityConstraintViolationException e){
			System.out.println("에러메시지 : " + e.getMessage());
			System.out.println("에러코드번호 : " + e.getErrorCode());
			System.out.println(">>> 중복된 아이디입니다. 새로운 아이디를 입력하세요! ");
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}


	@Override
	public MemberDTO login(Map<String, String> paraMap) {

//		ResultSet rs = null;
		MemberDTO member = null;

		try {
			conn = MyDBConnection.getConn();
			
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

		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			
			close();
			
//			try {
////				if(conn != null) conn.close();			---- 프로그램 종료가 되기 전까지는 close 하면 안 됨.
//				if(pstmt != null) pstmt.close();
//				if(rs != null) rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		
	
		return member;
	}

	@Override
	public int updatePoint(String userid) {
		int result = 0;

		try {
			conn = MyDBConnection.getConn();

			String sql = " update jdbc_member set point = point+10 "
						+ "where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			
			close();
		}
		
		System.out.println("updatePoint 메소드의 result : "+result);
		return result;
	}


}
