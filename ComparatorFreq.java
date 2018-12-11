import eped.*;
public class ComparatorFreq extends ComparatorBase<Query> {

    /**
     * Comparar dos listas
     * Funcion que compara si un numero es mayor que otro
     * @param q1 Primera consulta a comparar
     * @param q2 Segunda consulta a comparar
     * @return LESS -1, EQUAL 0, GREATER 1 
     */
    @Override
    public int compare(Query q1, Query q2) {
        int value = 0;
        if (q1.getFreq() != 0 && q2.getFreq() != 0) {
            if (q1.getFreq() < q2.getFreq()) value = LESS;
            else if (q1.getFreq() == q2.getFreq()) {
                int result = q1.getText().compareTo(q2.getText());
                if (result<0) value = GREATER;
                else value = LESS;
            }
            else if (q1.getFreq() > q2.getFreq()) value = GREATER;
        }
        return value; 
    }
}
