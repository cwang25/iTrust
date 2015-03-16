/*Insert patient Derek Morgan user. (MID:501)*/
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
(501,
'Morgan', 
'Derek', 
'mderek@ncsu.edu', 
'123 Valley Lane', 
'', 
'Raleigh', 
'NC', 
'27606-1234', 
'919-971-1111', 
'Mommy Morgan', 
'123-456-7890', 
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
'')
 ON DUPLICATE KEY UPDATE MID = MID;
/* Patient name: Derek Morgan, MID: 501 */
INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (501, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'What is your UC Case?', 'uc68')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 
 /*Insert patient Jennifer Jareau user. (MID:502)*/
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
(502,
'Jennifer', 
'Jareau', 
'jjareau@ncsu.edu', 
'567 Barn Drive', 
'', 
'Raleigh', 
'NC', 
'27616-5678', 
'919-471-4747', 
'Mommy Jareau', 
'123-456-7890', 
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
'AB',
'White',
'Female',
'')
 ON DUPLICATE KEY UPDATE MID = MID;
/* Patient name: Derek Morgan, MID: 501 */
INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (502, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'What is your UC Case?', 'uc68')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 /*End of patient insertion.*/

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
502,
'2012-09-30',
'Breakfast',
'Hot dog',
4,
80,
5,
480,
2,
0,
0,
5
),(
NULL,
502,
'2012-09-30',
'Lunch',
'Mango Passionfruit Juice',
1.2,
130,
0,
25,
32,
29,
0,
1
);
/*End of insert food diary.*/