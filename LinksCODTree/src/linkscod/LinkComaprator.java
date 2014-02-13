/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linkscod;

import java.util.Comparator;

/**
 *
 * @author Cristhian
 */
class LinkComaprator<T> implements Comparator<T>{

    

    @Override
    public int compare(T L1, T L2) {
         String Link1 = L1.toString();
         String Link2 = L2.toString();
         
         return Link1.compareToIgnoreCase(Link2);//To change body of generated methods, choose Tools | Templates.
    }
    
}
