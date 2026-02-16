
DELETE FROM task;

INSERT INTO task (title, description, priority, priority_weight, state, creation_date, deadline, active, assigned_to) VALUES
('Setup proyecto', 'Inicializar repo y dependencias', 'LOW', 1, 'DONE',  '2025-11-10', '2025-11-15', true,  NULL),

('Diseño de endpoints', 'Definir contratos REST', 'MEDIUM', 2, 'DONE',  '2026-01-05', '2026-01-12', true,  NULL),

('Implementar filtro múltiple', 'Specifications acumulables', 'HIGH', 3, 'DOING', '2026-01-20', '2026-02-05', true,  NULL), -- vencida

('Fix bug login', 'Error 401 intermitente', 'CRITICAL', 4, 'TODO', '2026-02-01', '2026-02-10', true,  NULL), -- vencida

('Refactor DTOs', 'Unificar nombres y validaciones', 'MEDIUM', 2, 'TODO', '2026-02-12', '2026-02-25', true,  NULL),

('Optimizar queries', 'Evitar N+1 en listados', 'HIGH', 3, 'DOING', '2026-02-08', '2026-02-18', true,  NULL),

('Agregar paginado', 'Pageable + sort por fecha', 'LOW', 1, 'DONE',  '2026-02-02', '2026-02-06', true,  NULL),

('Documentar API', 'Swagger/OpenAPI', 'MEDIUM', 2, 'TODO', '2026-02-15', '2026-01-01', true,  NULL),

('Implementar JWT', 'Auth con tokens', 'CRITICAL', 4, 'DOING', '2026-02-03', '2026-02-14', true,  NULL), -- vencida

('Deploy a producción', 'Configurar pipeline', 'HIGH', 3, 'TODO', '2026-02-16', '2026-03-10', true,  NULL);

DELETE FROM `user`;

INSERT INTO `user` (name, last_name, email, password, active, created_at) VALUES
('Mateo', 'Pillado', 'mateo.pillado@mail.com', 'PASSWORD_BCRYPT_1', true, '2025-10-10'),
('Lucia', 'Gomez', 'lucia.gomez@mail.com', 'PASSWORD_BCRYPT_2', true, '2025-12-03'),
('Juan', 'Perez', 'juan.perez@mail.com', 'PASSWORD_BCRYPT_3', true, '2026-01-15'),
('Sofia', 'Martinez', 'sofia.martinez@mail.com', 'PASSWORD_BCRYPT_4', true, '2026-02-01'),
('Nicolas', 'Lopez', 'nicolas.lopez@mail.com', 'PASSWORD_BCRYPT_5', true, '2026-02-12');
