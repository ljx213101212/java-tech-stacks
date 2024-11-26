

use db_migration_demo;

show tables;

select * from company;
select * from employee;
select * from country;
select version, description, success from flyway_schema_history;

SELECT version, description
FROM flyway_schema_history
WHERE success = true
ORDER BY installed_rank DESC
LIMIT 1;

select * from DATABASECHANGELOG;

SELECT *
    from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where TABLE_SCHEMA = 'db_migration_demo' and TABLE_NAME='company';
select * from DATABASECHANGELOGLOCK;

GRANT ALL PRIVILEGES ON security_applications_demo.* TO 'user'@'%';

SELECT User, Host, Db, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv
FROM mysql.db
WHERE User = 'user' AND Db = 'security_applications_demo';

SELECT User, Host, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv
FROM mysql.user
WHERE User = 'user';

show databases;

CREATE DATABASE IF NOT EXISTS security_applications_demo;
use security_applications_demo;

show tables;

describe users;
describe user_authorities;
describe authorities;

drop database security_applications_demo;

select * from users;
select * from authorities;
select * from user_authorities;

UPDATE users
SET 
    account_non_locked = 1,
    failed_attempt = 0,
    lock_time = NULL
WHERE email = 'user@example.com';


CREATE DATABASE IF NOT EXISTS security_applications_demo_secret_provider;
drop database security_applications_demo_secret_provider;

use security_applications_demo_secret_provider;

SELECT *
FROM users u
JOIN user_authorities ua ON u.id = ua.user_id
JOIN authorities a ON ua.authority_id = a.id
WHERE a.authority = 'STANDARD';

