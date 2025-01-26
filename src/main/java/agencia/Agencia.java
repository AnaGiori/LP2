package agencia;

import java.util.ArrayList;
import java.util.List;
import conta.Conta;

public class Agencia {
    private String codigo;
    private String nome;
    private List<Conta> contas;

    private static int contadorDeContas = 1;

    public Agencia(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public static String gerarNumeroConta() {
    return String.valueOf(contadorDeContas++);
    }
}
