package no.knowit.couchdb.dao;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.knowit.couchdb.model.Employee;

import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

public class EmployeeDao {

	private final String DESIGN_DOC_ID = "employees";

	private final Database database;

	public EmployeeDao(Database database) {
		this.database = database;
	}

	public List<Employee> queryAll() {
		String viewName = DESIGN_DOC_ID + "/all";
		ViewResult<Employee> result = database.queryView(
				viewName, 
				Employee.class, 
				null, 
				null);
		return fromViewResult(result);
	}
	
	public List<Employee> queryByFirstName(String firstName) {
		String viewName = DESIGN_DOC_ID + "/getByFirstName";
		ViewResult<Employee> result = database.queryViewByKeys(
				viewName, 
				Employee.class, 
				Arrays.<String> asList(firstName), 
				null, 
				null);
		return fromViewResult(result);
	}

	private List<Employee> fromViewResult(ViewResult<Employee> viewResult) {
		if (viewResult == null || viewResult.getTotalRows() < 1) {
			return emptyList();
		}

		List<Employee> result = new ArrayList<Employee>();

		for (ValueRow<Employee> row : viewResult.getRows()) {
			result.add(row.getValue());
		}

		return result;
	}

}
