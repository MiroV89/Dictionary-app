import eped.*;

public class Query {
    private String consulta;
    private int frecuencia;
    /* Construye una nueva query con el texto pasado como parámetro */
    public Query (String text)
    {
        this.consulta = text;
        frecuencia = 1;
    }
    /* Modifica la frecuencia de la query */
    public void setFreq(int freq)
    {
        this.frecuencia = freq;
    }
    /* Devuelve el texto de una query */
    public String getText()
    {
        return consulta;
    }
    /* Devuelve la frecuencia de una query */
    public int getFreq()
    {
        return frecuencia;
    }
}
