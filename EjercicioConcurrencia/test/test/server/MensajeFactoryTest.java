package test.server;

import mssg.Mensaje;
import mssg.MensajeFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MensajeFactoryTest {

    @Test
    public void testCrearMensajeTexto() {
        Mensaje mensaje = MensajeFactory.crearMensaje("texto", "Juan");
        assertEquals("Juan: Hola mundo!", mensaje.construirMensaje());
    }

    @Test
    public void testCrearMensajeAlerta() {
        Mensaje mensaje = MensajeFactory.crearMensaje("alerta", "Sistema");
        assertEquals("[ALERTA] Sistema: Servidor se reiniciará.", mensaje.construirMensaje());
    }

    @Test
    public void testCrearMensajeNotificacion() {
        Mensaje mensaje = MensajeFactory.crearMensaje("notificacion", "Bot");
        assertEquals("[NOTIFICACIÓN] Bot: Hay un nuevo usuario conectado.", mensaje.construirMensaje());
    }

    @Test
    public void testTipoDesconocido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MensajeFactory.crearMensaje("desconocido", "X");
        });
        assertTrue(exception.getMessage().contains("Tipo de mensaje desconocido"));
    }
}
