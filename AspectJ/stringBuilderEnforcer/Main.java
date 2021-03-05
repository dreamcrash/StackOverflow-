/** Original Question : https://stackoverflow.com/questions/54260578
 * 
 * Is it possible to use AOP/AspectJ to affect StringBuilder 
 * Could I use AOP to enforce that every time a StringBuilder 
 * is used the first inserted sign would be !!.
 * 
 * 
 */

package stringBuilderEnforcer;

public class Main {
    public static void main(String[] args) {
	System.out.println(new StringBuilder().append("22").toString());
	System.out.println(new StringBuilder().append("!2").append("!2").toString());
	System.out.println(new StringBuilder().append("!!2").append("!!2").toString());
    }
}
