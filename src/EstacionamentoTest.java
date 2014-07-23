import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class EstacionamentoTest {
	private static final int TOTAL_DE_VAGAS = 600;
	private Estacionamento estacionamento;
	private Calendar calendar;
	private Date horaDeEntrada;
	private Date horaDeSaida;
	

	@Before
	public void criarEstacionamento() {
		estacionamento = new Estacionamento(TOTAL_DE_VAGAS);
		calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		horaDeEntrada = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		horaDeSaida = calendar.getTime();
	}

	@Test
	public void deveTer500VagasDisponives() {
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS, vagasDisponiveis);
	}
	
	@Test
	public void deveEntrarUmCarro() {
		estacionamento.entrarCarro(palio(), horaDeEntrada);
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS - 1, vagasDisponiveis);
	}
	
	@Test
	public void deveSairUmCarro() {
		estacionamento.entrarCarro(palio(), horaDeEntrada);
		estacionamento.sairCarro(palio(), horaDeEntrada, horaDeSaida);
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS, vagasDisponiveis);
	}
	
	@Test
	public void naoPodeEntrarCarroDuplicado() {
		
		String retornoEstacionamentoSucesso = estacionamento.entrarCarro(palio(), horaDeEntrada);
		String retornoCarroExiste = estacionamento.entrarCarro(palio(), horaDeEntrada);
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals("Carro estacionado com sucesso.", retornoEstacionamentoSucesso);
		assertEquals("Carro já existe.", retornoCarroExiste);
		assertEquals(TOTAL_DE_VAGAS - 1, vagasDisponiveis);
	}
	
	@Test
	public void estacionamentoLotado() {
		for (int i = 0; i < TOTAL_DE_VAGAS; i++) {
			String retornoEstacionamentoSucesso = estacionamento.entrarCarro(new Carro("AAA-" + geraPlacaAleatoria(i)), horaDeEntrada);
			assertEquals("Carro estacionado com sucesso.", retornoEstacionamentoSucesso);
		}
		String retornoEstacionamentoLotado = estacionamento.entrarCarro(new Carro("AAA-" + geraPlacaAleatoria(TOTAL_DE_VAGAS + 1)), horaDeEntrada);
		assertEquals("Estacionamento lotado.", retornoEstacionamentoLotado);
	}

	@Test
	public void naoPodeSairCarroQueNaoExiste() {
		estacionamento.entrarCarro(corsa(), horaDeEntrada);
		String retornoCarroNaoExiste = estacionamento.sairCarro(palio(), horaDeEntrada, horaDeSaida);
		assertEquals("Carro não estacionado.", retornoCarroNaoExiste);
	}
	
	@Test
	public void naoPodeEntrarCarroComPlacaInvalida() {
		String retornoPlacaInvalida = estacionamento.entrarCarro(new Carro("000-9999"), horaDeEntrada);
		assertEquals("Placa inválida.", retornoPlacaInvalida);
	}
	
	@Test
	public void aPlacaDeveSerInvalida() {
		assertFalse(estacionamento.validarPlaca(""));
		assertFalse(estacionamento.validarPlaca("AAA"));
		assertFalse(estacionamento.validarPlaca("000-9999"));
		assertFalse(estacionamento.validarPlaca("Intelbras"));
	}
	
	@Test
	public void tentouEntrarUmCarroForaDoHorario(){
		calendar.set(Calendar.HOUR_OF_DAY, 7);
		horaDeEntrada = calendar.getTime();		
		String retornoForaHorario =  estacionamento.entrarCarro(palio(), horaDeEntrada);
		assertEquals("Fora de horário.", retornoForaHorario);
	}

	@Test
	public void tentouSairUmCarroForaDoHorario(){
		estacionamento.entrarCarro(palio(), horaDeEntrada);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		horaDeSaida = calendar.getTime();
		String retornoForaHorario =  estacionamento.sairCarro(palio(), horaDeEntrada, horaDeSaida);
		assertEquals("Fora de horário.", retornoForaHorario);
	}
	
	@Test
	public void umCarroNaoPodeSairAntesDeTerEntrado(){
		estacionamento.entrarCarro(palio(), horaDeEntrada);		
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		horaDeSaida = calendar.getTime();
		String retornoForaHorario =  estacionamento.sairCarro(palio(), horaDeEntrada, horaDeSaida);
		assertEquals("Hora de entrada menor que a hora de saída.", retornoForaHorario);
	}
	
	@Test
	public void UmCarroPermaneceDurante3HorasNoEstacionamento(){
		estacionamento.entrarCarro(palio(), horaDeEntrada);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		horaDeSaida = calendar.getTime();
		String retornoSaidaSucesso =  estacionamento.sairCarro(palio(), horaDeEntrada, horaDeSaida);
		assertEquals("Carro permaneceu durante 3 horas.", retornoSaidaSucesso);
	}
	
	
	
	private Carro palio() {
		return new Carro("AAA-9999");
	}
	
	private Carro corsa() {
		return new Carro("AAA-9998");
	}
	
	private String geraPlacaAleatoria(int indice) {
		if (indice < 10)
			return "000" + indice;
		else if (indice >= 10 && indice < 100)
			return "00" + indice;
		else if (indice >= 100 && indice < 1000)
			return "0" + indice;
		return null;
	}
}
