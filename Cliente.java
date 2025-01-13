import java.util.ArrayList;

class Cliente {
    private String nome;
    private String cpf;
    private String senha;
    private ArrayList<Conta> contas;
    
    // Construtor
    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    //Métodos
    public String getCpf() {
        return cpf;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public boolean autenticar(String cpf, String senha) {
        return this.cpf.equals(cpf) && this.senha.equals(senha);
    }

   public void criarConta(Agencia agencia, String tipo) {
        String numeroConta = agencia.gerarNumeroConta();
        Conta novaConta;
        switch (tipo) {
            case "ContaCorrente":
                novaConta = new ContaCorrente(agencia, numeroConta);
                break;
            case "ContaPoupanca":
                novaConta = new ContaPoupanca(agencia, numeroConta);
                break;
            case "ContaSalario":
                novaConta = new ContaSalario(agencia, numeroConta);
                break;
            default:
                System.out.println("Tipo de conta não reconhecido.");
                return;
        }
        contas.add(novaConta);
        System.out.println("Conta criada! Número: " + numeroConta);
    }
}
