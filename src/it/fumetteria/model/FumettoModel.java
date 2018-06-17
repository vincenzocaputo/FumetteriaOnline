package it.fumetteria.model;

import java.sql.SQLException;

import it.fumetteria.beans.FumettoBean;

public interface FumettoModel {
	FumettoBean doRetrieveByKey(int codice) throws SQLException;
	void doSave(FumettoBean bean) throws SQLException;
	void doUpdate(FumettoBean bean) throws SQLException;
}
