<%@page import="model.TipoUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tipo Usuário</title>
    </head>
    <body>
        <% 
            TipoUsuario tp = null;
            
            String action = request.getParameter("action");
                if(action == null){
                   action = "create"; 
                } else{
                    if(action.equals("update") ){
                       int id = Integer.valueOf(request.getParameter("id") );
                       
                       tp = new TipoUsuario();
                       tp.setId(id);
                       tp.load();
                       
                    }
                }         
        %>
        <h1>Tipo Usuario</h1>
        <form action="<%= request.getContextPath()%>/home?action=<%= action %>&task=tipousuario" method="post">
            
            <label for="id">Id:</label>            
            <input type="text" id="id" name="id" pattern="\d+" title="apenas digitos" 
                   value="<%= (tp != null) ? tp.getId() : "" %>" <%= (tp != null) ? "readonly" : "" %> required><br/>
            
            <label for="id">Nome</label>
            <input type="text" id="nome" name="nome" 
                   value="<%= ( (tp != null) && (tp.getNome() != null) ) ? tp.getNome() : "" %>"><br/>
            
            <input type="checkbox" id="modulo_administrativo" name="modulo_administrativo" value="S"    
                   <%=  (tp != null) && (tp.getModuloAdministrativo().equals("S")) ? "checked" : "" %> >
            <label for="modulo_administrativo">Administrativo</label><br/>
            
            <input type="checkbox" id="modulo_atendimento" name="modulo_atendimento" value="S">
            <label for="modulo_atendimento">Atendimento</label><br/>
            
            <input type="checkbox" id="modulo_agendamento" name="modulo_agendamento" value="S">
            <label for="modulo_agendamento">Agendamento</label><br/>
            
            <input type="submit" name="Salvar" value="Salvar">
                      
        </form>
        
    </body>
</html>
