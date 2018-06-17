package it.fumetteria.model;

import java.sql.SQLException;

import it.fumetteria.beans.SerieBean;
import java.util.Collection;

public interface SerieModel {
	void doSave(SerieBean bean) throws SQLException;
	SerieBean doRetrieveByKey(String nome) throws SQLException;
	void doUpdate(SerieBean sbean) throws SQLException;
	Collection<SerieBean> doRetrieveByCond(String nome) throws SQLException;
}
