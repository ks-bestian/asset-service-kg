package kr.co.bestiansoft.ebillservicekg.admin.bbs.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;

@Mapper
public interface BoardMapper {
    List<BoardVo> getBoardList (String brdSj, String brdType);
    BoardVo getBoardById(Long id);
    void deleteById(Long id);
    int updateById(BoardVo boardVo);

    List<BoardVo> getBoardMainList (String brdType);
    List<BoardVo> getBoardMainPopupList ();
    int updateNotiInqCnt(Long brdId, Long notiInqCnt);
    
    int insertBoard(BoardVo boardVo);
}
