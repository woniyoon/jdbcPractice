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
	public int editSubAndConts(Map<String, String> paraMap) {
		int result = 0;
		
		try {
			conn = MyDBConnection.getConn();
			

			String updateSQL = " update jdbc_board "
								+ " set subject = ?, contents = ? "
								+ " where boardno = ?";
			
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, paraMap.get("subject"));
			pstmt.setString(2, paraMap.get("contents"));
			pstmt.setString(3, paraMap.get("boardno"));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deletePost(Map<String, String> paraMap) {
		int result = 0;
		
		try {
			conn = MyDBConnection.getConn();
			

			String deleteSQL = " delete from jdbc_board "
							   + "where boardno = ? AND boardpasswd = ?";
			
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setString(1, paraMap.get("boardno"));
			pstmt.setString(2, paraMap.get("boardpasswd"));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int writeComment(CommentDTO cmtDTO) {
		int result = 0;
		
		try {
			conn = MyDBConnection.getConn();
			

			String insertSQL = " insert into jdbc_comment (commentno, fk_boardno, fk_userid, contents)"
					+ " values(comment_seq.nextval, ?, ?, ?) ";
					
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, cmtDTO.getFk_boardno());
			pstmt.setString(2, cmtDTO.getFk_userid());
			pstmt.setString(3, cmtDTO.getContents());
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				conn.commit();
			}
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			System.out.println(">>> 원글번호 " + cmtDTO.getFk_boardno() + "은 존재하지 않습니다. <<<");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<CommentDTO> fetchComments(String boardno) {
		List<CommentDTO> comments = new ArrayList<CommentDTO>();
		
		try {
			conn = MyDBConnection.getConn();
			
			String selectSQL = " select * \n"+
					" from\n"+
					" (select * \n"+
					" from jdbc_comment where fk_boardno = ?) \n"+
					" join (select name, userid from jdbc_member)\n"+
					" on fk_userid = userid " + 
					" order by 1 ";

			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, boardno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentDTO cmtDTO = new CommentDTO();
				
				cmtDTO.setCommentno(rs.getInt(1));
				cmtDTO.setFk_boardno(rs.getString(2));
				cmtDTO.setFk_userid(rs.getString(3));
				cmtDTO.setContents(rs.getString(4));
				cmtDTO.setWriteday(rs.getString(5));
				cmtDTO.setUsername(rs.getString(6));
				
				comments.add(cmtDTO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return comments;
	}

	@Override
	public Map<String, Integer> getStatisticsPerWeek() {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		
		try {
			conn = MyDBConnection.getConn();

			String selectSQL = "select count(*) as TOTAL\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-6,'yyyy-mm-dd'), 1, 0))  as previous6\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-5,'yyyy-mm-dd'), 1, 0))  as previous5\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-4,'yyyy-mm-dd'), 1, 0))  as previous4\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-3,'yyyy-mm-dd'), 1, 0))  as previous3\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-2,'yyyy-mm-dd'), 1, 0))  as previous2\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate-1,'yyyy-mm-dd'), 1, 0))  as previous1\n"+
							" , sum(decode(to_char(writeday, 'yyyy-mm-dd'), to_char(sysdate,'yyyy-mm-dd'), 1, 0))  as today\n"+
							"from jdbc_board\n"+
							"where (func_midnight(sysdate) - func_midnight(writeday)) < 7";

			pstmt = conn.prepareStatement(selectSQL);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				resultMap.put("TOTAL", rs.getInt(1));
				resultMap.put("previous6", rs.getInt(2));
				resultMap.put("previous5", rs.getInt(3));
				resultMap.put("previous4", rs.getInt(4));
				resultMap.put("previous3", rs.getInt(5));
				resultMap.put("previous2", rs.getInt(6));
				resultMap.put("previous1", rs.getInt(7));
				resultMap.put("TODAY", rs.getInt(8));				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return resultMap;
	}

}
