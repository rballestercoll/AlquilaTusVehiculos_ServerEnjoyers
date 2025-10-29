CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS vehiculos (
    id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(255),
    matricula VARCHAR(7) NOT NULL UNIQUE,
    modelo VARCHAR(255),
    pasajeros INT NOT NULL,
    precio_dia DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS alquiler (
    id_alquiler BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    vehiculo_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio_alquiler DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_alquiler_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_alquiler_vehiculo FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id_vehiculo)
);
