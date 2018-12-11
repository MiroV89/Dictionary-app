import eped.*;
import java.io.FileReader;
import java.io.BufferedReader;
public class Principal
{    
    public Principal(String estructura, String consultas, String operaciones)
    {
        /*long tInicio = System.currentTimeMillis(); // almacena el tiempo inicial*/
        boolean structL = estructura.trim().equals("L");
        boolean structT = estructura.trim().equals("T");
        QueryDepot historico = new QueryDepotList();
        if(structT) historico = new QueryDepotTree();
        consultas = consultas.trim();
        operaciones = operaciones.trim();
        if(structL || structT)
        {
            try {
                 BufferedReader lector = new BufferedReader(new FileReader(consultas));
                 String linea = lector.readLine();
                 while(linea != null) {
                     historico.incFreqQuery(linea);
                     linea = lector.readLine();
                 }
                 lector.close();
            }
            catch (Exception e){}
            
            try{
                int numQ = historico.numQueries();
                System.out.println("Consultas almacenadas: "+numQ+".");
                BufferedReader lectorO = new BufferedReader(new FileReader(operaciones));
                String lineaO = lectorO.readLine();
                while(lineaO != null){
                    int getFreq=0;
                    ListIF<Query> listQ = new ListDynamic();
                    String operacion = lineaO.substring(0,1);
                    String consulta = lineaO.substring(2);
                    getFreq = historico.getFreqQuery(consulta);
                    int rep = 100000; // numero de repeticiones
                    if(operacion.trim().equals("F")){
                        long tInicial = System.currentTimeMillis(); // almacena el tiempo inicial
                        for ( int cont = 1 ; cont < rep ; cont++ ) {
                                historico.getFreqQuery(consulta); // llamada al método del que quiere medir el tiempo de ejecución
                        }
                        long tFinal = System.currentTimeMillis(); // almacena el tiempo final
                        double duracion = ((double)tFinal - (double)tInicial)*1000 / (double)rep; // duración
                        System.out.println("La frecuencia de \""+consulta+"\" es "
                        +getFreq+".");
                        String cadena =String.format("-Tiempo: %.2f ns",duracion);
                        System.out.println(cadena);
                    }else if(operacion.equals("S")){
                        long tInicial = System.currentTimeMillis(); // almacena el tiempo inicial
                        for ( int cont = 1 ; cont < rep ; cont++ ) {
                                historico.listOfQueries(consulta); // llamada al método del que quiere medir el tiempo de ejecución
                        }
                        long tFinal = System.currentTimeMillis(); // almacena el tiempo final
                        double duracion = ((double)tFinal - (double)tInicial)*1000 / (double)rep; // duración
                        System.out.println("La lista de sugerencias para \""+consulta+"\" es:");
                        ListIF<Query> listaSug = new ListDynamic();
                        listaSug = historico.listOfQueries(consulta);
                        IteratorIF<Query> itr = listaSug.getIterator();
                        Query q;
                        if(!itr.hasNext()) System.out.println("No hay sugerencias.");
                        while (itr.hasNext())
                        {
                            q = itr.getNext();
                            System.out.println("\""+q.getText()+"\" con frecuencia "+q.getFreq()+".");
                        }
                        String cadena =String.format("-Tiempo: %.2f ns",duracion);
                        System.out.println(cadena);
                    }else{System.out.println("Parametros no validos");}
                    lineaO = lectorO.readLine();
                }
                lectorO.close();
            }catch (Exception e){}
        }
        else{System.out.println("Eliga Lista -L- o Arbol -T-");}
        /*long tFin = System.currentTimeMillis(); // almacena el tiempo final
        double duracion = ((double)tFin - (double)tInicio)*1000; // duración
        String cadena =String.format("-Tiempo: %.2f ms",duracion);
        System.out.println(cadena);*/
    }
    
        public static void main(String[] args) {
        Principal p = new Principal(args[0],args[1],args[2]);
    }
}
