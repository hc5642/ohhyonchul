package com.ohc.kakaopay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;
import com.ohc.kakaopay.svc.WorkNumber3Svc;
import com.ohc.kakaopay.svc.WorkNumber4Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayApplicationTests2 {
	
	@Autowired private WorkNumber3Svc svc3;
	@Autowired private WorkNumber4Svc svc4;

	/**
	 * 3.연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
	 * ( 취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@Test
	public void work3Test() {
		List<WorkNumber3Vo> list = svc3.doWork();
		for ( WorkNumber3Vo vo : list ) {
			log.info("###3.결과" + vo);
		}
		assertEquals("D", list.get(0).getBrCode());	// 2018 잠실점 D
		assertEquals("A", list.get(1).getBrCode());	// 2018 판교점 A
		assertEquals("C", list.get(2).getBrCode());	// 2018 강남점 C
		assertEquals("B", list.get(3).getBrCode());	// 2018 분당점 B
		assertEquals("B", list.get(4).getBrCode());	// 2019 분당점 B
		assertEquals("C", list.get(5).getBrCode());	// 2019 강남점 C
		assertEquals("D", list.get(6).getBrCode());	// 2019 잠실점 D
		assertEquals("A", list.get(7).getBrCode());	// 2019 판교점 A
		
	}
	
	/**
	 * 4-1.분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 
	 * 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
	 * ( 취소여부가 ‘Y’ 거래는 취소된 거래임,)
	 */
	@Test
	public void work4Test_1() {
		WorkNumber4Vo result = svc4.doWork("강남점");
		log.info("###4.결과 " + result);
		assertEquals("강남점", result.getBrName());
	}
	
	/**
	 * 4-2. 분당점 조회 시 404 에러
	 */
	@Test
	public void work4Test_2() {
		WorkNumber4Vo result = null;
		result = svc4.doWork("분당점");
		log.info("###4.결과 " + result);
		assertNull(result);
	}

}
