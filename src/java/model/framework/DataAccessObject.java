package model.framework;

import java.sql.Connection;
import controller.AppConfig;
import java.util.HashMap;
import java.util.StringJoiner;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Classe abstrata que implementa o padrão Data Access Object (DAO)
// Fornece operações básicas de CRUD para persistência em banco de dados relacional
// Implementa o padrão Unit of Work para gerenciamento eficiente de alterações
public abstract class DataAccessObject {
    
    // Nome da tabela no banco de dados associada à entidade
    private String tableEntity;
    
    // Flag que indica se a entidade é nova (ainda não persistida no banco)
    private boolean novelEntity;
    
    // Flag que indica se a entidade foi modificada (padrão Unit of Work)
    private boolean changedEntity;
    
    // Mapa que armazena os campos modificados e seus novos valores
    // Utilizado para otimizar updates, gravando apenas os campos alterados
    private HashMap<String, Object> dirtyFields;

    // Construtor que inicializa a entidade com o nome da tabela
    public DataAccessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();
        setNovelEntity(true);      // Inicialmente toda entidade é considerada nova
        setChangedEntity(false);   // Inicialmente não há modificações
    }

    // Retorna o nome da tabela no banco de dados
    private String getTableEntity() {
        return tableEntity;
    }

    // Verifica se a entidade é nova (não persistida)
    private boolean isNovelEntity() {
        return novelEntity;
    }

    // Verifica se a entidade foi modificada
    private boolean isChangedEntity() {
        return changedEntity;
    }

    // Define o nome da tabela com validação
    private void setTableEntity(String tableEntity) {
        if(tableEntity != null && 
                !tableEntity.isEmpty() &&
              !tableEntity.isBlank() ){
        this.tableEntity = tableEntity;
        }else{
        throw new IllegalArgumentException("Table must be valid");
        }
    }

    // Define se a entidade é nova (protegido para uso pelas subclasses)
    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }

    // Define se a entidade foi modificada
    // Se marcada como não modificada, limpa a lista de campos alterados
    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        if(this.changedEntity == false){
            dirtyFields.clear(); // Limpa os campos modificados quando a entidade está "limpa"
        }
    }
    
    // Registra uma modificação em um campo (Unit of Work pattern)
    // Armazena o campo e seu novo valor para atualização otimizada
    protected void addChange(String field, Object value){
        dirtyFields.put(field, value);
        setChangedEntity(true); // Marca a entidade como modificada
    }
    
    // Executa operação INSERT no banco de dados
    // Método privado chamado automaticamente pelo save() quando a entidade é nova
    private void insert() throws SQLException{
        
        String dml = "INSERT INTO " + getTableEntity();
        
        // StringJoiner para construir dinamicamente a lista de campos e valores
        StringJoiner fields = new StringJoiner(" , ");
        StringJoiner values = new StringJoiner(" , ");
        
        // Adiciona todos os campos modificados na query
        for(String field : dirtyFields.keySet()){
           fields.add(field);
           values.add("?");
        }
        
        // Monta a query SQL completa
        dml += " (" + fields + ") VALUES (" + values + ")";
        
        // Log para debug (se configurado)
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        
        // Obtém conexão com o banco e prepara a statement
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(dml);
        
        // Preenche os parâmetros da query com os valores dos campos modificados
        int index = 1;
        for( String field : dirtyFields.keySet()){
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        
        // Log adicional para debug
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        System.out.println( pst );
        
        // Executa a inserção
        pst.execute();
        
        // Libera recursos
        pst.close();
        DataBaseConnections.getInstance().CloseConnection(con);
    }
    
    // Executa operação UPDATE no banco de dados
    // Método privado chamado automaticamente pelo save() quando a entidade existe
    private void update() throws SQLException{
       String dml = "UPDATE " + getTableEntity() + " SET ";
       
       // StringJoiner para construir a lista de campos a atualizar
       StringJoiner changes = new StringJoiner(",");
       
       // Adiciona apenas os campos que foram modificados
       for( String field : dirtyFields.keySet() ){
           changes.add(field + " =  ? ");
       }
       
       // Monta a query completa com cláusula WHERE
       dml += changes + " WHERE " + getWhereClauseForOneEntity(); 
       
       // Log para debug
       if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
       
       // Obtém conexão e prepara a statement
       Connection con = DataBaseConnections.getInstance().getConnection();
       PreparedStatement pst = con.prepareStatement(dml);
        
        // Preenche os parâmetros da query
        int index = 1;
        for( String field : dirtyFields.keySet()){
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        
        // Log adicional
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        System.out.println( pst );
        
        // Executa a atualização
        pst.execute();
        
        // Libera recursos
        pst.close();
        DataBaseConnections.getInstance().CloseConnection(con);
    }
    
    // Salva a entidade no banco de dados (INSERT ou UPDATE automático)
    // Implementa o padrão Unit of Work - só executa operações se houver mudanças
    public void save() throws SQLException{
        if(isChangedEntity()){
            if(isNovelEntity()){
                insert();               // INSERT para entidades novas
                setNovelEntity(false);  // Após insert, não é mais nova
            }else{
                update();               // UPDATE para entidades existentes
            }
            setChangedEntity(false);    // Marca como não modificada após salvar
        }
    }
    
    // Exclui a entidade do banco de dados
    public void delete() throws SQLException{
        
        // Monta query DELETE com cláusula WHERE específica
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        // Log para debug
        if( AppConfig.getInstance().isVerbose() )
            System.out.println( dml );
        
        // Obtém conexão e executa a exclusão
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        
        st.execute( dml );
        st.close();
        
        // Libera conexão
        DataBaseConnections.getInstance().CloseConnection(con);
    }
    
    // Carrega a entidade do banco de dados
    // Retorna true se encontrou a entidade, false caso contrário
    public boolean load() throws SQLException{
        boolean result;
        
        // Monta query SELECT para buscar a entidade específica
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        // Log para debug
        if(AppConfig.getInstance().isVerbose() )
            System.out.println( dql );
        
        // Obtém conexão e executa a consulta
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery( dql );
        
        // Verifica se encontrou resultados
        result = rs.next();
       
        if(result){ 
           // Extrai todos os dados da linha encontrada
           ArrayList<Object> data = new ArrayList();
           for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
              data.add( rs.getObject(i) );
           }
           
           // Preenche o objeto com os dados do banco
           fill(data); 
           setNovelEntity(false); // Após carregar, não é mais uma entidade nova
           setChangedEntity(false);
       }
        
        return result;
    }
    
    // Retorna todas as entidades da tabela
    // Método genérico que funciona para qualquer subclasse de DataAccessObject
    public <T extends DataAccessObject> ArrayList<T> getAllTableEntities() throws SQLException{
        ArrayList<T> result = new ArrayList<>();
        
        // Query SELECT para buscar todos os registros
        String dql = "SELECT * FROM " + getTableEntity();
        
        // Log para debug
        if( AppConfig.getInstance().isVerbose() )
            System.out.println( dql );
        
        // Obtém conexão e executa a consulta
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery( dql );
        
        // Processa cada linha do resultado
        while ( rs.next() ){
            ArrayList<Object> data = new ArrayList<>();
            
            // Extrai todos os dados da linha
            for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
                data.add( rs.getObject(i) );                
            }
            
            // Cria e adiciona o objeto preenchido na lista de resultados
            result.add( fill(data).copy() );
        }
        
        // Libera recursos
        st.close();
        DataBaseConnections.getInstance().CloseConnection(con);
        
        return result;
    }
    
    // Métodos abstratos que devem ser implementados pelas subclasses
    // (Template Method Pattern)
    
    // Retorna a cláusula WHERE para identificar uma única entidade
    // Exemplo: "id = 123" ou "codigo = 'ABC'"
    protected abstract String getWhereClauseForOneEntity();
    
    // Preenche os atributos do objeto com dados do banco
    // Recebe uma lista com os valores na ordem das colunas da tabela
    protected abstract DataAccessObject fill(ArrayList<Object> data);
    
    // Cria uma cópia do objeto (clone)
    // Útil para operações que requerem cópias dos objetos persistentes
    protected abstract <T extends DataAccessObject> T copy();

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("equals must be override");
    }
    
    
    
}