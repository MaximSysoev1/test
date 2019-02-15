package servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
public class AddItem extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        logic.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categoryes", logic.findAllCategoryes());
        req.getRequestDispatcher("WEB-INF/jsp/additem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (!DbStore.getInstance().checkInputName(req.getParameter("itemname"))) {
            int price = Integer.parseInt(req.getParameter("price"));
            int volume = Integer.parseInt(req.getParameter("volume"));
            int cat = Integer.parseInt(req.getParameter("cat"));
            Item item=new Item(0, "", req.getParameter("itemname"), req.getParameter("desc"), price, volume, cat);
            logic.addItem(item);
            resp.sendRedirect(String.format("%s/additem", req.getContextPath()));
        } else {
            req.setAttribute("error", "Name invalid");
            doGet(req, resp);
        }

        //String uploadPath = req.getServletContext().getInitParameter("uploadDirectory");
        //FileOutputStream fos = new FileOutputStream(uploadPath + req.getParameter("img"));
  //      for (Part part : req.getParts()) {
           // String fileName = URLDecoder.decode(part.getSubmittedFileName(), "UTF-8");
   //         Part filePart = req.getPart("img");
   //         String fileName = getFileName(filePart);
   //         String path = getServletContext().getInitParameter("uploadDirectory");
   //         File file = new File(path + File.separator + fileName);
   //         file.getParentFile().mkdirs();
   //         InputStream inputStream = part.getInputStream();
   //         java.nio.file.Files.copy(inputStream, file.toPath());
   //         inputStream.close();
    //        break;
    //    }

        }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
