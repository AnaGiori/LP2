package conta;

import agencia.Agencia;
import excecoes.ContaInvalidaException;
import excecoes.LimiteSaqueExcedidoException;

public class ContaSalario extends Conta {
    private static final int LIMITE_SAQUES = 3;
    private int saquesRealizados;

    public ContaSalario(Agencia agencia, String numero) {
        super(agencia, numero);
        this.saquesRealizados = 0;
    }

    @Override
    public void sacar(double valor) throws LimiteSaqueExcedidoException {
        if (this.saquesRealizados >= LIMITE_SAQUES) {
            throw new LimiteSaqueExcedidoException("Limite de saques excedido");
        }
        super.sacar(valor);
        this.saquesRealizados++;
    }

    public void depositarSalario(double valor) {
        this.depositar(valor);
    }
}
