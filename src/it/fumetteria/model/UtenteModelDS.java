package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.fumetteria.beans.UtenteBean;

public class UtenteModelDS implements UtenteModel {
	private static final String NOME_TAB = "Utente";
	
	private DataSource ds;
	
	public UtenteModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public synchronized UtenteBean doRetrieveByKey(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = null;
		String selectSQL = "SELECT * FROM "+NOME_TAB+ " WHERE Email=?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				bean = new UtenteBean();
				bean.setEmail(rs.getString("Email"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setNome(rs.getString("Nome"));
				bean.setVia(rs.getString("Via"));
				bean.setCivico(rs.getString("Civico"));
				bean.setCap(rs.getString("Cap"));
				bean.setCitta(rs.getString("Citta"));
				bean.setProvincia(rs.getString("Provincia"));
				bean.setRuolo(rs.getString("Ruolo"));
			} 
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}

	public synchronized void doSave(UtenteBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO "+NOME_TAB+
						" (Email, Cognome, Nome, Via, Civico, CAP, Citta, Provincia, Password, Ruolo) VALUES(?,?,?,?,?,?,?,?,SHA(?),?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, bean.getEmail());
			preparedStatement.setString(2, bean.getCognome());
			preparedStatement.setString(3, bean.getNome());
			preparedStatement.setString(4, bean.getVia());
			preparedStatement.setString(5, bean.getCivico());
			preparedStatement.setString(6, bean.getCap());
			preparedStatement.setString(7, bean.getCitta());
			preparedStatement.setString(8, bean.getProvincia());
			preparedStatement.setString(9, bean.getPassword());
			preparedStatement.setString(10, bean.getRuolo());
			
			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
	}

	public synchronized UtenteBean doRetrieveByCond(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UtenteBean bean = new UtenteBean();

		String selectSQL = "SELECT * FROM " + UtenteModelDS.NOME_TAB + " WHERE Email = ? AND Password = SHA(?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setEmail(rs.getString("Email"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setNome(rs.getString("Nome"));
				bean.setVia(rs.getString("Via"));
				bean.setCivico(rs.getString("Civico"));
				bean.setCap(rs.getString("Cap"));
				bean.setCitta(rs.getString("Citta"));
				bean.setProvincia(rs.getString("Provincia"));
				bean.setPassword(rs.getString("password"));
				bean.setRuolo(rs.getString("Ruolo"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}
}
