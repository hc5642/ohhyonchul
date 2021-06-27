package com.ohhyonchul.fepmanager.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohhyonchul.fepmanager.dao.FepLineDao;
import com.ohhyonchul.fepmanager.dto.FepLineDto;
@Service
public class FepLineSvcImpl implements FepLineSvc {
	
	@Autowired
	private FepLineDao dao;

	@Override
	public List<FepLineDto> selectList(String searchKey) {
		return dao.selectList(searchKey);
	}

	@Override
	public int updateItem(FepLineDto dto) {
		return dao.updateItem(dto);
	}

	@Override
	public int insertItem(FepLineDto dto) {
		return dao.insertItem(dto);
	}

	@Override
	public int deleteItem(int seqNum) {
		return dao.deleteItem(seqNum);
	}

}
