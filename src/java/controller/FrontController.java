package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.TipoUsuario;
import model.Usuario;

@WebServlet(name = "FrontController", urlPatterns = {"/home"})
public class FrontController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {
            
            switch (task) {
                case "tipousuario": doGetTipoUsuario(request, response); break;
                
                case "usuario": doGetUsuario(request, response); break;
                
                default:
                    doDefault(request, response);
            }
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {
            
            switch (task) {
                case "tipousuario": doPostTipoUsuario(request, response); break;
                
                case "usuario": doPostUsuario(request, response); break;
                
                default:
                    doDefault(request, response);
            }
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
    }
    
      private void doGetTipoUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
          
          String action = request.getParameter("action");
            if((action != null) && action.equals("delete")){
                
                int id = Integer.valueOf(request.getParameter("id"));
                
                TipoUsuario tp = new TipoUsuario();
                tp.setId(id);
                
                
                tp.delete();
               
            }
            response.sendRedirect(request.getContextPath() +"/home/app/tipousuario.jsp");
    }
            
    
      private void doGetUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException , Exception{
          
          
        String action = request.getParameter("action");

        // DELETE
        if (action != null && action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = new Usuario();
            usuario.setId(id);
            

    
                usuario.delete();
           
        }

        // Redireciona para a listagem
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
       
    }
      
      
      private void doPostTipoUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException , Exception{
             
        String action = request.getParameter("action");
        
         int id = Integer.valueOf(request.getParameter("id"));
         
            String nome = request.getParameter("nome");
            
            String moduloAdministrativo = request.getParameter("modulo_administrativo");
            if(moduloAdministrativo == null){
                moduloAdministrativo = "N";
            }
            
            String moduloAgendamento = request.getParameter("modulo_agendamento");
            if(moduloAgendamento == null){
                moduloAgendamento = "N";
            }
            
            String moduloAtendimento = request.getParameter("modulo_atendimento");
            if(moduloAtendimento == null){
                moduloAtendimento = "N";
            }
        
         
        
        //Java Bean
        TipoUsuario tp = new TipoUsuario();
        
        tp.setId(id);
        
        if(action.equals("update") ) tp.load();
        
        tp.setNome(nome);
        tp.setModuloAdministrativo(moduloAdministrativo);
        tp.setModuloAgendamento(moduloAgendamento);
        tp.setModuloAtendimento(moduloAtendimento); 
        
            tp.save();
            
       
        
        response.sendRedirect(request.getContextPath() +"/home/app/tipousuario.jsp");
     
    }
      
      
      private void doPostUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception , Exception{
        String action = request.getParameter("action");

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        int tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuarioId"));

      
            Usuario usuario = new Usuario();
            usuario.setId(id);

            if ("update".equals(action)) {
                usuario.load(); // carrega os dados antigos antes de alterar
            }

            usuario.setNome(nome);
            usuario.setCpf(cpf);
            usuario.setSenha(senha);
            usuario.setTipoUsuarioId(tipoUsuarioId);

            usuario.save(); // DataAccessObject gerencia create/update automaticamente

        

        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }
   
      
    private void doDefault(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.sendRedirect("home/login.jsp");
    }
    
}
