package jdbc.day04.board;

import java.util.List;
import java.util.Map;

public interface InterBoardDAO {

	int	writePost(BoardDTO brdDTO);
	
	List<BoardDTO> fetchBoard();

	BoardDTO showContent(String boardNo);
	
	void incrementViewCount(String boardNo);

	BoardDTO editPost(String boardNo);

	int editSubAndConts(String newSubject, String newContents, String boardNo);

	int deletePost(Map<String, String> paraMap);
}
