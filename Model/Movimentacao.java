package Model;
public class Movimentacao {

    private int id_da_movimentacao;
    private String tipo_de_movimentacao;
    private String data_da_movimentacao;
    private int quantidade;
    private String nome_do_produto;
    private String login;
    private int id_produto;
    private int id_usuario;

    public Movimentacao(String tipo_de_movimentacao, String data_da_movimentacao, int quantidade, String nome_do_produto, String login, int id_produto, int id_usuario){
        this.tipo_de_movimentacao = tipo_de_movimentacao;
        this.data_da_movimentacao = data_da_movimentacao;
        this.nome_do_produto = nome_do_produto;
        this.login = login;
        this.id_produto = id_produto;
        this.id_usuario = id_usuario;
    }

    public Movimentacao(int id_da_movimentacao, String tipo_de_movimentacao, String data_da_movimentacao, String nome_do_produto, String login, int id_produto, int id_usuario){
        this.id_da_movimentacao = id_da_movimentacao;
        this.tipo_de_movimentacao = tipo_de_movimentacao;
        this.data_da_movimentacao = data_da_movimentacao;
        this.nome_do_produto = nome_do_produto;
        this.login = login;
        this.id_produto = id_produto;
        this.id_usuario = id_usuario;
    }

    public int getId_da_movimentacao() {
        return id_da_movimentacao;
    }

    public void setId_da_movimentacao(int id_da_movimentacao) {
        this.id_da_movimentacao = id_da_movimentacao;
    }

    public String getTipo_de_movimentacao() {
        return tipo_de_movimentacao;
    }

    public void setTipo_de_movimentacao(String tipo_de_movimentacao) {
        this.tipo_de_movimentacao = tipo_de_movimentacao;
    }

    public String getData_da_movimentacao() {
        return data_da_movimentacao;
    }

    public void setData_da_movimentacao(String data_da_movimentacao) {
        this.data_da_movimentacao = data_da_movimentacao;
    }

    public String getNome_do_produto() {
        return nome_do_produto;
    }

    public void setNome_do_produto(String nome_do_produto) {
        this.nome_do_produto = nome_do_produto;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
