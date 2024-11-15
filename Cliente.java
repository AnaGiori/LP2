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

    public void criarConta(String tipo) {
    int numeroConta = Conta.totalContas++;
    Conta novaConta = new Conta(numeroConta, tipo, 0.0);
    contas.add(novaConta);
    System.out.println("Conta criada! Número: " + numeroConta);
    }

}

