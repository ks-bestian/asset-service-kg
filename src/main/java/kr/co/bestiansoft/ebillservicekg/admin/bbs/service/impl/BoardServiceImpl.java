package kr.co.bestiansoft.ebillservicekg.admin.bbs.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.repository.BoardMapper;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    @Override
    public List<BoardVo> getBoardList(HashMap<String, Object> param, String brdType) {
        String brdSj = String.valueOf(param.get("brdSj"));

        List<BoardVo> result = boardMapper.getBoardList(brdSj, brdType);
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
        dto.setNotiInqCnt(dto.getNotiInqCnt() + 1);

        boardMapper.updateNotiInqCnt(brdId, dto.getNotiInqCnt());
        return dto;
    }
}