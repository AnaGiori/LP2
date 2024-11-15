class Conta {
    private int numeroConta;
    private String tipo;
    private double saldo;
    
    //Contador de contas
    public static int totalContas = 0;

    //Construtor
    public Conta(int numeroConta, String tipo, double saldo) {
        this.numeroConta = numeroConta;
        this.tipo = tipo;
        this.saldo = saldo;
    }
    
    //Métodos
    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado! Novo saldo: R$" + saldo);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void transferir(Conta contaDestino, double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
            System.out.println("Novo saldo da conta: R$" + saldo);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }
}
