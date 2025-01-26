import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import agencia.Agencia;
import conta.Conta;
import excecoes.AgenciaInvalidaException;
import excecoes.ClienteInvalidoException;
import excecoes.ContaInvalidaException;
import excecoes.LimiteSaqueExcedidoException;
import persistencia.GerenciadorPersistencia;
import cliente.Cliente;

public class Main {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();

    public static void main(String[] args) throws ContaInvalidaException {
        try {
            // Chama o método carregarDados para carregar as agências
            GerenciadorPersistencia.carregarDados();
        } catch (ContaInvalidaException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    criarConta(scanner);
                    break;
                case 3:
                    consultarSaldo(scanner);
                    break;
                case 4:
                    realizarDeposito(scanner);
                    break;
                case 5:
                    realizarSaque(scanner);
                    break;
                case 6:
                    realizarTransferencia(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Cadastrar cliente");
        System.out.println("2. Criar conta");
        System.out.println("3. Consultar saldo");
        System.out.println("4. Realizar depósito");
        System.out.println("5. Realizar saque");
        System.out.println("6. Realizar transferência");
        System.out.println("0. Sair");
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\nCadastro de cliente:");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        clientes.add(new Cliente(nome, cpf, senha));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void criarConta(Scanner scanner) {
        System.out.println("\nCriar conta:");

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            System.out.print("Informe o número da agência: ");
            String numeroAgencia = scanner.nextLine();  // Captura o número da agência

            try {
                // Aqui deve ser utilizado o número da agência corretamente, não o tipo da conta
                Agencia agencia = GerenciadorPersistencia.buscarAgencia(numeroAgencia);

                System.out.println("Escolha o tipo de conta:");
                System.out.println("1. Conta Corrente");
                System.out.println("2. Conta Poupança");
                System.out.println("3. Conta Salário");
                int tipoConta = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer

                String tipoContaStr = "";
                switch (tipoConta) {
                    case 1:
                        tipoContaStr = "ContaCorrente";
                        break;
                    case 2:
                        tipoContaStr = "ContaPoupanca";
                        break;
                    case 3:
                        tipoContaStr = "ContaSalario";
                        break;
                    default:
                        System.out.println("Tipo de conta inválido!");
                        return;
                }

                String numeroConta = agencia.gerarNumeroConta();
            
                cliente.criarConta(numeroConta, tipoContaStr, numeroAgencia, 0);

                Conta contaCriada = GerenciadorPersistencia.buscarConta(numeroConta);

                if (contaCriada != null) {
                    contas.add(contaCriada);  // Adicionando a conta ao cache de contas do sistema
                    System.out.println("Conta criada com sucesso!");
                }

            } catch (AgenciaInvalidaException e) {
                System.out.println("Erro ao buscar agência: " + e.getMessage());
            } catch (ContaInvalidaException e) {  // Aqui estamos tratando a exceção ContaInvalidaException
                System.out.println("Erro ao criar conta: " + e.getMessage());
            }
        }
    }


    private static Cliente buscarCliente(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        System.out.println("Cliente não encontrado!");
        return null;
    }

    private static void consultarSaldo(Scanner scanner) {
        System.out.println("\nConsultar saldo:");

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            System.out.print("Informe o número da conta: ");
            String numeroConta = scanner.nextLine();

            Conta conta = buscarConta(numeroConta);
            if (conta != null) {
                System.out.println("Saldo da conta: " + conta.getSaldo());
            }
        }
    }

    private static Conta buscarConta(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numeroConta)) {
                return conta;
            }
        }
        System.out.println("Conta não encontrada!");
        return null;
    }

    private static void realizarDeposito(Scanner scanner) {
        System.out.println("\nRealizar depósito:");

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            System.out.print("Informe o número da conta: ");
            String numeroConta = scanner.nextLine();

            Conta conta = buscarConta(numeroConta);
            if (conta != null) {
                System.out.print("Informe o valor do depósito: ");
                double valor = scanner.nextDouble();
                conta.depositar(valor);
                System.out.println("Depósito realizado com sucesso!");
            }
        }
    }

    private static void realizarSaque(Scanner scanner) {
        System.out.println("\nRealizar saque:");

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            System.out.print("Informe o número da conta: ");
            String numeroConta = scanner.nextLine();

            Conta conta = buscarConta(numeroConta);
            if (conta != null) {
                System.out.print("Informe o valor do saque: ");
                double valor = scanner.nextDouble();
                try {
                    conta.sacar(valor);
                    System.out.println("Saque realizado com sucesso!");
                } catch (LimiteSaqueExcedidoException e) {
                    System.out.println("Erro no saque: " + e.getMessage());
                }
            }
        }
    }

    private static void realizarTransferencia(Scanner scanner) {
        System.out.println("\nRealizar transferência:");

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            System.out.print("Informe o número da conta origem: ");
            String numeroContaOrigem = scanner.nextLine();
            Conta contaOrigem = buscarConta(numeroContaOrigem);

            if (contaOrigem != null) {
                System.out.print("Informe o número da conta destino: ");
                String numeroContaDestino = scanner.nextLine();
                Conta contaDestino = buscarConta(numeroContaDestino);

                if (contaDestino != null) {
                    System.out.print("Informe o valor da transferência: ");
                    double valor = scanner.nextDouble();

                    try {
                        contaOrigem.transferir(contaDestino, valor);
                        System.out.println("Transferência realizada com sucesso!");
                    } catch (ContaInvalidaException | LimiteSaqueExcedidoException e) {
                        System.out.println("Erro na transferência: " + e.getMessage());
                    }
                }
            }
        }
    }
}
