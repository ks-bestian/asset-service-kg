package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.impl;


import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.EasFileType;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository.OfficialDocumentMapper;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        if(vo.getBetweenRcvDtm() != null) {
            vo.setFromRcvDtm(parseFromDateRange(vo.getBetweenRcvDtm()));
            vo.setToRcvDtm(parseToDateRange(vo.getBetweenRcvDtm()));
        }else if(vo.getBetweenResDtm() != null) {
            vo.setFromResDtm(parseFromDateRange(vo.getBetweenResDtm()));
            vo.setToResDtm(parseToDateRange(vo.getBetweenResDtm()));
        }
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
        if(vo.getBetweenRcvDtm() != null) {
            vo.setFromRcvDtm(parseFromDateRange(vo.getBetweenRcvDtm()));
            vo.setToRcvDtm(parseToDateRange(vo.getBetweenRcvDtm()));
        }else if(vo.getBetweenResDtm() != null) {
            vo.setFromResDtm(parseFromDateRange(vo.getBetweenResDtm()));
            vo.setToResDtm(parseToDateRange(vo.getBetweenResDtm()));
        }
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getWorkList(vo);
    }

    @Override
    public List<DocumentListDto> getProcessedList(SearchDocumentVo vo) {
        if(vo.getBetweenRcvDtm() != null) {
            vo.setFromRcvDtm(parseFromDateRange(vo.getBetweenRcvDtm()));
            vo.setToRcvDtm(parseToDateRange(vo.getBetweenRcvDtm()));
        }else if(vo.getBetweenResDtm() != null) {
            vo.setFromResDtm(parseFromDateRange(vo.getBetweenResDtm()));
            vo.setToResDtm(parseToDateRange(vo.getBetweenResDtm()));
        }
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getProcessedList(vo);
    }

    @Override
    public void deleteDocument(String docId) {
        officialDocumentMapper.deleteDocument(docId);
    }

    @Override
    public int countWorkList() {
        return officialDocumentMapper.countWorkList(new SecurityInfoUtil().getAccountId());
    }

    public LocalDateTime parseFromDateRange(String dateRangeStr) {
        if (dateRangeStr == null || dateRangeStr.trim().isEmpty()) {
            return null;
        }

        try {
            String[] dates = dateRangeStr.split("~");
            if (dates.length > 0 && !dates[0].trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fromDate = LocalDate.parse(dates[0].trim(), formatter);
                return fromDate.atStartOfDay(); // 00:00:00으로 설정
            }
        } catch (DateTimeParseException e) {
            // 로깅 또는 다른 예외 처리
            System.err.println("날짜 형식이 올바르지 않습니다: " + dateRangeStr);
        }

        return null;
    }

    public static LocalDateTime parseToDateRange(String dateRangeStr) {
        if (dateRangeStr == null || dateRangeStr.trim().isEmpty()) {
            return null;
        }

        try {
            String[] dates = dateRangeStr.split("~");
            LocalDate toDate;

            if (dates.length > 1 && !dates[1].trim().isEmpty()) {
                // 종료일이 있는 경우
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                toDate = LocalDate.parse(dates[1].trim(), formatter);
            } else if (dates.length > 0 && !dates[0].trim().isEmpty()) {
                // 종료일이 없고 시작일만 있는 경우, 시작일을 종료일로 사용
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                toDate = LocalDate.parse(dates[0].trim(), formatter);
            } else {
                return null;
            }

            return toDate.atTime(23, 59, 59); // 23:59:59로 설정
        } catch (DateTimeParseException e) {
            // 로깅 또는 다른 예외 처리
            System.err.println("날짜 형식이 올바르지 않습니다: " + dateRangeStr);
        }

        return null;
    }
    @Override
    public List<ApprovalLIstDto> getApprovalList(SearchDocumentVo vo) {
        if(vo.getBetweenRcvDtm() != null) {
            vo.setFromRcvDtm(parseFromDateRange(vo.getBetweenRcvDtm()));
            vo.setToRcvDtm(parseToDateRange(vo.getBetweenRcvDtm()));
        }else if(vo.getBetweenResDtm() != null) {
            vo.setFromResDtm(parseFromDateRange(vo.getBetweenResDtm()));
            vo.setToResDtm(parseToDateRange(vo.getBetweenResDtm()));
        }
        vo.setUserId(new SecurityInfoUtil().getAccountId());

        return officialDocumentMapper.getApprovalList(vo);
    }

    public int countApprovalList(){
        return officialDocumentMapper.countApprovalList(new SecurityInfoUtil().getAccountId());
    }
    /**
     * 문자열 날짜를 LocalDateTime으로 변환
     *
     * @param dateStr "yyyy-MM-dd" 형식의 문자열
     * @param startOfDay true면 00:00:00, false면 23:59:59로 시간 설정
     * @return 변환된 LocalDateTime 객체
     */
    public static LocalDateTime parseDate(String dateStr, boolean startOfDay) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr.trim(), formatter);

            if (startOfDay) {
                return date.atStartOfDay();
            } else {
                return date.atTime(23, 59, 59);
            }
        } catch (DateTimeParseException e) {
            // 로깅 또는 다른 예외 처리
            System.err.println("날짜 형식이 올바르지 않습니다: " + dateStr);
        }

        return null;
    }

    /**
     * 문자열 날짜와 시간을 LocalDateTime으로 변환
     *
     * @param dateTimeStr "yyyy-MM-dd HH:mm:ss" 형식의 문자열
     * @return 변환된 LocalDateTime 객체
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTimeStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            // 잘못된 형식일 경우 다른 형식 시도
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(dateTimeStr.trim(), formatter).atStartOfDay();
            } catch (DateTimeParseException e2) {
                // 로깅 또는 다른 예외 처리
                System.err.println("날짜 형식이 올바르지 않습니다: " + dateTimeStr);
            }
        }

        return null;
    }



}
