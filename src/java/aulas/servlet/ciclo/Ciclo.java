package aulas.servlet.ciclo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

//classe que faz requisiçoões http | (sloicitação e resposta) | osss
@WebServlet(name = "Ciclo", urlPatterns = {"/aulas/servlet/ciclo"})
public class Ciclo extends HttpServlet {

    @Override
    public void init() throws ServletException {
        /*
        Inicialização (Servlet) dos recursos.
        
        Cria / atribui as variáveis globais(como conexão a banco de dados).
        Leitura dos padrões iniciais
        */
        super.init(); 
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          /*
        Recebimento de requisições HTTp enquanto o servlet estiver ativo.
        
        super.service(...)encaminhaas requisições aos métodos HTTP implementados       
        */
        //super.service(req, resp);
        System.out.println("\n---- HeaderNames Request ----\n");
        Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                System.out.println(name + " : " + request.getHeader(name));
            }
        System.out.println("\n---- HeaderNames Request ----\n");
        
        
        String html = "<!DOCTYPE html>";
        
            html += "<html>";
            html += "<head>";
            html += "<title>Servlet Ciclo de vida</title>";
            html += "</head>";
            html += "<body>";
            html += "<h1>Ciclo de vida Servlet</h1>";
            html += "</body>";
            html += "</html>";
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(html);
            pw.close();
            
            
            System.out.println("\n---- HeaderNames Response ----\n");
                for(String name : response.getHeaderNames()){
              System.out.println(name + " : " + response.getHeader(name));
            }
             System.out.println("\n---- HeaderNames Response ----\n");
    }
    
      @Override
    public void destroy() {
         /*
        Encerramento da Servlet, liberação dos recursos.
        
        Chamado quando o Servlet for encerrado,utilizado para  liberação de recursos (como conexão a banco de dados).
        */
        super.destroy(); 
    }
}
