package kr.co.bestiansoft.ebillservicekg.common.file.service;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface ComFileService {

	String saveFile(MultipartFile[] files);
	List<ComFileVo> getFileList(String fileGroupId);
	ComFileVo getFile(String fileId);
	void batchFileDelete();

//	void saveFileEbs(MultipartFile[] files, String billId);
	void saveFileEbs(MultipartFile[] files, String[] fileKindCd, String billId);
	void saveFileEbs(String[] myFileIds, String[] fileKindCdList, String billId) throws Exception;
	void saveFileEbsMtng(MultipartFile[] files, String[] fileKindCdList, Long mtngId);
	void saveFileEbsMtng(String[] myFileIds, String[] fileKindCdList, Long mtngId) throws Exception;
	
	void saveFileBillMng(EbsFileVo ebsFileVo);

	void saveFileBillDetailMng(BillMngVo billMngVo) throws Exception;
	
//	void saveFileEbs(MultipartFile file, String fileKindCd, String billId, String clsCd, String opbYn, Long detailSeq);
//	void saveFileEbs(String myFileId, String fileKindCd, String billId, String clsCd, String opbYn, Long detailSeq) throws Exception;
}
