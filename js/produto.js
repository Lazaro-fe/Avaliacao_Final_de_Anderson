// ========== VERIFICAÇÃO INICIAL CASO O USUARIO ABRA DIRETO PARA ESTA PAGINA ==========
window.addEventListener('load', function() {
    // Verifica se o usuário está logado
    const usuarioLogado = localStorage.getItem('usuarioLogado');
    
    if (!usuarioLogado) {
        alert('❌ Você precisa estar logado para acessar esta página!');
        window.location.href = './login.html';
        return;
    }
    
    // Mostra o nome do usuário logado
    document.getElementById('nomeUsuario').textContent = `Bem-vindo, ${usuarioLogado}!`;
    
    // Injeta dinamicamente o modal de detalhes de alteração se ele não existir
    injetarModalDetalhes();
    // Injeta a opção "Alterado" no filtro de movimentações
    injetarOpcaoFiltro();
    
    // Carrega os produtos ao iniciar a página
    carregarProdutos();
});

// ========== DOM ==========
let produtoEmEdicao = null;
const formProduto = document.getElementById('formProduto');
const formEdicao = document.getElementById('formEdicao');
const modalEdicao = document.getElementById('modalEdicao');
const fechar = document.querySelector('.fechar');
const btnSair = document.getElementById('btnSair');
const btnBuscar = document.getElementById('btnBuscar');
const btnLimparBusca = document.getElementById('btnLimparBusca');
const inputBusca = document.getElementById('inputBusca');

// Modal de Movimentações
const btnMovimentacoes = document.getElementById('btnMovimentacoes');
const modalMovimentacoes = document.getElementById('modalMovimentacoes');
const fecharMovimentacoes = document.querySelector('.fechar-movimentacoes');
const filtroTipo = document.getElementById('filtroTipoMovimentacao');
const inputBuscaMov = document.getElementById('inputBuscaMovimentacoes');
const btnBuscaMov = document.getElementById('btnBuscaMovimentacoes');
const btnLimparMov = document.getElementById('btnLimparMovimentacoes');

// ========== EVENT ==========
formProduto.addEventListener('submit', fazerCadastroProduto);
formEdicao.addEventListener('submit', salvarEdicaoProduto);
fechar.addEventListener('click', fecharModal);
btnSair.addEventListener('click', sairDaConta);
btnBuscar.addEventListener('click', buscarProduto);
btnLimparBusca.addEventListener('click', limparBusca);
window.addEventListener('click', fecharModalAoClicar);

// Event listeners para movimentações (filtram em tempo real)
btnMovimentacoes.addEventListener('click', abrirModalMovimentacoes);
fecharMovimentacoes.addEventListener('click', fecharModalMovimentacoes);
btnLimparMov.addEventListener('click', limparBuscaMovimentacoes);

// ATUALIZA EM TEMPO REAL
filtroTipo.addEventListener('change', filtrarMovimentacoes);
inputBuscaMov.addEventListener('keyup', filtrarMovimentacoes);
inputBuscaMov.addEventListener('input', filtrarMovimentacoes);

// ========== FUNCTIONS ==========

function injetarModalDetalhes() {
    if (!document.getElementById('modalDetalhesAlteracao')) {
        const modalHtml = `
            <div id="modalDetalhesAlteracao" class="modal">
                <div class="modal-content modal-detalhes">
                    <span class="fechar-detalhes" onclick="fecharModalDetalhes()">&times;</span>
                    <h2>🔍 Detalhes da Alteração</h2>
                    <div class="comparacao-container">
                        <div class="col-comparacao">
                            <h3>Original (Antes)</h3>
                            <p><strong>Nome:</strong> <span id="detalheAntesNome"></span></p>
                            <p><strong>Qtd:</strong> <span id="detalheAntesQtd"></span></p>
                            <p><strong>Preço:</strong> <span id="detalheAntesPreco"></span></p>
                            <p><strong>Categoria:</strong> <span id="detalheAntesCat"></span></p>
                        </div>
                        <div class="col-comparacao">
                            <h3>Modificado (Depois)</h3>
                            <p><strong>Nome:</strong> <span id="detalheDepoisNome"></span></p>
                            <p><strong>Qtd:</strong> <span id="detalheDepoisQtd"></span></p>
                            <p><strong>Preço:</strong> <span id="detalheDepoisPreco"></span></p>
                            <p><strong>Categoria:</strong> <span id="detalheDepoisCat"></span></p>
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.insertAdjacentHTML('beforeend', modalHtml);
    }
}

function injetarOpcaoFiltro() {
    if (filtroTipo && !filtroTipo.querySelector('option[value="alterado"]')) {
        const opt = document.createElement('option');
        opt.value = 'alterado';
        opt.textContent = '✏️ Alterações';
        filtroTipo.appendChild(opt);
    }
}

// ========== FUNCTION ==========

function fazerCadastroProduto(event) {
    event.preventDefault();
    
    const nome = document.getElementById('nomeProduto').value.trim();
    const quantidade = parseInt(document.getElementById('quantidadeProduto').value);
    const preco = parseFloat(document.getElementById('precoProduto').value);
    const categoria = document.getElementById('categoriaProduto').value.trim();
    
    if (nome === '' || isNaN(quantidade) || isNaN(preco) || categoria === '') {
        alert('❌ Por favor, preencha todos os campos!');
        return;
    }
    
    if (quantidade <= 0 || preco <= 0) {
        alert('❌ Quantidade e preço devem ser maiores que 0!');
        return;
    }
    
    const usuarioResponsavel = localStorage.getItem('usuarioLogado');
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    
    const novoID = produtos.length > 0 
        ? Math.max(...produtos.map(p => p.id)) + 1 
        : 1;
    
    const novoProduto = {
        id: novoID,
        nome: nome,
        quantidade: quantity = quantidade,
        preco: preco,
        categoria: categoria,
        usuario: usuarioResponsavel,
        dataCadastro: new Date().toLocaleString('pt-BR')
    };
    
    produtos.push(novoProduto);
    localStorage.setItem('produtos', JSON.stringify(produtos));
    
    registrarMovimentacao({
        tipo: 'entrada',
        produto: nome,
        quantidade: quantidade,
        categoria: categoria,
        usuario: usuarioResponsavel,
        dataHora: new Date().toLocaleString('pt-BR')
    });
    
    alert(`✅ Produto "${nome}" cadastrado com sucesso!`);
    formProduto.reset();
    carregarProdutos();
}

function registrarMovimentacao(movimentacao) {
    const movimentacoes = JSON.parse(localStorage.getItem('movimentacoes')) || [];
    movimentacoes.push(movimentacao);
    localStorage.setItem('movimentacoes', JSON.stringify(movimentacoes));
}

function carregarProdutos() {
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    const corpoProdutos = document.getElementById('corpoProdutos');
    
    corpoProdutos.innerHTML = '';
    
    if (produtos.length === 0) {
        corpoProdutos.innerHTML = '<tr class="sem-dados"><td colspan="7">Nenhum produto cadastrado ainda</td></tr>';
        return;
    }
    
    produtos.forEach(produto => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
            <td>${produto.id}</td>
            <td>${produto.nome}</td>
            <td>${produto.quantidade}</td>
            <td>R$ ${produto.preco.toFixed(2).replace('.', ',')}</td>
            <td>${produto.categoria}</td>
            <td>${produto.usuario}</td>
            <td class="acoes">
                <button class="btn-editar" onclick="abrirModalEdicao(${produto.id})">Editar</button>
                <button class="btn-deletar" onclick="deletarProduto(${produto.id})">Deletar</button>
            </td>
        `;
        corpoProdutos.appendChild(linha);
    });
}

function abrirModalEdicao(id) {
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    const produto = produtos.find(p => p.id === id);
    
    if (!produto) {
        alert('❌ Produto não encontrado!');
        return;
    }
    
    document.getElementById('editNomeProduto').value = produto.nome;
    document.getElementById('editQuantidadeProduto').value = produto.quantidade;
    document.getElementById('editPrecoProduto').value = produto.preco;
    document.getElementById('editCategoriaProduto').value = produto.categoria;
    
    produtoEmEdicao = id;
    modalEdicao.style.display = 'block';
}

function salvarEdicaoProduto(event) {
    event.preventDefault();
    
    if (produtoEmEdicao === null) {
        alert('❌ Erro ao atualizar produto!');
        return;
    }
    
    const nome = document.getElementById('editNomeProduto').value.trim();
    const quantidade = parseInt(document.getElementById('editQuantidadeProduto').value);
    const preco = parseFloat(document.getElementById('editPrecoProduto').value);
    const categoria = document.getElementById('editCategoriaProduto').value.trim();
    
    if (nome === '' || isNaN(quantidade) || isNaN(preco) || categoria === '') {
        alert('❌ Por favor, preencha todos os campos!');
        return;
    }
    
    if (quantidade <= 0 || preco <= 0) {
        alert('❌ Quantidade e preço devem ser maiores que 0!');
        return;
    }
    
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    const produto = produtos.find(p => p.id === produtoEmEdicao);
    
    if (produto) {
        // Guarda os dados ANTIGOS antes de alterar
        const antes = {
            nome: produto.nome,
            quantidade: produto.quantidade,
            preco: produto.preco,
            categoria: produto.categoria
        };

        const depois = {
            nome: nome,
            quantidade: quantidade,
            preco: preco,
            categoria: categoria
        };
        
        // Atualiza o produto
        produto.nome = nome;
        produto.quantidade = quantidade;
        produto.preco = preco;
        produto.categoria = categoria;
        
        localStorage.setItem('produtos', JSON.stringify(produtos));
        
        // REGISTRA A MOVIMENTAÇÃO DO TIPO 'ALTERADO' COM OS DETALHES
        registrarMovimentacao({
            tipo: 'alterado',
            produto: nome,
            quantidade: quantidade,
            categoria: categoria,
            usuario: localStorage.getItem('usuarioLogado'),
            dataHora: new Date().toLocaleString('pt-BR'),
            detalhes: { antes, depois }
        });
        
        alert('✅ Produto atualizado com sucesso!');
        fecharModal();
        carregarProdutos();
    }
}

function deletarProduto(id) {
    const confirmar = confirm('Tem certeza que deseja deletar este produto?');
    if (!confirmar) return;
    
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    const produtoDeletado = produtos.find(p => p.id === id);
    
    if (produtoDeletado) {
        registrarMovimentacao({
            tipo: 'saida',
            produto: produtoDeletado.nome,
            quantidade: produtoDeletado.quantidade,
            categoria: produtoDeletado.categoria,
            usuario: localStorage.getItem('usuarioLogado'),
            dataHora: new Date().toLocaleString('pt-BR')
        });
    }
    
    const novosProdutos = produtos.filter(p => p.id !== id);
    localStorage.setItem('produtos', JSON.stringify(novosProdutos));
    
    alert('✅ Produto deletado com sucesso!');
    carregarProdutos();
}

function buscarProduto() {
    const termoBusca = inputBusca.value.trim().toLowerCase();
    
    if (termoBusca === '') {
        alert('❌ Digite um nome para buscar!');
        return;
    }
    
    const produtos = JSON.parse(localStorage.getItem('produtos')) || [];
    const produtosFiltrados = produtos.filter(p => 
        p.nome.toLowerCase().includes(termoBusca)
    );
    
    const corpoProdutos = document.getElementById('corpoProdutos');
    corpoProdutos.innerHTML = '';
    
    if (produtosFiltrados.length === 0) {
        corpoProdutos.innerHTML = '<tr class="sem-dados"><td colspan="7">Nenhum produto encontrado</td></tr>';
        return;
    }
    
    produtosFiltrados.forEach(produto => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
            <td>${produto.id}</td>
            <td>${produto.nome}</td>
            <td>${produto.quantidade}</td>
            <td>R$ ${produto.preco.toFixed(2).replace('.', ',')}</td>
            <td>${produto.categoria}</td>
            <td>${produto.usuario}</td>
            <td class="acoes">
                <button class="btn-editar" onclick="abrirModalEdicao(${produto.id})">Editar</button>
                <button class="btn-deletar" onclick="deletarProduto(${produto.id})">Deletar</button>
            </td>
        `;
        corpoProdutos.appendChild(linha);
    });
}

function limparBusca() {
    inputBusca.value = '';
    carregarProdutos();
}

function abrirModalMovimentacoes() {
    modalMovimentacoes.style.display = 'block';
    filtroTipo.value = '';
    inputBuscaMov.value = '';
    carregarTodasMovimentacoes();
}

function carregarTodasMovimentacoes() {
    const movimentacoes = JSON.parse(localStorage.getItem('movimentacoes')) || [];
    renderizarLinhasMovimentacao(movimentacoes);
}

function filtrarMovimentacoes() {
    const movimentacoes = JSON.parse(localStorage.getItem('movimentacoes')) || [];
    const tipoFiltro = filtroTipo.value;
    const termoBusca = inputBuscaMov.value.trim().toLowerCase();
    
    let movimentacoesFiltradas = movimentacoes;
    if (tipoFiltro) {
        movimentacoesFiltradas = movimentacoesFiltradas.filter(m => m.tipo === tipoFiltro);
    }
    
    if (termoBusca) {
        movimentacoesFiltradas = movimentacoesFiltradas.filter(m => 
            m.produto.toLowerCase().includes(termoBusca)
        );
    }
    
    renderizarLinhasMovimentacao(movimentacoesFiltradas);
}

// Nova função auxiliar para renderizar as linhas de movimentação com suporte a "alterado"
function renderizarLinhasMovimentacao(lista) {
    const corpoMovimentacoes = document.getElementById('corpoMovimentacoes');
    corpoMovimentacoes.innerHTML = '';
    
    if (lista.length === 0) {
        corpoMovimentacoes.innerHTML = '<tr class="sem-dados"><td colspan="6">Nenhuma movimentação encontrada</td></tr>';
        return;
    }
    
    // Mapeia adicionando o index original do LocalStorage antes de dar reverse
    const listaComIndex = lista.map((mov, originalIdx) => {
        const movimentacoesGlobais = JSON.parse(localStorage.getItem('movimentacoes')) || [];
        // Encontra o index exato no array global original para não abrir o item errado
        const globalIndex = movimentacoesGlobais.findIndex(g => g.dataHora === mov.dataHora && g.produto === mov.produto && g.tipo === mov.tipo);
        return { ...mov, globalIndex };
    });
    
    listaComIndex.reverse().forEach(mov => {
        const linha = document.createElement('tr');
        
        let classeLinha = '';
        let iconeTipo = '';
        
        if (mov.tipo === 'entrada') {
            classeLinha = 'entrada';
            iconeTipo = '➕ Entrada';
        } else if (mov.tipo === 'saida') {
            classeLinha = 'saida';
            iconeTipo = '➖ Saída';
        } else if (mov.tipo === 'alterado') {
            classeLinha = 'alterado';
            iconeTipo = '✏️ Alterado';
            
            // Torna a linha visualmente clicável
            linha.setAttribute('onclick', `verDetalhesAlteracao(${mov.globalIndex})`);
            linha.style.cursor = 'pointer';
            linha.title = "Clique para ver o histórico desta alteração";
        }
        
        linha.className = `movimentacao-${classeLinha}`;
        linha.innerHTML = `
            <td>${mov.dataHora}</td>
            <td><span class="badge-${mov.tipo}">${iconeTipo}</span></td>
            <td>${mov.produto}</td>
            <td>${mov.quantidade}</td>
            <td>${mov.categoria}</td>
            <td>${mov.usuario}</td>
        `;
        corpoMovimentacoes.appendChild(linha);
    });
}

// Abre o modal comparativo das alterações passadas
function verDetalhesAlteracao(index) {
    const movimentacoes = JSON.parse(localStorage.getItem('movimentacoes')) || [];
    const mov = movimentacoes[index];
    
    if (!mov || !mov.detalhes) {
        alert('❌ Detalhes desta alteração não encontrados!');
        return;
    }
    
    const antes = mov.detalhes.antes;
    const depois = mov.detalhes.depois;
    
    document.getElementById('detalheAntesNome').textContent = antes.nome;
    document.getElementById('detalheAntesQtd').textContent = antes.quantidade;
    document.getElementById('detalheAntesPreco').textContent = `R$ ${antes.preco.toFixed(2).replace('.', ',')}`;
    document.getElementById('detalheAntesCat').textContent = antes.categoria;
    
    document.getElementById('detalheDepoisNome').textContent = depois.nome;
    document.getElementById('detalheDepoisQtd').textContent = depois.quantidade;
    document.getElementById('detalheDepoisPreco').textContent = `R$ ${depois.preco.toFixed(2).replace('.', ',')}`;
    document.getElementById('detalheDepoisCat').textContent = depois.categoria;
    
    // Destaca o que mudou em vermelho (antes) e verde (depois)
    destacarDiferenca('detalheAntesNome', 'detalheDepoisNome');
    destacarDiferenca('detalheAntesQtd', 'detalheDepoisQtd');
    destacarDiferenca('detalheAntesPreco', 'detalheDepoisPreco');
    destacarDiferenca('detalheAntesCat', 'detalheDepoisCat');
    
    document.getElementById('modalDetalhesAlteracao').style.display = 'block';
}

function destacarDiferenca(idAntes, idDepois) {
    const elAntes = document.getElementById(idAntes);
    const elDepois = document.getElementById(idDepois);
    
    if (elAntes.textContent !== elDepois.textContent) {
        elAntes.style.color = '#f44336';
        elAntes.style.fontWeight = 'bold';
        elDepois.style.color = '#4CAF50';
        elDepois.style.fontWeight = 'bold';
    } else {
        elAntes.style.color = '';
        elAntes.style.fontWeight = '';
        elDepois.style.color = '';
        elDepois.style.fontWeight = '';
    }
}

function fecharModalDetalhes() {
    document.getElementById('modalDetalhesAlteracao').style.display = 'none';
}

function fecharModalMovimentacoes() {
    modalMovimentacoes.style.display = 'none';
}

function limparBuscaMovimentacoes() {
    inputBuscaMov.value = '';
    filtroTipo.value = '';
    carregarTodasMovimentacoes();
}

function fecharModal() {
    modalEdicao.style.display = 'none';
    produtoEmEdicao = null;
    formEdicao.reset();
}

function fecharModalAoClicar(event) {
    if (event.target == modalEdicao) {
        fecharModal();
    }
    if (event.target == modalMovimentacoes) {
        fecharModalMovimentacoes();
    }
    if (event.target == document.getElementById('modalDetalhesAlteracao')) {
        fecharModalDetalhes();
    }
}

function sairDaConta() {
    const confirmar = confirm('Tem certeza que deseja sair?');
    if (confirmar) {
        localStorage.removeItem('usuarioLogado');
        localStorage.removeItem('horaLogin');
        alert('✅ Você saiu com sucesso!');
        window.location.href = './login.html';
    }
}

inputBusca.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        buscarProduto();
    }
});