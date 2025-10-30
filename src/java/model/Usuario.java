package model;

import java.math.BigInteger;
import java.security.MessageDigest;
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
    private String endereco;

    // Construtor padrão
    public Usuario() {
        super("usuarios");
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
    
   public String getEndereco(){
       return endereco;
   }
   
   public void setEndereco(String endereco){
       this.endereco = endereco;
       addChange("endereco", this.endereco);
   }

    public void setSenha(String senha) throws Exception {
        if (senha == null) {
            if (this.senha != null) {
                this.senha = senha;
                addChange("senha", this.senha);
            }
        } else {
            if (senha.equals(this.senha) == false) {

                String senhaSal = getId() + senha + getId() / 2;

                MessageDigest md = MessageDigest.getInstance("SHA-256");

                String hash = new BigInteger(1, md.digest(senhaSal.getBytes("UTF-8"))).toString(16);

                this.senha = hash;
                addChange("senha", this.senha);

            }
        }
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
    protected DataAccessObject fill(ArrayList data) {
// preenche os atributos na mesma ordem das colunas da tabela
        id = (int) data.get(0);
        nome = (String) data.get(1);
        cpf = (String) data.get(2);
        endereco = (String) data.get(3);
        senha = (String) data.get(4);
        tipoUsuarioId = (int) data.get(5);
        
        return this;
    }

// implementação do método abstrato para criar uma cópia do objeto
    @Override
    protected Usuario copy() {
        Usuario copia = new Usuario();

// copia todos os atributos para o novo objeto
        copia.setId(getId());
        copia.setNome(getNome());
        copia.setCpf(getCpf());
        copia.senha = (getSenha());
        copia.setTipoUsuarioId(getTipoUsuarioId());
        copia.endereco = (getEndereco());

// marca a cópia como não sendo uma nova entidade
        copia.setNovelEntity(false);

        return copia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario aux = (Usuario) obj;
            if (getId() == aux.getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Representação em string do objeto para debug e exibição
    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", cpf='" + cpf + '\''
                + ", senha='" + senha + '\''
                + ", tipoUsuarioId=" + tipoUsuarioId + '\''
                + ", endereco=" + endereco
                + '}';
    }
}
