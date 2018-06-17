package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.fumetteria.beans.ArticoloBean;
import it.fumetteria.beans.SiRiferisceBean;

public class SiRiferisceModelDS implements SiRiferisceModel {
	private static final String NOME_TAB = "SiRiferisce";
	private DataSource ds;
	
	public SiRiferisceModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	/* Restituisce tutti i codici degli articoli, dato un ordine (Nome articoli)*/
	public synchronized Collection<SiRiferisceBean> doRetrieveByCond(int ordine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<SiRiferisceBean> ordini = new LinkedList<SiRiferisceBean>();
		String selectSQL = "SELECT Ordine, Quantita, Costo, Articolo.Codice, Articolo.Nome, Articolo.Prezzo, Articolo.Sconto, Articolo.IsFumetto "
				+ "FROM "+NOME_TAB+" INNER JOIN Articolo ON articolo=codice WHERE Ordine=? ";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setInt(1, ordine);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {				
				SiRiferisceBean siriferisceBean = new SiRiferisceBean();
				ArticoloBean articoloBean = new ArticoloBean();
				articoloBean.setCodice(rs.getInt("Articolo.Codice"));
				articoloBean.setNome(rs.getString("Articolo.Nome"));
				articoloBean.setPrezzo(rs.getDouble("Articolo.Prezzo"));
				articoloBean.setSconto(rs.getInt("Articolo.Sconto"));
				articoloBean.setIsFumetto(rs.getBoolean("Articolo.IsFumetto"));
				siriferisceBean.setArticolo(articoloBean);
				siriferisceBean.setOrdine(rs.getInt("Ordine"));
				siriferisceBean.setCosto(rs.getDouble("Costo"));
				siriferisceBean.setQuantita(rs.getInt("Quantita"));
				
				ordini.add(siriferisceBean);
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
	
	public synchronized void doSave(SiRiferisceBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO "+NOME_TAB+" (Ordine, Articolo, Costo, Quantita) VALUES ((SELECT MAX(Numero) FROM Ordine),?,?,?)";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setInt(1, bean.getArticolo().getCodice());
			preparedStatement.setDouble(2, bean.getCosto());
			preparedStatement.setInt(3, bean.getQuantita());
			
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
