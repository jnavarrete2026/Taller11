// Ver en enunciado: copie aquí las clases Asiento, SalaCine, NoSuchElementException y ReservaService.
import java.util.ArrayList;
import java.util.List;

// ========================================================
// Clase 1: Asiento
// Representa un asiento individual de la sala.
// ========================================================
class Asiento {
    private String codigo;       // Ej: "A1", "B5"
    private String tipo;         // "ESTANDAR", "VIP", "4D"
    private boolean ocupado;

    public Asiento(String codigo, String tipo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del asiento no puede estar vacío");
        }

        if (!tipo.equals("ESTANDAR") && !tipo.equals("VIP") && !tipo.equals("4D")) {
            throw new IllegalArgumentException("Tipo de asiento inválido: " + tipo);
        }

        this.codigo = codigo;
        this.tipo = tipo;
        this.ocupado = false;
    }

    public double calcularPrecioBase() {
        switch (tipo) {
            case "ESTANDAR":
                return 5.0;

            case "VIP":
                return 8.5;

            case "4D":
                return 12.0;

            default:
                return 0.0;
        }
    }

    public void ocupar() {
        if (ocupado) {
            throw new IllegalStateException(
                "El asiento " + codigo + " ya está ocupado"
            );
        }

        ocupado = true;
    }

    public void liberar() {
        ocupado = false;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }
}

// ========================================================
// Clase 2: SalaCine
// Administra un conjunto de asientos y permite reservarlos.
// ========================================================
class SalaCine {
    private String nombre;
    private List<Asiento> asientos;
    private int capacidadMaxima;

    public SalaCine(String nombre, int capacidadMaxima) {
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException(
                "La capacidad debe ser mayor a 0"
            );
        }

        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.asientos = new ArrayList<>();
    }

    public void agregarAsiento(Asiento asiento) {
        if (asientos.size() >= capacidadMaxima) {
            throw new IllegalStateException(
                "La sala ha alcanzado su capacidad máxima"
            );
        }

        for (Asiento a : asientos) {
            if (a.getCodigo().equals(asiento.getCodigo())) {
                throw new IllegalArgumentException(
                    "Ya existe un asiento con el código "
                    + asiento.getCodigo()
                );
            }
        }

        asientos.add(asiento);
    }

    public Asiento buscarAsiento(String codigo) {
        for (Asiento a : asientos) {
            if (a.getCodigo().equals(codigo)) {
                return a;
            }
        }

        throw new NoSuchElementException(
            "Asiento no encontrado: " + codigo
        );
    }

    public int contarDisponibles() {
        int contador = 0;

        for (Asiento a : asientos) {
            if (!a.isOcupado()) {
                contador++;
            }
        }

        return contador;
    }

    public double calcularIngresoTotal() {
        double total = 0.0;

        for (Asiento a : asientos) {
            if (a.isOcupado()) {
                total += a.calcularPrecioBase();
            }
        }

        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}

// Excepción simple usada por SalaCine
class NoSuchElementException extends RuntimeException {

    public NoSuchElementException(String mensaje) {
        super(mensaje);
    }
}

// ========================================================
// Clase 3: ReservaService
// Orquesta el proceso de reserva, aplicando reglas de negocio
// como descuentos y límites por compra.
// ========================================================
class ReservaService {
    private static final int MAX_ASIENTOS_POR_COMPRA = 6;

    private SalaCine sala;

    public ReservaService(SalaCine sala) {
        this.sala = sala;
    }

    public double reservarAsientos(List<String> codigosAsientos) {
        if (codigosAsientos == null || codigosAsientos.isEmpty()) {
            throw new IllegalArgumentException(
                "Debe indicar al menos un asiento"
            );
        }

        if (codigosAsientos.size() > MAX_ASIENTOS_POR_COMPRA) {
            throw new IllegalArgumentException(
                "No se pueden reservar más de "
                + MAX_ASIENTOS_POR_COMPRA
                + " asientos por compra"
            );
        }

        double subtotal = 0.0;
        List<Asiento> reservados = new ArrayList<>();

        for (String codigo : codigosAsientos) {
            Asiento asiento = sala.buscarAsiento(codigo);
            asiento.ocupar();
            reservados.add(asiento);
            subtotal += asiento.calcularPrecioBase();
        }

        return aplicarDescuento(subtotal, reservados.size());
    }

    private double aplicarDescuento(
            double subtotal,
            int cantidadAsientos) {

        double descuento = 0.0;

        if (cantidadAsientos >= 4) {
            descuento = 0.15; // 15% para grupos grandes
        } else if (cantidadAsientos >= 2) {
            descuento = 0.05; // 5% para pares/tríos
        }

        return subtotal - (subtotal * descuento);
    }
}