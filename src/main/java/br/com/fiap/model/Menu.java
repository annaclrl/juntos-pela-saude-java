package br.com.fiap.model;

import br.com.fiap.service.ConsultaService;
import br.com.fiap.service.MedicoService;
import br.com.fiap.service.PacienteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() throws SQLException, ClassNotFoundException {

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();
        ConsultaService consultaService = new ConsultaService();

        boolean continuar = true;

        while(continuar){
            System.out.println("\n=== Juntos pela Saúde ===");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Médicos");
            System.out.println("3. Gerenciar Consultas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao){

                case 1:
                    menuPacientes(pacienteService);
                    break;
                case 2:
                    menuMedicos(medicoService);
                    break;
                case 3:
                    menuConsultas(consultaService);
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Finalizando programa...");
                    break;
                default:
                    System.out.println("Opçaõ inválida!");
                    break;
            }
        }
        pacienteService.close();
        medicoService.close();
        consultaService.close();
    }

    public static void menuPacientes(PacienteService service) throws SQLException{
        System.out.println("\n--- GERENCIAR PACIENTES ---");
        System.out.println("1. Cadastrar paciente");
        System.out.println("2. Listar pacientes");
        System.out.println("3. Atualizar paciente");
        System.out.println("4. Deletar paciente");
        System.out.println("5. Buscar paciente por CPF");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case 1:
                cadastrarPaciente(service);
                break;
            case 2:
                listarPaciente(service);
                break;
            case 3:
                atualizarPaciente(service);
                break;
            case 4:
                deletarPaciente(service);
                break;
            case 5:
                buscarPacientePorCpf(service);
        }
    }

    private static void cadastrarPaciente(PacienteService service) throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite seu logradouro: ");

        String logradouro = scanner.nextLine();
        System.out.print("Digite o número: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o complemento: ");
        String complemento = scanner.nextLine();
        System.out.print("Digite o cep: ");
        String cep = scanner.nextLine();

        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setCep(cep);


        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setEmail(email);
        paciente.setCpf(cpf);
        paciente.setTelefone(telefone);
        paciente.setIdade(idade);
        paciente.setEndereco(endereco);

        if (service.cadastrarPaciente(paciente)) {
            System.out.println("Paciente cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar paciente.");
        }
    }

    private static void listarPaciente(PacienteService service) throws SQLException {
        List<Paciente> pacientes = service.listarPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    private static void atualizarPaciente(PacienteService service) throws SQLException {
        System.out.print("CPF do paciente para atualizar: ");
        String cpf = scanner.nextLine();
        Paciente paciente = service.buscarPorCpf(cpf);
        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        System.out.print("Novo nome (" + paciente.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) paciente.setNome(nome);

        System.out.print("Novo email (" + paciente.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) paciente.setEmail(email);

        System.out.print("Novo telefone (" + paciente.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        if (!telefone.isBlank()) paciente.setTelefone(telefone);

        System.out.print("Nova idade (" + paciente.getIdade() + "): ");
        String idadeStr = scanner.nextLine();
        if (!idadeStr.isBlank()) paciente.setIdade(Integer.parseInt(idadeStr));

        System.out.print("Novo logradouro (" + paciente.getEndereco().getLogradouro() + "): ");
        String logradouro = scanner.nextLine();
        if (!logradouro.isBlank()) paciente.getEndereco().setLogradouro(logradouro);

        System.out.print("Novo número (" + paciente.getEndereco().getNumero() + "): ");
        String numeroStr = scanner.nextLine();
        if (!numeroStr.isBlank()) paciente.getEndereco().setNumero(Integer.parseInt(numeroStr));

        System.out.print("Novo complemento (" + paciente.getEndereco().getComplemento() + "): ");
        String complemento = scanner.nextLine();
        if (!complemento.isBlank()) paciente.getEndereco().setComplemento(complemento);

        System.out.print("Novo CEP (" + paciente.getEndereco().getCep() + "): ");
        String cep = scanner.nextLine();
        if (!cep.isBlank()) paciente.getEndereco().setCep(cep);

        if (service.atualizarPaciente(paciente)) {
            System.out.println("Paciente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar paciente.");
        }
    }

    private static void menuMedicos(MedicoService service) throws SQLException {
        System.out.println("\n--- GERENCIAR MÉDICOS ---");
        System.out.println("1. Cadastrar médico");
        System.out.println("2. Listar médicos");
        System.out.println("3. Atualizar médico");
        System.out.println("4. Deletar médico");
        System.out.println("5. Buscar médico por CRM");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                cadastrarMedico(service);
                break;
            case 2:
                listarMedicos(service);
                break;
            case 3:
                atualizarMedico(service);
                break;
            case 4:
                deletarMedico(service);
                break;
            case 5:
                buscarMedicoPorCrm(service);
                break;
        }
    }

    private static void menuConsultas(ConsultaService service) throws SQLException {
        System.out.println("\n--- GERENCIAR CONSULTAS ---");
        System.out.println("1. Agendar Consulta");
        System.out.println("2. Atualizar consulta");
        System.out.println("3. Cancelar consulta");
        System.out.println("4. Listar todas as consultas");
        System.out.println("5. Listar consultas por paciente");
        System.out.println("6. Listar consultas por médico");
        System.out.println("7. Adicionar feedback");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case 1:
                cadastrarConsulta(service);
                break;
            case 2:
                atualizarConsulta(service);
                break;
            case 3:
                deletarConsulta(service);
                break;
            case 4:
                listarConsultas(service);
                break;
            case 5:
                listarConsultasPorPaciente(service);
                break;
            case 6:
                listarConsultasPorMedico(service);
                break;
            case 7:
                adicionarFeedback(service);
                break;
        }
    }


}
