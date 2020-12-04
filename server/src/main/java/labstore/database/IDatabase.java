package labstore.database;

import java.sql.Connection;

public interface IDatabase {
  
  Connection getConnection();

}
