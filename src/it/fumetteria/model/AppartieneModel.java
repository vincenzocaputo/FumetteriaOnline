package it.fumetteria.model;

import java.sql.SQLException;

import it.fumetteria.beans.AppartieneBean;

public interface AppartieneModel {
	void doSave(AppartieneBean bean) throws SQLException;
	
	void doUpdate(AppartieneBean bean) throws SQLException;
	
	AppartieneBean doRetrieveByKey(int articolo) throws SQLException;

}
