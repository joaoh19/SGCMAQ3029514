package model.framework;

import model.TipoUsuario;


import java.sql.SQLException;
import java.util.ArrayList;

public class __Exemplo {
    public static void main(String[] args) throws SQLException{
        
            
        
        
        TipoUsuario tp = new TipoUsuario();
       
        tp.setId(44);
        tp.setModuloAdministrativo("N");
        tp.setModuloAgendamento("N");
        tp.setModuloAtendimento("S");
       
     
        
        
        tp.save(); // update
        
        tp.setModuloAdministrativo("S");
        tp.setModuloAgendamento("S");
        
        tp.save(); // update


       
//          tp.setId(44);
//          boolean status = tp.load(); // select(read)
//          System.out.println(status);
//          System.out.println(tp);
//          
//          tp.setNome("tipo 44");
//          tp.save(); // update
//          
//          System.out.println(tp);
//          
//          tp.delete();
          
          ArrayList<TipoUsuario> lst = new TipoUsuario().getAllTableEntities();
          System.out.println( lst );
          
          
        
    }
    
}
