/**
/ JokersRolex
**/
create database dbjoker;
use dbjoker;

create table clientes (
idcli int primary key auto_increment, 
nome varchar(30) not null, 
fone varchar(11) not null unique, 
email varchar(20) not null,
cep varchar(8) not null,
endereco varchar(50) not null,
numero varchar (7) not null,
comp varchar (30),
bairro varchar (20) not null,
cidade varchar (20) not null,
uf varchar (2) not null
);

create table usuarios (
iduser int primary key auto_increment, 
nome varchar(30) not null, 
login varchar(20) not null unique, 
senha varchar(250) not null ,
perfil varchar(5) not null);

insert into usuarios (nome, login, senha, perfil)
values('Admin','admin','admin','admin');

create table tecnicos (
idtec int primary key auto_increment,
nome varchar(30) not null unique);

create table pecas (
peca varchar(50) not null,
idpecas int primary key auto_increment,
dataen timestamp default current_timestamp,
idfor int,
barra varchar(50) not null unique,
descri varchar(200),
estoque int not null,
estoquemin int not null,
valor decimal(10,2),
medida varchar(20) not null,
lugar varchar(50),
validade date,
foreign key(idfor) references fornecedores(idfor)
);

create table fornecedores (
idfor int primary key auto_increment,
cnpj varchar(50) not null unique,
site varchar(50),  
nome varchar(30) not null, 
fone varchar(11) not null unique,
email varchar(20) not null,
cep varchar(8) not null,
endereco varchar(50) not null,
numero varchar (7) not null,
comp varchar (30),
bairro varchar (20) not null,
cidade varchar (20) not null,
uf varchar (2) not null
);

create table servicos (
os int primary key auto_increment,
relogio varchar (200) not null,
serie varchar (20),
defeito varchar (200) not null,
diagnostico varchar (200),
idtec int,
statusOS varchar (30) not null,
valor decimal (10,2),
obs varchar (200),
dataOS timestamp default current_timestamp,
dataSaida date,
idcli int not null,
usuario varchar(30) not null,
foreign key(idcli) references clientes(idcli),
foreign key(idtec) references tecnicos(idtec)
);