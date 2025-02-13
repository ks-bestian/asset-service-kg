package kr.co.bestiansoft.ebillservicekg;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.bestiansoft.ebillservicekg.login.repository.LoginMapper;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;
import kr.co.bestiansoft.ebillservicekg.test.repository2.TestMapper;
import kr.co.bestiansoft.ebillservicekg.test.vo.TestVo;

@SpringBootTest
class EbillServiceKgApplicationTests {

//	@Autowired
//	private TestMapper testMapper;
//	
//	@Autowired
//	private LoginMapper loginMapper;
	
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

}
