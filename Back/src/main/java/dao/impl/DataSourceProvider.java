package dao.impl;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceProvider {

	private static MariaDbDataSource dataSource;
	private static Properties properties = new Properties();

	public static DataSource getDataSource() throws SQLException{
		try {
			properties.load(DataSourceProvider.class.getResourceAsStream("/application.properties"));

			if (dataSource == null) {
				dataSource = new MariaDbDataSource();
				dataSource.setServerName(properties.getProperty("servername"));
				dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
				dataSource.setDatabaseName(properties.getProperty("databasename"));
				dataSource.setUser(properties.getProperty("user"));
				if ( properties.getProperty("password").isEmpty()) {
					dataSource.setPassword("");
				} else {
				dataSource.setPassword(properties.getProperty("password"));}
			}
		}catch (IOException e) {
			System.err.println("Vous n\'êtes pas connecté à la base de données, vérifiez qu\'elle est bien allumée et rééssayez");
		}
		return dataSource;
	}
}
