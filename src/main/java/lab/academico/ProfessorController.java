package lab.academico;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/exemplo-jpa/professor")
@WebServlet("")
public class ProfessorController extends HttpServlet {
	private String valor(HttpServletRequest req, String param, String padrao) {
		String result = req.getParameter(param);
		if (result == null) {
			result = padrao;
		}
		return result;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String msg;
			String op = valor(req, "operacao", "");
			String matricula = valor(req, "matricula", "");
			String nome = valor(req, "nome", "");
			if (op.equals("incluir")) {
				ProfessorDao.inclui(matricula, nome);
				msg = "Inclusão realizada com sucesso.";
			} else if (op.equals("alterar")) {
				ProfessorDao.alterar(matricula, nome);
				msg = "Alteração realizada com sucesso.";
			} else if (op.equals("excluir")) {
				ProfessorDao.excluir(matricula);
				msg = "Exclusão realizada com sucesso.";
			} else if (op.equals("")) {
				msg = "";
			} else {
				throw new IllegalArgumentException("Operação \"" + op + "\" não suportada.");
			}
			req.setAttribute("msg", msg);
			req.setAttribute("professores", ProfessorDao.listar());
			
			//req.getRequestDispatcher("ProfessorView.jsp").forward(req, resp);
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace(resp.getWriter());
		}
	}
}
