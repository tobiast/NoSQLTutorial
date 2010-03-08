package no.knowit.couchdb;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import no.knowit.couchdb.dao.DatabaseFactory;
import no.knowit.couchdb.dao.EmployeeDao;
import no.knowit.couchdb.model.Employee;

public class CouchDB {
	
	private static final Log LOG = LogFactory.getLog(CouchDB.class);
	
	public static void main(String[] args) {
		EmployeeDao dao = DatabaseFactory.getEmployeeDao();
		List<Employee> employees = dao.queryAll();
		for(Employee e : employees) {
			LOG.info(e.getFirstName() + " " + (e.getMiddleName() == null ? "" :  e.getMiddleName() + " ") + e.getLastName());
		}
	}

}
