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
function fazerLogin() {
    // Pegando os valores digitados
    const usuario = inputUsuario.value.trim();
    const senha = inputSenha.value.trim();

    // Validações
    if (usuario === '' || senha === '') {
        mostrarErro('Por favor, preencha todos os campos!');
        return;
    }

    // Pegando usuários do localStorage
    const usuariosRegistrados = JSON.parse(localStorage.getItem('usuariosRegistrados')) || {};

    // Verificando se o usuário existe e a senha está correta
    if (usuariosRegistrados[usuario] && usuariosRegistrados[usuario].senha === senha) {
        mostrarSucesso(`Bem-vindo, ${usuario}!`);
        
        // Salvando o usuário logado
        localStorage.setItem('usuarioLogado', usuario);
        localStorage.setItem('horaLogin', new Date().toLocaleString('pt-BR'));
        
        // Redirecionando após 1.5 segundos
        // login.html está em pages/, então vai para ../pages/produtos.html
        setTimeout(function() {
            window.location.href = '../pages/produto.html';
        }, 1500);
    } else {
        mostrarErro('Usuário ou senha incorretos!');
        // Limpando o campo de senha por segurança
        inputSenha.value = '';
        inputUsuario.focus();
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
    
    // Garantindo que o admin sempre existe
    const usuariosExistentes = JSON.parse(localStorage.getItem('usuariosRegistrados')) || {};
    
    // Se não tiver admin, adiciona
    if (!usuariosExistentes['admin']) {
        usuariosExistentes['admin'] = {
            senha: "1234",
            dataCadastro: "Pré-carregado",
            ativo: true
        };
        localStorage.setItem('usuariosRegistrados', JSON.stringify(usuariosExistentes));
        console.log('Admin carregado com sucesso!');
    }
});