// ========== DOM ==========
const inputUsuario = document.getElementById('inputUsuario');
const inputSenha = document.getElementById('inputSenha');
const butaoEntar = document.getElementById('butaoEntar');

// ========== EVENTOS ==========
butaoEntar.addEventListener('click', function() {
    cadastrarUsuario();
});

document.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        cadastrarUsuario();
    }
});

// ========== FUNÇÃO PRINCIPAL ==========
async function cadastrarUsuario() {
    const usuario = inputUsuario.value.trim();
    const senha = inputSenha.value.trim();

    // Validação básica
    if (usuario === '' || senha === '') {
        alert('❌ Erro: Por favor, preencha todos os campos!');
        return;
    }

    try {
        // Chamando o Spring Boot na rota de cadastro
        const resposta = await fetch('http://localhost:8080/api/usuario/cadastrar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ login: usuario, senha: senha })
        });

        if (resposta.ok) {
            alert('✅ Sucesso: Usuário cadastrado com sucesso!');
            // Redireciona para a tela de login
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1000);
        } else {
            // Caso o Java retorne um erro (ex: "Login ja cadastrado!")
            const mensagemErro = await resposta.text();
            alert('❌ Erro: ' + mensagemErro);
            inputUsuario.focus();
        }
    } catch (erro) {
        alert('❌ Erro: Erro de conexão com o servidor! O Spring Boot está rodando?');
        console.error('Detalhes do erro:', erro);
    }
}