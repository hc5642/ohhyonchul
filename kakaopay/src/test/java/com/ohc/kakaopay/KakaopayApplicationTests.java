package com.ohc.kakaopay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ohc.kakaopay.dao.WorkNumber1Dao;
import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayApplicationTests {
	
	@Autowired private WorkNumber1Dao dao1;
	@Autowired private WorkNumber2Dao dao2;
	@Autowired private WorkNumber3Dao dao3;
	@Autowired private WorkNumber4Dao dao4;

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
		System.out.println("###1.결과" + dao1.doWork());
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
		System.out.println("###2.결과" + dao2.doWork());
		assertEquals(4, list.size());
		assertEquals("11111115", list.get(0).getAcctNo()); // 2018
		assertEquals("11111118", list.get(1).getAcctNo()); // 2018
		assertEquals("11111114", list.get(2).getAcctNo()); // 2019
		assertEquals("11111118", list.get(3).getAcctNo()); // 2019
	}
	
	/**
	 * 3.연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
	 * ( 취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@Test
	public void work3Test() {
		List<WorkNumber3Vo> list = dao3.doWork();
		System.out.println("###3.결과" + dao3.doWork());
		assertEquals(8, list.size());
	}
	
	/**
	 * 4.분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 
	 * 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
	 * ( 취소여부가 ‘Y’ 거래는 취소된 거래임,)
	 */
	@Test
	public void work4Test_1() {
		WorkNumber4Vo vo = dao4.doWork("강남점");
		System.out.println("###4.결과" + dao4.doWork("강남점"));
		assertEquals("강남점", vo.getBrName());
	}
	
	/**
	 * 분당점 조회 시 404 에러
	 */
	@Test
	public void work4Test_2() {
		WorkNumber4Vo vo = dao4.doWork("분당점");
		System.out.println("###4.결과" + dao4.doWork("분당점"));
		assertNull(vo);
	}

}
