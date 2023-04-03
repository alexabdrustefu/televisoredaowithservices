package it.prova.televisoredaowithservices.service.test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import it.prova.televisoredaowithservices.model.Televisore;
import it.prova.televisoredaowithservices.service.MyServiceFactory;
import it.prova.televisoredaowithservices.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) {
		// parlo direttamente con il service
		TelevisoreService userService = MyServiceFactory.getTelevisoreServiceImpl();
		try {
			// ora con il service posso fare tutte le invocazioni che mi servono
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testInserimentoNuovoTelevisore(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testRimozioneTelevisore(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testFindByExample(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testFindAllByProdottiTra(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testFindTelevisorePiuGrande(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================test
			testFindAllMarcheProdottiUltimiSeiMesi(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			// ============================================================================================fine
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ============================================================================================metodoStaticoPerTest
	// metodi statici per il main
	public static void testInserimentoNuovoTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testInserimentoNuovoTelevisore inizio.............");
		Televisore newTelevisoreInstance = new Televisore("lg", "moedllo1212", 30, new Date(2023 - 10 - 12));
		if (televisoreService.inserisciNuovo(newTelevisoreInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoTelevisore FAILED ");
		televisoreService.inserisciNuovo(newTelevisoreInstance);

		System.out.println(".......testInserimentoNuovoTelevisore PASSED.............");
	}

	// ============================================================================================metodoStaticoPerTest
	private static void testRimozioneTelevisore(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testRimozioneTelevisore inizio.............");
		// recupero tutti gli user
		List<Televisore> interoContenutoTabella = televisoreService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idUltimo = interoContenutoTabella.get(interoContenutoTabella.size() - 1).getId();
		// ricarico per sicurezza con l'id individuato
		Televisore toBeRemoved = televisoreService.findById(idUltimo);
		if (televisoreService.rimuovi(toBeRemoved) != 1)
			throw new RuntimeException("testRimozioneTelevisore FAILED ");
		televisoreService.rimuovi(toBeRemoved);

		System.out.println(".......testRimozioneTelevisore PASSED.............");
	}

	// ============================================================================================metodoStaticoPerTest
	private static void testFindByExample(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindByExample inizio.............");

		Televisore example = new Televisore();
		example.setMarca("lg");
		example.setModello("modello1");
		televisoreService.findByExample(example);
		System.out.println(televisoreService);
		List<Televisore> result = televisoreService.findByExample(example);
		for (Televisore ricerca : result) {
			System.out.println(result);
		}

		System.out.println(".......testFindByExample PASSED.............");
	}

	// ============================================================================================metodoStaticoPerTest
	private static void testFindAllByProdottiTra(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindAllByProdottiTra inizio.............");

		Date data1 = (Date) new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2020");
		Date data2 = (Date) new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2023");
		List<Televisore> result = televisoreService.findAllByProdottiTra(data1, data2);
		for (Televisore ricerca : result) {
			System.out.println(ricerca);
		}

		System.out.println(".......testFindAllByProdottiTra PASSED.............");
	}

	// ============================================================================================metodoStaticoPerTest
	private static void testFindTelevisorePiuGrande(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindTelevisorePiuGrande inizio.............");
		Televisore televisoremax = televisoreService.findTelevisorePiuGrande();
		System.out.println(televisoremax);
		System.out.println(".......testFindTelevisorePiuGrande PASSED.............");
	}

	// ============================================================================================metodoStaticoPerTest
	private static void testFindAllMarcheProdottiUltimiSeiMesi(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testFindAllMarcheProdottiUltimiSeiMesi inizio.............");
		List<Televisore> result = televisoreService.findAllMarcheProdottiUltimi6Mesi();
		for (Televisore ricerca : result) {
			System.out.println(ricerca);
		}

		System.out.println(".......testFindAllMarcheProdottiUltimiSeiMesi PASSED.............");
	}
	// ============================================================================================fineMetodi

}
