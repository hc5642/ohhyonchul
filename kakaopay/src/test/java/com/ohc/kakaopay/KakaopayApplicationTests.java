package com.ohc.kakaopay;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ohc.kakaopay.dao.WorkNumber1Dao;
import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayApplicationTests {
	
	@Autowired private WorkNumber1Dao dao1;
	@Autowired private WorkNumber2Dao dao2;

	@Test
	public void contextLoads() {
	}
	
	/**
	 * 1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
	 * (단, 취소여부가 ‘Y’ 거래는 취소된 거래임, 합계 금액은 거래금액에서 수수료를 차감한 금액임)
	 */
	@Test
	public void work1Test() {
		// 결과 2018년은 주디, 2019년은 에이스
		List<WorkNumber1Vo> list = dao1.doWork();
		log.info("###1.결과" + dao1.doWork());
		assertEquals(2, list.size());
		assertEquals("11111119", list.get(0).getAcctNo()); // 2018
		assertEquals("11111112", list.get(1).getAcctNo()); // 2019
	}
	
	/**
	 * 2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
	 * (취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@Test
	public void work2Test() {
		List<WorkNumber2Vo> list = dao2.doWork();
		log.info("###2.결과" + dao2.doWork());
		assertEquals(4, list.size());
		assertEquals("11111115", list.get(0).getAcctNo()); // 2018
		assertEquals("11111118", list.get(1).getAcctNo()); // 2018
		assertEquals("11111114", list.get(2).getAcctNo()); // 2019
		assertEquals("11111118", list.get(3).getAcctNo()); // 2019
	}
	

}
