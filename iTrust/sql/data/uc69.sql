/*Insert a new user HCP Spencer Reid Nutritionist.(MID:9900000012)*/
INSERT INTO personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
phone,
specialty,
email)
VALUES (
9900000012,
null,
'hcp',
'Reid',
'Spencer',
'2110 Spencer Circle',
'Apt. 999',
'Raleigh',
'NC',
'27614',
'999-888-7777',
'Nutritionist',
'rspencer@iTrust.org'
) ON DUPLICATE KEY UPDATE mid = mid;
INSERT INTO users(MID, password, role, sQuestion, sAnswer) VALUES(9900000012, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'hcp', 'second letter?', 'b')
ON DUPLICATE KEY UPDATE mid = mid;
/*password: pw*/
INSERT INTO hcpassignedhos(HCPID, HosID) VALUES(9900000012,'9191919191'), (9900000012,'8181818181')
ON DUPLICATE KEY UPDATE HCPID = HCPID;
/*End of nutritionist user insertion.*/

/*Insert patient Aaron Hotchner user. (MID:500)*/
INSERT INTO patients
(MID, 
lastName, 
firstName,
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
dateofbirth,
mothermid,
fathermid,
bloodtype,
ethnicity,
gender, 
topicalnotes)
VALUES
(500,
'Hotchner', 
'Aaron', 
'haaron@ncsu.edu', 
'1247 Aaron Dr', 
'Suite 888', 
'Raleigh', 
'NC', 
'27606-1234', 
'919-971-0000', 
'Mommy Person', 
'704-538-2117', 
'Aetna', 
'1234 Aetna Blvd', 
'Suite 602', 
'Charlotte',
'NC', 
'28215', 
'704-555-1234', 
'ChetumNHowe', 
'1950-05-10',
0,
0,
'AB+',
'African American',
'Male',
''),
/*Insert a Patient with no food diary record.*/
/*Insert patient Jim Raynor user. (MID:502)*/
(503,
'Raynor', 
'Jim', 
'rjim@ncsu.edu', 
'1247 Raynor Dr', 
'Suite 888', 
'Raleigh', 
'NC', 
'27606-1234', 
'919-971-0000', 
'Mommy Person', 
'704-538-2117', 
'Aetna', 
'1234 Raynor Blvd', 
'Suite 602', 
'Charlotte',
'NC', 
'28215', 
'704-555-1234', 
'ChetumNHowe', 
'1950-05-10',
0,
0,
'B+',
'Asian American',
'Male',
'')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (503, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your uc case?', 'uc69'),
			(500, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your uc case?', 'uc69')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 /*End of patient insertion.*/
 
 /*Insert food Diary*/
INSERT INTO fooddiarytable (
rowID, 
ownerID, 
diaryDate,
mealType,
foodName, 
servingsNum, 
caloriesPerServing,
gramsOfFatPerServing, 
milligramsOfSodiumPerServing,
gramsOfCarbsPerServing, 
gramsOfSugarPerServing,
gramsOfFiberPerServing, 
gramsOfProteinPerServing
) 
VALUES (
NULL, 
500,
'2014-04-13',
'Snack',
'Oreos',
53,
140,
7,
90,
21,
13,
1,
0
),(
NULL,
500,
'2013-05-21',
'Breakfast',
'Cheese and Bean Dip',
0.75,
45,
2,
230,
5,
0,
2,
2
);
/*End of insert food diary.*/

/*Add suggestions for testing*/
INSERT INTO suggestions (suggDate, patientID, hcpID, sugg, isNew)
VALUES('2014-04-13', 500, 9900000012, 'First test suggestion','true'),
('2014-04-13', 500, 9900000012, 'Second test suggestion','true'),
('2013-05-21', 500, 9900000012, 'Third test suggestion','true'),
('2013-05-21', 500, 9900000012, 'Fourth test suggestion','true');
/*End of add suggestions*/

/*Insert declared HCP, only designaed Nutritionist can view the patient's food diary.*/
INSERT INTO declaredhcp
(PatientID,	HCPID)
VALUES
(500,9900000012),
(503,9900000012)
ON DUPLICATE KEY UPDATE PatientID = PatientID;
/*End of insert declared HCP*/