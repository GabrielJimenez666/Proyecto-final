package Parejas_cercana_Lista_enlazadas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
// Gabriel Jimenez - Codigo: 200151557

public class Parejas_cercana_Lista_enlazadas {

    private static int contador; // Cuenta el número de comparaciones.

    public Parejas_cercana_Lista_enlazadas() {
        contador = 0;
    }

    public static double[] Fuerza_Bruta(CrearLista coords, double dmin) { // Encuentra el par más cercano a través del algoritmo de fuerza bruta.
        double distancia_min = dmin;
        double[] DM = new double[3];// Matriz para almacenar distancia minima y el índice de puntos con el par más cercano.
        DM[0] = distancia_min;
        for (int i = 0; i < coords.tamano; i++) {
            for (int j = i + 1; j < coords.tamano; j++) {
                double d = distancia(coords, i, j); // Compara la distancia entre cada par de coordenadas y devuelve su distancia.
                if (d < distancia_min) {
                    contador++;
                    distancia_min = d;
                    DM[0] = d;
                    DM[1] = coords.get(i).p.pos;
                    DM[2] = coords.get(j).p.pos;
                } else {
                    contador++;
                }
            }
        }
        return DM;
    }

    public int getComparisons() {
        return contador;
    }

    public static void Printc(List<int[]> coords) { 
        for (int i = 0; i < coords.size(); i++) {
            System.out.println("x: " + coords.get(i)[0] + " y: " + coords.get(i)[1] + " pos: " + coords.get(i)[2]);
        }
    }

    public static CrearLista Lcandidatos(CrearLista coords, double min) {
        ArrayList<Punto> candidatos = new ArrayList<Punto>();
        int i = 0;
        while (i < coords.tamano / 2) { // Compara la distancia en las posiciones x e y.
            if (Math.abs(coords.get(i).p.x - coords.get(coords.tamano / 2).p.x) < min && Math.abs(coords.get(i).p.y - coords.get(coords.tamano / 2).p.y) < min) {
                contador++;
                candidatos.add(coords.get(i).p); 
                i++;
            } else {
                contador++;
                i = coords.tamano / 2;
            }
        }
        while (i < coords.tamano) { 
            if (Math.abs(coords.get(i).p.x - coords.get(coords.tamano / 2 - 1).p.x) < min
                    && Math.abs(coords.get(i).p.y - coords.get(coords.tamano / 2 - 1).p.y) < min) {
                contador++;
                candidatos.add(coords.get(i).p);
                i++;
            } else {
                contador++;
                i = coords.tamano;
            }
        }
        CrearLista listc;
        if (candidatos.isEmpty()) {
            listc = new CrearLista(null, candidatos.size());
        } else {
            listc = new CrearLista(new CrearNodo(candidatos.get(0)), candidatos.size());
            CrearNodo P = listc.principio;
            for (int j = 1; j < candidatos.size(); j++) {
                P.siguiente = new CrearNodo(candidatos.get(j));
                P = P.siguiente;
            }
        }
        return listc;
    }

    public static double[] ClosestPair(int N, CrearLista x, double distanciamins) {
        // Usa la recursividad para encontrar el par más cercano de un conjunto dado de coordenadas a través del algoritmo de fuerza bruta.
        if (N > 3) { 
            double[] g1 = new double[3];
            double[] g2 = new double[3];
            int offset = 0;
            contador++;
            if (N % 2 == 1) {
                offset = 1;

            }
            // Divide las coordenadas por la mitad hasta que solo haya 3 o menos coordenadas, luego usamos el algoritmo en ambos lados y comparamos.
            CrearLista list2 = x.subList(N / 2, N, N / 2);
            CrearLista list1 = x.subList(0, N / 2, N / 2);
            g1 = ClosestPair(N / 2, list1, distanciamins);
            g2 = ClosestPair(N / 2, list2, distanciamins);
            double[] g = new double[3];
            if (g1[0] < g2[0]) { 
                g = g1;
                contador++;
            } else {
                contador++;
                g = g2;
            }
            CrearLista candidatos = Lcandidatos(x, g[0]); // Lista para almacenar posibles candidatos luego aplicamos el algoritmo de Fuerza Bruta.
            if (candidatos.tamano > 1) {
                contador++;
                g1 = Fuerza_Bruta(candidatos, distanciamins);
                if (g1[0] < g[0]) {
                    contador++;
                    return g1; 
                } else {
                    contador++;
                    return g;
                }
            } else {
                contador++;
                return g;
            }
        } else {
            contador++;
            double[] vec = new double[3];
            return Fuerza_Bruta(x, distanciamins); // Aplicar el algoritmo Fuerza_Bruta.
        }
    }

    public static double distancia(CrearLista coords, int i, int j) { // calcula la distancia entre los elementos i-ésimo y j-ésimo
        int x1 = coords.get(i).p.x;
        int x2 = coords.get(j).p.x;
        int y1 = coords.get(i).p.y;
        int y2 = coords.get(j).p.y;
        double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        return d;
    }

    public static CrearLista Initialize(int size) { // Creando un conjunto de coordenadas aleatorias en una Lista Enlazada y ordenándola.
        Random r = new Random();
        CrearNodo P = null;
        CrearNodo PTR = null;
        for (int i = 0; i < size; i++) {
            Punto temp = new Punto(r.nextInt(10000), r.nextInt(10000), i);
            CrearNodo tempn = new CrearNodo(temp);
            if (i == 0) {
                PTR = tempn;
                P = PTR;
            } else {
                P.siguiente = tempn;
                P = P.siguiente;
            }
        }
        OrganizarLista(PTR);
        CrearLista list = new CrearLista(PTR, size);
        P = list.principio;
        while (P.siguiente != null) {
            P = P.siguiente;
        }
        return list;
    }

    private static void OrganizarLista(CrearNodo PTR) { // Ordena las listas enlazadas.
        CrearNodo P = PTR;
        int tm = 0;
        while (P != null) {
            tm++;
            P = P.siguiente;
        }
        P = PTR;
        for (int i = 0; i < tm; i++) {
            int min = P.p.x;
            CrearNodo P2 = P.siguiente;
            while (P2 != null) {
                if (P2.p.x < min) {
                    Punto temp = P2.p;
                    P2.p = P.p;
                    P.p = temp;
                    min = P.p.x;
                }
                P2 = P2.siguiente;
            }
            P = P.siguiente;
        }
    }

    public static void main(String[] args) {
        ExportarArchivo FTC = new ExportarArchivo();
        FTC.adherir(100000);
    }

}
