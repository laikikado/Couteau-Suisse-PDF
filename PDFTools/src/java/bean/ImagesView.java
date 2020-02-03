package bean;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named(value = "imagesView")
@Dependent
public class ImagesView {
 
    public List<String> getImages() {
        return Arrays.asList(new File("C:\\Users\\Alexandre\\Pictures\\images\\").list());
    }
}