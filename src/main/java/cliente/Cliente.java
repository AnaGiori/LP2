package cliente;

import java.util.List;
import java.util.ArrayList;
import agencia.Agencia;
import conta.Conta;
import conta.ContaCorrente;
import conta.ContaPoupanca;
import conta.ContaSalario;
import excecoes.AgenciaInvalidaException;
import excecoes.ContaInvalidaException;
import excecoes.ClienteInvalidoException;
import persistencia.GerenciadorPersistencia;
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String senha;
    private ArrayList<Conta> contas;

    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public String getCpf() {
        return cpf;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public boolean autenticar(String cpf, String senha) {
        return this.cpf.equals(cpf) && this.senha.equals(senha);
    }

    // Corrigido: Método de instância para criar conta
    public void criarConta(String numeroConta, String tipoContaStr, String numeroAgencia, double saldo) throws AgenciaInvalidaException, ContaInvalidaException {
        Agencia agencia = GerenciadorPersistencia.buscarAgencia(numeroAgencia);  // Buscar agência
        
        // Criando a conta e associando à agência
        Conta conta = criarContaPorTipo(agencia, tipoContaStr, numeroConta);  // Usando o método de instância
        
        if (conta != null) {
            conta.depositar(saldo);  // Depositar o saldo inicial

            // Adicionando a conta à lista de contas do cliente
            contas.add(conta);

            // Adicionando a conta à agência e ao cache global de contas
            agencia.adicionarConta(conta);
            GerenciadorPersistencia.contasCache.put(numeroConta, conta);

            // Salvando os dados
            List<Agencia> agencias = new ArrayList<>();
            agencias.add(agencia);
            GerenciadorPersistencia.salvarDados(agencias);

            System.out.println("Conta criada! Número: " + conta.getNumero());
        } else {
            System.out.println("Erro na criação da conta. Tipo de conta inválido.");
        }
    }

    // Método para criar conta com base no tipo
    private Conta criarContaPorTipo(Agencia agencia, String tipo, String numeroConta) {
        switch (tipo) {
            case "ContaCorrente":
                return new ContaCorrente(agencia, numeroConta);
            case "ContaPoupanca":
                return new ContaPoupanca(agencia, numeroConta);
            case "ContaSalario":
                return new ContaSalario(agencia, numeroConta);
            default:
                return null;  // Retorna null caso o tipo seja inválido
        }
    }
}