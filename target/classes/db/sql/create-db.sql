drop table IF EXISTS gateways;
CREATE TABLE IF NOT EXISTS gateways (
	serialNumber VARCHAR(200) PRIMARY KEY,
  	name VARCHAR(200),
	ipAddress VARCHAR(200)
);

drop table IF EXISTS devices;
CREATE TABLE IF NOT EXISTS devices (
	uid INTEGER PRIMARY KEY,
  	gatewaySerialNumber VARCHAR(200),
	vendor VARCHAR(200),
	dateCreated DATE,
	status VARCHAR(200)
);
