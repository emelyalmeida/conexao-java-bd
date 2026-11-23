drop database if exists db_estacionamento;
create database db_estacionamento;
use db_estacionamento;

create table tb_proprietario (
    cpf_proprietario bigint primary key not null,
    nm_proprietario varchar(60) not null,
    ds_profissao varchar(45)
);

create table tb_veiculo (
    cd_placa varchar(7) primary key not null,
    nm_marca varchar(45) not null,
    nm_modelo varchar(45) not null,
    ds_cor varchar(45),
    fk_cpf_proprietario bigint not null,
    foreign key (fk_cpf_proprietario)
        references tb_proprietario(cpf_proprietario)
);

alter table tb_veiculo
add ano_veiculo int;

insert into tb_proprietario
(cpf_proprietario, nm_proprietario, ds_profissao)
values
(45379172884, 'Mariana Oliveira', 'Designer'),
(94601256008, 'Lucas Ferreira', 'Engenheiro'),
(42240415881, 'Ana Martins', 'Professora'),
(34567890123, 'Pedro Santos', 'Analista');

insert into tb_veiculo
(cd_placa, nm_marca, nm_modelo, ds_cor, fk_cpf_proprietario, ano_veiculo)
values
('ABC1234', 'TOYOTA', 'COROLLA', 'PRATA', 45379172884, 2022),
('DEF5678', 'HONDA', 'CIVIC', 'PRETO', 94601256008, 2021),
('GHI9012', 'FIAT', 'ARGO', 'BRANCA', 42240415881, 2023),
('JKL3456', 'CHEVROLET', 'ONIX', 'AZUL', 45379172884, 2020),
('MNO7890', 'HYUNDAI', 'HB20', 'VERMELHA', 34567890123, 2024),
('PQR1234', 'VOLKSWAGEN', 'GOLF', 'CINZA', 94601256008, 2019),
('STU5678', 'RENAULT', 'DUSTER', 'PRETA', 42240415881, 2022),
('VWX9012', 'FORD', 'KA', 'BRANCA', 34567890123, 2018);

delimiter //

create function fn_conta_veiculos_por_cpf(p_cpf bigint)
returns int
deterministic
begin
    declare total int;

    select count(*) into total
    from tb_veiculo
    where fk_cpf_proprietario = p_cpf;

    return total;
end//

create procedure sp_cadastra_proprietario(
    in p_cpf bigint,
    in p_nome varchar(60),
    in p_profissao varchar(45)
)
begin
    if p_cpf is null or p_cpf <= 0 then
        signal sqlstate '45000'
        set message_text = 'Erro: O CPF do proprietário não pode ser vazio.';
    else
        insert into tb_proprietario (cpf_proprietario, nm_proprietario, ds_profissao)
        values (p_cpf, p_nome, p_profissao);

        select concat('Proprietário ', p_nome, ' cadastrado com sucesso!') as Mensagem;
    end if;
end//

create trigger trg_padroniza_veiculo_bi
before insert on tb_veiculo
for each row
begin
    set new.cd_placa = upper(new.cd_placa);
    set new.nm_marca = upper(new.nm_marca);
    set new.nm_modelo = upper(new.nm_modelo);
    set new.ds_cor = upper(new.ds_cor);
end//

delimiter ;

select fn_conta_veiculos_por_cpf(45379172884) as TOTAL_VEICULOS;
call sp_cadastra_proprietario(11122233344, 'Carla Souza', 'Médica');

select cd_placa as 'PLACA', nm_marca as 'MARCA',
       nm_modelo as 'MODELO', ds_cor as 'COR'
from tb_veiculo
where ds_cor is null;

select cd_placa as 'PLACA', nm_marca as 'MARCA',
       nm_modelo as 'MODELO', ano_veiculo as 'ANO'
from tb_veiculo
order by ano_veiculo desc;

select p.nm_proprietario as 'PROPRIETARIO',
       max(v.ano_veiculo) as 'ANO_MAIS_NOVO'
from tb_proprietario p
inner join tb_veiculo v
    on p.cpf_proprietario = v.fk_cpf_proprietario
group by p.nm_proprietario;

select p.nm_proprietario, v.nm_marca, v.nm_modelo
from tb_proprietario p
left join tb_veiculo v on p.cpf_proprietario = v.fk_cpf_proprietario
union
select p.nm_proprietario, v.nm_marca, v.nm_modelo
from tb_proprietario p
right join tb_veiculo v on p.cpf_proprietario = v.fk_cpf_proprietario;

update tb_veiculo
set ds_cor = 'CINZA'
where cd_placa = 'DEF5678';

delete from tb_veiculo
where cd_placa = 'VWX9012';
