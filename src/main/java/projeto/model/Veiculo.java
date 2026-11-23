package projeto.model;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private long cpfProprietario;
    private int ano;

    public Veiculo(String placa, String marca, String modelo, String cor,
                   long cpfProprietario, int ano) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.cpfProprietario = cpfProprietario;
        this.ano = ano;
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getCor() { return cor; }
    public long getCpfProprietario() { return cpfProprietario; }
    public int getAno() { return ano; }
}
