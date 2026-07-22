import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SalaCineTest {
    SalaCine sala;

    @BeforeEach
    void setUp(){
        sala = new SalaCine("Sala 1", 2);
        sala.agregarAsiento(new Asiento("A1", "VIP"));
    }

    @Test
    @DisplayName("Verificar una inserción válida.")
    void insercionValida(){
        sala.agregarAsiento(new Asiento("A2", "4D"));
        assertEquals(2, sala.contarDisponibles());
    }

    @Test
    @DisplayName("Verificar que no se pueda insertar asiento cuando la sala está llena.")
    void insercionInvalida(){
        sala.agregarAsiento(new Asiento("A2", "4D"));
        assertThrows(IllegalStateException.class, () -> sala.agregarAsiento(new Asiento("A3", "4D")));
    }

    @Test
    @DisplayName("Verificar que no se pueda insertar varios asientos con el mismo codigo.")
    void insercionMultipleInvalida(){
        assertThrows(IllegalArgumentException.class, () -> sala.agregarAsiento(new Asiento("A1", "4D")));
    }

    @Test
    @DisplayName("Verificar que se pueda buscar un asiento existente.")
    void busquedaValida(){
        Asiento asiento = sala.buscarAsiento("A1");
        assertEquals("A1", asiento.getCodigo());
    }

    @Test
    @DisplayName("Verificar que encuentre el ultimo asiento de la lista.")
    void encontrarUltimoAsiento(){
        sala.agregarAsiento(new Asiento("A2", "4D"));
        Asiento asiento = sala.buscarAsiento("A2");
        assertEquals("A2", asiento.getCodigo());
    }

    @Test
    @DisplayName("Verificar que no se pueda buscar un asiento inexistente.")
    void busquedaInvalida(){
        sala.agregarAsiento(new Asiento("A2", "4D"));
        assertThrows(NoSuchElementException.class, () -> sala.buscarAsiento("Z9"));
    }
}
