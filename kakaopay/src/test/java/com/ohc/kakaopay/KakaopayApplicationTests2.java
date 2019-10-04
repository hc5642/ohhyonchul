package com.ohc.kakaopay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayApplicationTests2 {
	
	@Autowired private WorkNumber3Dao dao3;
	@Autowired private WorkNumber4Dao dao4;

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
