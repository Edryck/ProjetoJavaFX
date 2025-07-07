-- Cria o Banco de dados e usa ele
create database finstock;
use finstock;

-- Tabela de usuários
create table usuarios (
	usuarioId int  not null auto_increment primary key unique,
    nomeUsuario varchar(75)  not null,
	emailUsuario varchar(75)  not null unique,
	senhaUsuario varchar(75)  not null
);

-- Tabela de produto
create table produto ( 
	idProduto  varchar(15)  not null,
	marca  varchar(45)  not null,
	descricao  varchar(75)  not null,
	quantidade  int  not null,
	categoria  varchar(45)  not null,
	precoCusto  double  not null,
	precoVenda  double  not null,
	ativo  tinyint(1) default null,
	fornecedor  varchar(75)  not null,
	imagem  varchar(300) default null -- Por padrão é nula por que não é nescessário ter a foto do produto
);

-- Tabela de venda
create table venda (
	idVenda int not null auto_increment primary key unique,
    nomeCliente varchar(75) not null,
	dataVenda datetime  not null,
    valorTotal decimal(10,2)  not null,
    formaPagamento varchar(45)  not null,
    quantidadeParcelas int null,
    statusVenda varchar(45) not null
);

-- Tabela de item da venda
create table itemVenda (
	idItemVenda int unsigned not null auto_increment unique,
    idVenda int unsigned not null,
    idProduto varchar(15) not null, 
    quantidadeIV int not null,
    precoUnitario decimal(10,2) not null,
    primary key (idItemVenda),
    foreign key (idVenda) references venda (idVenda),
    foreign key (idProduto) references produto (idProduto)
);

create table fornecedor (
	idFornecedor int not null auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) null, 
    telefone varchar(20) null, 
    statusForn varchar(20) not null
);
    
create table pedidos_compra (
  id_pedido int not null auto_increment,
  idFornecedor int not null, -- Chave estrangeira que liga ao fornecedor
  dataPedido date not null,
  dataEntregPrevista date null,
  valorTotalNota decimal(10, 2) not null,
  statusPedido varchar(20) not null, -- PENDENTE, FINALIZADO, CANCELADO
  primary key (id_pedido),
  foreign key (idFornecedor) references fornecedor(idFornecedor)
);

create table itensPedido (
	pedidoId int not null auto_increment primary key,
	idProduto varchar(15) not null,
	quantidade int not null,
	precoPedido decimal(10,2),
	foreign key (idProduto) references produto(idProduto)
);