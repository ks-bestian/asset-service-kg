package kr.co.bestiansoft.ebillservicekg.asset.manual.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MnulDto {
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

    public static MnulDto from(MnulVo vo) {
        return MnulDto.builder()
            .mnlId(vo.getMnlId())
            .eqpmntId(vo.getEqpmntId())
            .mnlSe(vo.getMnlSe())
            .seq(vo.getSeq())
            .filePath(vo.getFilePath())
            .fileNm(vo.getFileNm())
            .fileNm2(vo.getFileNm2())
            .orgnlFileNm(vo.getOrgnlFileNm())
            .fileExtn(vo.getFileExtn())
            .fileSz(vo.getFileSz())
            .mnlLng(vo.getMnlLng())
            .mnlNm(vo.getMnlNm())
            .build();
    }

}
