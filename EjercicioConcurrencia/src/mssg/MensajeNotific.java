package mssg;

public class MensajeNotific implements Mensaje {
    private final String contenido;

    public MensajeNotific(String contenido) {
        this.contenido = contenido;
    }

    @Override

    public String construirMensaje() {
        return "🔔 " + contenido;
    }
}

