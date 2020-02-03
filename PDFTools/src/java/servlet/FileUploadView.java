package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

@Named
@RequestScoped
public class FileUploadView {
    
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
 
    public void upload() throws IOException {
        if (file != null) {

            
            Path path = Paths.get("C:\\Users\\Alexandre\\Pictures\\images\\");
            String filename = FilenameUtils.getBaseName(file.getFileName());
            String extension = FilenameUtils.getExtension(file.getFileName());
            Path fichierTempo = Files.createTempFile(path, filename, "." + extension);
            
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            try (InputStream input = file.getInputstream()) {
                Files.copy(input, fichierTempo, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
     
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
