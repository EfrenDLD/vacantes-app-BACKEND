SELECT 1;

INSERT INTO usuario (nombre, email, username, contrasenia, perfil, estatus)
VALUES ('Usuario de Prueba', 'usuario@prueba.com', 'usuario1', '123456', 'ADMIN', 'ACTIVO');

INSERT INTO usuario (nombre, email, username, contrasenia, perfil, estatus)
VALUES ('Luis Hernández', 'luis.hernandez@example.com', 'luisH', 'password123', 'ADMIN', 'ACTIVO');

SELECT 1;
INSERT INTO vacante (fecha_publicacion, nombre, descripcion, detalle, activo, id_usuario)
VALUES 
('2025-10-15', 'Ingeniero de Software Senior (Backend)', 'Desarrollo backend con Java y Spring Boot', 'Buscamos un ingeniero con experiencia en desarrollo de APIs REST, microservicios y bases de datos relacionales.', true, 1),
('2025-10-10', 'Desarrollador Frontend (React)', 'Desarrollo de interfaces con React', 'Se requiere experiencia en React, JavaScript moderno, CSS y trabajo con APIs REST.', true, 1),
('2025-09-30', 'Ingeniero DevOps', 'Automatización y despliegue continuo', 'Experiencia en Docker, Kubernetes, CI/CD, AWS o Azure. Conocimientos de scripts y automatización.', true, 1),
('2025-10-01', 'Desarrollador Full Stack (React/Node)', 'Desarrollo completo frontend y backend', 'Dominio de React en frontend y Node.js con Express en backend. Experiencia con MongoDB.', true, 1),
('2025-09-25', 'Ingeniero de Datos', 'Procesamiento y análisis de datos', 'Experiencia con Python, SQL, ETL, y herramientas de Big Data como Spark o Hadoop.', true, 1),
('2025-10-05', 'QA Automation Engineer', 'Automatización de pruebas', 'Experiencia en Selenium, Cypress, o Playwright. Conocimientos de frameworks de testing.', true, 1),
('2025-10-12', 'Desarrollador Mobile (React Native)', 'Desarrollo de aplicaciones móviles', 'Experiencia en React Native para iOS y Android. Manejo de APIs y estado con Redux.', true, 1),
('2025-10-08', 'UX/UI Designer', 'Diseño de experiencias de usuario', 'Dominio de Figma, Adobe XD. Experiencia en diseño de interfaces y prototipado.', false, 1),
('2025-10-20', 'Arquitecto de Software', 'Diseño de arquitecturas escalables', 'Experiencia en patrones de diseño, microservicios, arquitecturas cloud-native y liderazgo técnico.', true, 1),
('2025-10-18', 'Desarrollador Backend Python', 'Backend con Python y Django/Flask', 'Experiencia con Django o Flask, PostgreSQL, desarrollo de APIs RESTful y GraphQL.', true, 1);