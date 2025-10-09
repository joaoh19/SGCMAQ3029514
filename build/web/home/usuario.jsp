<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tipo Usario</title>
    </head>
    <body>
        
        <%ArrayList<Usuario> dados = new Usuario().getAllTableEntities();%>
        
        <h1>Cadastro Usuario</h1>
        <table>
            
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Cpf</th>
                <th>Senha</th>
                <th>Tipo Usuario(Id)</th>
                <th></th>
                <th></th>           
            </tr> 
            
            
            
            <% for(Usuario tp : dados) { %>
            <tr>
                
                <td><%= tp.getId() %> </td>
                <td><%= tp.getNome() %> </td>
                <td><%= tp.getSenha()%> </td>
                <td><%= tp.getTipoUsuarioId()%> </td>
                
                <td> <a href="<%= request.getContextPath()%>/home/usuario_form.jsp?actin=update">Alterar</a> </td>
                <td> <a href="<%=request.getContextPath()%>/home/usuario?action=delete&id=<%=tp.getId()%>" onclick="return confirm('Deseja realmente excluir Tipo Usuario \n\
                        <%=tp.getId()%>  (<%=tp.getNome()%> )?')">Excluir</a> </td>
            </tr>
            <%}%>
            
        </table>
        
        
            <a href="<%= request.getContextPath()%>/home/usuario_form.jsp?action=create">Adicionar</a>
            
            <!--é possível fazer requisições por uma jsp, pois quando ela é executada ela tbm se torna uma servlet-->
        
    </body>
</html>
