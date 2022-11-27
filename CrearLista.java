
package Parejas_cercana_Lista_enlazadas;

public class CrearLista {
        CrearNodo principio;
    int tamano;
    
    public CrearLista(CrearNodo h, int s) { //Implementando nuestra Lista Enlazada con captadores.
        this.principio = h;
        this.tamano = s;
    }

    public CrearNodo get(int i) {
        if (i == 0) {
            return this.principio;
        } else {
            if (i < 0 || i > tamano) {

                return null;
            } else {
                CrearNodo P = this.principio;
                for (int j = 0; j < i; j++) {
                    P = P.siguiente;
                }
                return P;
            }
        }
    }

    public CrearLista subList(int i, int f, int tamano) { //Crea una sublista que va desde el elemento en diferentes posiciones.
        CrearLista lists = new CrearLista(new CrearNodo(this.get(i).p), tamano);
        CrearNodo P = lists.principio;
        for (int j = 1; j < tamano; j++) {
            P.siguiente = new CrearNodo(this.get(j).p);
            P = P.siguiente;
        }
        return lists;
    }
}
