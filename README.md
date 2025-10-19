# EJERCICIO PROCESOS – LS Y GREP  

Repositorio: [https://github.com/Luiloprom/ejer_procesos.git](https://github.com/Luiloprom/ejer_procesos.git)

## Índice  

- [Main](#main)
  - [Métodos en Main.java](#métodos)
    - [lanzar](#método-lanzar)
    - [escribir](#método-escribir)
    - [leer](#método-leer)
    - [main](#método-main)
  - [Constantes](#constantes)
- [Test con JUnit](#test-con-junit)
  - [testLanzar](#testlanzar)
  - [testEscribir](#testescribir)
  - [testLeer](#testleer)

---

### Main

En mi archivo **App.java** tengo tres métodos principales más el método **main**.

#### Métodos  

- **Método lanzar** :  
    ```java
        public static String lanzar(String[] comando, String entrada) throws Exception {
            Process a = Runtime.getRuntime().exec(comando);
            if (entrada != null) {
                escribir(a, entrada);
            }
            String salida = leer(a);
            String error = MSG_ERROR + Arrays.toString(comando);
            return (a.waitFor() == 0) ? salida : error;
        }
    ```
    > Este método lanza un proceso con un comando y una posible entrada.
    > En caso de que la entrada no sea null, escribira.  
    > Si el proceso finaliza correctamente (`exit code == 0`), devuelve su salida.  
    > En caso contrario, devuelve un mensaje de error definido por la constante `MSG_ERROR`.

---

- **Método escribir** :  
    ```java
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
    ```
    > Este método escribe líneas en el flujo de salida del padre.

---

- **Método leer** :  
    ```java
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
    ```
    > Este método lee la salida del hijo.

---

- **Método main** :  
    ```java
        public static void main(String[] args) throws Exception {
            String contenido = lanzar(COMANDO_1, null);
            String resultado = lanzar(COMANDO_2, contenido);
            System.out.println(resultado);
        }
    ```
    > En este `main`, se lanza un `ls` y su salida se pasa como entrada al comando `grep a`.  
    > Finalmente, se imprime el resultado del `grep` o un mensaje de error en caso de fallo.

---

### Constantes  

```java
public static final String[] COMANDO_1 = { "ls" };
public static final String[] COMANDO_2 = { "grep", "a" };
public static final String MSG_ERROR = "Ha ocurrido un error en el proceso : ";
```

---

### Test con JUnit  

Este test verifica el correcto funcionamiento de los métodos definidos en `App.java`.

- Test **testLanzarSinEntrada**
```java
    @Test
    public void testLanzarSinEntrada() throws Exception {
        String[] comando = { "echo", "hola buenas" };
        String salida = App.lanzar(comando, null);
        assertEquals(salida.trim(), "hola buenas");
    }
```
> Uso `trim` porque `echo` mete siempre un `\n` automatico.

---

- Test **testLanzarConEntrada**
```java
    @Test
    public void testLanzarConEntrada() throws Exception {
        String[] comando = { "grep", "a" };
        String entrada = "hola \n coche \n cara";
        String salida = App.lanzar(comando, entrada);
        assertTrue(salida.contains("hola") && salida.contains("cara"));
    }
```

---

- Test **testLanzarError**
```java
@Test
public void testLanzarError() throws Exception {
    String[] comando = { "grep", "a" };
    String entrada = "coche";
    String salida = App.lanzar(comando, entrada);
    assertTrue(salida.contains(App.MSG_ERROR));
}
```
> Comprueba que el metodo lanzar devuelve el mensaje de error corecto

---

- Test **testEscribir**
```java
    @Test
    public void testEscribir() throws Exception {
        Process p = Runtime.getRuntime().exec("cat");
        String entrada = "hola";
        App.escribir(p, entrada);
        String salida = App.leer(p);
        assertTrue(salida.contains("hola"));
    }
```

---

- Test **testLeer**
```java
    @Test
    public void testLeer() throws Exception {
        String[] comando = { "echo", "hola\nbuenas" };
        Process p = Runtime.getRuntime().exec(comando);
        String salida = App.leer(p);
        assertTrue(salida.contains("hola") && salida.contains("buenas"));
    }
```

