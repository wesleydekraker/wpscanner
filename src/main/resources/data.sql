INSERT INTO administrator(id, username, password) VALUES(1, 'admin', '96087a8d832f2496e520925aed08501ab57306fdea9dc2ddcce86f725e9db2a5');

INSERT INTO plugin(id, directory_name, display_name) VALUES(1, 'paid-memberships-pro', 'Paid Memberships Pro');
INSERT INTO plugin(id, directory_name, display_name) VALUES(2, 'event-calendar-wd', 'Event Calendar WD');
INSERT INTO theme(id, directory_name, display_name) VALUES(1, 'Newspaper', 'Newspaper');
INSERT INTO theme(id, directory_name, display_name) VALUES(2, 'woostify', 'Woostify');


INSERT INTO plugin_vulnerability(id, plugin_id, first_version, last_version, vulnerability_type) VALUES(1, 1,  1000000000, 2005005000, 'SQL Injection');
INSERT INTO plugin_vulnerability(id, plugin_id, first_version, last_version, vulnerability_type) VALUES(2, 2,  1000000000, 1001021000, 'XSS');
INSERT INTO theme_vulnerability(id, theme_id, first_version, last_version, vulnerability_type) VALUES(1, 1, 6004000000, 6007001000, 'Privilege Escalation');
INSERT INTO theme_vulnerability(id, theme_id, first_version, last_version, vulnerability_type) VALUES(2, 2, 1000000000, 1009002000, 'CSRF ');
