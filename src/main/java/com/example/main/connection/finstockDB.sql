create database finstock;
use finstock;

create table usuarios (
	usuarioId int  not null auto_increment primary key unique,
    nomeUsuario varchar(75)  not null,
	emailUsuario varchar(75)  not null unique,
	senhaUsuario varchar(75)  not null
);

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
	imagem  varchar(300) default null
);

create table venda (
	idVenda int  not null auto_increment primary key unique,
	dataVenda datetime  not null,
    valorTotal decimal(4,2)  not null,
    metodoPagamento varchar(45)  not null
);

create table itemVenda (
	idIV int  not null auto_increment primary key unique,
    idVenda int  not null unique auto_increment,
    idProduto varchar(15)  not null, 
    quantidadeIV int  not null,
    precoUnitario decimal(4,2)  not null,
    foreign key (idVenda) references venda (idVenda),
    foreign key (idProduto) references produto (idProduto)
);