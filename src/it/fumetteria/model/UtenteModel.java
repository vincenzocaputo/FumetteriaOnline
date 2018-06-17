package it.fumetteria.model;

import java.sql.SQLException;

import it.fumetteria.beans.UtenteBean;

public interface UtenteModel {
	UtenteBean doRetrieveByKey(String email) throws SQLException;
	void doSave(UtenteBean bean) throws SQLException;
	UtenteBean doRetrieveByCond(String email, String password) throws SQLException;
}
