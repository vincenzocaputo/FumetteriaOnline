package it.fumetteria.model;

import java.sql.SQLException;
import java.util.Collection;

import it.fumetteria.beans.ArticoloBean;
import it.fumetteria.search.ArticoloRicercaBean;
import it.fumetteria.search.RicercaBean;

public interface ArticoloModel {
	Collection<RicercaBean> doSearch(ArticoloRicercaBean bean) throws SQLException;
	int doSave(ArticoloBean bean) throws SQLException;
	void doUpdate(ArticoloBean bean) throws SQLException;
	boolean doDelete(int codice) throws SQLException;
	ArticoloBean doRetrieveByKey(int codice) throws SQLException;
	Collection<RicercaBean> doRetrieveByCond(String categoria) throws SQLException;
	Collection<RicercaBean> doRetrieveBySerie(String serie) throws SQLException;
}
