package persistencia;

import cliente.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import agencia.Agencia;
import conta.Conta;
import conta.ContaCorrente;
import conta.ContaPoupanca;
import conta.ContaSalario;
import excecoes.AgenciaInvalidaException;
import excecoes.ContaInvalidaException;
import excecoes.ClienteInvalidoException;

public class GerenciadorPersistencia {
    private static final String ARQUIVO_AGENCIAS = "agencias.csv";
    private static final String ARQUIVO_CONTAS = "contas.csv";
    private static Map<String, Agencia> agenciasCache = new HashMap<>();
    public static Map<String, Conta> contasCache = new HashMap<>();

    // Salva os dados das agências e contas no arquivo
    public static void salvarDados(List<Agencia> agencias) {
        try {
            // Salvando as agências no arquivo agencias.csv
            BufferedWriter agenciasWriter = new BufferedWriter(new FileWriter(ARQUIVO_AGENCIAS));
            for (Agencia agencia : agencias) {
                agenciasWriter.write(agencia.getCodigo() + "," + agencia.getNome());
                for (Conta conta : agencia.getContas()) {
                    agenciasWriter.write("," + conta.getNumero() + "," + conta.getClass().getSimpleName() + "," + conta.getSaldo());
                }
                agenciasWriter.newLine();
            }
            agenciasWriter.close();

            // Salvando as contas no arquivo contas.csv
            BufferedWriter contasWriter = new BufferedWriter(new FileWriter(ARQUIVO_CONTAS));
            for (Conta conta : contasCache.values()) {
                contasWriter.write(conta.getNumero() + "," + conta.getSaldo() + "," + conta.getClass().getSimpleName());
                contasWriter.newLine();
            }
            contasWriter.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // Carrega os dados das agências e contas do arquivo
    public static List<Agencia> carregarDados() throws ContaInvalidaException {
        List<Agencia> agencias = new ArrayList<>();
        try {
            // Carregando as agências do arquivo agencias.csv
            BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_AGENCIAS));
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String codigoAgencia = dados[0].trim();  // Removendo espaços extras
                String nomeAgencia = dados[1].trim();    // Removendo espaços extras
                System.out.println("Código da Agência: " + codigoAgencia + ", Nome: " + nomeAgencia);  // Para depuração
                Agencia agencia = new Agencia(codigoAgencia, nomeAgencia);
                agencias.add(agencia);
                agenciasCache.put(codigoAgencia, agencia);

                // Carregando as contas associadas à agência
                for (int i = 2; i < dados.length; i += 3) {
                    String numeroContaStr = dados[i];
                    String tipoContaStr = dados[i + 1];
                    double saldoStr = Double.parseDouble(dados[i + 2]);
                    Conta conta = criarContaPorTipo(agencia, numeroContaStr, tipoContaStr);
                    conta.depositar(saldoStr);
                    agencia.adicionarConta(conta);
                    contasCache.put(numeroContaStr, conta);  // Registrando a conta no cache de contas
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
        return agencias;
    }

    // Busca a agência no cache
    public static Agencia buscarAgencia(String codigo) throws AgenciaInvalidaException {
        if (agenciasCache.containsKey(codigo)) {
            return agenciasCache.get(codigo);
        }
        throw new AgenciaInvalidaException("Agência não encontrada: " + codigo);
    }

    // Busca a conta no cache
    public static Conta buscarConta(String numeroConta) throws ContaInvalidaException {
        if (contasCache.containsKey(numeroConta)) {
            return contasCache.get(numeroConta);
        }
        throw new ContaInvalidaException("Conta não encontrada: " + numeroConta);
    }

    // Cria a conta de acordo com o tipo e a agência
    public static Conta criarContaPorTipo(Agencia agencia, String numero, String tipoStr) throws ContaInvalidaException {
        Conta conta = null;

        switch (tipoStr) {
        case "ContaCorrente":
            conta = new ContaCorrente(agencia, numero);
            break;
        case "ContaPoupanca":
            conta = new ContaPoupanca(agencia, numero);
            break;
        case "ContaSalario":
            conta = new ContaSalario(agencia, numero);
            break;
        default:
            throw new ContaInvalidaException("Tipo de conta inválido: " + tipoStr);
        }
        contasCache.put(numero, conta);
        return conta;
    }
}
