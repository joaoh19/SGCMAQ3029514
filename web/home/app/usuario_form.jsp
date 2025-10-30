<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Usuário</title>
    </head>
    <body>
        
        <%@include file="/home/app/modulos.jsp" %>
        
        <%
            Usuario usuario = null;
            String action = request.getParameter("action");
            if (action == null) {
                action = "create";
            } else if (action.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                usuario = new Usuario();
                usuario.setId(id);
                usuario.load();
            }
        %>

        <h1>Cadastro de Usuário</h1>

        <form action="<%= request.getContextPath() %>/home?action=<%= action %>&task=usuario" method="post">

            <label for="id">ID:</label>
            <input type="text" id="id" name="id" pattern="\d+" title="Apenas números"
                   value="<%= (usuario != null) ? usuario.getId() : "" %>"
                   <%= (usuario != null) ? "readonly" : "" %> required><br/><br/>

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome"
                   value="<%= (usuario != null && usuario.getNome() != null) ? usuario.getNome() : "" %>"
                   required><br/><br/>

            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" title="Formato: 000.000.000-00"
                   value="<%= (usuario != null && usuario.getCpf() != null) ? usuario.getCpf() : "" %>"
                   ><br/><br/>

            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha"
                   value="<%= (usuario != null && usuario.getSenha() != null) ? usuario.getSenha() : "" %>"
                   required><br/><br/>
            
            <label for="endereco">CEP:</label>
            <input type="text" id="cep" name="cep" pattern="\d{5}-\d{3}" title="">
                   <br/><br/>
            
            <label for="endereco">Endereço:</label>
            <textarea type="text" id="text" name="endereco" rows="4" cols="50">"<%= (usuario != null && usuario.getEndereco()!= null) ? usuario.getEndereco(): ""%>"
                   </textarea><br/><br/>

            <label for="tipoUsuarioId">Tipo de Usuário (ID):</label>
            <input type="text" id="tipoUsuarioId" name="tipoUsuarioId" pattern="\d+" title="Apenas números"
                   value="<%= (usuario != null) ? usuario.getTipoUsuarioId() : "" %>"
                   required><br/><br/>

            <input type="submit" value="Salvar">
        </form>
    </body>
</html>
