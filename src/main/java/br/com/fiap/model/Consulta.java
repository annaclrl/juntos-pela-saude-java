package br.com.fiap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {

    private int codigo;
    private Paciente paciente;
    private Medico medico;
    private String status;
    private String feedback;
    private LocalDateTime dataHora;

    public Consulta() { }

    public Consulta(int codigo, Paciente paciente, Medico medico, LocalDateTime dataHora, String status) {
        this.codigo = codigo;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public boolean consultaRealizada() {
            return dataHora != null && dataHora.isBefore(LocalDateTime.now());
    }

    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }


    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nPaciente: " + (paciente != null ? paciente.getNome() + " (CPF: " + paciente.getCpf() + ")" : "N/A") +
                "\nMédico: " + (medico != null ? medico.getNome() + " (CRM: " + medico.getCrm() + ")" : "N/A") +
                "\nStatus: " + status +
                "\nData e Hora: " + (dataHora != null ? getDataHoraFormatada() : "N/A");
    }

}

