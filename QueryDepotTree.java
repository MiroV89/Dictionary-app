import eped.*;
public class QueryDepotTree implements QueryDepot
{
    TreeIF<Object> tree;
    private QueueIF<TreeIF<Object>> children;
    ComparatorIF<Query> comparatorFreq = new ComparatorFreq();
    public QueryDepotTree() {
         tree = new TreeDynamic();
         tree.setRoot("");
    }
    
    /** 
     * Devuelve el número de consultas diferentes (sin contar repeticiones) 
     * que hay almacenadas en el depósito 
     * @returns el número de consultas diferentes almacenadas 
     **/
    public int numQueries ()
    {
         /*TreeIF<Object> arbol = tree;
         IteratorIF<Object> treeIt = arbol.getIterator(0);
         int num = 0;
         while (treeIt.hasNext()) {
             Object o = treeIt.getNext();
             if (o instanceof Integer) {
                 num++;
             }
         }
         return num;*/
         int num = 0;
         num = numQ(tree,num);
         return num;
    }
    
    private int numQ(TreeIF<Object> arbol, int num)
    {
        ListIF<TreeIF<Object>> lista = arbol.getChildren();
        IteratorIF<TreeIF<Object>> itr = lista.getIterator();
        TreeIF<Object> arbAux = new TreeDynamic();
        while(itr.hasNext())
        {
            arbAux = itr.getNext();
            if(arbAux.isLeaf())
            {num++;}
            else{num = numQ(arbAux,num);}
        }
        return num;
    }
    
    /**
     * Consulta la frecuencia de una consulta en el depósito 
     * @returns la frecuencia de la consulta. Si no está, devolverá 0 
     * @param el texto de la consulta 
     **/
    public int getFreqQuery (String q)
    {
        int freq = getFreq(tree,q);
        return freq;
    }
    
    private int getFreq(TreeIF<Object> tree, String q)
    {
        ListIF<TreeIF<Object>> lista = tree.getChildren();
        IteratorIF<TreeIF<Object>> itr= lista.getIterator();
        TreeIF<Object> arbAux;
        boolean encontrado = false;
        int freq = 0;
        if (!q.isEmpty()) {
            String letra = q.substring(0,1);
            while (!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if (arbAux.getRoot().equals(letra))
                {
                    encontrado = true;
                    freq = getFreq(arbAux,q.substring(1));
                }
            }
        }    
        else
        {
            while(!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if(arbAux.isLeaf())
                {
                    encontrado = true;
                    freq = (int)arbAux.getRoot();
                }
            }
        }
        return freq;
    }
    
    
    /** 
     * Dado un prefijo de consulta, devuelve una lista, ordenada por 
     * frecuencias de mayor a menor, de todas las consultas almacenadas 
     * en el depósito que comiencen por dicho prefijo 
     * @returns la lista de consultas ordenada por frecuencias y orden 
     * lexicográfico en caso de coincidencia de frecuencia 
     * @param el prefijo 
     **/
    public ListIF<Query> listOfQueries (String prefix)
    {
        TreeIF<Object> arbol = new TreeDynamic();
        arbol.setRoot("");
        arbol.addChild(treePrefix(tree,prefix));
        ListIF<Query> listaS = listaQuTr(arbol);
        return listaS.sort(comparatorFreq);
    }
    
    /**
     * Devuelve un arbol cuyos nodos principales se corresponden
     * con prefix.
     */
    private TreeIF<Object> treePrefix (TreeIF<Object> tree, String prefix)
    {
        ListIF<TreeIF<Object>> listaT = tree.getChildren();
        TreeIF<Object> arbolAux2 = new TreeDynamic();
        IteratorIF<TreeIF<Object>> itr = listaT.getIterator();
        TreeIF<Object> arbAux = new TreeDynamic();
        if (!prefix.isEmpty()) {
            String letra = prefix.substring(0,1);
            boolean encontrado = false;
            while(!encontrado &&itr.hasNext())
            {
                arbAux = itr.getNext();
                if(arbAux.getRoot().equals(letra))
                {
                    encontrado = true;
                }
            }
            if(!encontrado)
            {
                TreeIF<Object> treeNotFound = new TreeDynamic();
                System.out.println("No hay sugerencias.");
                return treeNotFound;
            }
            if (prefix.length() > 1){
                arbolAux2.setRoot(arbAux.getRoot());
                arbolAux2.addChild(treePrefix(arbAux,prefix.substring(1)));
            }
            else{arbolAux2 = arbAux;}
        }
        else if (prefix.isEmpty())return tree;
        return arbolAux2;
    }
    
    /**
     * Reconstruye una lista de queries desde un arbol
     */
    private ListIF<Query> listaQuTr(TreeIF<Object> tree)
    {
        ListIF<TreeIF<Object>> listaT = tree.getChildren();
        IteratorIF<TreeIF<Object>> itr = listaT.getIterator();     
        ListIF<Query> listaS = new ListDynamic();
        TreeIF<Object> arbAux = new TreeDynamic();
        String cadena = "";        
        while (itr.hasNext())
        {
            arbAux = itr.getNext();
            int freq = 1;
            String letra;
            if(arbAux.isLeaf())
            {
                freq = (int)arbAux.getRoot();
                Query query = new Query("");
                query.setFreq(freq);
                listaS.insert(query);
            }
            else
            {
                letra = (String)arbAux.getRoot();
                ListIF<Query> listaRecursiva = listaQuTr(arbAux);
                IteratorIF<Query> itr2 = new ListIterator<Query>(listaRecursiva);
                while (itr2.hasNext())
                {
                    Query q = itr2.getNext();
                    Query qu = new Query (letra+q.getText());
                    qu.setFreq(q.getFreq());
                    listaS.insert(qu);
                }
            }           
        }
        return listaS;
    }
    
    
    /** 
     * Incrementa en uno la frecuencia de una consulta en el depósito 
     * Si la consulta no existía en la estructura, la deberá añadir 
     * @param el texto de la consulta 
     **/
    public void incFreqQuery (String q)
    {
        subirFreq(tree,q);
    }
    
    /**
     * Si existe la consulta q aumenta la frecuencia
     * Si no existe, crea una consulta nueva con frecuencia 1.
     */
    private void subirFreq(TreeIF<Object> tree, String q)
    {
        ListIF<TreeIF<Object>> lista = tree.getChildren();
        IteratorIF<TreeIF<Object>> itr= lista.getIterator();
        TreeIF<Object> arbAux = new TreeDynamic();
        boolean encontrado = false;
        int index = 0;
        int freq = 1;
        if (!q.isEmpty()) {
            String letra = q.substring(0,1);
            while (!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if(arbAux.getRoot().equals(letra))
                {
                    encontrado = true;
                    subirFreq(arbAux,q.substring(1));
                }
            }
            if(!encontrado)
            {
                arbAux = new TreeDynamic();
                arbAux.setRoot(letra);
                tree.addChild(arbAux);
                subirFreq(arbAux,q.substring(1));
            }
        }    
        else
        {
            while(!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if(arbAux.isLeaf())
                {
                    encontrado = true;
                    freq = freq+(int)arbAux.getRoot();
                    tree.removeChild(index);
                }
                index++;
            }
            if(!encontrado)
            {
                arbAux = new TreeDynamic();
            }
            arbAux.setRoot(freq);
            tree.addChild(arbAux);
        }
    }
    
    /**
     * Decrementa en uno la frecuencia de una consulta en el depósito
     * Si la frecuencia decrementada resultase ser 0, deberá eliminar 
     * la información referente a la consulta del depósito 
     * @precondición la consulta debe estar ya en el depósito 
     * @param el texto de la consulta 
     **/
    public void decFreqQuery (String q)
    {
        bajarFreq(tree,q);
    }
    
    private void bajarFreq(TreeIF<Object> tree, String q)
    {
        ListIF<TreeIF<Object>> lista = tree.getChildren();
        IteratorIF<TreeIF<Object>> itr= lista.getIterator();
        TreeIF<Object> arbAux;
        boolean encontrado = false;
        int index = 0;
        if (!q.isEmpty()) {
            String letra = q.substring(0,1);
            while (!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if (arbAux.getRoot().equals(letra))
                {
                    encontrado = true;
                    bajarFreq(arbAux,q.substring(1));
                    tree.removeChild(index);
                    if(!arbAux.isLeaf())
                    {
                         tree.addChild(arbAux);
                    }
                }
                index++;
            }
            if(!encontrado)
            {System.out.println("No encontrado");}
        }
        else
        {
            while (!encontrado && itr.hasNext())
            {
                arbAux = itr.getNext();
                if(arbAux.isLeaf())
                {
                    encontrado = true;
                    int freq = (int)arbAux.getRoot();
                    if(freq>1) 
                    {
                         arbAux.setRoot(freq - 1);
                         tree.removeChild(index);
                         tree.addChild(arbAux);
                    }
                    else tree.removeChild(index);
                }
                index++;
            }
            if (!encontrado){System.out.println("No existe la busqueda");}
        }
    }
}
