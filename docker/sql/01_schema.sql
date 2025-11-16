-- 1. Tabla Clientes (LIMPIA, sin password ni rol)
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20)
);

-- 2. Tabla Roles (NUEVA)
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Tabla Usuarios (NUEVA)
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- 4. Tabla Vehiculos (Sin cambios)
CREATE TABLE IF NOT EXISTS vehiculos (
    id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(255),
    matricula VARCHAR(7) NOT NULL UNIQUE,
    modelo VARCHAR(255),
    pasajeros INT NOT NULL,
    precio_dia DECIMAL(10,2) NOT NULL
);

-- 5. Tabla Alquiler (Sin cambios)
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

-- 6. Insertar los Roles (IMPORTANTE)
INSERT IGNORE INTO roles (id, nombre) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, nombre) VALUES (2, 'ROLE_USER');