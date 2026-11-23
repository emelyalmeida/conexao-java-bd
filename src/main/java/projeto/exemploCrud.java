package projeto;

import projeto.view.TelaCadastro;

public class exemploCrud {

    public static void main(String[] args) {
        Conexao.conectar();

        TelaCadastro tela = new TelaCadastro();
        tela.setVisible(true);
    }
}
