package br.com.fiap.model;

import br.com.fiap.service.ConsultaService;
import br.com.fiap.service.FuncionarioService;
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
        FuncionarioService funcionarioService = new FuncionarioService();

        boolean continuar = true;

        while(continuar){
            System.out.println("\n=== Juntos pela Saúde ===");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Médicos");
            System.out.println("3. Gerenciar Funcionários");
            System.out.println("4. Gerenciar Consultas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao){

                case 1:
                    menuPacientes(pacienteService);
                    break;
                case 2:
                    menuMedicos(medicoService);
                    break;
                case 3:
                    menuFuncionarios(funcionarioService);
                case 4:
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
        funcionarioService.close();
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
        int opcao = scanner.nextInt();

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
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Primeiro telefone: ");
        String telefone1 = scanner.nextLine();
        System.out.print("Segundo telefone: ");
        String telefone2 = scanner.nextLine();


        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setEmail(email);
        paciente.setCpf(cpf);
        paciente.setTelefone1(telefone1);
        paciente.setTelefone2(telefone2);
        paciente.setIdade(idade);

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
        System.out.print("Código do paciente para atualizar: ");
        int codigo = scanner.nextInt();
        Paciente paciente = service.buscarPorCodigo(codigo);
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


        System.out.print("Nova idade (" + paciente.getIdade() + "): ");
        String idadeStr = scanner.nextLine();
        if (!idadeStr.isBlank()) paciente.setIdade(Integer.parseInt(idadeStr));

        System.out.print("Novo primeiro telefone (" + paciente.getEmail() + "): ");
        String telefone1 = scanner.nextLine();
        if (!telefone1.isBlank()) paciente.setTelefone2(telefone1);

        System.out.print("Novo segundo telefone (" + paciente.getEmail() + "): ");
        String telefone2 = scanner.nextLine();
        if (!telefone2.isBlank()) paciente.setTelefone2(telefone2);

        if (service.atualizarPaciente(paciente)) {
            System.out.println("Paciente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar paciente.");
        }
    }

    private static void deletarPaciente(PacienteService service) throws SQLException {
        System.out.print("Código do paciente para deletar: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        if (service.deletarPaciente(codigo)) {
            System.out.println("Paciente deletado com sucesso!");
        } else {
            System.out.println("Erro ao deletar paciente.");
        }
    }

    private static void buscarPacientePorCpf(PacienteService service) throws SQLException {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        Paciente paciente = service.buscarPorCpf(cpf);
        if (paciente != null) {
            System.out.println(paciente);
        } else {
            System.out.println("Paciente não encontrado!");
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

    private static void cadastrarMedico(MedicoService service) throws SQLException {

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Primeiro telefone: ");
        String telefone1 = scanner.nextLine();
        System.out.print("Segundo telefone: ");
        String telefone2 = scanner.nextLine();
        System.out.print("CRM: ");
        int crm = Integer.parseInt(scanner.nextLine());
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Medico medico = new Medico();
        medico.setNome(nome);
        medico.setEmail(email);
        medico.setCpf(cpf);
        medico.setIdade(idade);
        medico.setTelefone1(telefone1);
        medico.setTelefone2(telefone2);
        medico.setCrm(crm);
        medico.setEspecialidade(especialidade);

        if (service.cadastrarMedico(medico)) {
            System.out.println("Médico cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar médico.");
        }
    }

    private static void listarMedicos(MedicoService service) throws SQLException {
        List<Medico> medicos = service.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
        } else {
            medicos.forEach(System.out::println);
        }
    }

    private static void atualizarMedico(MedicoService service) throws SQLException {
        System.out.print("Código do médico para atualizar: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Medico medico = service.buscarPorCodigo(codigo);
        if (medico == null) {
            System.out.println("Médico não encontrado!");
            return;
        }

        System.out.print("Novo nome (" + medico.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) medico.setNome(nome);

        System.out.print("Novo email (" + medico.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) medico.setEmail(email);

        System.out.print("Nova idade (" + medico.getIdade() + "): ");
        String idadeStr = scanner.nextLine();
        if (!idadeStr.isBlank()) medico.setIdade(Integer.parseInt(idadeStr));

        System.out.print("Novo primeiro telefone (" + medico.getEmail() + "): ");
        String telefone1 = scanner.nextLine();
        if (!telefone1.isBlank()) medico.setTelefone1(telefone1);

        System.out.print("Novo segundo telefone (" + medico.getEmail() + "): ");
        String telefone2 = scanner.nextLine();
        if (!telefone2.isBlank()) medico.setTelefone2(telefone2);

        System.out.print("Nova especialidade (" + medico.getNome() + "): ");
        String especialidade = scanner.nextLine();
        if (!especialidade.isBlank()) medico.setEspecialidade(especialidade);

        if (service.atualizarMedico(medico)) {
            System.out.println("Médico atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar médico.");
        }
    }

    private static void deletarMedico(MedicoService service) throws SQLException {
        System.out.print("Código do médico para deletar: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        if (service.deletarMedico(codigo)) {
            System.out.println("Médico deletado com sucesso!");
        } else {
            System.out.println("Erro ao deletar médico.");
        }
    }

    private static void buscarMedicoPorCrm(MedicoService service) throws SQLException {
        System.out.print("Digite o CRM: ");
        int crm = scanner.nextInt();
        Medico medico = service.buscarPorCrm(crm);
        if (medico != null) {
            System.out.println(medico);
        } else {
            System.out.println("Médico não encontrado!");
        }
    }

    public static void menuFuncionarios(FuncionarioService service) throws SQLException{
        System.out.println("\n--- GERENCIAR FUNCIONÁRIOS ---");
        System.out.println("1. Cadastrar funcionários");
        System.out.println("2. Listar funcionários");
        System.out.println("3. Atualizar funcionário");
        System.out.println("4. Deletar funcionário");
        System.out.println("5. Buscar funcionário por CPF");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                cadastrarFuncionario(service);
                break;
            case 2:
                listarFuncionario(service);
                break;
            case 3:
                atualizarFuncionario(service);
                break;
            case 4:
                deletarFuncionario(service);
                break;
            case 5:
                buscarFuncionarioPorCpf(service);
        }
    }

    private static void cadastrarFuncionario(FuncionarioService service) throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Primeiro telefone: ");
        String telefone1 = scanner.nextLine();
        System.out.print("Segundo telefone: ");
        String telefone2 = scanner.nextLine();


        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setCpf(cpf);
        funcionario.setTelefone1(telefone1);
        funcionario.setTelefone2(telefone2);
        funcionario.setIdade(idade);

        if (service.cadastrarFuncionario(funcionario)) {
            System.out.println("Funcionário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar funcionário.");
        }
    }

    private static void listarFuncionario(FuncionarioService service) throws SQLException {
        List<Funcionario> funcionarios = service.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            funcionarios.forEach(System.out::println);
        }
    }

    private static void atualizarFuncionario(FuncionarioService service) throws SQLException {
        System.out.print("Código do funcionário para atualizar: ");
        int codigo = scanner.nextInt();
        Funcionario funcionario= service.buscarPorCodigo(codigo);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.print("Novo nome (" + funcionario.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) funcionario.setNome(nome);

        System.out.print("Novo email (" + funcionario.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) funcionario.setEmail(email);


        System.out.print("Nova idade (" + funcionario.getIdade() + "): ");
        String idadeStr = scanner.nextLine();
        if (!idadeStr.isBlank()) funcionario.setIdade(Integer.parseInt(idadeStr));

        System.out.print("Novo primeiro telefone (" + funcionario.getEmail() + "): ");
        String telefone1 = scanner.nextLine();
        if (!telefone1.isBlank()) funcionario.setTelefone2(telefone1);

        System.out.print("Novo segundo telefone (" + funcionario.getEmail() + "): ");
        String telefone2 = scanner.nextLine();
        if (!telefone2.isBlank()) funcionario.setTelefone2(telefone2);

        if (service.atualizarFuncionario(funcionario)) {
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar funcionário.");
        }
    }

    private static void deletarFuncionario(FuncionarioService service) throws SQLException {
        System.out.print("Código do funcionário para deletar: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        if (service.deletarFuncionario(codigo)) {
            System.out.println("Paciente deletado com sucesso!");
        } else {
            System.out.println("Erro ao deletar funcionário.");
        }
    }

    private static void buscarFuncionarioPorCpf(FuncionarioService service) throws SQLException {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        Funcionario funcionario = service.buscarPorCpf(cpf);
        if (funcionario != null) {
            System.out.println(funcionario);
        } else {
            System.out.println("Funcionário não encontrado!");
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
