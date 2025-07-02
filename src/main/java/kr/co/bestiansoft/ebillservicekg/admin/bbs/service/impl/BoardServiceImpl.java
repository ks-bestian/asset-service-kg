package kr.co.bestiansoft.ebillservicekg.admin.bbs.service.impl;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.repository.BoardMapper;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final ComFileService comFileService;

    /**
     *
     * @param param Param containing the data required to get board:
     *              - lang
     *              - brdSj
     *              - regNm
     *              - deptNm
     * @param brdType
     * @return
     */
    @Override
    public List<BoardVo> getBoardList(HashMap<String, Object> param, String brdType) {
        param.put("brdType", brdType);
        List<BoardVo> result = boardMapper.getBoardList(param);
        return result;
    }


    /**
     *
     * @param boardVo
     * @param brdType
     * @return
     */
    @Transactional
    @Override
    public BoardVo createBoard(BoardVo boardVo, String brdType) {
    	boardVo.setBrdType(brdType);
        boardVo.setRegId(new SecurityInfoUtil().getAccountId());
    	boardMapper.insertBoard(boardVo);
    	return boardVo;
    }

    @Transactional
    @Override
    public int updateBoard(BoardVo boardVo, Long brdId) {
    	boardVo.setBrdId(brdId);
        int dto = boardMapper.updateById(boardVo);

        return dto;
    }

    @Transactional
    @Override
    public void deleteBoard(List<Long> boardIds) {
        for (Long id : boardIds) {
            boardMapper.deleteById(id);
        }
    }

    @Transactional
    @Override
    public BoardVo getBoardById(Long brdId, String lang) {
    	BoardVo dto = boardMapper.getBoardById(brdId, lang);

    	if(dto != null) {
    		dto.setNotiInqCnt(dto.getNotiInqCnt() + 1);
            boardMapper.updateNotiInqCnt(brdId, dto.getNotiInqCnt());
    	}
        return dto;
    }
    
    @Transactional
    @Override
    public BoardVo createBoardFile(BoardVo boardVo, String brdType) {
        if(boardVo.getFiles() != null) {
            String fileGroupId = comFileService.saveFileList(boardVo.getFiles());
            boardVo.setFileGroupId(fileGroupId);
        }
    	
    	boardVo.setBrdType(brdType);

        boardVo.setRegId(new SecurityInfoUtil().getAccountId());
    	boardMapper.insertBoard(boardVo);
    	boardVo.setFiles(null);
    	return boardVo;
    }
    
    @Override
    public List<BoardVo> getBoardMainList() {
    	return boardMapper.getBoardMainList();
    }
}