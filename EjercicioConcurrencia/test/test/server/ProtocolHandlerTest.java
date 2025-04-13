package test.server;

import server.ProtocolHandler;
import server.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.Socket;

public class ProtocolHandlerTest {

    private ProtocolHandler protocolHandler;
    private UserManager userManager;
    private Socket dummySocket;

    @BeforeEach
    public void setUp() {
        protocolHandler = new ProtocolHandler();
        userManager = new UserManager();
        dummySocket = new Socket();
    }

    @Test
    public void testLoginConUsuarioValido() {
        String input = "/login Juan";
        String respuesta = protocolHandler.procesarInput(input, userManager, dummySocket);
        assertTrue(respuesta.contains("Te has logueado"), "El login debería ser exitoso.");
    }

    @Test
    public void testLoginSinNombre() {
        String input = "/login";
        String respuesta = protocolHandler.procesarInput(input, userManager, dummySocket);
        assertTrue(respuesta.contains("Error"), "Debería mostrar error si no hay nombre.");
    }

    @Test
    public void testComandoInvalido() {
        String input = "/comandoInexistente";
        String respuesta = protocolHandler.procesarInput(input, userManager, dummySocket);
        assertNull(respuesta, "Debería retornar null si es un comando desconocido.");
    }
}
