package jdbc.day04.board;

import java.util.List;
import java.util.Map;

public interface InterBoardDAO {

	int	writePost(BoardDTO brdDTO);
	
	List<BoardDTO> fetchBoard();

	BoardDTO showContent(String boardNo);
	
	void incrementViewCount(String boardNo);

	BoardDTO editPost(String boardNo);

	int editSubAndConts(Map<String, String> paraMap);

	int deletePost(Map<String, String> paraMap);

	int writeComment(CommentDTO cmtDTO);

	List<CommentDTO> fetchComments(String boardno);
}
