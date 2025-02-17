package kr.co.bestiansoft.ebillservicekg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.login.repository.LoginMapper;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;
import kr.co.bestiansoft.ebillservicekg.test.repository2.TestMapper;
import kr.co.bestiansoft.ebillservicekg.test.vo.TestVo;
import reactor.core.publisher.Mono;

@SpringBootTest
class EbillServiceKgApplicationTests {

//	@Autowired
//	private TestMapper testMapper;
//	
//	@Autowired
//	private LoginMapper loginMapper;
	
//	@Autowired
//	private DocumentService documentService;
	
	@Autowired
	private ComFileService comFileService;
	
	@Autowired
	private EDVHelper edv;
	
	@Autowired
	private DocumentMapper documentMapper;
	
	
    @Test
    void contextLoads() {
    }
    
//    @Test
//    void insertTest() {
//    	TestVo testVo = new TestVo();
//    	testVo.setTitle("title1");
//    	testVo.setContents("contents1");
//    	testMapper.insertTest(testVo);
//    }
//    
//    @Test
//    void selectTest() {
//    	
//    	LoginUserVo user = loginMapper.selectUser("XK4544");
//    	System.out.println("user======================");
//		System.out.println(user);
//    	
//    	
//    	List<TestVo> testList = testMapper.selectTest(null);
//    	System.out.println("testList===================");
//    	System.out.println(testList);
//    }

    
    @Test
    void convertTest() throws Exception {
    	
//    	Mono<String> mono = comFileService.convertDocToPdf("qqq");
//    	String pdfFileId = mono.block();
//    	System.out.println(pdfFileId);

//    	Mono<String> mono = webClient
//			.post()
//			.uri("http://localhost:8082/converttopdf/test")
//			.retrieve()
//			.bodyToMono(String.class);
//    	String str = mono.block();
//    	System.out.println(str);
    	
    	
    	
//    	String docPath = "C:\\Users\\chojh\\Downloads\\aspose-test\\word\\file-sample_1MB.docx";
//    	String fileId = StringUtil.getUUUID();
//    	edv.save(fileId, new FileInputStream(docPath));
//    	Mono<String> mono = comFileService.convertDocToPdf(fileId);
//
//    	
//    	mono.subscribe(
//    		pdfFileId -> {
//    			
//    			try {
//    				System.out.println("pdfFileId============");
//        			System.out.println(pdfFileId);
//    			}
//    			catch(Exception e) {
//    				e.printStackTrace();
//    			}
//    			
//    		},
//    		error -> {
//    			error.printStackTrace();
//    		}
//    	);
//    	Thread.sleep(60000);
//    	System.out.println("bye");
    }
}
