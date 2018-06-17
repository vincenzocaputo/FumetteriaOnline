package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.fumetteria.beans.SerieBean;

public class SerieModelDS implements SerieModel {
	private static final String NOME_TAB = "Serie";
	private DataSource ds;
	
	public SerieModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public synchronized void doSave(SerieBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement=null;
		
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (Nome, Periodicita) VALUES(?,?)";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, bean.getNome());
			preparedStatement.setString(2, bean.getPeriodicita());
			
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
	
	public synchronized SerieBean doRetrieveByKey(String nome) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement=null;
		
		SerieBean bean = new SerieBean();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE nome=?";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome);
			
			ResultSet rs = preparedStatement.executeQuery();
				
			if(rs.next()) {
				
				bean.setNome(rs.getString("Nome"));
				bean.setPeriodicita(rs.getString("Periodicita"));
			} else {
				bean = null;
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
		return bean;
	}
	public synchronized Collection<SerieBean> doRetrieveByCond(String nome) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<SerieBean> serie = new LinkedList<SerieBean>();

		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE nome LIKE ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome+"%");
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				SerieBean bean = new SerieBean();
				bean.setNome(rs.getString("Nome"));
				bean.setPeriodicita(rs.getString("Periodicita"));
				serie.add(bean);
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
		return serie;
	}

	@Override
	public void doUpdate(SerieBean sbean) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String selectSQL = "UPDATE "+NOME_TAB+" SET Periodicita=? WHERE Nome=?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, sbean.getPeriodicita());
			preparedStatement.setString(2, sbean.getNome());
			
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
}
