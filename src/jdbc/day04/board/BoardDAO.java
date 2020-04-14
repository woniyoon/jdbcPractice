package jdbc.day04.board;

import java.sql.*;
import java.util.*;

import jdbc.connection.MyDBConnection;


public class BoardDAO implements InterBoardDAO {

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
	public int writePost(BoardDTO brdDTO) {
		
		int result = 0;

		try {
			
			conn = MyDBConnection.getConn();
			
			String sql = " insert into jdbc_board (boardno, fk_userid, subject, contents, boardpasswd)"
					+ " values(board_seq.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, brdDTO.getFk_userid());
			pstmt.setString(2, brdDTO.getSubject());
			pstmt.setString(3, brdDTO.getContents());
			pstmt.setString(4, brdDTO.getBoardpasswd());
			
			result = pstmt.executeUpdate();

			
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			
			close();
//			try {
//				if(pstmt != null) pstmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		
		return result;
	}

	@Override
	public List<BoardDTO> fetchBoard() {
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " select B.boardno,\n"+
						 "       B.subject,\n"+
						 "       M.name,\n"+
//						 "       B.contents,\n"+
						 "       to_char(B.writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday,\n"+
						 "       B.viewcount\n"+
						 " from jdbc_board B join jdbc_member M\n"+
						 " on B.fk_userid = M.userid\n"+
						 " order by 1 desc ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO brdDTO = new BoardDTO();
				brdDTO.setBoardno(rs.getInt(1));
				brdDTO.setSubject(rs.getString(2));
				
				MemberDTO member = new MemberDTO();
				member.setName(rs.getString(3));
				brdDTO.setMember(member);
				
				brdDTO.setWriteday(rs.getString(4));
				brdDTO.setViewcount(rs.getInt(5));
				
				boardList.add(brdDTO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return boardList;
	}

	@Override
	public BoardDTO showContent(String boardNo) {
		BoardDTO brdDTO = null;

		try {
			conn = MyDBConnection.getConn();
			
			String selectSQL = "select contents, fk_userid, subject, boardno\n"+
					"from jdbc_board\n"+
					"where boardno = ?"; 
			
			
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				brdDTO = new BoardDTO();
				brdDTO.setContents(rs.getString(1));
				brdDTO.setFk_userid(rs.getString(2));
				brdDTO.setSubject(rs.getString(3));
				brdDTO.setBoardno(rs.getInt(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return brdDTO;
	}

	@Override
	public void incrementViewCount(String boardNo) {
		
		try {
			conn = MyDBConnection.getConn();
			

			String updateSQL = " update jdbc_board " + 
							   " set viewcount = (viewcount + 1) " +
							   " where boardno = ?\n";
			
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, boardNo);
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public BoardDTO editPost(String boardNo) {
		BoardDTO brdDTO = null;
		
		try {
			conn = MyDBConnection.getConn();
						
			String selectSQL = " select boardno,\n"+
								"       fk_userid,\n"+
								"       subject,\n"+
								"       contents,\n"+
								"       boardpasswd\n"+
								" from jdbc_board\n"+
								" where boardno = ? ";
			
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				brdDTO = new BoardDTO();
				
				brdDTO.setBoardno(rs.getInt(1));
				brdDTO.setFk_userid(rs.getString(2));
				brdDTO.setSubject(rs.getString(3));
				brdDTO.setContents(rs.getString(4));
				brdDTO.setBoardpasswd(rs.getString(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return brdDTO;
	}

	@Override
	public int editSubAndConts(String newSubject, String newContents, String boardNo) {
		int result = 0;
		
		try {
			conn = MyDBConnection.getConn();
			

			String updateSQL = " update jdbc_board "
								+ " set subject = ?, contents = ? "
								+ " where boardno = ?";
			
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, newSubject);
			pstmt.setString(2, newContents);
			pstmt.setString(3, boardNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deletePost(String boardNo, String password) {
		int result = 0;
		
		try {
			conn = MyDBConnection.getConn();
			

			String deleteSQL = " delete from jdbc_board "
							   + "where boardno = ? AND boardpasswd = ?";
			
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setString(1, boardNo);
			pstmt.setString(2, password);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
