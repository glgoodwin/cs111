/* Gabrielle Goodwin
 * CS 111 PS6
 * Task 1
 * */
public class Exponentiation{
  public static void main(String[] args){
     Exponentiation ex = new Exponentiation();
     int a = 6;
     int b = 8;
     System.out.println(ex.power(a,b));
  }
/* Given two integers, a base and an exponent, the power method should return
* an integer that is the value of raising the base to the exponent
* power. The method assumes that the exponent is an integer greater than
* or equal to zero.
*/
    public int power(int base, int exponent){
      if (exponent == 0){
        return 1; //anything to the 0 power is 1
      }else{
        return base*(power(base, exponent-1));
      } // multiplies the base by itself the given number of times
    }
}