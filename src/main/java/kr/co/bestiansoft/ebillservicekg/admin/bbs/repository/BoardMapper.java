package kr.co.bestiansoft.ebillservicekg.admin.bbs.repository;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;

@Mapper
public interface BoardMapper {
    List<BoardVo> getBoardList (HashMap<String, Object> param);
    BoardVo getBoardById(Long id, String lang);
    void deleteById(Long id);
    int updateById(BoardVo boardVo);

    List<BoardVo> getBoardMainList ();
    List<BoardVo> getBoardMainPopupList ();
    int updateNotiInqCnt(Long brdId, Long notiInqCnt);
    
    int insertBoard(BoardVo boardVo);
}
