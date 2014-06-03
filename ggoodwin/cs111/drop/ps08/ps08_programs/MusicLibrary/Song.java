public class Song{
  //instance variables
  String title;
  String artist;
  String genre;
  
  //constructer
  public Song(java.lang.String songTitle, java.lang.String songArtist, java.lang.String songGenre){
    title = songTitle;
    artist = songArtist;
    genre = songGenre;
  }
  //methods
  public java.lang.String getTitle(){
    return title;
  }
  public java.lang.String getArtist(){
    return artist;
  }
  public java.lang.String getGenre(){
    return genre;
  }
    public String toString()
  {
    String result = title+", "+artist+", "+genre;
    return result;
  }
  //testing
   public static void main(String[] args)
  {
 Song s1 = new Song("hello","Gabe","rock");
 System.out.println(s1);
}
}