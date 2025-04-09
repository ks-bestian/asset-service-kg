package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class EasFileVo extends ComDefaultVO {
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

    @Builder
    public EasFileVo(String fileId, String docId, String fileType, String orgFileId, String orgFileNm, String pdfFileId, String pdfFileNm, int fileSize, String fileExt, char deleteYn, LocalDateTime deleteDtm, String deleteUserId) {
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
    }
}