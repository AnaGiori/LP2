package conta;

import agencia.Agencia;
import excecoes.ContaInvalidaException;
import excecoes.LimiteSaqueExcedidoException;

public class ContaCorrente extends Conta {

    public ContaCorrente(Agencia agencia, String numero) {
        super(agencia, numero);
    }

    @Override
    public void sacar(double valor) throws LimiteSaqueExcedidoException {
        super.sacar(valor);
    }
}
