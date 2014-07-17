
public class Carro {
	private String placa;
	
	public Carro(String placa) {
		this.setPlaca(placa);
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public boolean equals(Object obj) {
		Carro carro = (Carro) obj;
		return carro != null && carro.getPlaca().equals(this.getPlaca());
	}
	
	
}
