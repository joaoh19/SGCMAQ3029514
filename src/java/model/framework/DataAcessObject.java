package model.framework;

import java.util.HashMap;


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
    
    private void insert(){
        System.out.println("insert()");
    }
    private void update(){
        System.out.println("update()");
    }
             
    public void save(){
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
}
