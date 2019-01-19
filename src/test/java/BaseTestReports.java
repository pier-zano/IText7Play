


import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTestReports {
  public static final String DBHOST_SQL   = "sqlcluster1.pa.cis";

  private static String  TESTPATH = "C:/temp/IText7";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    try {
//      InputStream is = BaseTestReports.class.getResourceAsStream("/data/dbpassword.properties");
//      m_properties = new Properties();
//      m_properties.load(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Before
  public void setupStampa() {
   // System.out.println("Inizializzazione oggetto Stampa...");
    try {
      // m_stampa.setDebugMode(true);
      File outDir = new File(TESTPATH);
      outDir.mkdirs();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  //  m_properties.clear();
  }

  void eseguiCmd(String commandLine) {
    try {
      Runtime.getRuntime().exec("cmd /C " + commandLine);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected File getFileOutput(String fileName) {
    File out = new File(TESTPATH + "/" + fileName);
    return out;
  }
  
  /**
   * Apre adobe reader con il file pdf passato
   * 
   * @param f
   *          file pdf
   */
  protected void openOutputWithReader(File f) {
    if (f == null)
      return;
    eseguiCmd(f.getAbsolutePath());
  }

  /**
   * Cerca di cancellare il file indicato: se la cancellazione non &egrave;
   * possibile, chiude adobe reader e cerca nuovamente di cancellarlo.
   * 
   * @param f
   *          file da trattare
   * @throws InterruptedException
   *           per errori gravi durante il blocco del thread corrente durante la
   *           chiusura di adobe reader
   */
  private void closePDFReader(File f) throws InterruptedException {
    if ( !f.exists())
      return;
    if ( !f.canWrite() || !f.delete()) {
      //file lockato, probabilmente e' aperto con Adobe Reader
      eseguiCmd("taskkill /IM AcroRd32.exe");
      Thread.sleep(1500);
      if ( !f.canWrite() || !f.delete()) {
        Assert.fail("Non riesco a sbloccare " + f.getAbsolutePath());
      }
    }
  }

  /**
   * Cercando di un-lockare il file passato.
   * 
   * @param fOutput
   *          file da un-lockare, se null non fa nulla
   */
  private void unlockOutputFile(File fOutput) {
    try {
      if (fOutput == null)
        return;
      if (fOutput != null) {
        closePDFReader(fOutput);
      }
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Sblocco file impossibile: " + fOutput.getAbsolutePath());
    }

  }

}
