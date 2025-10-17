package es.etg.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {

    public static final String[] COMANDO_1 = { "ls" };
    public static final String[] COMANDO_2 = { "grep", "r" };

    public static void main(String[] args) throws Exception {
        String contenido = lanzar(COMANDO_1, null);
        String resultado = lanzar(COMANDO_2, contenido);
        System.out.println(resultado);
    }

    // Metodo para lanzar un proceso con una posible entrada 
    public static String lanzar(String[] comando, String entrada) throws Exception {
        Process a = Runtime.getRuntime().exec(comando);
        if (entrada != null){
            escribir(a, entrada);
        }
        return leer(a);
    }

    // Metodo para escribir en el OutStream del padre
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

    // Metodo para leer el input del padre
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

    // Metodo para esperar a que termine el proceso
    public static int esperar(Process p) throws Exception {
        int exitVal = p.waitFor();
        return exitVal;
    }
}