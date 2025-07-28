package kr.co.bestiansoft.ebillservicekg.admin.bbs.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.repository.BoardMapper;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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
     * Retrieves a list of BoardVo objects based on the provided parameters and board type.
     *
     * @param param a HashMap containing various parameters for filtering the board list
     * @param brdType a String specifying the type of the board to filter the list
     * @return a List of BoardVo objects that match the provided parameters and board type
     */
    @Override
    public List<BoardVo> getBoardList(HashMap<String, Object> param, String brdType) {
        param.put("brdType", brdType);
        List<BoardVo> result = boardMapper.getBoardList(param);
        return result;
    }


    /**
     * Creates a new board entry in the system with the specified board information and type.
     *
     * @param boardVo an object containing the board's details, such as title, content, and other properties
     * @param brdType a String representing the type of the board
     * @return the created BoardVo object with the updated*/
    @Transactional
    @Override
    public BoardVo createBoard(BoardVo boardVo, String brdType) {
    	boardVo.setBrdType(brdType);
        boardVo.setRegId(new SecurityInfoUtil().getAccountId());
    	boardMapper.insertBoard(boardVo);
    	return boardVo;
    }

    /**
     * Updates an existing board entry with the specified details and board ID.
     *
     * @param boardVo the object containing the updated details of the board
     * @param brdId the unique identifier of the board to be updated
     * @return an integer indicating the number of rows affected by the update operation
     */
    @Transactional
    @Override
    public int updateBoard(BoardVo boardVo, Long brdId) {
    	boardVo.setBrdId(brdId);
        int dto = boardMapper.updateById(boardVo);

        return dto;
    }

    /**
     * Deletes multiple boards from the system based on their unique identifiers.
     *
     * @param boardIds a list of unique board IDs that need to be deleted
     */
    @Transactional
    @Override
    public void deleteBoard(List<Long> boardIds) {
        for (Long id : boardIds) {
            boardMapper.deleteById(id);
        }
    }

    /**
     * Retrieves detailed information about a specific board identified by its ID and language.
     * If the board is found, the notification inquiry count is incremented and updated in the database.
     *
     * @param brdId the unique identifier of the board to be retrieved
     * @param lang the language in which the board details should be retrieved
     * @return a BoardVo object containing the details of the specified board,
     *         or null if no board exists with the given ID and language
     */
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

    /**
     * Creates a new board entry with associated files, board details, and type.
     * If files are provided in the BoardVo object, they are saved and linked to the board.
     *
     * @param boardVo an object containing the details of the board, such as title, content, and associated files
     * @param brdType a String representing the type of the board for categorization
     * @return the created BoardVo object with updated information, such as the generated file group ID and other properties
     */
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

    /**
     * Retrieves the main list of boards without requiring any additional parameters.
     *
     * @return a List of BoardVo objects representing the main board entries
     */
    @Override
    public List<BoardVo> getBoardMainList() {
    	return boardMapper.getBoardMainList();
    }
}