package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioTeste {
    public static void main(String[] args) throws SQLException {
        
        // Criar uma instância de Usuario
        Usuario usuario = new Usuario();
        
        // TESTE 1: Criar e salvar um novo usuário
        System.out.println("=== TESTE 1: Criar novo usuário ===");
        usuario.setId(100);
        usuario.setNome("João Silva");
        usuario.setCpf("123.456.789-00");
        usuario.setSenha("senha123");
        usuario.setTipoUsuarioId(1); // ID do tipo de usuário
        
        usuario.save(); // INSERT
        System.out.println("Usuário salvo: " + usuario);
        System.out.println();
        
        // TESTE 2: Carregar um usuário existente
        System.out.println("=== TESTE 2: Carregar usuário existente ===");
        Usuario usuario2 = new Usuario();
        usuario2.setId(100);
        
        boolean encontrado = usuario2.load(); // SELECT
        System.out.println("Usuário encontrado: " + encontrado);
        if (encontrado) {
            System.out.println("Dados do usuário: " + usuario2);
        }
        System.out.println();
        
        // TESTE 3: Atualizar um usuário
        System.out.println("=== TESTE 3: Atualizar usuário ===");
        usuario2.setNome("João Silva Santos"); // Alterar o nome
        usuario2.setSenha("novaSenha456"); // Alterar a senha
        
        usuario2.save(); // UPDATE
        System.out.println("Usuário atualizado: " + usuario2);
        System.out.println();
        
        // TESTE 4: Buscar todos os usuários
        System.out.println("=== TESTE 4: Listar todos os usuários ===");
        ArrayList<Usuario> listaUsuarios = new Usuario().getAllTableEntities();
        System.out.println("Total de usuários: " + listaUsuarios.size());
        for (Usuario u : listaUsuarios) {
            System.out.println(u);
        }
        System.out.println();
        
        // TESTE 5: Excluir um usuário
        System.out.println("=== TESTE 5: Excluir usuário ===");
        usuario2.delete(); // DELETE
        System.out.println("Usuário excluído com sucesso!");
        
        // Verificar se realmente foi excluído
        Usuario usuario3 = new Usuario();
        usuario3.setId(100);
        boolean aindaExiste = usuario3.load();
        System.out.println("Usuário ainda existe? " + aindaExiste);
    }
}