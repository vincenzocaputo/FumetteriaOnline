package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.fumetteria.beans.FumettoBean;

public class FumettoModelDS implements FumettoModel{
	private static final String NOME_TAB = "Fumetto";
	
	private DataSource ds;
	
	public FumettoModelDS(DataSource ds) {
		this.ds = ds;
	}

	public synchronized FumettoBean doRetrieveByKey(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		FumettoBean bean = new FumettoBean();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" INNER JOIN Articolo ON Fumetto.Articolo=Articolo.Codice WHERE Articolo = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setInt(1, codice);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setCodice(rs.getInt("Articolo.Codice"));
				bean.setNome(rs.getString("Articolo.Nome"));
				bean.setIsFumetto(rs.getBoolean("isFumetto"));
				bean.setPrezzo(rs.getDouble("Prezzo"));
				bean.setSconto(rs.getInt("Sconto"));
				bean.setCategoria(rs.getString("Categoria"));
				bean.setGiacenza(rs.getInt("Giacenza"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setDataInserimento(rs.getDate("DataInserimento"));
				bean.setNumeroPagine(rs.getInt("NumPagine"));
				bean.setFormato(rs.getString("Formato"));
				bean.setInterni(rs.getString("Interni"));
				bean.setGenere(rs.getString("Genere"));
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
	
	public synchronized void doSave(FumettoBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (Articolo, Genere, Interni, NumPagine, Formato) VALUES((SELECT MAX(Codice) FROM Articolo),?,?,?,?)";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, bean.getGenere());
			preparedStatement.setString(2, bean.getInterni());
			preparedStatement.setInt(3, bean.getNumeroPagine());
			preparedStatement.setString(4, bean.getFormato());
			
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

	public synchronized void doUpdate(FumettoBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE "+NOME_TAB+" SET Genere=?, Interni=?, NumPagine=?, Formato=? WHERE Articolo=?";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, bean.getGenere());
			preparedStatement.setString(2, bean.getInterni());
			preparedStatement.setInt(3, bean.getNumeroPagine());
			preparedStatement.setString(4, bean.getFormato());
			preparedStatement.setInt(5, bean.getCodice());
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
