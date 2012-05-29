import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: Re4PzZ(Alexander M.)
 * www.re4pzz.bplaced.net/
 */
public class checkForDirs {

    public static String Script = "Hellhounds";
    public static String[] Images = {"Title.png", "Info.png", "XP.png",
            "HP.png", "ATT.png", "STR.png",
            "DEF.png", "MAG.png", "RNG.png"
    };
    public static BufferedImage Titlepaint;
    public static BufferedImage Infopaint;
    public static BufferedImage XPpaint;
    public static BufferedImage HPpaint;
    public static BufferedImage ATTpaint;
    public static BufferedImage STRpaint;
    public static BufferedImage DEFpaint;
    public static BufferedImage MAGpaint;
    public static BufferedImage RNGpaint;
    public static BufferedImage SkillPaint;
    public static BufferedImage[] BufferdImages= {Titlepaint, Infopaint, XPpaint, HPpaint
            , ATTpaint, STRpaint, DEFpaint, MAGpaint
            , RNGpaint, SkillPaint};
    public static JFileChooser fr = new JFileChooser();
    public static FileSystemView fw = fr.getFileSystemView();
    public static File RSBot = new File(fw.getDefaultDirectory() + "/RSBot");
    public static File Settings = new File(fw.getDefaultDirectory() + "/RSBot/Settings");
    public static File Re4PzZ = new File(fw.getDefaultDirectory() + "/RSBot/Settings/Re4PzZ");
    public static File ScriptFolder = new File(fw.getDefaultDirectory() + "/RSBot/Settings/Re4PzZ/" + Script);
    public static void checkForDir() {

        if(RSBot.exists() == false){
            RSBot.mkdir();
            System.out.print("Directory is NOT valid! Directory created:");
            System.out.print("\n");
            System.out.print(RSBot);
            System.out.print("\n");
        }
        if(Settings.exists() == false){
            Settings.mkdir();
            System.out.print("Directory is NOT valid! Directory created:");
            System.out.print("\n");
            System.out.print(Settings);
            System.out.print("\n");
        }
        if(Re4PzZ.exists() == false){
            Re4PzZ.mkdir();
            System.out.print("Directory is NOT valid! Directory created:");
            System.out.print("\n");
            System.out.print(Re4PzZ);
            System.out.print("\n");
        }
        if(ScriptFolder.exists() == false){
            ScriptFolder.mkdir();
            System.out.print("Directory is NOT valid! Directory created:");
            System.out.print("\n");
            System.out.print(ScriptFolder);
            System.out.print("\n");
        }else{
            System.out.print("All directories are valid!");
        }
    }

    public static void loadImages(){
        for(int i = 0; i<Images.length; i++){
            if(new File(ScriptFolder + "//" + Images[i]).exists()){
                try {
                    BufferdImages[i] = ImageIO.read(new File(ScriptFolder + "//" + Images[i]));
                    System.out.print("\n");
                    System.out.print(Images[i] + " is valid!");
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }else{
                System.out.print("\n");
                System.out.print("Download: " + Images[i]);
                URL bild = null;
                try {
                    bild = new URL("http://re4pzz.bplaced.com/scripts/" + Script + "/images/" + Images[i]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                InputStream in = null;
                try {
                    in = bild.openStream();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                byte[] buffer = new byte[8192];

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(new File(ScriptFolder + "//" + Images[i]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                int _tmp = 0;

                try {
                    while((_tmp = in.read(buffer)) > 0){
                        out.write(buffer, 0 , _tmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                    try {
                        BufferdImages[i] = ImageIO.read(new File(ScriptFolder + Images[i]));
                        System.out.print(BufferdImages[i]);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }
        }
    }
    public static void main(String[] args) {
        checkForDir();
        loadImages();
        for(int i = 0; i<BufferdImages.length; i++){
            System.out.print(BufferdImages[i]);
        }
    }
}
