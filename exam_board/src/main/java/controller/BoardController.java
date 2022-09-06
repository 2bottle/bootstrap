package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String destPage;
	private RequestDispatcher rdp; 
	private BoardService bsv; 
	private int isUp;

    public BoardController() {
    	bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		
		String uri = req.getRequestURI(); 
		String path = uri.substring(uri.lastIndexOf("/")+1); 
		
		switch (path) {
			case "register":
				destPage = "/board/register.jsp";
				break;
			case "insert":
				try {
					isUp = bsv.register(new BoardVO(req.getParameter("title"), req.getParameter("writer"), req.getParameter("content")));
					destPage="list";
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "list":
				try {
					List<BoardVO> list = bsv.getList();
					destPage = "/board/list.jsp";
					req.setAttribute("list", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "detail":
				try {
					int bno = Integer.parseInt(req.getParameter("bno"));
					BoardVO bvo = bsv.getDetail(bno);
					req.setAttribute("bvo", bvo);
					destPage="/board/detail.jsp";
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "modify":
				try {
					int bno = Integer.parseInt(req.getParameter("bno"));
					BoardVO bvo = bsv.getDetail(bno);
					req.setAttribute("bvo", bvo);
					destPage="/board/modify.jsp";
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "update":
				try {
					BoardVO bvo = new BoardVO(Integer.parseInt(req.getParameter("bno")), req.getParameter("title"), req.getParameter("content"));
					isUp = bsv.modify(bvo);
					destPage = "detail?bno="+bvo.getBno();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "remove":
				try {
					int bno = Integer.parseInt(req.getParameter("bno"));
					isUp = bsv.remove(bno);
					destPage="list";
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
		
		rdp = req.getRequestDispatcher(destPage);
		rdp.forward(req, res);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
