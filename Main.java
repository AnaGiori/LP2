import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Cliente> clientes = new ArrayList<>();

    public static Cliente autenticarUsuario(String cpf, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.autenticar(cpf, senha)) {
                return cliente;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menu de Opcoes ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Criar Conta");
            System.out.println("3. Consultar Saldo");
            System.out.println("4. Depositar");
            System.out.println("5. Sacar");
            System.out.println("6. Transferir");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    
                    Cliente clientenovo= new Cliente(nome, cpf, senha);
                    
                    clientes.add(clientenovo);
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("CPF: ");
                    cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                    Cliente cliente = autenticarUsuario(cpf, senha);
                    if (cliente != null) {
                        System.out.print("Tipo da conta: \n");
                        System.out.print("1. Corrente\n");
                        System.out.print("2. Poupanca\n");
                        int opcaoTipoConta= scanner.nextInt();
                        
                        if (opcaoTipoConta==1) {
                        	String tipoConta = "Corrente";
                        	cliente.criarConta(tipoConta);
                            
                        } else if (opcaoTipoConta==2) {
                        	String tipoConta = "Poupanca";
                        	cliente.criarConta(tipoConta);
                            
                        } else {
                        	System.out.println("Tipo de conta não reconhecida.");
                        	break;
                        }
                         
                    } else {
                        System.out.println("Autenticação falhou.");
                    }
                    break;

                case 3:
                    System.out.print("CPF: ");
                    cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                    cliente = autenticarUsuario(cpf, senha);
                    if (cliente != null) {
                        for (Conta conta : cliente.getContas()) {
                            System.out.println("Conta " + conta.getNumeroConta() + " - Saldo: R$" + conta.getSaldo());
                        }
                    } else {
                        System.out.println("Autenticação falhou.");
                    }
                    break;

                case 4:
                    System.out.print("CPF: ");
                    cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                    cliente = autenticarUsuario(cpf, senha);
                    if (cliente != null) {
                        System.out.print("Número da conta: ");
                        int numeroConta = scanner.nextInt();
                        System.out.print("Valor do depósito: ");
                        double valor = scanner.nextDouble();

                        for (Conta conta : cliente.getContas()) {
                            if (conta.getNumeroConta() == numeroConta) {
                                conta.depositar(valor);
                                System.out.println("Depósito realizado com sucesso!");
                                break;
                            } else {
                            	System.out.println("Número de conta não encontrado.");
                            }
                        }
                    } else {
                        System.out.println("Autenticação falhou.");
                    }
                    break;

                case 5:
                    System.out.print("CPF: ");
                    cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                    cliente = autenticarUsuario(cpf, senha);
                    if (cliente != null) {
                        System.out.print("Número da conta: ");
                        int numeroConta = scanner.nextInt();
                        System.out.print("Valor do saque: ");
                        double valor = scanner.nextDouble();

                        for (Conta conta : cliente.getContas()) {
                            if (conta.getNumeroConta() == numeroConta) {
                                conta.sacar(valor);
                                break;
                            } else {
                            	System.out.println("Número de conta não encontrado.");
                            }
                        }
                    } else {
                        System.out.println("Autenticação falhou.");
                    }
                    break;
                
                

                case 0:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}
