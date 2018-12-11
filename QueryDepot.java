import eped.*;
public interface QueryDepot {
    /* Devuelve el número de consultas diferentes (sin contar repeticiones) */
    /* que hay almacenadas en el depósito */
    /* @returns el número de consultas diferentes almacenadas */
    public int numQueries ();
    /* Consulta la frecuencia de una consulta en el depósito */
    /* @returns la frecuencia de la consulta. Si no está, devolverá 0 */
    /* @param el texto de la consulta */
    public int getFreqQuery (String q);
    /* Dado un prefijo de consulta, devuelve una lista, ordenada por */
    /* frecuencias de mayor a menor, de todas las consultas almacenadas */
    /* en el depósito que comiencen por dicho prefijo */
    /* @returns la lista de consultas ordenada por frecuencias y orden */
    /* lexicográfico en caso de coincidencia de frecuencia */
    /* @param el prefijo */
    public ListIF<Query> listOfQueries (String prefix);
    /* Incrementa en uno la frecuencia de una consulta en el depósito */
    /* Si la consulta no existía en la estructura, la deberá añadir */
    /* @param el texto de la consulta */
    public void incFreqQuery (String q);
    /* Decrementa en uno la frecuencia de una consulta en el depósito*/
    /* Si la frecuencia decrementada resultase ser 0, deberá eliminar */
    /* la información referente a la consulta del depósito */
    /* @precondición la consulta debe estar ya en el depósito */
    /* @param el texto de la consulta */
    public void decFreqQuery (String q);
}
