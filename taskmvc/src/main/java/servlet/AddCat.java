package servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class AddCat extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        logic.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/addcat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        String desc = req.getParameter("text");
        Category category = new Category(0, name, desc);
        if (!DbStore.getInstance().checkInputName(name)) {
            logic.addCategory(category);
            resp.sendRedirect(String.format("%s/addcat", req.getContextPath()));
        } else {
            req.setAttribute("error", "Name invalid");
            doGet(req, resp);
        }
    }
}
