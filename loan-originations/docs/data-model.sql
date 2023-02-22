CREATE TABLE Borrower (
    borrower_id INT PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(100),
    phone_number VARCHAR(20),
    email_address VARCHAR(50),
    date_of_birth DATE,
    income DECIMAL(10, 2),
    employment_history VARCHAR(100),
    credit_score INT,
    other_factors VARCHAR(100)
);

CREATE TABLE Loan_Product (
    loan_product_id INT PRIMARY KEY,
    loan_amount DECIMAL(10, 2),
    interest_rate DECIMAL(5, 2),
    term INT,
    repayment_schedule VARCHAR(100),
    other_features VARCHAR(100)
);

CREATE TABLE Loan_Application (
    application_id INT PRIMARY KEY,
    borrower_id INT,
    loan_product_id INT,
    date_of_submission DATE,
    information_provided VARCHAR(100),
    supporting_documentation VARCHAR(100),
    FOREIGN KEY (borrower_id) REFERENCES Borrower(borrower_id),
    FOREIGN KEY (loan_product_id) REFERENCES Loan_Product(loan_product_id)
);

CREATE TABLE Approval_Process (
    approval_id INT PRIMARY KEY,
    application_id INT,
    underwriting_decision VARCHAR(100),
    credit_check VARCHAR(100),
    other_factors VARCHAR(100),
    FOREIGN KEY (application_id) REFERENCES Loan_Application(application_id)
);

CREATE TABLE Funding_Process (
    funding_id INT PRIMARY KEY,
    approval_id INT,
    date_of_disbursement DATE,
    fees DECIMAL(10, 2),
    other_details VARCHAR(100),
    FOREIGN KEY (approval_id) REFERENCES Approval_Process(approval_id)
);

CREATE TABLE Loan_Servicing (
    loan_servicing_id INT PRIMARY KEY,
    funding_id INT,
    payment_history VARCHAR(100),
    delinquencies VARCHAR(100),
    other_factors VARCHAR(100),
    FOREIGN KEY (funding_id) REFERENCES Funding_Process(funding_id)
);
