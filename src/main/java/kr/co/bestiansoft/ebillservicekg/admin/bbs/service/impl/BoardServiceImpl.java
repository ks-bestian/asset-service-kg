package kr.co.bestiansoft.ebillservicekg.admin.bbs.service.impl;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public List<BoardVo> getBoardList(HashMap<String, Object> param, String brdType) {
        String brdSj = String.valueOf(param.getOrDefault("brdSj", ""));
        param.put("brdSj", brdSj);
        param.put("brdType", brdType);
        List<BoardVo> result = boardMapper.getBoardList(param);
        return result;
    }

    @Transactional
    @Override
    public BoardVo createBoard(BoardVo boardVo, String brdType) {
    	boardVo.setBrdType(brdType);

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
    public BoardVo getBoardById(Long brdId) {
    	BoardVo dto = boardMapper.getBoardById(brdId);
    	if(dto != null) {
    		dto.setNotiInqCnt(dto.getNotiInqCnt() + 1);
            boardMapper.updateNotiInqCnt(brdId, dto.getNotiInqCnt());
    	}
        return dto;
    }
    
    @Transactional
    @Override
    public BoardVo createBoardFile(BoardVo boardVo, String brdType) {
    	String fileGroupId = comFileService.saveFile(boardVo.getFiles());
    	
    	boardVo.setBrdType(brdType);
    	boardVo.setFileGroupId(fileGroupId);
    	
    	boardMapper.insertBoard(boardVo);
    	boardVo.setFiles(null);
    	return boardVo;
    }
}