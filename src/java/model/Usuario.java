package model;

import model.framework.DataAccessObject;
import java.util.ArrayList;

// Classe que representa a entidade Usuario do sistema
// Mapeia para a tabela 'usuarios' do banco de dados sgcm_bd
public class Usuario extends DataAccessObject {
    
    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private int tipoUsuarioId; // Chave estrangeira para tabela tipo_usuario

    // Construtor padrão
    public Usuario() {
        super("sgcm_bd.usuarios");
    }

    // Construtor completo para criar novo usuário
    public Usuario(String nome, String cpf, String senha, int tipoUsuarioId) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.tipoUsuarioId = tipoUsuarioId;
    }

    // Getters e Setters com registro de alterações (Unit of Work)
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        addChange("id", id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", nome);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        addChange("cpf", cpf);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
        addChange("senha", senha);
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
        addChange("tipo_usuario_id", tipoUsuarioId);
    }

    // Retorna a cláusula WHERE para identificar um usuário único pelo ID
    @Override
    protected String getWhereClauseForOneEntity() {
        return "id = " + getId();
    }

    // Preenche os atributos do objeto com dados do banco
    // Ordem das colunas: id, nome, cpf, senha, tipo_usuario_id
    @Override
    protected DataAccessObject fill(ArrayList<Object> data) {
        setId((Integer) data.get(0));
        setNome((String) data.get(1));
        setCpf((String) data.get(2));
        setSenha((String) data.get(3));
        setTipoUsuarioId((Integer) data.get(4));
        
        return this;
    }

    // Cria uma cópia do objeto Usuario
    @Override
    protected Usuario copy() {
        Usuario copia = new Usuario();
        copia.id = this.id;
        copia.nome = this.nome;
        copia.cpf = this.cpf;
        copia.senha = this.senha;
        copia.tipoUsuarioId = this.tipoUsuarioId;
        
        return copia;
    }

    // Representação em string do objeto para debug e exibição
    @Override
    public String toString() {
        return "Usuario{" + 
               "id=" + id + 
               ", nome='" + nome + '\'' + 
               ", cpf='" + cpf + '\'' + 
               ", senha='" + senha + '\'' + 
               ", tipoUsuarioId=" + tipoUsuarioId + 
               '}';
    }
}