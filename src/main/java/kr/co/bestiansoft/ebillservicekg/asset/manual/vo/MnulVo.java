package kr.co.bestiansoft.ebillservicekg.asset.manual.vo;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
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
    private String fileNm2;
    private String orgnlFileNm;
    private String fileExtn;
    private Long fileSz;
    private String mnlLng;
    private String mnlNm;

    @JsonIgnore
    private MultipartFile file;

    private String fileId;
    private List<String> videoFileUrl;
    
    private MnulDto videoFile;


    @Builder
    public MnulVo(String mnlId, String eqpmntId, String mnlSe, int seq, String filePath, String fileNm, String orgnlFileNm, String fileExtn, Long fileSz, String mnlLng, String mnlNm, MultipartFile file, String fileId, List<String> videoFileUrl, MnulDto videoFile) {
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
        this.mnlNm = mnlNm;
        this.file = file;
        this.fileId = fileId;
        this.videoFileUrl = videoFileUrl;
        this.videoFile = videoFile;
    }

}
