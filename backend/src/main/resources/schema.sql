CREATE DATABASE IF NOT EXISTS votezy_lite;
USE votezy_lite;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone_number VARCHAR(50) NOT NULL,
  date_of_birth DATE,
  gender VARCHAR(20),
  address VARCHAR(1000),
  aadhar_number VARCHAR(50) NOT NULL UNIQUE,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS admins (
  id BIGINT PRIMARY KEY,
  CONSTRAINT fk_admin_user FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS parties (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  symbol_path VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS elections (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(1500),
  start_date_time DATETIME NOT NULL,
  end_date_time DATETIME NOT NULL,
  status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS candidates (
  id BIGINT PRIMARY KEY,
  party_id BIGINT,
  election_id BIGINT,
  manifesto VARCHAR(3000),
  profile_photo_path VARCHAR(500),
  approval_status VARCHAR(20),
  CONSTRAINT fk_candidate_user FOREIGN KEY (id) REFERENCES users(id),
  CONSTRAINT fk_candidate_party FOREIGN KEY (party_id) REFERENCES parties(id),
  CONSTRAINT fk_candidate_election FOREIGN KEY (election_id) REFERENCES elections(id)
);

CREATE TABLE IF NOT EXISTS voters (
  id BIGINT PRIMARY KEY,
  voter_id_number VARCHAR(255) NOT NULL UNIQUE,
  constituency VARCHAR(255) NOT NULL,
  CONSTRAINT fk_voter_user FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS votes (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  voter_id BIGINT NOT NULL,
  candidate_id BIGINT NOT NULL,
  election_id BIGINT NOT NULL,
  cast_at DATETIME NOT NULL,
  CONSTRAINT fk_vote_voter FOREIGN KEY (voter_id) REFERENCES voters(id),
  CONSTRAINT fk_vote_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(id),
  CONSTRAINT fk_vote_election FOREIGN KEY (election_id) REFERENCES elections(id),
  CONSTRAINT uk_voter_election UNIQUE (voter_id, election_id)
);
