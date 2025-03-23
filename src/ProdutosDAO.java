

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    private Connection conn; 
    private PreparedStatement prep;
    private ResultSet resultset;
    
    public ProdutosDAO(Connection conn) {
        this.conn = conn;
    }

   
    public int cadastrarProduto(ProdutosDTO produto) {
        int status;
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            status = prep.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    public List<ProdutosDTO> listarProdutos(int produtoPesquisa) {
    String sql;
    if (produtoPesquisa == -1) {
        
        sql = "SELECT * FROM produtos";
    } else {
        sql = "SELECT * FROM produtos WHERE id = ?";
    }

    try {
        conectaDAO dao = new conectaDAO();
        boolean ct = dao.conectar();
        if (!ct) {
            JOptionPane.showMessageDialog(null, "Sem conexão com o banco");
            return null;
        }

        PreparedStatement stmt = this.conn.prepareStatement(sql);

        if (produtoPesquisa != -1) {
            stmt.setInt(1, produtoPesquisa); 
        }

        ResultSet rs = stmt.executeQuery();
        List<ProdutosDTO> listagem = new ArrayList<>();

        while (rs.next()) {
            ProdutosDTO prod = new ProdutosDTO();
            prod.setId(rs.getInt("id"));
            prod.setNome(rs.getString("nome"));
            prod.setValor(rs.getInt("valor"));
            prod.setStatus(rs.getString("status"));
            listagem.add(prod);
        }

        dao.desconectar();
        return listagem;

    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
    public int venderProduto(int idVenda) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    int st;
    String status = "Vendido";

    try {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        
        stmt.setString(1, status);  
        stmt.setInt(2,idVenda);
        
        st = stmt.executeUpdate();
        return st;

    } catch (SQLException e) {
        System.out.println(e.getErrorCode());
        return e.getErrorCode();
        
    }
   
    }
    
    
    
    
    
}