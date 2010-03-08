package no.knowit.trafikanten.domain;

import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class TransactionManager {

	private final EmbeddedGraphDatabase database;

	public TransactionManager(EmbeddedGraphDatabase database) {
		this.database = database;
	}

	public Transaction beginTransaction() {
		return database.beginTx();
	}

}
