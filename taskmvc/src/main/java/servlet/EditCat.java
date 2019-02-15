package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCat extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        logic.close();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("cat", logic.findCatById(id));
        req.getRequestDispatcher("WEB-INF/jsp/editcat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (!DbStore.getInstance().checkInputName(req.getParameter("name"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String text = req.getParameter("text");
            Category category = new Category(id, name, text);
            logic.updateCat(id, category);
            resp.sendRedirect(String.format("%s/editcat?id="+id, req.getContextPath()));
        } else {
            req.setAttribute("error", "Name invalid");
            doGet(req, resp);
        }

    }
}
