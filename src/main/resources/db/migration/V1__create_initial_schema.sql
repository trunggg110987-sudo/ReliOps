CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(254) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(120) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status VARCHAR(24) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS provinces (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT pk_provinces PRIMARY KEY (id),
    CONSTRAINT uk_provinces_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS districts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL,
    province_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT pk_districts PRIMARY KEY (id),
    CONSTRAINT uk_districts_province_name UNIQUE (province_id, name),
    CONSTRAINT uk_districts_code UNIQUE (code),
    CONSTRAINT fk_districts_province FOREIGN KEY (province_id) REFERENCES provinces (id)
);

CREATE TABLE IF NOT EXISTS amenities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT pk_amenities PRIMARY KEY (id),
    CONSTRAINT uk_amenities_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS properties (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_id BIGINT NOT NULL,
    district_id BIGINT NOT NULL,
    title VARCHAR(160) NOT NULL,
    description TEXT NOT NULL,
    property_type VARCHAR(30) NOT NULL,
    address_detail VARCHAR(255) NOT NULL,
    area DECIMAL(8,2) NOT NULL,
    bedroom_count INT NOT NULL,
    bathroom_count INT NOT NULL,
    monthly_rent DECIMAL(15,2) NOT NULL,
    electricity_price DECIMAL(12,2) NOT NULL,
    water_price DECIMAL(12,2) NOT NULL,
    status VARCHAR(24) NOT NULL,
    reviewed_by BIGINT,
    reviewed_at TIMESTAMP(6),
    rejection_reason VARCHAR(500),
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_properties PRIMARY KEY (id),
    CONSTRAINT fk_properties_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_properties_district FOREIGN KEY (district_id) REFERENCES districts (id),
    CONSTRAINT fk_properties_reviewer FOREIGN KEY (reviewed_by) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS property_images (
    id BIGINT NOT NULL AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    image_path VARCHAR(500) NOT NULL,
    sort_order INT NOT NULL,
    CONSTRAINT pk_property_images PRIMARY KEY (id),
    CONSTRAINT fk_property_images_property FOREIGN KEY (property_id) REFERENCES properties (id)
);

CREATE TABLE IF NOT EXISTS property_amenities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    amenity_id BIGINT NOT NULL,
    CONSTRAINT pk_property_amenities PRIMARY KEY (id),
    CONSTRAINT uk_property_amenities_pair UNIQUE (property_id, amenity_id),
    CONSTRAINT fk_property_amenities_property FOREIGN KEY (property_id) REFERENCES properties (id),
    CONSTRAINT fk_property_amenities_amenity FOREIGN KEY (amenity_id) REFERENCES amenities (id)
);

CREATE TABLE IF NOT EXISTS rental_requests (
    id BIGINT NOT NULL AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    desired_start_date DATE NOT NULL,
    message TEXT,
    status VARCHAR(20) NOT NULL,
    processed_at TIMESTAMP(6),
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_rental_requests PRIMARY KEY (id),
    CONSTRAINT fk_rental_requests_property FOREIGN KEY (property_id) REFERENCES properties (id),
    CONSTRAINT fk_rental_requests_customer FOREIGN KEY (customer_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS contracts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    rental_request_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_rent DECIMAL(15,2) NOT NULL,
    electricity_price DECIMAL(12,2) NOT NULL,
    water_price DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_contracts PRIMARY KEY (id),
    CONSTRAINT uk_contracts_rental_request UNIQUE (rental_request_id),
    CONSTRAINT fk_contracts_property FOREIGN KEY (property_id) REFERENCES properties (id),
    CONSTRAINT fk_contracts_customer FOREIGN KEY (customer_id) REFERENCES users (id),
    CONSTRAINT fk_contracts_rental_request FOREIGN KEY (rental_request_id) REFERENCES rental_requests (id)
);

CREATE TABLE IF NOT EXISTS invoices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    contract_id BIGINT NOT NULL,
    billing_period DATE NOT NULL,
    rent_amount DECIMAL(15,2) NOT NULL,
    previous_electricity_index DECIMAL(12,3) NOT NULL,
    current_electricity_index DECIMAL(12,3) NOT NULL,
    electricity_amount DECIMAL(15,2) NOT NULL,
    previous_water_index DECIMAL(12,3) NOT NULL,
    current_water_index DECIMAL(12,3) NOT NULL,
    water_amount DECIMAL(15,2) NOT NULL,
    other_fees DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    status VARCHAR(24) NOT NULL,
    issued_at TIMESTAMP(6) NOT NULL,
    due_date DATE NOT NULL,
    CONSTRAINT pk_invoices PRIMARY KEY (id),
    CONSTRAINT uk_invoices_contract_period UNIQUE (contract_id, billing_period),
    CONSTRAINT fk_invoices_contract FOREIGN KEY (contract_id) REFERENCES contracts (id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    status VARCHAR(16) NOT NULL,
    reported_at TIMESTAMP(6) NOT NULL,
    confirmed_at TIMESTAMP(6),
    rejection_reason VARCHAR(500),
    note VARCHAR(500),
    CONSTRAINT pk_payments PRIMARY KEY (id),
    CONSTRAINT uk_payments_invoice UNIQUE (invoice_id),
    CONSTRAINT fk_payments_invoice FOREIGN KEY (invoice_id) REFERENCES invoices (id)
);

CREATE TABLE IF NOT EXISTS favorites (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_favorites PRIMARY KEY (id),
    CONSTRAINT uk_favorites_customer_property UNIQUE (customer_id, property_id),
    CONSTRAINT fk_favorites_customer FOREIGN KEY (customer_id) REFERENCES users (id),
    CONSTRAINT fk_favorites_property FOREIGN KEY (property_id) REFERENCES properties (id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    contract_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id),
    CONSTRAINT uk_reviews_contract UNIQUE (contract_id),
    CONSTRAINT fk_reviews_contract FOREIGN KEY (contract_id) REFERENCES contracts (id),
    CONSTRAINT fk_reviews_customer FOREIGN KEY (customer_id) REFERENCES users (id),
    CONSTRAINT fk_reviews_property FOREIGN KEY (property_id) REFERENCES properties (id)
);
