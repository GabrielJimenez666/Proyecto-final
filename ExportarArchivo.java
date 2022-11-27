
package Parejas_cercana_Lista_enlazadas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExportarArchivo {
     private static void crear(String nombre)
    // crea un archivo con un nombre dado
    {
        try {
            // define el nombre del archivo
            String fname = (nombre);
            // crea un nuevo objeto de archivo
            File f = new File(fname);

            String msg = "creating file `" + fname + "' ... ";
            // crea el nuevo archivo
            f.createNewFile();

        } catch (IOException err) {
           // se queja si hay un error de entrada/salida
            err.printStackTrace();
        }

        return;
    }

    private static void escribir(String nombre, int tamano, ArrayList<Integer> es, ArrayList<Integer> comparaciones,
            ArrayList<Integer> Tiempo_recorrido)
    // escribe datos en un archivo con un nombre y tamaño determinados
    {
        try {
           // define el nombre del archivo
            String filename = (nombre);
            PrintWriter out = new PrintWriter(filename);
            String fmt = ("%10s %10s %10s\n");
            for (int i = 0; i < tamano; ++i) {
                out.printf(fmt, es.get(i), comparaciones.get(i), Tiempo_recorrido.get(i));
            }

            out.close(); // cierra el flujo de salida
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }

        return;
    }

    public void adherir(int tamano) { 
        ArrayList<Integer> TiempoProm = new ArrayList<Integer>();
        ArrayList<Integer> TotalCoords = new ArrayList<Integer>();
        ArrayList<Integer> Comparaciones = new ArrayList<Integer>(); 
        for (int i = 100; i < tamano; i = i * 3/2) {
            Parejas_cercana_Lista_enlazadas len = new Parejas_cercana_Lista_enlazadas();
            System.out.println(i);
            long total = 0;
            int count;
            CrearLista list = len.Initialize(i); // Crear una lista de coordenadas y ordenarlas por posición x.
            for (int j = 0; j < 256; j++) {
                long startTime = System.nanoTime();
                len.ClosestPair(i, list, 999999999);
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime; 
                total = total + totalTime;
            }
            count = len.getComparisons();
            count = count / 256;// Encontramos las comparaciones promedio por iteración y las almacenamos en una lista.
            total = total / 256;// Tiempo de ejecución promedio
            TiempoProm.add((int) total);
            TotalCoords.add(i);// Cantidad de coordenadas en la matriz
            Comparaciones.add(count);
        }
        crear("tiempos.txt");
        escribir("tiempos.txt", TotalCoords.size(), TotalCoords, Comparaciones, TiempoProm);
    }
}
