package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ListServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        logic.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int role = logic.findByLogin(session.getAttribute("login").toString());
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("role", role);
        req.setAttribute("item", logic.findAllItemsById(id));
        req.setAttribute("items", logic.findAllItems());
        req.setAttribute("categoryes", logic.findAllCategoryes());
        req.setAttribute("catSize", logic.listCategoruesSize());
        req.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
