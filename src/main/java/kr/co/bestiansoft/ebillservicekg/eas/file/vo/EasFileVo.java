package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EasFileVo{
    private String fileId;
    private String docId;
    private String fileType;
    private String orgFileId;
    private String orgFileNm;
    private String pdfFileId;
    private String pdfFileNm;
    private int fileSize;
    private String fileExt;
    private char deleteYn;
    private LocalDateTime deleteDtm;
    private String deleteUserId;
    private MultipartFile[] files;
    private String regId;
    private LocalDateTime regDt;

    @Builder
    public EasFileVo(String fileId, String docId, String fileType, String orgFileId, String orgFileNm, String pdfFileId, String pdfFileNm, int fileSize, String fileExt, char deleteYn, LocalDateTime deleteDtm, String deleteUserId, MultipartFile[] files, String regId, LocalDateTime regDt) {
        this.fileId = fileId;
        this.docId = docId;
        this.fileType = fileType;
        this.orgFileId = orgFileId;
        this.orgFileNm = orgFileNm;
        this.pdfFileId = pdfFileId;
        this.pdfFileNm = pdfFileNm;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.deleteYn = deleteYn;
        this.deleteDtm = deleteDtm;
        this.deleteUserId = deleteUserId;
        this.files = files;
        this.regId = regId;
        this.regDt = regDt;
    }

    public EasFileVo fromSaveFileDto(SaveFileDto dto, EasFileVo base) {
        EasFileVo result = new EasFileVo();
        // Copy of basic information
        result.setDocId(base.getDocId());
        result.setFileType(base.getFileType());
        result.setRegId(base.getRegId());
        result.setRegDt(base.getRegDt());
        result.setDeleteYn(base.getDeleteYn());
        result.setDeleteUserId(base.getDeleteUserId());
        result.setDeleteDtm(base.getDeleteDtm());
        result.setPdfFileId(base.getPdfFileId());
        result.setPdfFileNm(base.getPdfFileNm());

        // SaveFileDto information setting
        result.setFileId(dto.getFileId());
        result.setOrgFileId(dto.getFileId());
        result.setOrgFileNm(dto.getOrgFileNm());
        result.setFileSize(dto.getFileSize());
        result.setFileExt(dto.getFileExt());

        return result;
    }

}