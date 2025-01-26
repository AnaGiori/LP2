package conta;

import agencia.Agencia;
import excecoes.ContaInvalidaException;
import excecoes.LimiteSaqueExcedidoException;

public abstract class Conta {
    protected double saldo;
    protected Agencia agencia;
    protected String numero;

    public Conta(Agencia agencia, String numero) {
        this.agencia = agencia;
        this.numero = numero;
        agencia.adicionarConta(this);
    }

    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void sacar(double valor) throws LimiteSaqueExcedidoException {
        if (this.saldo < valor) {
           throw new LimiteSaqueExcedidoException("Saldo insuficiente para o saque.");
        }
        this.saldo -= valor;
        System.out.println("Saque realizado! Novo saldo: " + this.saldo);
    }

    public void transferir(Conta contaDestino, double valor) throws ContaInvalidaException, LimiteSaqueExcedidoException {
        this.sacar(valor);
        contaDestino.depositar(valor);
    }

    public double getSaldo() {
        return this.saldo;
    }

    public Agencia getAgencia() {
        return this.agencia;
    }

    public String getNumero() {
        return this.numero;
    }

}
