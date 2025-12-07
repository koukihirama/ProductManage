package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.entity.CategoryBean;

@WebServlet("/category-list")
public class CategoryListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDAO dao = new CategoryDAO();

        try {
            List<CategoryBean> categories = dao.findAll();

            // JSP に渡す
            request.setAttribute("categories", categories);

            // JSP にフォワード
            RequestDispatcher rd = request.getRequestDispatcher("/category-list.jsp");
            rd.forward(request, response);

        } catch (SQLException e) {
           
            throw new ServletException(e);
        }
    }
}