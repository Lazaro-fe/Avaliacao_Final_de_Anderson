// ========== DOM ==========
const inputUsuario = document.getElementById('inputUsuario');
const inputSenha = document.getElementById('inputSenha');
const butaoEntar = document.getElementById('butaoEntar');

// ========== EVENT ==========
butaoEntar.addEventListener('click', function() {
    fazerCadastro();
});

document.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        fazerCadastro();
    }
});

// ========== FUNCTION ==========
function fazerCadastro() {
    // Pegando os valores digitados
    const usuario = inputUsuario.value.trim();
    const senha = inputSenha.value.trim();

    // Validações
    if (usuario === '' || senha === '') {
        mostrarErro('Por favor, preencha todos os campos!');
        return;
    }

    // Verificando se o usuário já existe
    if (usuarioJaExiste(usuario)) {
        mostrarErro('Este usuário já está registrado!');
        inputUsuario.value = '';
        inputSenha.value = '';
        inputUsuario.focus();
        return;
    }

    // Salvando o novo usuário
    salvarUsuario(usuario, senha);
    
    mostrarSucesso(`Usuário "${usuario}" cadastrado com sucesso!`);
    
    // Redirecionando para login após 2 segundos
    setTimeout(function() {
        // index.html está na raiz, login.html está em pages/
        window.location.href = './pages/login.html';
    }, 2000);
}

// Função para verificar se o usuário já existe
function usuarioJaExiste(usuario) {
    const usuariosRegistrados = JSON.parse(localStorage.getItem('usuariosRegistrados')) || {};
    return usuariosRegistrados.hasOwnProperty(usuario);
}

// Função para salvar o usuário no localStorage
function salvarUsuario(usuario, senha) {
    // Pegando os usuários já registrados
    const usuariosRegistrados = JSON.parse(localStorage.getItem('usuariosRegistrados')) || {};
    
    // Adicionando o novo usuário
    usuariosRegistrados[usuario] = {
        senha: senha,
        dataCadastro: new Date().toLocaleString('pt-BR'),
        ativo: true
    };
    
    // Salvando de volta no localStorage
    localStorage.setItem('usuariosRegistrados', JSON.stringify(usuariosRegistrados));
    
    console.log('Usuário cadastrado com sucesso:', usuario);
}

// Função para mostrar erro
function mostrarErro(mensagem) {
    alert('❌ Erro: ' + mensagem);
}

// Função para mostrar sucesso
function mostrarSucesso(mensagem) {
    alert('✅ ' + mensagem);
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
            senha: "12345",
            dataCadastro: "Pré-carregado",
            ativo: true
        };
        localStorage.setItem('usuariosRegistrados', JSON.stringify(usuariosExistentes));
        console.log('Admin carregado com sucesso!');
    }
});