package kr.co.bestiansoft.ebillservicekg.asset.manual.vo;


import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class MnulVo extends ComDefaultVO {
    private String mnlId;
    private String eqpmntId;
    private String mnlSe;
    private int seq;
    private String filePath;
    private String fileNm;
    private String orgnlFileNm;
    private String fileExtn;
    private Long fileSz;
    private String mnlLng;
    private MultipartFile file;



    @Builder
    public MnulVo(String mnlId, String eqpmntId, String mnlSe, int seq, String filePath, String fileNm, String orgnlFileNm, String fileExtn, Long fileSz, String mnlLng, MultipartFile file) {
        this.mnlId = mnlId;
        this.eqpmntId = eqpmntId;
        this.mnlSe = mnlSe;
        this.seq = seq;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.orgnlFileNm = orgnlFileNm;
        this.fileExtn = fileExtn;
        this.fileSz = fileSz;
        this.mnlLng = mnlLng;
        this.file = file;
    }

}
