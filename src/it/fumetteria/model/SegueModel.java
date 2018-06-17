package it.fumetteria.model;

import java.sql.SQLException;
import java.util.Collection;

import it.fumetteria.beans.SegueBean;

public interface SegueModel {
	void doSave(SegueBean bean) throws SQLException;
	boolean doDelete(String utente, String serie) throws SQLException;
	Collection<SegueBean> doRetrieveByCond(String utente) throws SQLException;
	boolean doRetrieveByKey(String serie, String utente) throws SQLException;
}
