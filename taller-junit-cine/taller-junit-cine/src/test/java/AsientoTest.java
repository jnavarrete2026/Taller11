import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {
    Asiento asiento;

    @BeforeEach
    void setUp(){
        asiento=new Asiento("A1","VIP");
    }

    @Test
    @DisplayName("Constructor crea asiento válido")
    void constructorValido(){
        assertFalse(asiento.isOcupado());
        assertEquals("A1",asiento.getCodigo());
    }
    //ocupar()
    @Test
    @DisplayName("Asiento libre puede ocuparse correctamente")
    void ocuparAsientoLibre() {
    Asiento asientoLibre = new Asiento("B1", "VIP");

    assertFalse(asientoLibre.isOcupado());

    asientoLibre.ocupar();

    assertTrue(asientoLibre.isOcupado());
    }
    @Test
    @DisplayName("No se puede ocupar un asiento ya ocupado")
    void ocuparAsientoYaOcupado() {
    Asiento asiento = new Asiento("C1", "VIP");
    asiento.ocupar();
    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> asiento.ocupar()
    );

    assertEquals("El asiento C1 ya está ocupado", exception.getMessage());
    }

    // TODO: Traducir los demás casos de su tabla a métodos @Test.
}
