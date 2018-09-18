package coneccion;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DB {
	
	protected JdbcTemplate db;
	
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.db = new JdbcTemplate(dataSource);
	}
}