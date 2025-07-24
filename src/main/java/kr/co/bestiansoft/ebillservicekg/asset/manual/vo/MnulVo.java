package kr.co.bestiansoft.ebillservicekg.asset.manual.vo;


import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int fileSz;
    private String mnlLng;


    public MnulVo(MnulVo er) {
        this.eqpmntId = er.getEqpmntId();
        this.mnlSe = er.getMnlSe();
        this.seq = er.getSeq();
        this.filePath = er.getFilePath();
        this.fileNm = er.getFileNm();
        this.orgnlFileNm = er.getOrgnlFileNm();
        this.fileExtn = er.getFileExtn();
        this.fileSz = er.getFileSz();
        this.mnlLng = er.getMnlLng();

    }

}
