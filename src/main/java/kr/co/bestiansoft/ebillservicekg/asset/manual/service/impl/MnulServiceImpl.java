package kr.co.bestiansoft.ebillservicekg.asset.manual.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.manual.repository.MnulMapper;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MnulServiceImpl implements MnulService {
    private final MnulMapper mnulMapper;
    @Value("${file.upload.path}")
    private String fileUploadDir;

    @Override
    public int createMnul(List<MnulVo> mnulVoList, String eqpmntId, String mnlSe) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
            if (mnulVo.getFile() != null) {
                MultipartFile file = mnulVo.getFile();
                String fileNm = file.getOriginalFilename();
                String fileType = file.getContentType();

                try {
                    String filePath = FileUtil.upload(file, fileUploadDir, fileType, fileNm);
                    mnulVo.setFilePath(filePath);
                    mnulVo.setFileNm(file.getOriginalFilename());
                    mnulVo.setOrgnlFileNm(file.getOriginalFilename());
                    mnulVo.setFileExtn(file.getContentType());
                    mnulVo.setFileSz(file.getSize());
                } catch (IOException e) {
                    throw new RuntimeException("파일저장실패 : " + e);
                }
            }

            mnulVo.setMnlId(StringUtil.getMnlUUID());
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setMnlSe(mnlSe);
            mnulVo.setSeq(i);
            mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);

//            Optional.ofNullable(mnulVo.getMnlLng2())
//                    .ifPresent(mnulVo::setMnlLng);
            mnulVo.setMnlLng("KR");

            i++;
        }

        return mnulMapper.createMnul(mnulVoList);
    }

    public void saveMultipartFiles(MultipartFile[] files) {
        String fileId = null;
    }

    @Override
    public List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds) {

        return mnulMapper.getMnulListByEquipIds(eqpmntIds);
    }

    @Override
    public List<MnulVo> getMnulListByEqpmntId(String eqpmntId) {
        return mnulMapper.getMnulListByEqpmntId(eqpmntId);
    }

    @Override
    public void deleteMnul(String eqpmntId) {
        mnulMapper.deleteMnul(eqpmntId);
    }

    @Override
    public void deleteMnulById(List<String> ids) {
        for (String id : ids) {
            mnulMapper.deleteMnulById(id);
        }
    }

    @Override
    public int upsertMnul(List<MnulVo> mnulVoList, String eqpmntId) {
        for (MnulVo vo : mnulVoList) {
            if (vo.getMnlId() == null || vo.getMnlId().isEmpty()) {
                vo.setMnlId(StringUtil.getMnlUUID());
            }
            vo.setEqpmntId(eqpmntId);
            vo.setMnlLng("KR"); //todo 수정
            mnulMapper.upsertMnul(vo);
        }
        return 1;
    }
}
