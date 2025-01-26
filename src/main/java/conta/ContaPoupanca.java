package conta;

import agencia.Agencia;

public class ContaPoupanca extends Conta {
    private static final double RENDIMENTO_MENSAL = 0.5;

    public ContaPoupanca(Agencia agencia, String numero) {
        super(agencia, numero);
    }

    public void renderJuros() {
        this.saldo *= (1 + RENDIMENTO_MENSAL / 100);
    }
}
