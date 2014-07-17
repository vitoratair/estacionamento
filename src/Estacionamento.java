
public class Estacionamento {
	private static final String CARRO_SAIU_DO_ESTACIONAMENTO = "Carro saiu do estacionamento.";
	private static final String CARRO_NAO_ESTACIONADO = "Carro não estacionado.";
	private static final String CARRO_ESTACIONADO_COM_SUCESSO = "Carro estacionado com sucesso.";
	private static final String ESTACIONAMENTO_LOTADO = "Estacionamento lotado.";
	private static final String PLACA_INVALIDA = "Placa inválida.";
	private static final String CARRO_JA_EXISTE = "Carro já existe.";
	private static final String REGEX_PLACA_VALIDA = "[A-Z]{3}-\\d{4}";
	
	private Carro[] carros;
	
	public Estacionamento(int totalDeVagas) {
		this.carros = new Carro[totalDeVagas];
	}
	
	public int getVagasDisponiveis() {
		int vagasDisponiveis = carros.length;
		for (Carro carro : carros) {	
			if (carro != null) vagasDisponiveis--;
		}
		return vagasDisponiveis;
	}

	public String entrarCarro(Carro carro) {
		if (isCarroEstacionado(carro)) return CARRO_JA_EXISTE;
		if (!validarPlaca(carro.getPlaca())) return PLACA_INVALIDA;
		int vagaDisponivel = buscarVagaDisponivel();
		if (vagaDisponivel == -1) return ESTACIONAMENTO_LOTADO;
		carros[vagaDisponivel] = carro;
		return CARRO_ESTACIONADO_COM_SUCESSO;
	}
	
	private int buscarVagaDisponivel() {
		for (int numeroDaVaga = 0; numeroDaVaga < carros.length; numeroDaVaga++) {
			if (carros[numeroDaVaga] == null) return numeroDaVaga;
		}
		return -1;
	}

	public String sairCarro(Carro carro) {
		if (!isCarroEstacionado(carro)) return CARRO_NAO_ESTACIONADO;
		carros[buscarVagaDoCarro(carro)] = null;
		return CARRO_SAIU_DO_ESTACIONAMENTO;
	}
	
	private int buscarVagaDoCarro(Carro carro) {
		for (int vagaDoCarro = 0; vagaDoCarro < carros.length; vagaDoCarro++) {
			if (carro.equals(carros[vagaDoCarro])) return vagaDoCarro;
		}
		return -1;
	}
	
	private boolean isCarroEstacionado(Carro carro) {
		return buscarVagaDoCarro(carro) > -1;
	}
	
	public boolean validarPlaca(String placa) {
		return placa.matches(REGEX_PLACA_VALIDA);
	}
}