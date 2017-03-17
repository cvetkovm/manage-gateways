INSERT INTO gateways (serialNumber, name, ipAddress) VALUES ('AAAA1111', 'Gateway1', '139.130.4.5');
INSERT INTO gateways (serialNumber, name, ipAddress) VALUES ('BBBB2222', 'Gateway2', '138.130.4.5');
INSERT INTO gateways (serialNumber, name, ipAddress) VALUES ('CCCC3333', 'Gateway3', '168.130.4.5');
INSERT INTO gateways (serialNumber, name, ipAddress) VALUES ('DDDD4444', 'Gateway4', '254.130.4.5');

INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('AAAA1111', 1, 'Toshiba', DATE '2010-12-17', 'online');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('AAAA1111', 2, 'Samsung', DATE '2015-12-17', 'offline');

INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('BBBB2222', 3, 'Sony', DATE '2011-12-17', 'offline');

INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('CCCC3333', 4, 'Iphone', DATE '2011-12-17', 'offline');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('CCCC3333', 5, 'Motorola', DATE '2011-12-17', 'online');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('CCCC3333', 6, 'Lenovo', DATE '2011-12-17', 'offline');

INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('DDDD4444', 7, 'Alcatel', DATE '2011-12-17', 'online');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('DDDD4444', 8, 'Erickson', DATE '2011-11-17', 'offline');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('DDDD4444', 9, 'HTC', DATE '2011-12-17', 'offline');
INSERT INTO devices (gatewaySerialNumber, uid, vendor, dateCreated, status) VALUES ('DDDD4444', 10, 'HP', DATE '2011-12-17', 'online');






