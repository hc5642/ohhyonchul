package com.ohc.kakaopay;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.svc.WorkNumber1Svc;
import com.ohc.kakaopay.svc.WorkNumber2Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayApplicationTests {
	
	@Autowired private WorkNumber1Svc svc1;
	@Autowired private WorkNumber2Svc svc2;

	@Test
	public void contextLoads() {
	}
	
	/**
	 * 1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
	 * (단, 취소여부가 ‘Y’ 거래는 취소된 거래임, 합계 금액은 거래금액에서 수수료를 차감한 금액임)
	 */
	@Test
	public void work1Test() {
		List<WorkNumber1Vo> list = svc1.doWork();
		log.info("###1.결과" + list);
		assertEquals(2, list.size());
		assertEquals("11111119", list.get(0).getAcctNo()); // 2018 주디
		assertEquals("11111112", list.get(1).getAcctNo()); // 2019 에이스
	}
	
	/**
	 * 2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
	 * (취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@Test
	public void work2Test() {
		List<WorkNumber2Vo> list = svc2.doWork();
		log.info("###2.결과" + list);
		assertEquals(4, list.size());
		assertEquals("11111115", list.get(0).getAcctNo()); // 2018 사라
		assertEquals("11111118", list.get(1).getAcctNo()); // 2018 제임스
		assertEquals("11111114", list.get(2).getAcctNo()); // 2019 테드
		assertEquals("11111118", list.get(3).getAcctNo()); // 2019 제임스
	}
	

}
