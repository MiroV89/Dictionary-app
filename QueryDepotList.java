import eped.*;
public class QueryDepotList implements QueryDepot {
     ListIF<Query> listaQuery;
     ComparatorIF<Query> comparatorFreq = new ComparatorFreq();
     ComparatorIF<Query> comparatorString = new ComparatorString();
     public QueryDepotList(){
         listaQuery=new ListDynamic();
     }
     
     public QueryDepotList(ListIF<Query> lista){
         listaQuery=lista;
     }
     
     public int numQueries(){
         return listaQuery.getLength();
     }
     
     public int getFreqQuery(String q){
         Query query;
         IteratorIF<Query> itr = listaQuery.getIterator();
         while (itr.hasNext())
         {
             query = itr.getNext();
             if(query.getText().equals(q))
             {return query.getFreq();}
         }
         return 0;
     }
     
     public ListIF<Query> listOfQueries(String prefix){
         IteratorIF<Query> itr = listaQuery.getIterator();
         ListDynamic listaAux= new ListDynamic();
         Query q;
         while (itr.hasNext())
         {
             q = itr.getNext();
             if(q.getText().startsWith(prefix))
             {
                 listaAux.insert(q);
             }
         }
         return listaAux.sort(comparatorFreq);
     }
     
     public void incFreqQuery(String q){
         Query query;
         boolean encontrado = false;
         IteratorIF<Query> itr = listaQuery.getIterator();
         while (!encontrado && itr.hasNext())
         {
             query = itr.getNext();
             if(query.getText().equals(q))
             {
                 encontrado = true;
                 query.setFreq(query.getFreq()+1);
             }
         }
         if(!encontrado)
         {
             query = new Query(q);
             listaQuery.insert(query);
             listaQuery = listaQuery.sort(comparatorString);
         }
     }
     
     public void decFreqQuery(String q){
         Query query;
         boolean encontrado = false;
         IteratorIF<Query> itr = listaQuery.getIterator();
         while (!encontrado && itr.hasNext())
         {
             query = itr.getNext();
             if(query.getText().equals(q))
             {
                 encontrado = true;
                 query.setFreq(query.getFreq()-1);
                 if(query.getFreq()<1)
                 {listaQuery = borrar(listaQuery,query);}
             }
         }
         if(!encontrado)
         {System.out.println("No se ha encontrado la consulta");}
     }
 
     private ListIF<Query> borrar(ListIF lista, Query q)
     {
         IteratorIF<Query> itr = lista.getIterator();
         ListIF<Query> listaAux = new ListDynamic();
         Query que;
         while(itr.hasNext()) {
             que = itr.getNext();
             if(!que.equals(q)){listaAux.insert(que);}
         }         
         return listaAux;
     }
}