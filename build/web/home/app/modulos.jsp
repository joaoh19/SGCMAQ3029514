<%@page import="model.TipoUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    String usuarioLogado = (String) session.getAttribute("usuario");
    TipoUsuario tipoUsuarioLogado = (TipoUsuario) session.getAttribute("tipo_usuario");
    
    if( ( usuarioLogado == null ) | ( tipoUsuarioLogado == null ) ){
        
        response.sendRedirect( request.getContextPath() + "/home/login.jsp" );
        
    }
    

%>

        <h1>Menu</h1>
        <menu>
            <li><a href="<%= request.getContextPath() %>/home/app/menu.jsp">Menu</a></li>
            
            <%
            
            if( tipoUsuarioLogado.getModuloAdministrativo().equals("S") ){%>
            <li><a href="<%= request.getContextPath() %>/home/app/tipousuario.jsp">Tipo Usuario </a></li>
            <li><a href="<%= request.getContextPath() %>/home/app/usuario.jsp">Usuarios </a></li>
            
            <%} %>
            
            
            <li><a href="<%= request.getContextPath() %>/home?task=logout">Logout <%= session.getAttribute("usuario") %> </a></li>
        </menu>
