package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.fumetteria.beans.SegueBean;
import it.fumetteria.beans.SerieBean;

public class SegueModelDS implements SegueModel{
	private static final String NOME_TAB = "Segue";
	private DataSource ds;
	
	public SegueModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public synchronized void doSave(SegueBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (Utente, Serie) VALUES(?,?)";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, bean.getUtente());
			preparedStatement.setString(2, bean.getSerie().getNome());
			
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
	
	public synchronized boolean doDelete(String utente, String serie) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + NOME_TAB + " WHERE Utente=? AND Serie=?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, utente);
			preparedStatement.setString(2, serie);
			
			result = preparedStatement.executeUpdate();
			
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
		return (result != 0);
	}
	
	public synchronized Collection<SegueBean> doRetrieveByCond(String utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<SegueBean> seriePreferite = new LinkedList<SegueBean>();
		
		String selectSQL = "SELECT Utente, Serie, Periodicita FROM "+NOME_TAB+" INNER JOIN Serie ON Serie=Nome WHERE Utente=?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, utente);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				SegueBean bean = new SegueBean();
				SerieBean serie = new SerieBean();
				serie.setNome(rs.getString("Serie"));
				serie.setPeriodicita(rs.getString("Periodicita"));
				bean.setUtente(rs.getString("utente"));
				bean.setSerie(serie);
				
				seriePreferite.add(bean);
				
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
		
		return seriePreferite;
	}

	public synchronized boolean doRetrieveByKey(String serie, String utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE Utente=? AND Serie=?";
		boolean found = false;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, utente);
			preparedStatement.setString(2, serie);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				found=true;
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
		
		return found;
	}
}
