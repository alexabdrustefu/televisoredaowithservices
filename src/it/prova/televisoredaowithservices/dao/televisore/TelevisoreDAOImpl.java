package it.prova.televisoredaowithservices.dao.televisore;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.televisoredaowithservices.dao.AbstractMySQLDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public class TelevisoreDAOImpl extends AbstractMySQLDAO implements TelevisoreDAO {

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public List<Televisore> list() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from televisore")) {

			while (rs.next()) {
				Televisore teleTemp = new Televisore();
				teleTemp.setMarca(rs.getString("MARCA"));
				teleTemp.setModello(rs.getString("MODELLO"));
				teleTemp.setPollici(rs.getInt("POLLICI"));
				teleTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				teleTemp.setId(rs.getLong("ID"));
				result.add(teleTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public Televisore get(Long idInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Televisore result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from televisore where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Televisore();
					result.setMarca(rs.getString("MARCA"));
					result.setModello(rs.getString("MODELLO"));
					result.setPollici(rs.getInt("POLLICI"));
					result.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public int update(Televisore input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE televisore SET marca=?, modello=?, pollici=?, dataproduzione=? where id=?;")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			ps.setInt(3, input.getPollici());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(4, new java.sql.Date(input.getDataProduzione().getTime()));
			ps.setLong(5, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public int insert(Televisore input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO televisore(marca,modello,pollici,dataproduzione) VALUES (?, ?, ?, ?);")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			ps.setInt(3, input.getPollici());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(4, new java.sql.Date(input.getDataProduzione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public int delete(Televisore input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM televisore WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public List<Televisore> findByExample(Televisore input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		String example = "select * from televisore where 1=1 ";
		if (input.getMarca() != null && !input.getMarca().isEmpty()) {
			example += " and marca like '" + input.getMarca() + "%' ";
		}

		if (input.getModello() != null && !input.getModello().isEmpty()) {
			example += " and modello like '" + input.getModello() + "%' ";
		}

		if (input.getPollici() != 0) {
			example += " and pollici = '" + input.getPollici() + "%' ";
		}

		if (input.getDataProduzione() != null) {
			example += " and dataproduzione='" + new java.sql.Date(input.getDataProduzione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(example);

			while (rs.next()) {
				Televisore teleTemp = new Televisore();
				teleTemp.setMarca(rs.getString("MARCA"));
				teleTemp.setModello(rs.getString("MODELLO"));
				teleTemp.setPollici(rs.getInt("POLLICI"));
				teleTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				teleTemp.setId(rs.getLong("ID"));
				result.add(teleTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public List<Televisore> findAllByProdottiTra(Date data1, Date data2) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		try (PreparedStatement ps = connection
				// la query mi include gli elementi compresi tra un range stabilito
				.prepareStatement("select * from televisore where dataproduzione > ? and dataproduzione < ?;")) {
			ps.setDate(1, new java.sql.Date(data1.getTime()));
			ps.setDate(2, new java.sql.Date(data2.getTime()));

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					Televisore teleTemp = new Televisore();
					teleTemp.setMarca(rs.getString("MARCA"));
					teleTemp.setModello(rs.getString("MODELLO"));
					teleTemp.setPollici(rs.getInt("POLLICI"));
					teleTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
					teleTemp.setId(rs.getLong("ID"));
					result.add(teleTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public Televisore findTelevisorePiuGrande() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		Televisore result = new Televisore();

		try (Statement ps = connection.createStatement();
				// query che mi ordina i televisori in base ai pollici in modo decrescente con
				// il limite di uno
				// quindi stamperà l'elemento con i pollici piu grandi
				ResultSet rs = ps.executeQuery("select * from televisore order by pollici desc limit 1;")) {

			if (rs.next()) {
				result.setMarca(rs.getString("MARCA"));
				result.setModello(rs.getString("MODELLO"));
				result.setPollici(rs.getInt("POLLICI"));
				result.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				result.setId(rs.getLong("ID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// ========================================================================overrideDellInterfaccia

	@Override
	public List<Televisore> findAllMarcheProdottiUltimiSeiMesi() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		try (Statement ps = connection.createStatement();
				//questa quey mi stampa gli elementi uguali o maggiori della rispettiva data stampando gli elementi
				//compresa tra i sei mesi stabiliti
				ResultSet rs = ps.executeQuery("select * from televisore where dataproduzione >= '2022-04-22';")) {

			while (rs.next()) {
				Televisore teleTemp = new Televisore();
				teleTemp.setMarca(rs.getString("MARCA"));
				teleTemp.setModello(rs.getString("MODELLO"));
				teleTemp.setPollici(rs.getInt("POLLICI"));
				teleTemp.setDataProduzione(rs.getDate("DATAPRODUZIONE"));
				teleTemp.setId(rs.getLong("ID"));
				result.add(teleTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}
