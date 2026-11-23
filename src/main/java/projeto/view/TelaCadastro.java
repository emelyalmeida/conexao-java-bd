package projeto.view;

import projeto.dao.ProprietarioDAO;
import projeto.dao.VeiculoDAO;
import projeto.model.Proprietario;
import projeto.model.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastro extends JFrame {

    private final ProprietarioDAO propDAO = new ProprietarioDAO();
    private final VeiculoDAO veiDAO = new VeiculoDAO();

    private final JTextField txtCpf = new JTextField(12);
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtProfissao = new JTextField(15);
    private final DefaultTableModel modeloProp =
            new DefaultTableModel(new String[]{"CPF", "Nome", "Profissão", "Qtd. veículos"}, 0);
    private final JTable tabelaProp = new JTable(modeloProp);

    private final JTextField txtPlaca = new JTextField(8);
    private final JTextField txtMarca = new JTextField(12);
    private final JTextField txtModelo = new JTextField(12);
    private final JTextField txtCor = new JTextField(10);
    private final JTextField txtCpfDono = new JTextField(12);
    private final JTextField txtAno = new JTextField(6);
    private final DefaultTableModel modeloVei =
            new DefaultTableModel(new String[]{"Placa", "Marca", "Modelo", "Cor", "CPF dono", "Ano"}, 0);
    private final JTable tabelaVei = new JTable(modeloVei);

    public TelaCadastro() {
        super("Estacionamento - Cadastro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Proprietários", montarAbaProprietario());
        abas.addTab("Veículos", montarAbaVeiculo());
        add(abas);

        listarProprietarios();
        listarVeiculos();
    }

    private JPanel montarAbaProprietario() {
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("CPF:")); form.add(txtCpf);
        form.add(new JLabel("Nome:")); form.add(txtNome);
        form.add(new JLabel("Profissão:")); form.add(txtProfissao);

        JButton bCad = new JButton("Cadastrar (procedure)");
        JButton bAtu = new JButton("Atualizar");
        JButton bExc = new JButton("Excluir");
        JButton bLim = new JButton("Limpar");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(bCad); botoes.add(bAtu); botoes.add(bExc); botoes.add(bLim);

        bCad.addActionListener(e -> executar(() -> {
            propDAO.cadastrarViaProcedure(lerProprietario());
            listarProprietarios();
            limparProprietario();
        }));
        bAtu.addActionListener(e -> executar(() -> {
            propDAO.atualizar(lerProprietario());
            listarProprietarios();
        }));
        bExc.addActionListener(e -> executar(() -> {
            propDAO.excluir(Long.parseLong(txtCpf.getText().trim()));
            listarProprietarios();
            limparProprietario();
        }));
        bLim.addActionListener(e -> limparProprietario());

        tabelaProp.getSelectionModel().addListSelectionListener(e -> {
            int i = tabelaProp.getSelectedRow();
            if (i >= 0 && !e.getValueIsAdjusting()) {
                txtCpf.setText(String.valueOf(modeloProp.getValueAt(i, 0)));
                txtNome.setText(String.valueOf(modeloProp.getValueAt(i, 1)));
                Object prof = modeloProp.getValueAt(i, 2);
                txtProfissao.setText(prof == null ? "" : prof.toString());
            }
        });

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(form, BorderLayout.NORTH);
        topo.add(botoes, BorderLayout.SOUTH);

        JPanel p = new JPanel(new BorderLayout());
        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabelaProp), BorderLayout.CENTER);
        return p;
    }

    private JPanel montarAbaVeiculo() {
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Placa:")); form.add(txtPlaca);
        form.add(new JLabel("Marca:")); form.add(txtMarca);
        form.add(new JLabel("Modelo:")); form.add(txtModelo);
        form.add(new JLabel("Cor:")); form.add(txtCor);
        form.add(new JLabel("CPF dono:")); form.add(txtCpfDono);
        form.add(new JLabel("Ano:")); form.add(txtAno);

        JButton bCad = new JButton("Cadastrar");
        JButton bAtu = new JButton("Atualizar");
        JButton bExc = new JButton("Excluir");
        JButton bLim = new JButton("Limpar");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(bCad); botoes.add(bAtu); botoes.add(bExc); botoes.add(bLim);

        bCad.addActionListener(e -> executar(() -> {
            veiDAO.inserir(lerVeiculo());
            listarVeiculos();
            listarProprietarios();
            limparVeiculo();
        }));
        bAtu.addActionListener(e -> executar(() -> {
            veiDAO.atualizar(lerVeiculo());
            listarVeiculos();
            listarProprietarios();
        }));
        bExc.addActionListener(e -> executar(() -> {
            veiDAO.excluir(txtPlaca.getText().trim().toUpperCase());
            listarVeiculos();
            listarProprietarios();
            limparVeiculo();
        }));
        bLim.addActionListener(e -> limparVeiculo());

        tabelaVei.getSelectionModel().addListSelectionListener(e -> {
            int i = tabelaVei.getSelectedRow();
            if (i >= 0 && !e.getValueIsAdjusting()) {
                txtPlaca.setText(String.valueOf(modeloVei.getValueAt(i, 0)));
                txtMarca.setText(String.valueOf(modeloVei.getValueAt(i, 1)));
                txtModelo.setText(String.valueOf(modeloVei.getValueAt(i, 2)));
                Object cor = modeloVei.getValueAt(i, 3);
                txtCor.setText(cor == null ? "" : cor.toString());
                txtCpfDono.setText(String.valueOf(modeloVei.getValueAt(i, 4)));
                txtAno.setText(String.valueOf(modeloVei.getValueAt(i, 5)));
            }
        });

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(form, BorderLayout.NORTH);
        topo.add(botoes, BorderLayout.SOUTH);

        JPanel p = new JPanel(new BorderLayout());
        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabelaVei), BorderLayout.CENTER);
        return p;
    }

    private Proprietario lerProprietario() {
        return new Proprietario(Long.parseLong(txtCpf.getText().trim()),
                txtNome.getText().trim(), txtProfissao.getText().trim());
    }

    private Veiculo lerVeiculo() {
        return new Veiculo(txtPlaca.getText().trim().toUpperCase(),
                txtMarca.getText().trim(), txtModelo.getText().trim(), txtCor.getText().trim(),
                Long.parseLong(txtCpfDono.getText().trim()),
                Integer.parseInt(txtAno.getText().trim()));
    }

    private void listarProprietarios() {
        executar(() -> {
            modeloProp.setRowCount(0);
            for (Proprietario p : propDAO.listar()) {
                modeloProp.addRow(new Object[]{p.getCpf(), p.getNome(), p.getProfissao(),
                        propDAO.contarVeiculos(p.getCpf())});
            }
        });
    }

    private void listarVeiculos() {
        executar(() -> {
            modeloVei.setRowCount(0);
            for (Veiculo v : veiDAO.listar()) {
                modeloVei.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo(),
                        v.getCor(), v.getCpfProprietario(), v.getAno()});
            }
        });
    }

    private void limparProprietario() {
        txtCpf.setText(""); txtNome.setText(""); txtProfissao.setText("");
        tabelaProp.clearSelection();
    }

    private void limparVeiculo() {
        txtPlaca.setText(""); txtMarca.setText(""); txtModelo.setText("");
        txtCor.setText(""); txtCpfDono.setText(""); txtAno.setText("");
        tabelaVei.clearSelection();
    }

    private interface Acao { void run() throws Exception; }

    private void executar(Acao acao) {
        try {
            acao.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha CPF e Ano com números válidos.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro no banco: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
