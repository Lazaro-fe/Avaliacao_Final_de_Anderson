package Model;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categoria;

    @Column(nullable = false)
    private String categoria_do_produto;

    public Categoria(){
    }

    public Categoria(String categoria_do_produto){
        this.categoria_do_produto = categoria_do_produto;
    }

    public Categoria(int id_categoria, String categoria_do_produto){
        this.id_categoria = id_categoria;
        this.categoria_do_produto = categoria_do_produto;
    }
    
    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria_do_produto() {
        return categoria_do_produto;
    }

    public void setCategoria_do_produto(String categoria_do_produto) {
        this.categoria_do_produto = categoria_do_produto;
    }
}