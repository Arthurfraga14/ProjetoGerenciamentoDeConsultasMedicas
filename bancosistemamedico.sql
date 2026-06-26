CREATE DATABASE IF NOT EXISTS welson;
USE welson;

CREATE TABLE IF NOT EXISTS paciente (
    idpaciente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS medico (
    idmedico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS consulta (
    idconsulta INT AUTO_INCREMENT PRIMARY KEY,
    medico_idmedico INT,
    paciente_idpaciente INT,
    data_consulta DATE NOT NULL,
    hora_consulta TIME NOT NULL,
    observacao TEXT
    );