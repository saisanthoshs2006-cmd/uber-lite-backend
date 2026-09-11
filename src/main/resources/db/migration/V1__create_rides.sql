CREATE TABLE rides (
    id UUID PRIMARY KEY,
    passenger_id VARCHAR(100) NOT NULL,
    driver_id VARCHAR(100),
    pickup_location VARCHAR(255) NOT NULL,
    dropoff_location VARCHAR(255) NOT NULL,
    estimated_fare DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_rides_status ON rides (status);
CREATE INDEX idx_rides_passenger_id ON rides (passenger_id);
