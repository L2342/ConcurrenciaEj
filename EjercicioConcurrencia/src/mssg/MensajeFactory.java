package mssg;

public class MensajeFactory {
    public static Mensaje crearMensaje(String tipo, String contenido) {
        return switch (tipo) {
            case "texto" -> new MensajeTexto(contenido);
            case "alerta" -> new MensajeAlerta(contenido);
            case "notificacion" -> new MensajeNotific(contenido);
            default -> throw new IllegalArgumentException("Tipo de mensaje desconocido");
        };
    }
}


