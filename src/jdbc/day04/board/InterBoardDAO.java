package jdbc.day04.board;

import java.util.List;

public interface InterBoardDAO {

	int	writePost(BoardDTO brdDTO);
	
	List<BoardDTO> fetchBoard();

	BoardDTO showContent(String boardNo);
	
	int incrementViewCount(String boardNo);
}
