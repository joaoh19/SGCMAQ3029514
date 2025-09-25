<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
            
        <form action="<%= request.getContextPath() %>/aulas/jsp/formulariojsp" method="post">

            <h1>Formulário</h1>
            <p>
                <label for="campoA"> Campo A: </label>
                <input type="text" id="campoA" name="campoA" pattern="\d+" title="apenas digitos" required/><br><br/>

                <input type="checkbox" id="opcaoA" name="opcaoA">
                <label for="opcaoA">OPção A </label><br><br/>

                <input type="checkbox" id="opcaoB" name="opcaoB">
                <label for="opcaoB">OPção B </label><br><br/>

                <input type="submit"  name="btEnviar" value="Salvar">
            </p>


        </form>
    </body>
</html>
