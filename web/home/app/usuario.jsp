<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usario</title>
    </head>
    <body>
        
        <%@include file="/home/app/modulos.jsp" %>
        
        
        <%ArrayList<Usuario> dados = new Usuario().getAllTableEntities();%>
        
        <h1>Cadastro Usuario</h1>
        <table>
            
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Cpf</th>
                <th>Tipo Usuario(Id)</th>
                <th></th>
                <th></th>           
            </tr> 
            
            <% for(Usuario us : dados) { %>
            <tr>
                <td><%= us.getId() %> </td>
                <td><%= us.getNome() %> </td>
                <td><%= us.getCpf() %> </td>
                <td><%= us.getTipoUsuarioId()%> </td>
                <td> <a href="<%= request.getContextPath()%>/home/app/usuario_form.jsp?action=update&id=<%=us.getId()%>">Alterar</a> </td>
                <td> <a href="<%=request.getContextPath()%>/home?action=delete&id=<%=us.getId()%>&task=usuario" onclick="return confirm('Deseja realmente excluir Tipo Usuario \n\
                        <%=us.getId()%>  (<%=us.getNome()%> )?')">Excluir</a> </td>
            </tr>
            <%}%>
            
        </table>
        
        <a href="<%= request.getContextPath()%>/home/app/usuario_form.jsp?action=create">Adicionar</a>
        
    </body>
</html>
