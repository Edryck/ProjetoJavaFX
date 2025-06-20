create database GerenciaAi; 
use GerenciaAi;

create table Produto (
	idProduto int auto_increment primary key,
	marcaProduto varchar(45),
	tipoProduto varchar(45),
	precoVenda decimal(3,2),
    precoCusto decimal(3,2)
);

create table Transacao (
	idTransacao int auto_increment primary key,
    tipoTransacao varchar(15),
    valorTransacao decimal(4,2),
    dataTransacao date,
    descricaoTransacao varchar(100)
);

create table ProdutoTransacao (
	idProduto int,
    idTransacao int,
    foreign key (idProduto) references Produto(idProduto),
    foreign key (idTransacao) references Transacao(idTransacao)
);