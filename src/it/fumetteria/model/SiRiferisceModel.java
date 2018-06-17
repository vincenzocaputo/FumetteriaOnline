package it.fumetteria.model;

import java.sql.SQLException;
import java.util.Collection;

import it.fumetteria.beans.SiRiferisceBean;

public interface SiRiferisceModel {
	Collection<SiRiferisceBean> doRetrieveByCond(int ordine) throws SQLException;
	void doSave(SiRiferisceBean bean) throws SQLException;
}
