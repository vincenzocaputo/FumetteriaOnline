package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.fumetteria.beans.OrdineBean;

public class OrdineModelDS implements OrdineModel{
	private static final String NOME_TAB = "Ordine";
	private DataSource ds;
	
	public OrdineModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public synchronized Collection<OrdineBean> doRetrieveAll() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" ORDER BY Numero DESC";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				OrdineBean ordine = new OrdineBean();
				
				ordine.setNumero(rs.getInt("Numero"));
				ordine.setDataEmissione(rs.getDate("DataEmissione"));
				ordine.setDataConsegna(rs.getDate("DataConsegna"));
				ordine.setTotale(rs.getDouble("Totale"));
				
				
				ordine.setUtente(rs.getString("utente"));
				ordini.add(ordine);
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
		
		return ordini;
	}
	
	public synchronized int doSave(OrdineBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (DataEmissione, Totale, Utente) VALUES (CURDATE(), ? , ?)";
		int codice = 0;
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setDouble(1, bean.getTotale());
			preparedStatement.setString(2, bean.getUtente());
			
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			while(rs.next()) {
				codice = rs.getInt(1);
			}
			

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
		return codice;
	}

	public synchronized Collection<OrdineBean> doRetrieveByCond(String utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<OrdineBean> ordini = new LinkedList<OrdineBean>();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE Utente=? ORDER BY Numero DESC";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, utente);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				OrdineBean ordine = new OrdineBean();
				
				ordine.setNumero(rs.getInt("Numero"));
				ordine.setDataEmissione(rs.getDate("DataEmissione"));
				ordine.setDataConsegna(rs.getDate("DataConsegna"));
				ordine.setTotale(rs.getDouble("Totale"));
				
				ordine.setUtente(rs.getString("utente"));
				
				ordini.add(ordine);
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
		
		return ordini;
	}

	@Override
	public OrdineBean doRetrieveByKey(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		OrdineBean bean = null;
		String selectSQL = "SELECT * FROM "+NOME_TAB+ " WHERE numero=?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, codice);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				bean = new OrdineBean();
				bean.setNumero(rs.getInt("numero"));
				bean.setDataEmissione(rs.getDate("dataEmissione"));
				bean.setDataConsegna(rs.getDate("dataConsegna"));
				bean.setTotale(rs.getDouble("Totale"));
				bean.setUtente(rs.getString("utente"));
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

	@Override
	public int doUpdate(int codice, String dataConsegna) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "UPDATE Ordine SET DataConsegna=? WHERE Numero=?";
		int row=0;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, dataConsegna);
			preparedStatement.setInt(2, codice);
			row=preparedStatement.executeUpdate();
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
		return row;
	}
}
