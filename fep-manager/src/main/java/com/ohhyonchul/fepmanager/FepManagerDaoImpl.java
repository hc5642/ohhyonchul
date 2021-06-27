package com.ohhyonchul.fepmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FepManagerDaoImpl implements FepManagerDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from books", Integer.class);
    }
	
	public int save(FepManagerVo vo) {
        return jdbcTemplate.update(
                "insert into books (name, price) values(?,?)",
                vo.getUserid(), vo.getUsername());
    }

    public int update(FepManagerVo vo) {
        return jdbcTemplate.update(
                "update books set price = ? where id = ?",
                vo.getUserid(), vo.getUsername());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete books where id = ?",
                id);
    }
    
    public List<FepManagerVo> findAll() {
        return jdbcTemplate.query(
                "select * from books",
                (rs, rowNum) ->
                        new FepManagerVo(
                                rs.getString("id"),
                                rs.getString("name")
                        )
        );
    }
    
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select name from books where id = ?",
                new Object[]{id},
                String.class
        );
    }
	
	
}
