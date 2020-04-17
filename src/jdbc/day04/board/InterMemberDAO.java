package jdbc.day04.board;

import java.util.List;
import java.util.Map;


public interface InterMemberDAO {
	
	int register(MemberDTO memDTO);

	MemberDTO login(Map<String, String> paraMap);	// map에 아이디와 암호를 다 넣어줌
	
	int updatePoint(String userid);					// 게시판에 글 쓴 유저의 point를 업데이트
	
	List<MemberDTO> selectAllMember();
}


// DAO : DB Access Object
// DTO : Data Transfer Object (Data를 전달하는 객체 == 행)

