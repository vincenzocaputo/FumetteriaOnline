package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.fumetteria.beans.AppartieneBean;

public class AppartieneModelDS implements AppartieneModel{
	private static final String NOME_TAB = "Appartiene";
	private DataSource ds;
	
	public AppartieneModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public synchronized void doSave(AppartieneBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (Fumetto, Serie, Numero) VALUES(?,?,?)";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, bean.getFumetto());
			preparedStatement.setString(2, bean.getSerie());
			preparedStatement.setInt(3, bean.getNumero());
			
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
	
	public synchronized void doUpdate(AppartieneBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE "+NOME_TAB+" SET Numero=? WHERE Fumetto=? AND Serie=?";
		try {
			connection = ds.getConnection();
			
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, bean.getNumero());
			preparedStatement.setInt(2, bean.getFumetto());
			preparedStatement.setString(3, bean.getSerie());
			
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
	
	public synchronized AppartieneBean doRetrieveByKey(int articolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		AppartieneBean bean = new AppartieneBean();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE Fumetto=?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setInt(1, articolo);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setFumetto(rs.getInt("fumetto"));
				bean.setNumero(rs.getInt("numero"));
				bean.setSerie(rs.getString("serie"));
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
