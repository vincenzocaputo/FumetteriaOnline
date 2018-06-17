package it.fumetteria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.fumetteria.beans.AppartieneBean;
import it.fumetteria.beans.ArticoloBean;
import it.fumetteria.search.ArticoloRicercaBean;
import it.fumetteria.search.RicercaBean;


public class ArticoloModelDS implements ArticoloModel{
	private static final String NOME_TAB = "Articolo";
		
	private DataSource ds;
	
	public ArticoloModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public synchronized Collection<RicercaBean> doSearch(ArticoloRicercaBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		Collection<RicercaBean> articoli = new LinkedList<RicercaBean>();
		RicercaBean result = null;
		ArticoloBean articolo = null;
		AppartieneBean appartiene = null;
		
		String testSQL = "SELECT *, (Prezzo*(100-Sconto)/100) AS PrezzoScontato "+
				"FROM (Articolo LEFT JOIN Fumetto ON Articolo.Codice = Fumetto.Articolo) LEFT JOIN Appartiene ON Articolo.Codice=Appartiene.Fumetto "+
				"WHERE ((Nome LIKE ? OR ? IS NULL) OR (Serie LIKE ? OR ? IS NULL)) AND " +
				"(Categoria LIKE ? OR ? = 'seleziona') AND " +
				"(Genere LIKE ? OR ? IS NULL) AND "+
				"(Interni LIKE ? OR ? = 'seleziona') AND "+
				"( (? = TRUE AND Sconto<>0) OR (? = FALSE)) AND "+
				"( (? = TRUE AND Giacenza>0) OR (? = FALSE)) AND "+
				"((Prezzo>=? AND Prezzo<=?) OR (? = -1 AND Prezzo<=?) OR (Prezzo>=? AND ? = -1) OR (? = -1 AND ? = -1)) AND "+
				"(? = TRUE AND Fumetto.Articolo IN (SELECT Fumetto FROM Appartiene) OR (? = FALSE)) AND "+
				"Giacenza>=0";
		
		if(bean.getOrdinamento().equals("codice")) {
			testSQL += " ORDER BY Codice DESC";
		} else if(bean.getOrdinamento().equals("prezzoC")) {
			testSQL += " ORDER BY PrezzoScontato ASC";
		} else if(bean.getOrdinamento().equals("prezzoD")) {
			testSQL += " ORDER BY PrezzoScontato DESC";
		}
		try {
			connection = ds.getConnection();
			
			preparedStatement = connection.prepareStatement(testSQL);
			preparedStatement.setString(1, "%"+bean.getNome()+"%");
			preparedStatement.setString(2, bean.getNome());
			
			preparedStatement.setString(3, "%"+bean.getNome()+"%");
			preparedStatement.setString(4, bean.getNome());
			
			preparedStatement.setString(5, bean.getCategoria());
			preparedStatement.setString(6, bean.getCategoria());
			
			preparedStatement.setString(7, "%"+bean.getGenere()+"%");
			preparedStatement.setString(8, bean.getGenere());
			
			preparedStatement.setString(9, bean.getInterni());
			preparedStatement.setString(10, bean.getInterni());
			
			preparedStatement.setBoolean(11, bean.isScontato());
			preparedStatement.setBoolean(12, bean.isScontato());
			
			preparedStatement.setBoolean(13, bean.isDisponibile());
			preparedStatement.setBoolean(14, bean.isDisponibile());
			
			preparedStatement.setDouble(15, bean.getPrezzoMin());
			preparedStatement.setDouble(16, bean.getPrezzoMax());
			preparedStatement.setDouble(17, bean.getPrezzoMin());
			preparedStatement.setDouble(18, bean.getPrezzoMax());
			preparedStatement.setDouble(19, bean.getPrezzoMin());
			preparedStatement.setDouble(20, bean.getPrezzoMax());
			preparedStatement.setDouble(21, bean.getPrezzoMin());
			preparedStatement.setDouble(22, bean.getPrezzoMax());
			
			preparedStatement.setBoolean(23, bean.isInSerie());
			preparedStatement.setBoolean(24, bean.isInSerie());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				articolo = new ArticoloBean();
				appartiene = new AppartieneBean();
				result = new RicercaBean();
				
				articolo.setCodice(rs.getInt("Codice"));
				articolo.setCategoria(rs.getString("Categoria"));
				articolo.setDataInserimento(rs.getDate("DataInserimento"));
				articolo.setDescrizione(rs.getString("Descrizione"));
				articolo.setGiacenza(rs.getInt("Giacenza"));
				articolo.setIsFumetto(rs.getBoolean("isFumetto"));
				articolo.setNome(rs.getString("Articolo.Nome"));
				articolo.setPrezzo(rs.getDouble("Articolo.Prezzo"));
				articolo.setSconto(rs.getInt("Sconto"));
				
				appartiene.setSerie(rs.getString("Appartiene.Serie"));
				appartiene.setNumero(rs.getInt("Appartiene.Numero"));
				
				result.setArticolo(articolo);
				result.setSerie(appartiene);
				articoli.add(result);
				
			}
			rs.close();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return articoli;
	}
	
	public synchronized int doSave(ArticoloBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO "+NOME_TAB+
				" (Nome, isFumetto, Prezzo, Sconto, Categoria, Giacenza, Descrizione, DataInserimento) VALUES(?,?,?,?,?,?,?,CURDATE())";
		int codice=0;
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, bean.getNome());
			preparedStatement.setBoolean(2, bean.isFumetto());
			preparedStatement.setDouble(3, bean.getPrezzo());
			preparedStatement.setInt(4, bean.getSconto());
			preparedStatement.setString(5, bean.getCategoria());
			preparedStatement.setInt(6, bean.getGiacenza());
			preparedStatement.setString(7, bean.getDescrizione());
			
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			
			if(rs.next()) {
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

	public synchronized void doUpdate(ArticoloBean bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE "+NOME_TAB+" SET Nome=?, Prezzo=?, Sconto=?, Giacenza=?, Descrizione=? WHERE Codice=?";
		try {			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, bean.getNome());
			preparedStatement.setDouble(2, bean.getPrezzo());
			preparedStatement.setInt(3, bean.getSconto());
			preparedStatement.setInt(4, bean.getGiacenza());
			preparedStatement.setString(5, bean.getDescrizione());
			preparedStatement.setInt(6, bean.getCodice());
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

	public synchronized boolean doDelete(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "UPDATE "+NOME_TAB+" SET Giacenza=-1 WHERE Codice=?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, codice);

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

	public synchronized ArticoloBean doRetrieveByKey(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ArticoloBean bean = new ArticoloBean();
		
		String selectSQL = "SELECT * FROM "+NOME_TAB+" WHERE Codice = ?";
		
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
	
	public synchronized Collection<RicercaBean> doRetrieveByCond(String categoria) throws SQLException  {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<RicercaBean> articoli = new LinkedList<RicercaBean>();

		String selectSQL;
		boolean isCategoria = false;
		if(categoria.equals("novita")) {
			selectSQL = "SELECT * FROM "+NOME_TAB+" "
					+ 	"LEFT JOIN Appartiene ON Codice=Fumetto "
					+ 	"WHERE Giacenza>=0 "
					+ 	"ORDER BY DataInserimento DESC LIMIT 10";
		} else if(categoria.equals("promozioni")) {
			selectSQL = "SELECT * FROM "+NOME_TAB+" "
					+ 	"LEFT JOIN Appartiene ON Codice=Fumetto "
					+ 	"WHERE Sconto>0 AND Giacenza>=0 "
					+ 	"ORDER BY Sconto DESC LIMIT 10";
		} else if(categoria.equals("venduti")) {
			selectSQL = "SELECT *, count(ordine) as num "
					+ "FROM ("+NOME_TAB+" LEFT JOIN Appartiene ON Codice=Fumetto) "
					+ "INNER JOIN SiRiferisce ON Articolo.Codice=SiRiferisce.Articolo "
					+ "WHERE Giacenza>=0 "
					+ "GROUP BY Articolo.Codice "
					+ "ORDER BY num DESC LIMIT 10";
		} else {
			selectSQL = "SELECT * FROM "+NOME_TAB+" "
					+ 	"LEFT JOIN Appartiene ON Codice=Fumetto "
					+ 	"WHERE Categoria=? AND Giacenza>=0 "
					+ 	"ORDER BY Articolo.Codice DESC";
			isCategoria=true;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			
			if(isCategoria)
				preparedStatement.setString(1, categoria);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				RicercaBean bean = new RicercaBean();
				ArticoloBean articolo = new ArticoloBean();
				AppartieneBean appartiene = new AppartieneBean();
				
				articolo.setCodice(rs.getInt("Codice"));
				articolo.setNome(rs.getString("Articolo.Nome"));
				articolo.setIsFumetto(rs.getBoolean("isFumetto"));
				articolo.setPrezzo(rs.getDouble("Prezzo"));
				articolo.setSconto(rs.getInt("Sconto"));
				articolo.setCategoria(rs.getString("Categoria"));
				articolo.setGiacenza(rs.getInt("Giacenza"));
				articolo.setDescrizione(rs.getString("Descrizione"));
				articolo.setDataInserimento(rs.getDate("DataInserimento"));
				appartiene.setFumetto(rs.getInt("Codice"));
				appartiene.setSerie(rs.getString("Appartiene.Serie"));
				appartiene.setNumero(rs.getInt("Appartiene.Numero"));
				
				bean.setArticolo(articolo);
				bean.setSerie(appartiene);
				articoli.add(bean);
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
		return articoli;
	}


	@Override
	public Collection<RicercaBean> doRetrieveBySerie(String serie) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<RicercaBean> articoli = new LinkedList<RicercaBean>();

		String selectSQL = "SELECT * FROM "+NOME_TAB+" "
						+ "LEFT JOIN Appartiene ON Codice=Fumetto "
						+ "WHERE Serie=? AND Giacenza>=0 "
						+ "ORDER BY Articolo.Codice DESC";


		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setString(1, serie);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				RicercaBean bean = new RicercaBean();
				ArticoloBean articolo = new ArticoloBean();
				AppartieneBean appartiene = new AppartieneBean();
				
				articolo.setCodice(rs.getInt("Codice"));
				articolo.setNome(rs.getString("Articolo.Nome"));
				articolo.setIsFumetto(rs.getBoolean("isFumetto"));
				articolo.setPrezzo(rs.getDouble("Prezzo"));
				articolo.setSconto(rs.getInt("Sconto"));
				articolo.setCategoria(rs.getString("Categoria"));
				articolo.setGiacenza(rs.getInt("Giacenza"));
				articolo.setDescrizione(rs.getString("Descrizione"));
				articolo.setDataInserimento(rs.getDate("DataInserimento"));
				appartiene.setFumetto(rs.getInt("Codice"));
				appartiene.setSerie(rs.getString("Appartiene.Serie"));
				appartiene.setNumero(rs.getInt("Appartiene.Numero"));
				
				bean.setArticolo(articolo);
				bean.setSerie(appartiene);
				articoli.add(bean);
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
		return articoli;
	}


}
