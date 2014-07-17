import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EstacionamentoTest {
	private static final int TOTAL_DE_VAGAS = 600;
	private Estacionamento estacionamento;

	@Before
	public void criarEstacionamento() {
		estacionamento = new Estacionamento(TOTAL_DE_VAGAS);
	}

	@Test
	public void deveTer500VagasDisponives() {
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS, vagasDisponiveis);
	}
	
	@Test
	public void deveEntrarUmCarro() {
		estacionamento.entrarCarro(palio());
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS - 1, vagasDisponiveis);
	}
	
	@Test
	public void deveSairUmCarro() {
		estacionamento.entrarCarro(palio());
		estacionamento.sairCarro(palio());
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals(TOTAL_DE_VAGAS, vagasDisponiveis);
	}
	
	@Test
	public void naoPodeEntrarCarroDuplicado() {
		String retornoEstacionamentoSucesso = estacionamento.entrarCarro(palio());
		String retornoCarroExiste = estacionamento.entrarCarro(palio());
		int vagasDisponiveis = estacionamento.getVagasDisponiveis();
		assertEquals("Carro estacionado com sucesso.", retornoEstacionamentoSucesso);
		assertEquals("Carro já existe.", retornoCarroExiste);
		assertEquals(TOTAL_DE_VAGAS - 1, vagasDisponiveis);
	}
	
	@Test
	public void estacionamentoLotado() {
		for (int i = 0; i < TOTAL_DE_VAGAS; i++) {
			String retornoEstacionamentoSucesso = estacionamento.entrarCarro(new Carro("AAA-" + geraPlacaAleatoria(i)));
			assertEquals("Carro estacionado com sucesso.", retornoEstacionamentoSucesso);
		}
		String retornoEstacionamentoLotado = estacionamento.entrarCarro(new Carro("AAA-" + geraPlacaAleatoria(TOTAL_DE_VAGAS + 1)));
		assertEquals("Estacionamento lotado.", retornoEstacionamentoLotado);
	}

	@Test
	public void naoPodeSairCarroQueNaoExiste() {
		estacionamento.entrarCarro(corsa());
		String retornoCarroNaoExiste = estacionamento.sairCarro(palio());
		assertEquals("Carro não estacionado.", retornoCarroNaoExiste);
	}
	
	@Test
	public void naoPodeEntrarCarroComPlacaInvalida() {
		String retornoPlacaInvalida = estacionamento.entrarCarro(new Carro("000-9999"));
		assertEquals("Placa inválida.", retornoPlacaInvalida);
	}
	
	@Test
	public void aPlacaDeveSerInvalida() {
		assertFalse(estacionamento.validarPlaca(""));
		assertFalse(estacionamento.validarPlaca("AAA"));
		assertFalse(estacionamento.validarPlaca("000-9999"));
		assertFalse(estacionamento.validarPlaca("Intelbras"));
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
