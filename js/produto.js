// // ========== VERIFICAÇÃO INICIAL ==========
// window.addEventListener('load', function() {
//     const usuarioId = localStorage.getItem('usuarioId');
//     const nomeUsuario = localStorage.getItem('usuarioLogado');

//     if (!usuarioId) {
//         alert('Você precisa estar logado para acessar esta página!');
//         window.location.href = './login.html';
//         return;
//     }

//     document.getElementById('nomeUsuario').textContent = `Bem-vindo, ${nomeUsuario}!`;

//     injetarModalDetalhes();
//     injetarOpcaoFiltro();
//     carregarProdutos();
// });

// ========== VARIÁVEIS GLOBAIS ==========
let produtoEmEdicao = null;
let produtosGlobais = [];
let movimentacoesGlobais = [];

// ========== DOM ==========
const formProduto = document.getElementById('formProduto');
const formEdicao = document.getElementById('formEdicao');
const modalEdicao = document.getElementById('modalEdicao');
const fechar = document.querySelector('.fechar');
const btnSair = document.getElementById('btnSair');
const btnBuscar = document.getElementById('btnBuscar');
const btnLimparBusca = document.getElementById('btnLimparBusca');
const inputBusca = document.getElementById('inputBusca');

const btnMovimentacoes = document.getElementById('btnMovimentacoes');
const modalMovimentacoes = document.getElementById('modalMovimentacoes');
const fecharMovimentacoes = document.querySelector('.fechar-movimentacoes');
const filtroTipo = document.getElementById('filtroTipoMovimentacao');
const inputBuscaMov = document.getElementById('inputBuscaMovimentacoes');
const btnLimparMov = document.getElementById('btnLimparMovimentacoes');

// ========== EVENTOS ==========
formProduto.addEventListener('submit', fazerCadastroProduto);
formEdicao.addEventListener('submit', salvarEdicaoProduto);
fechar.addEventListener('click', fecharModal);
btnSair.addEventListener('click', sairDaConta);
btnBuscar.addEventListener('click', buscarProduto);
btnLimparBusca.addEventListener('click', limparBusca);
window.addEventListener('click', fecharModalAoClicar);

btnMovimentacoes.addEventListener('click', abrirModalMovimentacoes);
fecharMovimentacoes.addEventListener('click', fecharModalMovimentacoes);
btnLimparMov.addEventListener('click', limparBuscaMovimentacoes);

filtroTipo.addEventListener('change', filtrarMovimentacoes);
inputBuscaMov.addEventListener('keyup', filtrarMovimentacoes);
inputBuscaMov.addEventListener('input', filtrarMovimentacoes);
inputBusca.addEventListener('keypress', function(event) { if (event.key === 'Enter') buscarProduto(); });


// ========== FUNÇÕES DE INTERFACE (MODAIS) ==========
function injetarModalDetalhes() {
    if (!document.getElementById('modalDetalhesAlteracao')) {
        const modalHtml = `
            <div id="modalDetalhesAlteracao" class="modal">
                <div class="modal-content modal-detalhes">
                    <span class="fechar-detalhes" onclick="fecharModalDetalhes()" style="float:right; cursor:pointer; font-size:24px;">&times;</span>
                    <h2>🔍 Detalhes da Alteração</h2>
                    <div class="comparacao-container" style="display:flex; justify-content:space-between; margin-top:20px;">
                        <div class="col-comparacao" style="width:45%; background:#f9f9f9; padding:15px; border-radius:8px;">
                            <h3 style="color:#d32f2f;">Original (Antes)</h3>
                            <p><strong>Nome:</strong> <span id="detalheAntesNome"></span></p>
                            <p><strong>Qtd:</strong> <span id="detalheAntesQtd"></span></p>
                            <p><strong>Preço:</strong> <span id="detalheAntesPreco"></span></p>
                            <p><strong>Categoria:</strong> <span id="detalheAntesCat"></span></p>
                        </div>
                        <div class="col-comparacao" style="width:45%; background:#e8f5e9; padding:15px; border-radius:8px;">
                            <h3 style="color:#388e3c;">Modificado (Depois)</h3>
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


// ========== FUNÇÕES DE BANCO DE DADOS (API REST) ==========
async function fazerCadastroProduto(event) {
    event.preventDefault();
    
    const nome = document.getElementById('nomeProduto').value.trim();
    const quantidade = parseInt(document.getElementById('quantidadeProduto').value);
    const preco = parseFloat(document.getElementById('precoProduto').value);
    const categoria = document.getElementById('categoriaProduto').value.trim();
    
    if (nome === '' || isNaN(quantidade) || isNaN(preco) || categoria === '') {
        alert('❌ Por favor, preencha todos os campos!');
        return;
    }
    
    const novoProduto = { nomeDoProduto: nome, quantidade, preco, categoria };
    
    try {
        const resposta = await fetch('http://localhost:8080/api/produto/cadastrar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoProduto)
        });

        if (resposta.ok) {
            await resposta.text(); 
            
            const resLista = await fetch('http://localhost:8080/api/produto/listar');
            const lista = await resLista.json();
            const produtoCriadoNoBanco = lista[lista.length - 1];
            
            // Registra a ENTRADA
            if (produtoCriadoNoBanco) {
                await registrarMovimentacaoNoBanco({
                    tipo: 'entrada',
                    produtoId: produtoCriadoNoBanco.id,
                    quantidade: quantidade
                });
            }
            
            alert(`✅ Produto "${nome}" cadastrado com sucesso!`);
            formProduto.reset();
            carregarProdutos();
        } else {
            alert('❌ Erro ao salvar o produto no servidor.');
        }
    } catch (erro) {
        console.error(erro);
        alert('❌ Falha na conexão com o servidor!');
    }
}

async function carregarProdutos() {
    const corpoProdutos = document.getElementById('corpoProdutos');
    corpoProdutos.innerHTML = '';
    
    try {
        const resposta = await fetch('http://localhost:8080/api/produto/listar');
        produtosGlobais = await resposta.json();
        
        if (produtosGlobais.length === 0) {
            corpoProdutos.innerHTML = '<tr class="sem-dados"><td colspan="7">Nenhum produto cadastrado ainda</td></tr>';
            return;
        }
        
        const usuarioVisual = localStorage.getItem('usuarioLogado') || 'Sistema';

        produtosGlobais.forEach(produto => {
            const linha = document.createElement('tr');
            linha.innerHTML = `
                <td>${produto.id}</td>
                <td>${produto.nomeDoProduto}</td>
                <td>${produto.quantidade}</td>
                <td>R$ ${produto.preco.toFixed(2).replace('.', ',')}</td>
                <td>${produto.categoria}</td>
                <td>${usuarioVisual}</td>
                <td class="acoes">
                    <button class="btn-editar" onclick="abrirModalEdicao(${produto.id})">Editar</button>
                    <button class="btn-deletar" onclick="deletarProduto(${produto.id})">Deletar</button>
                </td>
            `;
            corpoProdutos.appendChild(linha);
        });
    } catch (erro) {
        console.error("Erro ao carregar produtos:", erro);
    }
}

function abrirModalEdicao(id) {
    const produto = produtosGlobais.find(p => p.id === id);
    if (!produto) { alert('❌ Produto não encontrado!'); return; }
    
    document.getElementById('editNomeProduto').value = produto.nomeDoProduto;
    document.getElementById('editQuantidadeProduto').value = produto.quantidade;
    document.getElementById('editPrecoProduto').value = produto.preco;
    document.getElementById('editCategoriaProduto').value = produto.categoria;
    
    produtoEmEdicao = id;
    modalEdicao.style.display = 'block';
}

async function salvarEdicaoProduto(event) {
    event.preventDefault();
    if (produtoEmEdicao === null) return;
    
    const nome = document.getElementById('editNomeProduto').value.trim();
    const quantidade = parseInt(document.getElementById('editQuantidadeProduto').value);
    const preco = parseFloat(document.getElementById('editPrecoProduto').value);
    const categoria = document.getElementById('editCategoriaProduto').value.trim();
    
    if (nome === '' || isNaN(quantidade) || isNaN(preco) || categoria === '') {
        alert('❌ Por favor, preencha todos os campos!'); return;
    }
    
    const produtoAntigo = produtosGlobais.find(p => p.id === produtoEmEdicao);
    const dadosNovos = { nomeDoProduto: nome, quantidade, preco, categoria };
    
    try {
        const resposta = await fetch(`http://localhost:8080/api/produto/atualizar/${produtoEmEdicao}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosNovos)
        });

        if (resposta.ok) {
            await resposta.text();
            
            const antes = { nome: produtoAntigo.nomeDoProduto, quantidade: produtoAntigo.quantidade, preco: produtoAntigo.preco, categoria: produtoAntigo.categoria };
            const depois = { nome, quantidade, preco, categoria };
            
            // Registra a ALTERAÇÃO enviando os detalhes visuais
            await registrarMovimentacaoNoBanco({
                tipo: 'alterado',
                produtoId: produtoEmEdicao,
                quantidade: quantidade,
                detalhesVisuais: { antes, depois }
            });
            
            alert('✅ Produto atualizado com sucesso!');
            fecharModal();
            carregarProdutos();
        }
    } catch (erro) { console.error(erro); }
}

async function deletarProduto(id) {
    const confirmar = confirm('Tem certeza que deseja deletar este produto?');
    if (!confirmar) return;
    
    const produto = produtosGlobais.find(p => p.id === id);
    
    try {
        if (produto) {
            // Registra a SAÍDA
            await registrarMovimentacaoNoBanco({
                tipo: 'saida',
                produtoId: id,
                quantidade: produto.quantidade
            });
        }
        
        const resposta = await fetch(`http://localhost:8080/api/produto/deletar/${id}`, { method: 'DELETE' });
        
        if (resposta.ok) {
            await resposta.text();
            alert('✅ Produto deletado com sucesso!');
            carregarProdutos();
        } else {
            alert('❌ Não é possível deletar, este produto já tem histórico de movimentação no Banco de Dados!');
        }
    } catch (erro) { console.error(erro); }
}

function buscarProduto() {
    const termoBusca = inputBusca.value.trim().toLowerCase();
    const corpoProdutos = document.getElementById('corpoProdutos');
    corpoProdutos.innerHTML = '';
    
    if (termoBusca === '') return carregarProdutos();
    
    const filtrados = produtosGlobais.filter(p => p.nomeDoProduto.toLowerCase().includes(termoBusca));
    
    if (filtrados.length === 0) {
        corpoProdutos.innerHTML = '<tr class="sem-dados"><td colspan="7">Nenhum produto encontrado</td></tr>';
        return;
    }
    
    const usuarioVisual = localStorage.getItem('usuarioLogado') || 'Sistema';
    filtrados.forEach(produto => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
            <td>${produto.id}</td>
            <td>${produto.nomeDoProduto}</td>
            <td>${produto.quantidade}</td>
            <td>R$ ${produto.preco.toFixed(2).replace('.', ',')}</td>
            <td>${produto.categoria}</td>
            <td>${usuarioVisual}</td>
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


// ========== MOVIMENTAÇÕES ==========
async function registrarMovimentacaoNoBanco(dados) {
    const usuarioId = localStorage.getItem('usuarioId') || 1;
    
    const movParaOBanco = {
        tipo_de_movimentacao: dados.tipo,
        quantidade: dados.quantidade,
        data_da_movimentacao: "gerado_pelo_java", 
        produto: { id: dados.produtoId },
        usuario: { id_usuario: parseInt(usuarioId) }
    };
    
    try {
        const res = await fetch('http://localhost:8080/api/movimentacao/cadastrar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(movParaOBanco)
        });

        if (res.ok) {
            const movOficialDoBanco = await res.json();
            
            // Salvando os dados do "Antes e Depois" no LocalStorage do Navegador
            if (dados.detalhesVisuais && movOficialDoBanco.produto) {
                const idChave = `${movOficialDoBanco.produto.id}_${movOficialDoBanco.data_da_movimentacao}`;
                const detalhesSalvos = JSON.parse(localStorage.getItem('detalhesAlteracoes')) || {};
                detalhesSalvos[idChave] = dados.detalhesVisuais;
                localStorage.setItem('detalhesAlteracoes', JSON.stringify(detalhesSalvos));
            }
        }
    } catch (erro) {
        console.error("Erro ao registrar movimentação:", erro);
    }
}

function abrirModalMovimentacoes() {
    modalMovimentacoes.style.display = 'block';
    filtroTipo.value = '';
    inputBuscaMov.value = '';
    carregarTodasMovimentacoes();
}

async function carregarTodasMovimentacoes() {
    const corpo = document.getElementById('corpoMovimentacoes');
    corpo.innerHTML = '<tr><td colspan="6">A carregar dados do banco...</td></tr>';
    
    try {
        const resposta = await fetch('http://localhost:8080/api/movimentacao/listar');
        movimentacoesGlobais = await resposta.json();
        renderizarLinhasMovimentacao(movimentacoesGlobais);
    } catch(e) { console.error(e); }
}

function renderizarLinhasMovimentacao(lista) {
    const corpoMovimentacoes = document.getElementById('corpoMovimentacoes');
    corpoMovimentacoes.innerHTML = '';
    
    if (lista.length === 0) {
        corpoMovimentacoes.innerHTML = '<tr class="sem-dados"><td colspan="6">Nenhuma movimentação encontrada na base de dados</td></tr>';
        return;
    }
    
    lista.reverse().forEach(mov => {
        const linha = document.createElement('tr');
        
        let classeLinha = '';
        let iconeTipo = '';
        
        if (mov.tipo_de_movimentacao === 'entrada') {
            classeLinha = 'entrada'; iconeTipo = '➕ Entrada';
        } else if (mov.tipo_de_movimentacao === 'saida') {
            classeLinha = 'saida'; iconeTipo = '➖ Saída';
        } else if (mov.tipo_de_movimentacao === 'alterado') {
            classeLinha = 'alterado'; iconeTipo = '✏️ Alterado';
            
            // Habilita o clique para ver o Antes e Depois
            linha.setAttribute('onclick', `verDetalhesAlteracao(${mov.id_da_movimentacao})`);
            linha.style.cursor = 'pointer';
            linha.title = "Clique para ver o histórico desta alteração";
        }
        
        const nomeProduto = mov.produto ? mov.produto.nomeDoProduto : 'Excluído';
        const catProduto = mov.produto ? mov.produto.categoria : '-';
        const nomeUsu = mov.usuario ? mov.usuario.login : 'Sistema';
        
        linha.className = `movimentacao-${classeLinha}`;
        linha.innerHTML = `
            <td>${mov.data_da_movimentacao}</td>
            <td><span class="badge-${mov.tipo_de_movimentacao}" style="font-weight:bold;">${iconeTipo}</span></td>
            <td>${nomeProduto}</td>
            <td>${mov.quantidade}</td>
            <td>${catProduto}</td>
            <td><strong>${nomeUsu}</strong></td>
        `;
        corpoMovimentacoes.appendChild(linha);
    });
}

function filtrarMovimentacoes() {
    const tipoFiltro = filtroTipo.value;
    const termoBusca = inputBuscaMov.value.trim().toLowerCase();
    let filtradas = movimentacoesGlobais;
    
    if (tipoFiltro) filtradas = filtradas.filter(m => m.tipo_de_movimentacao === tipoFiltro);
    if (termoBusca) filtradas = filtradas.filter(m => {
        const nomeProd = m.produto ? m.produto.nomeDoProduto.toLowerCase() : '';
        return nomeProd.includes(termoBusca);
    });
    
    renderizarLinhasMovimentacao(filtradas);
}

// ========== MODAL COMPARATIVO DE ALTERAÇÕES ==========
function verDetalhesAlteracao(idDaMovimentacao) {
    const mov = movimentacoesGlobais.find(m => m.id_da_movimentacao === idDaMovimentacao);
    if (!mov || !mov.produto) return;

    const idChave = `${mov.produto.id}_${mov.data_da_movimentacao}`;
    const detalhesSalvos = JSON.parse(localStorage.getItem('detalhesAlteracoes')) || {};
    const detalhes = detalhesSalvos[idChave];

    if (!detalhes) {
        alert('❌ Detalhes desta alteração não estão disponíveis no navegador.');
        return;
    }
    
    const antes = detalhes.antes;
    const depois = detalhes.depois;
    
    document.getElementById('detalheAntesNome').textContent = antes.nome;
    document.getElementById('detalheAntesQtd').textContent = antes.quantidade;
    document.getElementById('detalheAntesPreco').textContent = `R$ ${antes.preco.toFixed(2).replace('.', ',')}`;
    document.getElementById('detalheAntesCat').textContent = antes.categoria;
    
    document.getElementById('detalheDepoisNome').textContent = depois.nome;
    document.getElementById('detalheDepoisQtd').textContent = depois.quantidade;
    document.getElementById('detalheDepoisPreco').textContent = `R$ ${depois.preco.toFixed(2).replace('.', ',')}`;
    document.getElementById('detalheDepoisCat').textContent = depois.categoria;
    
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
        elAntes.style.color = '#d32f2f'; // Vermelho para o antigo
        elAntes.style.textDecoration = 'line-through';
        elDepois.style.color = '#388e3c'; // Verde para o novo
        elDepois.style.fontWeight = 'bold';
    } else {
        elAntes.style.color = ''; elAntes.style.textDecoration = '';
        elDepois.style.color = ''; elDepois.style.fontWeight = '';
    }
}

// ========== FUNÇÕES DE FECHAMENTO E LIMPEZA ==========
function fecharModalDetalhes() { 
    const modal = document.getElementById('modalDetalhesAlteracao');
    if(modal) modal.style.display = 'none'; 
}
function fecharModalMovimentacoes() { modalMovimentacoes.style.display = 'none'; }
function fecharModal() { modalEdicao.style.display = 'none'; produtoEmEdicao = null; formEdicao.reset(); }
function limparBuscaMovimentacoes() { inputBuscaMov.value = ''; filtroTipo.value = ''; carregarTodasMovimentacoes(); }

function fecharModalAoClicar(event) {
    if (event.target == modalEdicao) fecharModal();
    if (event.target == modalMovimentacoes) fecharModalMovimentacoes();
    if (event.target == document.getElementById('modalDetalhesAlteracao')) fecharModalDetalhes();
}

function sairDaConta() {
    if (confirm('Tem certeza que deseja sair?')) {
        localStorage.removeItem('usuarioLogado');
        localStorage.removeItem('usuarioId');
        alert('✅ Você saiu com sucesso!');
        window.location.href = './login.html';
    }
}