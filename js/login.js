// ========== DOM ==========
const inputUsuario = document.getElementById('inputUsuario');
const inputSenha = document.getElementById('inputSenha');
const butaoEntar = document.getElementById('butaoEntar');

// ========== EVENT ==========
butaoEntar.addEventListener('click', function() {
    fazerLogin();
});
document.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        fazerLogin();
    }
});

// ========== FUNCTION ==========
async function fazerLogin() {
    // Pegando os valores digitados
    const usuario = inputUsuario.value.trim();
    const senha = inputSenha.value.trim();

    // Validações básicas
    if (usuario === '' || senha === '') {
        mostrarErro('Por favor, preencha todos os campos!');
        return;
    }

    try {
        // Chamada real ao seu backend Spring Boot
        const resposta = await fetch('http://localhost:8080/api/usuario/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ login: usuario, senha: senha })
        });

        if (resposta.ok) {
            const usuarioLogado = await resposta.json();
            
            // Guarda os dados reais vindos da sua base de dados MySQL
            // Corrigido de .id para .id_usuario conforme o seu modelo Java
            localStorage.setItem('usuarioId', usuarioLogado.id_usuario);
            localStorage.setItem('usuarioLogado', usuarioLogado.login);
            
            mostrarSucesso(`Bem-vindo, ${usuarioLogado.login}!`);
            
            setTimeout(function() {
                window.location.href = 'produto.html'; 
            }, 1500);
        } else {
            mostrarErro('Usuário ou senha incorretos!');
            inputSenha.value = '';
            inputUsuario.focus();
        }
    } catch (erro) {
        mostrarErro('Erro de conexão com o servidor!');
        console.error(erro);
    }
}

// Função para mostrar erro
function mostrarErro(mensagem) {
    alert('❌ Erro: ' + mensagem);
}

// Função para mostrar sucesso
function mostrarSucesso(mensagem) {
    alert('✅ Sucesso: ' + mensagem);
}

// Limpando campos quando a página carrega
window.addEventListener('load', function() {
    inputUsuario.value = '';
    inputSenha.value = '';
    inputUsuario.focus();
});