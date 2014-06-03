/** One can have different kinds of dictionaries, like dictionaries based on 
 * arrays, or linked lists, or hash tables, or Binary search trees, etc.
 * However, any kind of dictionary should provide a way to add a word and search for a word.
 *
 */
public interface Dictionary {

   /**
   * Adds input String to the dictionary 
   */
   public void addWord(String word);
   
   /**
   * Searches the dictionary for the input String.
   * Returns true if found, false otherwise.
   */
   public boolean searchWord(String word);
}