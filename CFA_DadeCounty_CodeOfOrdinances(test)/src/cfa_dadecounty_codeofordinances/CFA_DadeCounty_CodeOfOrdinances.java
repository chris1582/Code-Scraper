
package cfa_dadecounty_codeofordinances;

import java.net.URL;

/**
 * @author Marco Biffi
 */

public class CFA_DadeCounty_CodeOfOrdinances {

    
    public static void main(String[] args) {
        try{                     //http://library.municode.com/HTML/10620/level2/PTIIICOOR_CH8AACACOSEPR.html
                                 //http://library.municode.com/HTML/10620/level2/PTIIICOOR_CH8BEMMA.html
            //http://library.municode.com/HTML/10620/level2/PTIIICOOR_CH11DDIPATR.html
            //http://library.municode.com/HTML/10620/level2/PTIIICOOR_CH8CBUSEME.html
            //http://library.municode.com/HTML/10620/level2/PTIIACSTLE_COMPARATIVE_TABLEHORUCH.html
                String url = "http://library.municode.com/HTML/10620/level2/PTIIACSTLE_COMPARATIVE_TABLEHORUCH.html";
                Hierarchy hi = new Hierarchy(url);
                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
