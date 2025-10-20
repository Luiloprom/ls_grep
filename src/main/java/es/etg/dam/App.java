package es.etg.dam;

public class App {

    public static final String[] COMANDO_1 = { "ls", };
    public static final String[] COMANDO_2 = { "grep", "a" };
    

    public static void main(String[] args) throws Exception {
        Proceso exec = new Comando(COMANDO_1);
        String salida = exec.lanzar(null);

        exec = new Comando(COMANDO_2);
        salida = exec.lanzar(salida);

        System.out.println(salida);
    }

}