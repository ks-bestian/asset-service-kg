package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.impl;


import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.EasFileType;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository.OfficialDocumentMapper;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService {

    private final OfficialDocumentMapper officialDocumentMapper;
    private final EasFileService easFileService;

    /**
     * Retrieves a list of documents based on the search criteria provided.
     *
     * @param vo the search criteria encapsulated in a {@link SearchDocumentVo} object.
     *           This includes fields such as document attributes, time limits,
     *           document type, and received date ranges.
     *           The method also sets the user ID in the search criteria using the account information.
     *
     * @return a list of {@link DocumentListDto} objects that match the search criteria.
     *         Each object contains details such as document ID, attributes, sender information,
     *         and timestamps related to document receipt and checking.
     */
    @Override
    public List<DocumentListDto> getDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getDocumentList(vo);
    }

    /**
     * Saves an official document to the database.
     *
     * @param vo an instance of {@link OfficialDocumentVo} containing the details
     *           of the document to be saved, such as ID, attributes, status, and other metadata.
     * @return an integer indicating the number of rows affected by the save operation.
     */
    public int saveOfficialDocument(OfficialDocumentVo vo){
        return officialDocumentMapper.saveOfficialDocument(vo);
    }



    /**
     * Updates the status of an official document in the database.
     *
     * @param docId the unique identifier of the document whose status is to be updated
     * @param status the new status to be applied to the specified document
     * @return an integer representing the number of rows affected by the update operation
     */
    @Override
    public int updateStatusOfficialDocument(String docId, String status) {
        return officialDocumentMapper.updateStatus(docId,status);
    }

    /**
     * Counts the total number of documents associated with the currently logged-in account.
     *
     * @return the count of documents as an integer.
     */
    @Override
    public int countDocumentList() {
        return officialDocumentMapper.countDocumentList(new SecurityInfoUtil().getAccountId());
    }

    /**
     * Retrieves the details of a document by its identifier.
     *
     * @param docId The unique identifier of the document.
     * @return A DTO containing the document details, including its associated files.
     */
    @Override
    public DocumentDetailDto getDocumentDetail(String docId) {
        DocumentDetailDto documentDetailDto = officialDocumentMapper.getDocumentDetail(docId);
        documentDetailDto.setFiles(easFileService.getAttachFiles(docId, EasFileType.ATTACHMENT_FILE.getCodeId()));
        return documentDetailDto;
    }

    /**
     * Retrieves a list of DocumentUserDto objects associated with the specified document ID.
     *
     * @param docId the unique identifier of the document for which the users are to be fetched
     * @return a list of DocumentUserDto representing the users associated with the specified document
     */
    @Override
    public List<DocumentUserDto> getDocumentUser(String docId) {
        return officialDocumentMapper.getDocumentUser(docId);
    }



    @Override
    public Boolean isReject(String docId) {
        return officialDocumentMapper.isReject(docId);
    }

    @Override
    public List<DocumentListDto> getRejectDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getRejectDocumentList(vo);
    }

    @Override
    public int countRejectDocument() {
        return officialDocumentMapper.countRejectDocument(new SecurityInfoUtil().getAccountId());
    }

    @Override
    public List<DocumentListDto> getEndDocumentList(SearchDocumentVo vo) {
        return officialDocumentMapper.getEndDocumentList(vo);
    }

    @Override
    public List<DocumentListDto> getMyDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getMyDocumentList(vo);
    }

    @Override
    public List<DocumentListDto> getWorkList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getWorkList(vo);
    }

    @Override
    public List<DocumentListDto> getProcessedList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getProcessedList(vo);
    }
}
