package ro.dsci.internship;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
  public final String nume;
  public final String prenume;
  public final String email;

  public User(String nume, String prenume, String email) {
    this.nume = nume;
    this.prenume = prenume;
    this.email = email;
  }

  // read
  public String read() {

    String numeFisier = "fisier.csv";
    File obiectFisier = new File(numeFisier);
    // cream un obiect de tip file pe care l pasam in constructorul lui scanner

    StringBuffer sb = new StringBuffer();
    try {
      Scanner ccz = new Scanner(obiectFisier);
      // cream un obiect de tip scanner ptr a lua inputul/fisierul csv
      while (ccz.hasNext()) // cat timp inca mai citeste ceva din fisier
      {
        // afisam ce a citit
        String citit = ccz.nextLine();
        sb.append(citit).append(" \n");
        System.out.println(sb.toString());
      }

    } catch (Exception e)

    {
      throw new RuntimeException(e);
    }
    return sb.toString();
  }// end read

  public void write(String filePath) {
    try {
      FileWriter a = new FileWriter(filePath, true);
      BufferedWriter b = new BufferedWriter(a);
      PrintWriter c = new PrintWriter(b);

      // scriem intr un fisier
      // punand la sfarsitul fisierului ce am scris/nu stergand tot si scriind
      // peste
      // punem ce vrem sa scriem intr-un buffer si
      // facem un obiect cu care sa putem println-ui ce trebuie
      c.println(nume + " , " + prenume + ", " + email);
      c.close();

    }

    catch (Exception e) {
      throw new RuntimeException(e);
    }

  }// end write

  @Override
  public String toString() {
    return "User[name=" + nume + ", prenume=" + prenume + ", email=" + email + "]";
  }
}// end class User