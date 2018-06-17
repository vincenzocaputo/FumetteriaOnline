package it.fumetteria.model;

import java.sql.SQLException;
import java.util.Collection;

import it.fumetteria.beans.OrdineBean;

public interface OrdineModel {
	int doSave(OrdineBean bean) throws SQLException;
	Collection<OrdineBean> doRetrieveAll() throws SQLException;
	Collection<OrdineBean> doRetrieveByCond(String utente) throws SQLException;
	OrdineBean doRetrieveByKey(int codice) throws SQLException;
	int doUpdate(int codice, String dataConsegna) throws SQLException;
}
