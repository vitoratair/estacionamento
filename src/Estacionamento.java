import java.util.Calendar;
import java.util.Date;


public class Estacionamento {
	private static final String HORA_DE_ENTRADA_MENOR_QUE_A_HORA_DE_SAÍDA = "Hora de entrada menor que a hora de saída.";
	private static final String FORA_DE_HORÁRIO = "Fora de horário.";
	private static final String CARRO_NAO_ESTACIONADO = "Carro não estacionado.";
	private static final String CARRO_ESTACIONADO_COM_SUCESSO = "Carro estacionado com sucesso.";
	private static final String ESTACIONAMENTO_LOTADO = "Estacionamento lotado.";
	private static final String PLACA_INVALIDA = "Placa inválida.";
	private static final String CARRO_JA_EXISTE = "Carro já existe.";
	private static final String REGEX_PLACA_VALIDA = "[A-Z]{3}-\\d{4}";
	
	private Carro[] carros;
	private Date abertura;
	private Date fechamento;
	
	
	public Estacionamento(int totalDeVagas) {
		this.carros = new Carro[totalDeVagas];
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		this.abertura = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 22);
		this.fechamento = calendar.getTime();
	}
	
	public int getVagasDisponiveis() {
		int vagasDisponiveis = carros.length;
		for (Carro carro : carros) {	
			if (carro != null) vagasDisponiveis--;
		}
		return vagasDisponiveis;
	}

	public String entrarCarro(Carro carro, Date horaDeEntrada) {
		if (!isHorarioFuncionamento(horaDeEntrada)) return FORA_DE_HORÁRIO;
		if (isCarroEstacionado(carro)) return CARRO_JA_EXISTE;
		if (!validarPlaca(carro.getPlaca())) return PLACA_INVALIDA;
		int vagaDisponivel = buscarVagaDisponivel();
		if (vagaDisponivel == -1) return ESTACIONAMENTO_LOTADO;
		carros[vagaDisponivel] = carro;
		return CARRO_ESTACIONADO_COM_SUCESSO;
	}
	
	private boolean isHorarioFuncionamento(Date hora) {
		return (hora.after(abertura) && hora.before(fechamento)) || (hora.equals(abertura) || hora.equals(fechamento));
		
	}

	private int buscarVagaDisponivel() {
		for (int numeroDaVaga = 0; numeroDaVaga < carros.length; numeroDaVaga++) {
			if (carros[numeroDaVaga] == null) return numeroDaVaga;
		}
		return -1;
	}

	public String sairCarro(Carro carro, Date horaDeEntrada, Date horaDeSaida) {
		if (!isCarroEstacionado(carro)) return CARRO_NAO_ESTACIONADO;
		if (!isHorarioFuncionamento(horaDeSaida)) return FORA_DE_HORÁRIO;		
		if (isHorarioEntradaMenorQueSaida(horaDeEntrada, horaDeSaida)) return HORA_DE_ENTRADA_MENOR_QUE_A_HORA_DE_SAÍDA;
		
		carros[buscarVagaDoCarro(carro)] = null;
		
		return "Carro permaneceu durante " + calcularPermanencia(horaDeEntrada, horaDeSaida) + " horas.";
		
	}
	
	private int calcularPermanencia(Date horaDeEntrada, Date horaDeSaida) {
		return getHora(horaDeSaida.getTime()) - getHora(horaDeEntrada.getTime());
	}
	
	private int getHora(long hora){
		
		return (int) (hora/1000/60/60);
		
	}

	private boolean isHorarioEntradaMenorQueSaida(Date horaDeEntrada, Date horaDeSaida) {
		return horaDeSaida.before(horaDeEntrada);
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