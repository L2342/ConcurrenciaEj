package mssg;

public class MensajeAlerta implements Mensaje {
    private final String contenido;
    
    public MensajeAlerta(String contenido) {
        this.contenido = contenido;
    }
    
    @Override
    public String construirMensaje() {
        return "⚠ " + contenido; 
    }
}

