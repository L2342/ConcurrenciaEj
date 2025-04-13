package mssg;

public class MensajeTexto implements Mensaje {
    private final String contenido;

    public MensajeTexto(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String construirMensaje() {
        return contenido;
    }
}

