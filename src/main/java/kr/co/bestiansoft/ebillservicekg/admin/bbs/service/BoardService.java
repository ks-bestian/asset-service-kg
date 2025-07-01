package kr.co.bestiansoft.ebillservicekg.admin.bbs.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;

public interface BoardService {

    List<BoardVo> getBoardList(HashMap<String, Object> param, String brdType);
    BoardVo createBoard(BoardVo boardVo, String brdType);
    int updateBoard(BoardVo boardVo, Long brdId);
    void deleteBoard(List<Long> boardIds);
    BoardVo getBoardById(Long brdId, String lang);
    BoardVo createBoardFile(BoardVo boardVo, String brdType);
    List<BoardVo> getBoardMainList();
}
