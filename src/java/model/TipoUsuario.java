package model;

import model.framework.DataAcessObject;

public class TipoUsuario extends DataAcessObject {
    
    private int id;
    private String nome;
    private String moduloAdministrativo;
    private String moduloAgendamento;
    private String moduloAtendimento;
    
    public TipoUsuario(){
        super("tipo_usuario"); // avisa qual tabela pertence --> vc mapeia.
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getModuloAdministrativo() {
        return moduloAdministrativo;
    }

    public String getModuloAgendamento() {
        return moduloAgendamento;
    }

    public String getModuloAtendimento() {
        return moduloAtendimento;
    }

    public void setId(int id) {
        this.id = id;
        addChange("id", this.id);
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", this.nome);
    }

    public void setModuloAdministrativo(String moduloAdministrativo) {
        this.moduloAdministrativo = moduloAdministrativo;
        addChange("modulo_Administrativo", this.moduloAdministrativo);
    }

    public void setModuloAgendamento(String moduloAgendamento) {
        this.moduloAgendamento = moduloAgendamento;
        addChange("modulo_Agendamento", this.moduloAgendamento);
    }

    public void setModuloAtendimento(String moduloAtendimento) {
        this.moduloAtendimento = moduloAtendimento;
        addChange("modulo_Atendimento", this.moduloAtendimento);
    }
    
    
}
