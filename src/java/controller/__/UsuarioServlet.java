package controller.__;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.Usuario;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/home/usuario"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // DELETE
        if (action != null && action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = new Usuario();
            usuario.setId(id);
            

            try {
                usuario.delete();
            } catch (Exception ex) {
                ExceptionLogTrack.getInstance().addLog(ex);
            }
        }

        // Redireciona para a listagem
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        int tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuarioId"));

        try {
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

        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }

        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }
}
