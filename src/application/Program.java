package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection connection = null;
		Statement st = null;
		
		try {
			connection = DB.getConnection();
			
			connection.setAutoCommit(false);
			
			st = connection.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
//			int x = 1;

//			if (x < 2) {
//				throw new SQLException("Fake error");
//			}
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			connection.commit();
			
			System.out.println("rows1 " + rows1);
			System.out.println("rows2 " + rows2);
			
		}
		catch (SQLException e) {
			try {
				connection.rollback();
				throw new DbException("Transaction rolled back! Caused by" + e.getMessage());
			} catch (SQLException e1) { // Esta excessão é para caso dê erro no rollback
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}
}