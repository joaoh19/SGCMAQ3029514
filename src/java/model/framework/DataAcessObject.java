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




public abstract class DataAcessObject {
    
    private String tableEntity;
    private boolean novelEntity;
    private boolean changedEntity; //pra ver se foi criado ou nao um usuario
    private HashMap<String, Object> dirtyFields; // se o campo for alterado ele tem q ser validado

    public DataAcessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();
        setNovelEntity(true);
        setChangedEntity(false);
        
    }

    private String getTableEntity() {
        return tableEntity;
    }

    private boolean isNovelEntity() {
        return novelEntity;
    }

    private boolean isChangedEntity() {
        return changedEntity;
    }

    private void setTableEntity(String tableEntity) {
        if(tableEntity != null && 
                !tableEntity.isEmpty() &&
              !tableEntity.isBlank() ){
        this.tableEntity = tableEntity;
        }else{
        throw new IllegalArgumentException("Table must be valid");
        }
        
    }

    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }

    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        if(this.changedEntity == false){
            dirtyFields.clear();
        }
    }
    
    //Unity of Work // qual campo e qual valor 
    protected void addChange(String field, Object value){
        dirtyFields.put(field, value);
        setChangedEntity(true);        
    }
    
    private void insert() throws SQLException{
        
        String dml = "INSERT INTO " + getTableEntity();
        
            StringJoiner fields = new StringJoiner(" , ");
            StringJoiner values = new StringJoiner(" , ");
        
        for(String field : dirtyFields.keySet()){
           fields.add(field);
           values.add("?");
        }
        
        dml += " (" + fields + ") VALUES (" + values + ")";
        
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        
        Connection con = DataBaseConnections.getInstance().getConnection();
         PreparedStatement  pst = con.prepareStatement(dml);
        
        int index = 1;
        for( String field : dirtyFields.keySet()){
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        System.out.println( pst );
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().CloseConnection(con);
        
    }
    private void update() throws SQLException{
       String dml = "UPDATE " + getTableEntity() + " SET ";
       
       StringJoiner changes = new StringJoiner(",");
       
       for( String field : dirtyFields.keySet() ){
           changes.add(field + " =  ? ");
       }
       
       dml += changes + " WHERE " + getWhereClauseForOneEntity(); 
       
       if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
       
       Connection con = DataBaseConnections.getInstance().getConnection();
         PreparedStatement  pst = con.prepareStatement(dml);
        
        int index = 1;
        for( String field : dirtyFields.keySet()){
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        if(AppConfig.getInstance().isVerbose())
            System.out.println( dml );
        System.out.println( pst );
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().CloseConnection(con);
        
    }
    
    
             
    public void save() throws SQLException{
        if(isChangedEntity()){
            if(isNovelEntity()){
                insert();
                setNovelEntity(false);
            }else{
                update();
            }
                setChangedEntity(false);               
        }
    }
    
    
    public void delete() throws SQLException{
        
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println( dml );
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        
        st.execute( dml );
        st.close();
        
        DataBaseConnections.getInstance().CloseConnection(con);
        
    }
    
    public boolean load() throws SQLException{
        boolean result;
        
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if(AppConfig.getInstance().isVerbose() )
            System.out.println( dql );
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery( dql );
        
       result = rs.next();
       
       if(result){ 
           ArrayList<Object> data = new ArrayList();
           for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
              data.add( rs.getObject(i) );
           }
           
           fill(data); 
           setNovelEntity(false);
       }
        
        return result;
    }
    
    public <T extends DataAcessObject> ArrayList<T> getAllTableEntities() throws SQLException{
        ArrayList<T> result = new ArrayList<>();
        
        String dql = "SELECT * FROM " + getTableEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println( dql );
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery( dql );
        
        while ( rs.next() ){
            ArrayList<Object> data = new ArrayList<>();
            
            for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
                data.add( rs.getObject(i) );                
            }
                result.add( fill(data).copy() );
        }
        
        st.close();
        
        DataBaseConnections.getInstance().CloseConnection(con);
        
        return result;
    }
    
    //Padrão template method
    protected abstract String getWhereClauseForOneEntity();
    protected abstract DataAcessObject fill(ArrayList<Object> data);
    protected abstract <T extends DataAcessObject> T copy();
}
