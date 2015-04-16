/*Insert patient user for use cases 73 (MID:730)*/
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
(730,
'Jareau', 
'Jennifer', 
'jjennifer@ncsu.edu', 
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
VALUES (730, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your uc case?', 'uc73')
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
730,
'2014-03-16',
'Lunch',
'ChocolateShake',
2,
500,
23.5,
259,
66.5,
42.4,
0,
5.9
),(
NULL,
730,
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
),(
NULL,
730,
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
730,
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
730,
'2012-09-30',
'Lunch',
'Mango Passionfriut Juice',
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

/*Insert food diary labels.*/
INSERT INTO fooddiarylabels (rowid, mid, label) 
VALUES 
(2,730, 'Southbeach'),
(3, 730, 'Atkins'),
(NULL, 730, 'Jenny Craig'),
(NULL, 730, 'Low carb');

INSERT INTO fooddiarysetlabels (mid, diarydate, label, labelrowID)
VALUES
(730, '2012-09-30', 'Southbeach',2);
/*End of insert food diary labels.*/