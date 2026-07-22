import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertEquals("VIP", asiento.getTipo());
    }

    @Test
    @DisplayName("Constructor crea asiento inválido")
    void constructorInvalidoException(){
        assertThrows(IllegalArgumentException.class, 
            () -> new Asiento("","VIP")
        );
    }

    //ocupar()
    //prueba que un asiento libre puede ocuparse correctamente(caso normal)
    @Test
    @DisplayName("Asiento libre puede ocuparse correctamente")
    void ocuparAsientoLibre() {
    Asiento asientoLibre = new Asiento("B1", "VIP");

    assertFalse(asientoLibre.isOcupado());

    asientoLibre.ocupar();

    assertTrue(asientoLibre.isOcupado());
    }
    // prueba que un asiento ocupado no puede ocuparse nuevamente y lanza la excepción esperada(Caso limite)
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

}
