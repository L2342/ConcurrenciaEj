# ConcurrenciaEj
Desarrollar el Taller de Concurrencia en Equipos de 3 personas, plazo máximo el sabado a media noche gracias, todos deben entregar el taller

Taller

Nuevo Protocolo de Comunicación
Diseña un protocolo simple para el intercambio de mensajes entre cliente y servidor (por ejemplo, comandos como /login usuario o /msg texto).

Servidor Multicliente
Crea un servidor capaz de aceptar y manejar múltiples conexiones simultáneamente (usando hilos).

Cliente Interactivo
Crea un cliente con una ventana gráfica (JFrame) que permita ver mensajes en tiempo real y enviar mensajes desde un campo de texto.

Implementar el Patrón Singleton
El servidor debe implementar el patrón Singleton para asegurar que sólo haya una instancia activa del mismo.

Aplicar Factory Method
Usa Factory Method para crear diferentes tipos de mensajes enviados (texto, alerta, notificación).

Broadcast Mejorado
El servidor debe poder enviar un mensaje a todos los clientes conectados.

Cliente con Login
Al iniciar, el cliente debe solicitar un nombre de usuario único antes de permitir enviar mensajes.

Servidor con Lista de Usuarios Conectados
El servidor debe mantener una lista actualizada de usuarios conectados y enviarla a todos los clientes cuando alguien entre o salga.

Implementar el Patrón Observer
Usa Observer para actualizar dinámicamente la lista de usuarios en el cliente.

Validaciones OWASP
Implementa validaciones para proteger de inyección de comandos (por ejemplo, filtrar caracteres peligrosos como ;, <, >).

Código Limpio (Clean Code)
Sigue las reglas de Clean Code: nombres claros, funciones cortas, mínima duplicación de código.

Manejo de Errores y Excepciones
Implementa manejo de excepciones adecuado para conexiones caídas, entradas inválidas, etc.

Implementar una Funcionalidad Adicional Creativa
Agrega algo extra: un sistema de emojis, envío de archivos pequeños, comandos de color de texto, o mensajes privados.

Pruebas Unitarias (JUnit 5)
Crea al menos dos pruebas unitarias (por ejemplo: prueba de envío de mensaje o conexión al servidor).

Entrega de Reporte de Calidad
Analiza tu código con SonarQube o una revisión manual de calidad: identifica 3 posibles mejoras.
