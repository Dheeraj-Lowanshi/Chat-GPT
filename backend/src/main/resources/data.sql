INSERT INTO users (id, full_name, email, password, phone_number, date_of_birth, gender, address, aadhar_number, role)
VALUES (1, 'System Admin', 'admin@votezy.com', 'admin123', '9999999999', '1990-01-01', 'OTHER', 'HQ', '111122223333', 'ADMIN')
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO admins (id) VALUES (1)
ON DUPLICATE KEY UPDATE id=id;
