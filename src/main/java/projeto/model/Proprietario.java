package projeto.model;

public class Proprietario {
    private long cpf;
    private String nome;
    private String profissao;

    public Proprietario(long cpf, String nome, String profissao) {
        this.cpf = cpf;
        this.nome = nome;
        this.profissao = profissao;
    }

    public long getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getProfissao() { return profissao; }
}
