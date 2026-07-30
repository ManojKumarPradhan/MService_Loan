
CREATE TABLE IF NOT EXISTS `loans` (

    `loan_id` BIGINT AUTO_INCREMENT PRIMARY KEY,

    `customer_id` BIGINT NOT NULL,

    `account_number` BIGINT NOT NULL,

    `loan_number` VARCHAR(30) UNIQUE NOT NULL,

    `loan_type` VARCHAR(30) NOT NULL,

    `principal_amount` DECIMAL(15,2) NOT NULL,

    `interest_rate` DECIMAL(5,2) NOT NULL,

    `tenure_months` INT NOT NULL,

    `emi_amount` DECIMAL(15,2) NOT NULL,

    `outstanding_amount` DECIMAL(15,2) NOT NULL,

    `status` VARCHAR(20) NOT NULL,

    `created_at` DATE NOT NULL,

    `updated_at` DATE DEFAULT NULL,

    `created_by` INT NOT NULL,

    `updated_by` INT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `loan_payments` (

    `payment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,

    `loan_id` BIGINT NOT NULL,

    `amount_paid` DECIMAL(15,2) NOT NULL,

    `payment_date` DATE NOT NULL,

    `payment_mode` VARCHAR(20),

    `transaction_reference` VARCHAR(100),

    `created_at` DATE NOT NULL,

    `updated_at` DATE DEFAULT NULL,

    `created_by` INT NOT NULL,

    `updated_by` INT DEFAULT NULL,

    CONSTRAINT fk_loan_payment
        FOREIGN KEY (loan_id)
        REFERENCES loans(loan_id)
        ON DELETE CASCADE
);