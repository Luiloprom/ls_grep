package es.etg.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Comando implements Proceso {

    public static final String MSG_ERROR = "Ha ocurrido un error en el proceso : ";

    private String[] comando;

    public Comando(String[] comando){
        this.comando = comando;
    }

    @Override
    public String lanzar(String entrada) throws Exception {
        Process a = Runtime.getRuntime().exec(comando);
        if (entrada != null) {
            escribir(a, entrada);
        }
        String salida = leer(a);
        String error = MSG_ERROR + Arrays.toString(comando);
        return (a.waitFor() == 0) ? salida : error;
    }

    public static void escribir(Process p, String contenido) throws Exception {
        OutputStream out = p.getOutputStream();
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(out))) {
            for (String linea : contenido.split("\n")) {
                pw.println(linea);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static String leer(Process p) throws Exception {
        StringBuilder sb = new StringBuilder();
        String linea;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }
}
