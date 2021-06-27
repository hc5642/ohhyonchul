package com.ohhyonchul.fepmanager.dao;

import java.util.List;
import com.ohhyonchul.fepmanager.dto.FepLineDto;

public interface FepLineDao {
	
	public List<FepLineDto> selectList(String searchKey);
	
	public int updateItem(FepLineDto dto);
	public int insertItem(FepLineDto dto);
	public int deleteItem(int seqNum);
	

}
