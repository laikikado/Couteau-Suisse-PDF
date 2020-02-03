package bean;

import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;

@Named(value = "traitement")
@RequestScoped
public class Traitement {
    private String nomFichier;
    private BufferedImage image;
    
    public String getNomFichier() {
        return nomFichier;
    }
    
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
        try {
            image = ImageIO.read(new URL("file:///"+nomFichier));
        }
        catch (IOException ex) {}
     }
    
    public boolean isPrésente() { return image!=null; }

}