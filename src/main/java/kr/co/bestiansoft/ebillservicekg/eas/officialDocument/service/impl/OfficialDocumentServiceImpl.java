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

    /**
     * Checks if the document specified by the given ID is marked as rejected.
     *
     * @param docId The unique identifier of the document to be checked.
     * @return A Boolean value: true if the document is rejected, false otherwise.
     */
    @Override
    public Boolean isReject(String docId) {
        return officialDocumentMapper.isReject(docId);
    }

    /**
     * Retrieves a list of rejected documents based on the provided search criteria.
     *
     * @param vo an instance of SearchDocumentVo containing search criteria, including user identification
     * @return a list of DocumentListDto representing the rejected documents matching the criteria
     */
    @Override
    public List<DocumentListDto> getRejectDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getRejectDocumentList(vo);
    }

    /**
     * Counts the number of rejected documents associated with the current user's account.
     * The method utilizes account information retrieved from the SecurityInfoUtil class.
     *
     * @return the total number of rejected documents for the account.
     */
    @Override
    public int countRejectDocument() {
        return officialDocumentMapper.countRejectDocument(new SecurityInfoUtil().getAccountId());
    }

    /**
     * Retrieves a list of documents specific to the currently authenticated user.
     *
     * @param vo a SearchDocumentVo object containing the search criteria and
     *           additional data necessary to fetch the document list
     * @return a list of DocumentListDto objects representing the user's documents
     */
    @Override
    public List<DocumentListDto> getMyDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getMyDocumentList(vo);
    }

    /**
     * Retrieves the work list based on the provided search criteria.
     *
     * @param vo an instance of SearchDocumentVo containing search parameters such as date range and user ID
     * @return a list of DocumentListDto objects matching the search criteria
     */
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
        log.info(vo.toString());
        return officialDocumentMapper.getWorkList(vo);
    }

    /**
     * Retrieves a processed list of documents based on the given search criteria encapsulated in the SearchDocumentVo object.
     *
     * @param vo the search criteria containing filtering information like date ranges and other conditions. It is updated internally
     *           to set the from and to dates or user ID as necessary.
     * @return a list of DocumentListDto objects that match the search criteria.
     */
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

    /**
     * Deletes a document with the specified document ID.
     *
     * @param docId the unique identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        officialDocumentMapper.deleteDocument(docId);
    }

    /**
     * Counts the total number of work list items associated with the current user's account.
     *
     * @return the total number of work list items associated with the account ID retrieved
     *         from the SecurityInfoUtil for the current user.
     */
    @Override
    public int countWorkList() {
        return officialDocumentMapper.countWorkList(new SecurityInfoUtil().getAccountId());
    }

    /**
     * Parses a date range string and extracts the starting date of the range.
     * The starting date is converted to a {@link LocalDateTime} with the time set to 00:00:00.
     * The expected date format in the input is "yyyy-MM-dd".
     *
     * @param dateRangeStr the date range string, formatted as "yyyy-MM-dd~yyyy-MM-dd".
     *                     The method extracts the starting date from this range. If null or empty, null is returned.
     *                     If the date format is invalid, null is returned.
     * @return the starting date as a {@link LocalDateTime} with the time set to 00:00:00,
     *         or null if the input is invalid or parsing fails.
     */
    public LocalDateTime parseFromDateRange(String dateRangeStr) {
        if (dateRangeStr == null || dateRangeStr.trim().isEmpty()) {
            return null;
        }

        try {
            String[] dates = dateRangeStr.split("~");
            if (dates.length > 0 && !dates[0].trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fromDate = LocalDate.parse(dates[0].trim(), formatter);
                return fromDate.atStartOfDay(); // Set to 00:00:00
            }
        } catch (DateTimeParseException e) {
            // Logging or other exception processing
            System.err.println("The date format is not correct: " + dateRangeStr);
        }

        return null;
    }

    /**
     * Parses a date range string and returns the end date as a LocalDateTime object.
     * The method expects the input string to represent a date range in the format "startDate~endDate".
     * If the end date is not provided, the start date is used as the end date and a time of 23:59:59 is set.
     * If the input is invalid or cannot be parsed, the method returns null.
     *
     * @param dateRangeStr the input string representing a date range, where dates are in the format "yyyy-MM-dd".
     *                     It may include a single date or two dates separated by a tilde (~).
     * @return a LocalDateTime object representing the end date with time set to 23:59:59,
     *         or null if the input is invalid or cannot be parsed.
     */
    public static LocalDateTime parseToDateRange(String dateRangeStr) {
        if (dateRangeStr == null || dateRangeStr.trim().isEmpty()) {
            return null;
        }

        try {
            String[] dates = dateRangeStr.split("~");
            LocalDate toDate;

            if (dates.length > 1 && !dates[1].trim().isEmpty()) {
                //If there is an end date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                toDate = LocalDate.parse(dates[1].trim(), formatter);
            } else if (dates.length > 0 && !dates[0].trim().isEmpty()) {
                // If there is no end date and only the start date, the start date is used as the end date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                toDate = LocalDate.parse(dates[0].trim(), formatter);
            } else {
                return null;
            }

            return toDate.atTime(23, 59, 59); // 23:59:59as setting
        } catch (DateTimeParseException e) {
            // Logging or different exception treatment
            System.err.println("The date format is not correct: " + dateRangeStr);
        }

        return null;
    }

    /**
     * Retrieves a list of approvals based on the given search criteria.
     * The method processes the date range filters and user identification
     * before fetching the data from the data mapper.
     *
     * @param vo the search criteria encapsulated in a SearchDocumentVo object.
     *           This includes filters such as date ranges and user-specific data.
     * @return a list of ApprovalListDto containing the approvals matching
     *         the provided search criteria.
     */
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

    /**
     * Counts the number of approval items in the list associated with the current account.
     *
     * @return the count of approval items for the current account.
     */
    public int countApprovalList(){
        return officialDocumentMapper.countApprovalList(new SecurityInfoUtil().getAccountId());
    }

}
