-- the code governing the structure of the rental system database

-- Vehicles table (type of vehicle, rate, availability, ...)
CREATE TABLE IF NOT EXISTS vehicles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL,               -- e.g "Car", "Van", "Bike"
    brand TEXT NOT NULL,              -- e.g."Toyota"
    model TEXT NOT NULL,       -- e.g.:"Corolla"
    seats INTEGER,              -- Cars only
    capacity FLOAT,             -- Vans only
    category TEXT,              -- Bikes only
    rate REAL NOT NULL,               -- Daily rental rate
    available INTEGER DEFAULT 1       -- stock available
);

-- Rented Table (holds all the orders made: who, what and when)
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer TEXT NOT NULL,
    vehicle TEXT NOT NULL,
    cost FLOAT NOT NULL
);


-- Prefil data:
-- for cars
INSERT INTO vehicles (type, brand, model, seats, rate, available) VALUES
('Car', 'Toyota', 'Corolla', 5, 4500.00, 10),
('Car', 'Honda', 'Civic', 5, 4800.00, 10),
('Car', 'Mazda', 'Axela', 5, 4300.00, 10),
('Car', 'Nissan', 'Sunny', 5, 4000.00, 10);

-- for vans
INSERT INTO vehicles (type, brand, model, capacity, rate, available) VALUES
('Van', 'Nissan', 'Caravan', 12, 7500.00, 10),
('Van', 'Toyota', 'Hiace', 14, 8000.00, 10),
('Van', 'Mazda', 'Bongo', 10, 7200.00, 10),
('Van', 'Hyundai', 'H1', 11, 7800.00, 10);

-- for bikez
INSERT INTO vehicles (type, brand, model, category, rate, available) VALUES
('Bike', 'Yamaha', 'YBR125', 'Standard', 1500.00, 10),
('Bike', 'Honda', 'CB125F', 'Commuter', 1600.00, 10),
('Bike', 'Suzuki', 'GSX150', 'Sport', 2000.00, 10),
('Bike', 'Bajaj', 'Boxer150', 'Utility', 1300.00, 10);
