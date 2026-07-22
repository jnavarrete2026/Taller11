import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservaServiceTest {

    private SalaCine sala;
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {

        sala = new SalaCine("Sala 1", 7);

        sala.agregarAsiento(new Asiento("A1", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A2", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A3", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A4", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A5", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A6", "ESTANDAR"));
        sala.agregarAsiento(new Asiento("A7", "ESTANDAR"));

        reservaService = new ReservaService(sala);
    }

    @Test
    @DisplayName("CP-16: Reservar dos asientos correctamente")
    void reservarDosAsientos() {

        List<String> codigos = new ArrayList<>();
        codigos.add("A1");
        codigos.add("A2");

        double total = reservaService.reservarAsientos(codigos);

        assertEquals(9.5, total, 0.001);

        assertTrue(sala.buscarAsiento("A1").isOcupado());
        assertTrue(sala.buscarAsiento("A2").isOcupado());
        assertFalse(sala.buscarAsiento("A3").isOcupado());
    }

    @Test
    @DisplayName("CP-17: Reservar el máximo permitido de asientos")
    void reservarSeisAsientos() {

        List<String> codigos = new ArrayList<>();
        codigos.add("A1");
        codigos.add("A2");
        codigos.add("A3");
        codigos.add("A4");
        codigos.add("A5");
        codigos.add("A6");

        double total = reservaService.reservarAsientos(codigos);

        assertEquals(25.5, total, 0.001);

        assertTrue(sala.buscarAsiento("A1").isOcupado());
        assertTrue(sala.buscarAsiento("A2").isOcupado());
        assertTrue(sala.buscarAsiento("A3").isOcupado());
        assertTrue(sala.buscarAsiento("A4").isOcupado());
        assertTrue(sala.buscarAsiento("A5").isOcupado());
        assertTrue(sala.buscarAsiento("A6").isOcupado());

        assertFalse(sala.buscarAsiento("A7").isOcupado());
    }

    @Test
    @DisplayName("CP-18: Intentar reservar más de seis asientos")
    void reservarSieteAsientos() {

        List<String> codigos = new ArrayList<>();
        codigos.add("A1");
        codigos.add("A2");
        codigos.add("A3");
        codigos.add("A4");
        codigos.add("A5");
        codigos.add("A6");
        codigos.add("A7");

        assertThrows(
                IllegalArgumentException.class,
                () -> reservaService.reservarAsientos(codigos)
        );

        assertFalse(sala.buscarAsiento("A1").isOcupado());
        assertFalse(sala.buscarAsiento("A7").isOcupado());
    }

}