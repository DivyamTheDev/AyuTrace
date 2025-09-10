-- AyurTrace Database Schema for PostgreSQL
-- This schema supports the complete herb traceability system

-- Create database (run separately if needed)
-- CREATE DATABASE ayurtrace_db;

-- Users table for authentication and role management
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('FARMER', 'PROCESSOR', 'LAB_TECHNICIAN', 'REGULATOR', 'CONSUMER', 'ADMIN')),
    organization_name VARCHAR(255),
    license_number VARCHAR(100),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    pin_code VARCHAR(10),
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_verified BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Collection events table for herb collection tracking
CREATE TABLE IF NOT EXISTS collection_events (
    id BIGSERIAL PRIMARY KEY,
    collection_id VARCHAR(50) UNIQUE NOT NULL,
    herb_name VARCHAR(255) NOT NULL,
    scientific_name VARCHAR(255),
    quantity_kg DECIMAL(10,3) NOT NULL,
    collection_location VARCHAR(500) NOT NULL,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    collection_date TIMESTAMP NOT NULL,
    collection_method VARCHAR(20) CHECK (collection_method IN ('HAND_PICKED', 'CUTTING_TOOL', 'DIGGING', 'SHAKING', 'MECHANICAL')),
    season VARCHAR(10) CHECK (season IN ('SPRING', 'SUMMER', 'MONSOON', 'WINTER')),
    weather_conditions VARCHAR(255),
    soil_type VARCHAR(255),
    altitude_meters INTEGER,
    collection_time VARCHAR(20),
    plant_part_used VARCHAR(100),
    harvest_maturity VARCHAR(50),
    storage_conditions TEXT,
    additional_notes TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'COLLECTED' CHECK (status IN ('COLLECTED', 'IN_STORAGE', 'SENT_FOR_PROCESSING', 'PROCESSED', 'TESTED', 'APPROVED', 'REJECTED', 'RECALLED')),
    collector_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Processing steps table for herb processing tracking
CREATE TABLE IF NOT EXISTS processing_steps (
    id BIGSERIAL PRIMARY KEY,
    processing_id VARCHAR(50) UNIQUE NOT NULL,
    step_type VARCHAR(20) NOT NULL CHECK (step_type IN ('CLEANING', 'SORTING', 'WASHING', 'DRYING', 'GRINDING', 'SIEVING', 'EXTRACTION', 'DISTILLATION', 'FERMENTATION', 'STEAMING', 'ROASTING', 'PACKAGING', 'STORAGE')),
    step_name VARCHAR(255) NOT NULL,
    input_quantity_kg DECIMAL(10,3),
    output_quantity_kg DECIMAL(10,3),
    processing_date TIMESTAMP NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    temperature_celsius INTEGER,
    humidity_percentage INTEGER,
    processing_duration_hours INTEGER,
    equipment_used VARCHAR(500),
    processing_parameters TEXT,
    batch_number VARCHAR(100),
    yield_percentage DECIMAL(5,2),
    quality_grade VARCHAR(50),
    moisture_content_percentage DECIMAL(5,2),
    color_description VARCHAR(255),
    aroma_description VARCHAR(255),
    texture_description VARCHAR(255),
    storage_conditions_post_processing VARCHAR(500),
    packaging_type VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS' CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'ON_HOLD', 'REJECTED', 'REWORK_REQUIRED')),
    notes TEXT,
    compliance_standards VARCHAR(255),
    collection_event_id BIGINT NOT NULL REFERENCES collection_events(id),
    processor_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quality tests table for lab testing and quality assurance
CREATE TABLE IF NOT EXISTS quality_tests (
    id BIGSERIAL PRIMARY KEY,
    test_id VARCHAR(50) UNIQUE NOT NULL,
    test_type VARCHAR(20) NOT NULL CHECK (test_type IN ('PHYSICAL', 'CHEMICAL', 'MICROBIOLOGICAL', 'DNA_BARCODING', 'HEAVY_METALS', 'PESTICIDE_RESIDUE', 'AFLATOXIN', 'COMPREHENSIVE')),
    test_name VARCHAR(255) NOT NULL,
    test_date TIMESTAMP NOT NULL,
    lab_name VARCHAR(255) NOT NULL,
    lab_accreditation VARCHAR(255),
    test_method VARCHAR(255),
    sample_quantity_grams DECIMAL(8,3),
    
    -- Physical test results
    moisture_content DECIMAL(5,2),
    ash_content DECIMAL(5,2),
    extractive_value DECIMAL(5,2),
    foreign_matter DECIMAL(5,2),
    
    -- Chemical test results
    active_compounds TEXT, -- JSON format
    heavy_metals TEXT, -- JSON format
    pesticide_residues TEXT, -- JSON format
    aflatoxins DECIMAL(8,3), -- ppb
    ph_value DECIMAL(4,2),
    
    -- Microbiological test results
    total_bacterial_count INTEGER,
    yeast_mould_count INTEGER,
    ecoli_presence BOOLEAN,
    salmonella_presence BOOLEAN,
    staphylococcus_presence BOOLEAN,
    
    -- DNA barcoding results
    dna_barcode VARCHAR(500),
    species_confirmation VARCHAR(255),
    authenticity_score DECIMAL(5,2),
    adulteration_detected BOOLEAN,
    adulterants_found TEXT,
    
    -- Results and compliance
    test_result VARCHAR(20) NOT NULL CHECK (test_result IN ('PASS', 'FAIL', 'CONDITIONAL_PASS', 'PENDING', 'RETEST_REQUIRED', 'INCONCLUSIVE')),
    compliance_standards VARCHAR(255),
    grade_assigned VARCHAR(50),
    shelf_life_months INTEGER,
    storage_recommendations VARCHAR(500),
    test_report_url VARCHAR(1000),
    certificate_number VARCHAR(100),
    remarks TEXT,
    retest_required BOOLEAN DEFAULT false,
    retest_reason VARCHAR(500),
    
    collection_event_id BIGINT NOT NULL REFERENCES collection_events(id),
    tester_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Product batches table for final packaged products with QR codes
CREATE TABLE IF NOT EXISTS product_batches (
    id BIGSERIAL PRIMARY KEY,
    batch_id VARCHAR(50) UNIQUE NOT NULL,
    qr_code VARCHAR(100) UNIQUE NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    brand_name VARCHAR(255),
    manufacturer_name VARCHAR(255) NOT NULL,
    manufacturer_license VARCHAR(255),
    product_form VARCHAR(20) NOT NULL CHECK (product_form IN ('POWDER', 'CAPSULE', 'TABLET', 'LIQUID_EXTRACT', 'OIL', 'CREAM', 'OINTMENT', 'SYRUP', 'TEA_BAG', 'DRIED_HERB', 'ESSENTIAL_OIL', 'TINCTURE')),
    total_quantity_kg DECIMAL(10,3),
    unit_size VARCHAR(50),
    units_produced INTEGER,
    manufacturing_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    shelf_life_months INTEGER,
    batch_size VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'PRODUCED' CHECK (status IN ('PRODUCED', 'TESTED', 'APPROVED', 'RELEASED', 'DISTRIBUTED', 'RECALLED', 'EXPIRED', 'REJECTED')),
    
    -- Product specifications
    ingredients TEXT, -- JSON format
    composition TEXT, -- Percentage of each herb
    dosage_instructions TEXT,
    contraindications TEXT,
    side_effects TEXT,
    storage_instructions VARCHAR(500),
    
    -- Regulatory information
    regulatory_approvals TEXT, -- JSON format
    ayush_license VARCHAR(255),
    gmp_certificate VARCHAR(255),
    iso_certification VARCHAR(255),
    organic_certification VARCHAR(255),
    
    -- Distribution
    distributor_info TEXT, -- JSON format
    retail_price DECIMAL(10,2),
    mrp DECIMAL(10,2),
    
    -- Packaging information
    packaging_material VARCHAR(255),
    label_information TEXT,
    barcode VARCHAR(50), -- Traditional barcode
    
    -- Quality & testing
    final_quality_grade VARCHAR(50),
    batch_testing_completed BOOLEAN DEFAULT false,
    release_approved BOOLEAN DEFAULT false,
    approved_by VARCHAR(255),
    approval_date TIMESTAMP,
    
    -- Traceability
    blockchain_hash VARCHAR(255),
    blockchain_verified BOOLEAN DEFAULT false,
    traceability_score DECIMAL(5,2),
    sustainability_score DECIMAL(5,2),
    notes TEXT,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Junction tables for many-to-many relationships

-- Batch to collection events relationship
CREATE TABLE IF NOT EXISTS batch_collection_events (
    batch_id BIGINT NOT NULL REFERENCES product_batches(id) ON DELETE CASCADE,
    collection_event_id BIGINT NOT NULL REFERENCES collection_events(id) ON DELETE CASCADE,
    PRIMARY KEY (batch_id, collection_event_id)
);

-- Batch to processing steps relationship
CREATE TABLE IF NOT EXISTS batch_processing_steps (
    batch_id BIGINT NOT NULL REFERENCES product_batches(id) ON DELETE CASCADE,
    processing_step_id BIGINT NOT NULL REFERENCES processing_steps(id) ON DELETE CASCADE,
    PRIMARY KEY (batch_id, processing_step_id)
);

-- Batch to quality tests relationship
CREATE TABLE IF NOT EXISTS batch_quality_tests (
    batch_id BIGINT NOT NULL REFERENCES product_batches(id) ON DELETE CASCADE,
    quality_test_id BIGINT NOT NULL REFERENCES quality_tests(id) ON DELETE CASCADE,
    PRIMARY KEY (batch_id, quality_test_id)
);

-- Indexes for better performance
CREATE INDEX IF NOT EXISTS idx_collection_events_collector_id ON collection_events(collector_id);
CREATE INDEX IF NOT EXISTS idx_collection_events_herb_name ON collection_events(herb_name);
CREATE INDEX IF NOT EXISTS idx_collection_events_collection_date ON collection_events(collection_date);
CREATE INDEX IF NOT EXISTS idx_collection_events_status ON collection_events(status);
CREATE INDEX IF NOT EXISTS idx_collection_events_location ON collection_events(collection_location);

CREATE INDEX IF NOT EXISTS idx_processing_steps_collection_event_id ON processing_steps(collection_event_id);
CREATE INDEX IF NOT EXISTS idx_processing_steps_processor_id ON processing_steps(processor_id);
CREATE INDEX IF NOT EXISTS idx_processing_steps_step_type ON processing_steps(step_type);
CREATE INDEX IF NOT EXISTS idx_processing_steps_processing_date ON processing_steps(processing_date);

CREATE INDEX IF NOT EXISTS idx_quality_tests_collection_event_id ON quality_tests(collection_event_id);
CREATE INDEX IF NOT EXISTS idx_quality_tests_tester_id ON quality_tests(tester_id);
CREATE INDEX IF NOT EXISTS idx_quality_tests_test_date ON quality_tests(test_date);
CREATE INDEX IF NOT EXISTS idx_quality_tests_test_result ON quality_tests(test_result);
CREATE INDEX IF NOT EXISTS idx_quality_tests_lab_name ON quality_tests(lab_name);

CREATE INDEX IF NOT EXISTS idx_product_batches_batch_id ON product_batches(batch_id);
CREATE INDEX IF NOT EXISTS idx_product_batches_qr_code ON product_batches(qr_code);
CREATE INDEX IF NOT EXISTS idx_product_batches_manufacturing_date ON product_batches(manufacturing_date);
CREATE INDEX IF NOT EXISTS idx_product_batches_status ON product_batches(status);

CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- Triggers for updated_at timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply the trigger to all tables with updated_at column
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_collection_events_updated_at BEFORE UPDATE ON collection_events FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_processing_steps_updated_at BEFORE UPDATE ON processing_steps FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_quality_tests_updated_at BEFORE UPDATE ON quality_tests FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_product_batches_updated_at BEFORE UPDATE ON product_batches FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Insert sample data for testing (optional)
/*
INSERT INTO users (username, email, password, full_name, role, organization_name, state, is_active, is_verified) VALUES
('admin', 'admin@ayurtrace.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'System Administrator', 'ADMIN', 'AyurTrace Systems', 'Maharashtra', true, true),
('farmer1', 'farmer@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Ram Kumar', 'FARMER', 'Organic Farms Ltd', 'Kerala', true, true),
('lab1', 'lab@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Dr. Priya Sharma', 'LAB_TECHNICIAN', 'Quality Labs India', 'Karnataka', true, true);
*/
