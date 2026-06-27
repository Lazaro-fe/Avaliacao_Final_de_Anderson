package Model;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id_produto;

    @Column(nullable = false)
    private String nome_do_produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double preco;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    public Produto(){
    }

    public Produto(String nome_do_produto, int quantidade, double preco, int id_usuario, int id_categoria, String login){
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_usuario = id_usuario;
        this.id_categoria = id_categoria;
        this.login = login;
    }

    public Produto(int id_produto, String nome_do_produto, int quantidade, double preco, int id_usuario, int id_categoria, String login){
        this.id_produto = id_produto;
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_usuario = id_usuario;
        this.id_categoria = id_categoria;
        this.login = login;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_do_produto() {
        return nome_do_produto;
    }

    public void setNome_do_produto(String nome_do_produto) {
        this.nome_do_produto = nome_do_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }
}