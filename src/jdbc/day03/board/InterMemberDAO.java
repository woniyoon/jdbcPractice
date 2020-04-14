package jdbc.day03.board;

import java.util.Map;

public interface InterMemberDAO {
	// DAO : DB Access Object
	// DTO : Data Transfer Object (Data를 전달하는 객체 == 행)
	int register(MemberDTO memDTO);

	MemberDTO login(Map<String, String> paraMap);	// map에 아이디와 암호를 다 넣어줌
}
