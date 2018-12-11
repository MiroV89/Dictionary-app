import eped.*;
public class ComparatorString extends ComparatorBase<Query> {

    /**
     * Comparar dos listas
     * Funcion que compara si un numero es mayor que otro
     * @param q1 Primera consulta a comparar
     * @param q2 Segunda consulta a comparar
     * No se da el caso de EQUAL, puesto que si existiera otra cadena igual
     * se le incrementa la frequencia.
     * @return LESS -1, GREATER 1 
     */
    @Override
    public int compare(Query q1, Query q2) {
        int value = 0;
        if (!q1.getText().equals("") && !q2.getText().equals("")) {
            int result = q1.getText().compareTo(q2.getText());
            if (result<0) value = GREATER;
            else value = LESS;
        }
        return value; 
    }
}
