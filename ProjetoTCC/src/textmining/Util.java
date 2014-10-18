package textmining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import weka.core.FastVector;
import weka.core.Stopwords;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stemmers.Stemming;

public class Util {

  public static String attrNameText = "text";
  public static String attrNameClass = "class";

  public static String pathConcat(String path, String text) {
    while (Util.hasSlash(path)) {
      path = path.substring(0, path.length() - 1);
    }
    return path + text;
  }

  public static String pathJoin(String path, String text) {
    if (!Util.hasSlash(path)) {
      path = path + File.separator;
    }
    return path + text;
  }

  public static boolean hasSlash(String path) {
    if (path.endsWith(File.separator) || path.endsWith("\\")
        || path.endsWith("/")) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean interesting(String a, String b) {
    return interesting(a, b, "true");
  }

  public static boolean interesting(String a, String b, String defaultString) {
    if (a == defaultString && b == defaultString) {
      return false;
    } else {
      return true;
    }
  }

  public static void printIfInteresting(String prompt, String sep, String a,
      String b) {
    if (interesting(a, b)) {
      System.out.println(prompt + ": " + a + sep + " " + b);
    }
  }

  public static File STOPWORDS_FILE = makeStopWordFile();

  private static File makeStopWordFile() {
    File stops = new File("C:\\ArquivosTCC\\stopwords\\stopwords_portugues");
    File stops_stemmed = new File("C:\\ArquivosTCC\\stopwords_portugues_stemmed");
    
    if(stops.exists() && !stops.isDirectory()){
    	return stops;
    }
    
    try {

      if (!stops.exists()) {
        stops.createNewFile();
      }

      // Use weka internal stopwords
      Stopwords sw = new Stopwords();
      sw.write(stops);

      // Stem the stopped words.
      SnowballStemmer x = new SnowballStemmer();
      Stemming s = new Stemming();
      String[] options = new String[4];
      options[0] = "-i"; // unpruned tree
      options[1] = stops.getAbsolutePath();
      options[2] = "-o";
      options[3] = stops_stemmed.getAbsolutePath();
      s.useStemmer(x, options);
      return stops;
      // return stops_stemmed; // return stemmed stop if necessary
    } catch (Exception e) {
      e.printStackTrace();
      return stops_stemmed;
    }

  }

  public static File loadDir(String path) throws Exception {
    File dir = new File(path);
    if (dir.exists()) {
      if (dir.isDirectory()) {
        return dir;
      } else {
        throw new IOException("The directory you mentioned: " + path
            + " is actually a file.");
      }
    } else {
      dir.mkdir();
      return dir;
    }
  }

  public static FastVector getFastVector(String fileName) throws Exception {
    if (fileName.equals("")) {
      throw new IOException("The class list file does not exist! " + fileName);
    }
    
    //BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Álvaro\\workspace\\ProjetoTCC\\src\\data\\dir\\training-classlist.txt"));
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String line = "";
    FastVector classValues = new FastVector(100);
    while ((line = br.readLine()) != null) {
      line = line.trim();
      if (!line.equals(""))
        classValues.addElement(line);
    }
    for (int i = 0; i < classValues.size(); i++) {
      System.out.print((String) classValues.elementAt(i));
    }
    return classValues;
  }

  public static void debugExit(String msg) {
    System.out.println(msg);
    debugExit();
  }

  public static void debugExit() {
    System.out.println("Exiting for debug issue...");
    System.exit(0);
  }

  public static void printTable(Hashtable<String, String> ret) {

    Set<String> keys = ret.keySet();
    Iterator<String> iter = keys.iterator();
    while (iter.hasNext()) {
      Object key = iter.next();
      System.out.println(key + " " + ret.get(key) + "\n");
    }
  }

  public static void printTable(Hashtable<String, String> ret,
      BufferedWriter out) throws Exception {
    Vector<String> v = new Vector<String>(ret.keySet());
    Collections.sort(v);
    Iterator<String> it = v.iterator();
    while (it.hasNext()) {
      String element = it.next();
      out.write(element + " " + (String) ret.get(element) + "\n");
    }
    out.close();
  }

  public static boolean isDirectory(String dirName) {
    File dir = new File(dirName);
    if (dir.isDirectory())
      return true;
    return false;
  }

  public static void main(String[] args) throws Exception {
    // System.out.println(String.format("%-10.3f%-10.3f%-10.3f%-10.3f\n",
    // 0.977777, 0.2392103, 0.213021392, 1.32193123));
    

	  File pasta = new File("C:\\Users\\Álvaro\\workspace\\ProjetoTCC\\src\\data\\dir\\training\\phishing");
	  
	  File[] arquivos = pasta.listFiles();
	  
	  
	  for (File file : arquivos) {
		  System.out.println(file.getName()+ "	phishing");
	  }
	  
	  File pasta1 = new File("C:\\Users\\Álvaro\\workspace\\ProjetoTCC\\src\\data\\dir\\training\\not_phishing");
	  
	  File[] arquivos1 = pasta1.listFiles();
	  
	  
	  for (File file : arquivos1) {
		  if(!file.isDirectory()){
			  System.out.println(file.getName()+ "	not_phishing");  
		  }		  
	  }
	  
//	  FileReader m = new FileReader("C:\\Users\\Álvaro\\workspace\\ProjetoTCC\\src\\data\\dir\\training\\EMAIL000240");
//
//		StringBuffer text = new StringBuffer();
//		int l;
//		while ((l = m.read()) != -1) {
//			text.append((char) l);
//		}
//		m.close();
//		
//		String texto = text.toString();
//		
//		System.out.println(texto);
//		System.out.println("#########################################################################################################");
//		System.out.println("############################################### NOVO TEXTO ##############################################");
//		System.out.println("#########################################################################################################");
//		String texto1 = texto.replaceAll("<.*?>", "");
//		texto1 = texto1.replaceAll("<.*?>", "");
//		texto1 = texto1.replaceAll("[0-9]", "");
//		System.out.println(texto1);
	  
		
		 
	  
	  
  }

}
