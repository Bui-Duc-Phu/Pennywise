use penyWise;

CREATE TABLE UserProfile (
    profileId INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    imageUri NVARCHAR(MAX),
    budget FLOAT NOT NULL,
    savings FLOAT NOT NULL
);


CREATE TABLE ExpenseCategory (
    categoryId INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    budget FLOAT NOT NULL,
    spent FLOAT NOT NULL
);

CREATE TABLE Expense (
    expenseId INT PRIMARY KEY IDENTITY(1,1),
    categoryId INT NOT NULL,
    amount FLOAT NOT NULL,
    date DATE NOT NULL,
    description NVARCHAR(MAX),
    FOREIGN KEY (categoryId) REFERENCES ExpenseCategory(categoryId) ON DELETE CASCADE
);


CREATE TABLE Income (
    incomeId INT PRIMARY KEY IDENTITY(1,1),
    amount FLOAT NOT NULL,
    source NVARCHAR(255) NOT NULL,
    date DATE NOT NULL
);


CREATE TABLE SavingGoal (
    goalId INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    targetAmount FLOAT NOT NULL,
    savedAmount FLOAT NOT NULL,
    progress AS (savedAmount * 100.0 / targetAmount) PERSISTED
);



INSERT INTO ExpenseCategory (name, budget, spent)
VALUES ('Shopping', 2000, 150),
       ('Food', 1000, 300),
       ('Transport', 800, 100);

-- Thêm khoản chi tiêu
INSERT INTO Expense (categoryId, amount, date, description)
VALUES (1, 50, '2024-11-01', 'Bought clothes'),
       (2, 20, '2024-11-02', 'Lunch'),
       (3, 15, '2024-11-03', 'Gas refill');

-- Thêm thu nhập
INSERT INTO Income (amount, source, date)
VALUES (5000, 'Salary', '2024-11-01'),
       (200, 'Freelance', '2024-11-05');

-- Thêm mục tiêu tiết kiệm
INSERT INTO SavingGoal (name, targetAmount, savedAmount)
VALUES ('Buy a Car', 10000, 2000),
       ('Vacation', 5000, 1500);

SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE';


-- tổng lương
SELECT SUM(amount) AS TotalIncome
FROM Income;

-- tổng chi phí
SELECT SUM(amount) AS TotalExpense
FROM Expense;



-- chi tiêt các hạng mục chi phí
SELECT 
    c.name AS CategoryName,
    SUM(e.amount) AS TotalSpent
FROM Expense e
INNER JOIN ExpenseCategory c ON e.categoryId = c.categoryId
GROUP BY c.name;


-- lấy thông tin chi phí theo ngày
SELECT 
    date AS ExpenseDate,
    SUM(amount) AS TotalSpent
FROM Expense
GROUP BY date
ORDER BY date ASC;


-- luong chi phí chênh lệch
SELECT 
    (SELECT SUM(amount) FROM Income) AS TotalIncome,
    (SELECT SUM(amount) FROM Expense) AS TotalExpense,
    ((SELECT SUM(amount) FROM Income) - (SELECT SUM(amount) FROM Expense)) AS Balance;


