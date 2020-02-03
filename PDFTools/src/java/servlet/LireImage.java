package servlet;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LireImage", urlPatterns = {"/LireImage"})
public class LireImage extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
        response.setContentType("image/png");
        
        String nom = request.getParameter("name");
        String path = "C:\\Users\\Alexandre\\Pictures\\images\\";
        File f = new File(path+nom);
        BufferedImage bi = ImageIO.read(f);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();
    }
}