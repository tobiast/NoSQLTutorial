package no.knowit.couchdb.dao;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;

public class DatabaseFactory {

	private static String DATABASE_HOST = "localhost";

	private static String DATABASE_NAME = "employees";

	private static int DATABASE_PORT = Server.DEFAULT_PORT;

	// private static int DATABASE_PORT = 59840;

	private static Server server = new ServerImpl(DATABASE_HOST, DATABASE_PORT);

	private static EmployeeDao employeeDao = null;

	private DatabaseFactory() {
		// intentional
	}

	public static synchronized EmployeeDao getEmployeeDao() {
		if (employeeDao == null) {
			Database database = new Database(server, DATABASE_NAME);
			employeeDao = new EmployeeDao(database);
		}
		return employeeDao;
	}

}
