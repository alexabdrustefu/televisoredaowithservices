package it.prova.televisoredaowithservices.dao.televisore;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import it.prova.televisoredaowithservices.dao.IBaseDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public interface TelevisoreDAO extends IBaseDAO<Televisore> {
	List<Televisore> findAllByProdottiTra(Date data1, Date data2) throws Exception;

	Televisore findTelevisorePiuGrande() throws Exception;

	List<Televisore> findAllMarcheProdottiUltimiSeiMesi() throws Exception;

}
