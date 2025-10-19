package es.etg.dam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testLanzarSinEntrada() throws Exception {
        String[] comando = { "echo", "hola buenas" };
        String salida = App.lanzar(comando, null);
        assertEquals(salida.trim(), "hola buenas");
    }

    @Test
    public void testLanzarConEntrada() throws Exception {
        String[] comando = { "grep", "a" };
        String entrada = "hola \n coche \n cara";
        String salida = App.lanzar(comando, entrada);
        assertTrue(salida.contains("hola") && salida.contains("cara"));
    }

    @Test
    public void testLanzarError() throws Exception {
        String[] comando = { "grep", "a" };
        String entrada = "coche";
        String salida = App.lanzar(comando, entrada);
        assertTrue(salida.contains(App.MSG_ERROR));
    }

    @Test
    public void testEscribir() throws Exception {
        Process p = Runtime.getRuntime().exec("cat");
        String entrada = "hola";
        App.escribir(p, entrada);
        String salida = App.leer(p);
        assertTrue(salida.contains("hola"));
    }

    @Test
    public void testLeer() throws Exception {
        String[] comando = { "echo", "hola\nbuenas" };
        Process p = Runtime.getRuntime().exec(comando);
        String salida = App.leer(p);
        assertTrue(salida.contains("hola") && salida.contains("buenas"));
    }

}
