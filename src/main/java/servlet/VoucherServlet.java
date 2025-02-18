package servlet;

import dao.VoucherDAO;
import model.Voucher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manager/vouchers")
public class VoucherServlet extends HttpServlet {

    private VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Manager có thể xem tất cả voucher, Customer chỉ có thể xem voucher của mình
        if (action == null) {
            List<Voucher> vouchers = voucherDAO.getAllVouchers();
            request.setAttribute("vouchers", vouchers);
            request.getRequestDispatcher("/WEB-INF/manager_vouchers.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            String voucherCode = request.getParameter("voucherCode");
            Voucher voucher = voucherDAO.getVoucherByCode(voucherCode);
            request.setAttribute("voucher", voucher);
            request.getRequestDispatcher("/WEB-INF/edit_voucher.jsp").forward(request, response);
        } else if (action.equals("filter")) {
            String voucherCode = request.getParameter("voucherCode");
            Voucher voucher = voucherDAO.getVoucherByCode(voucherCode);
           // request.setAttribute("vouchers", voucher != null ? List.of(voucher) : voucherDAO.getAllVouchers());
            request.getRequestDispatcher("/WEB-INF/manager_vouchers.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("create")) {  // Manager tạo mới voucher
            String voucherCode = request.getParameter("voucherCode");
            int discountAmount = Integer.parseInt(request.getParameter("discountAmount"));
            String validUntil = request.getParameter("validUntil");
            Voucher voucher = new Voucher(voucherCode, discountAmount, validUntil);
            voucherDAO.addVoucher(voucher);
            response.sendRedirect("/manager/vouchers");
        } else if (action.equals("update")) {  // Manager cập nhật voucher
            String voucherCode = request.getParameter("voucherCode");
            int discountAmount = Integer.parseInt(request.getParameter("discountAmount"));
            String validUntil = request.getParameter("validUntil");
            Voucher voucher = new Voucher(voucherCode, discountAmount, validUntil);
            voucherDAO.updateVoucher(voucher);
            response.sendRedirect("/manager/vouchers");
        } else if (action.equals("delete")) {  // Manager xóa voucher
            String voucherCode = request.getParameter("voucherCode");
            voucherDAO.deleteVoucher(voucherCode);
            response.sendRedirect("/manager/vouchers");
        }
    }
}
