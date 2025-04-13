package test.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.UserManager;

import java.net.Socket;
import java.io.IOException;

public class UserManagerTest {

    @Test
    public void testAgregarUsuarioCorrecto() throws IOException {
        UserManager userManager = new UserManager();
        Socket dummySocket = new Socket();

        boolean resultado = userManager.agregarUsuario("usuarioPrueba", dummySocket);

        assertTrue(resultado, "El usuario debería agregarse correctamente.");
        dummySocket.close();  // Cerramos para evitar sockets colgados
    }

    @Test
    public void testAgregarUsuarioDuplicado() throws IOException {
        UserManager userManager = new UserManager();
        Socket socket1 = new Socket();
        Socket socket2 = new Socket();

        userManager.agregarUsuario("usuarioPrueba", socket1);
        boolean resultadoDuplicado = userManager.agregarUsuario("usuarioPrueba", socket2);

        assertFalse(resultadoDuplicado, "No debería permitir usuarios duplicados.");
        socket1.close();
        socket2.close();
    }

    @Test
    public void testRemoverUsuarioPorSocket() throws IOException {
        UserManager userManager = new UserManager();
        Socket dummySocket = new Socket();

        userManager.agregarUsuario("usuarioPrueba", dummySocket);
        userManager.removerUsuarioPorSocket(dummySocket);

        assertFalse(userManager.getListaUsuarios().contains("usuarioPrueba"), "El usuario debería ser eliminado.");
        dummySocket.close();
    }
}
