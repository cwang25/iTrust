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
VALUES 
(
NULL, 
500,
'2014-04-12',
'Snack',
'Coke',
4,
90,
0,
31,
25,
25,
0,
0
),(
NULL, 
500,
'2014-04-12',
'Dinner',
'Subway',
2,
290,
40,
800,
46,
8,
5,
18
),(
NULL, 
500,
'2014-04-12',
'Lunch',
'Burgerking Chicken Sandwitch',
1,
640,
36,
1140,
53,
7,
3,
29
),(
NULL, 
500,
'2014-04-12',
'Breakfast',
'Chicken Salad',
1,
360,
4.5,
1120,
7,
5,
2,
28
),(
NULL, 
500,
'2014-04-11',
'Dinner',
'Sushi California Roll',
4,
129,
4.7,
269,
19,
1.7,
1,
3
),(
NULL, 
500,
'2014-04-11',
'Lunch',
'Sushi California Roll',
2,
129,
4.7,
269,
19,
1.7,
1,
3
),(
NULL, 
500,
'2014-04-11',
'Breakfast',
'Chick-fil-a Sausage Biscuit',
1,
670,
44,
1470,
44,
5,
2,
23
),(
NULL, 
500,
'2014-04-10',
'Dinner',
'Bojangle Sweet Iced Tea',
3,
75,
0,
0,
18.5,
18,
0,
0
),(
NULL, 
500,
'2014-04-10',
'Dinner',
'Bojangle Cajun Filet Biscuit',
1,
515,
28,
1000,
45,
9,
0,
21
),(
NULL, 
500,
'2014-04-10',
'Snack',
'Fries',
1,
500,
25,
355,
63.2,
0,
0,
5.3
),(
NULL, 
500,
'2014-04-10',
'Lunch',
'McDaonalds Big Mac',
1,
549,
29,
970,
46,
9,
0,
25
),(
NULL, 
500,
'2014-04-10',
'Breakfast',
'Chick-fil-a  Bacon Biscuit',
1,
450,
23,
1160,
44,
6,
2,
17
),(
NULL, 
500,
'2014-04-10',
'Snack',
'Oreos',
7,
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
'2013-08-09',
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
VALUES('2013-08-09', 500, 9900000012, 'Hello, glad to meet you.  I will send you suggestions based on your food diary.','true'),
('2013-08-09', 500, 9900000012, 'Did you forget to eat lunch?','true'),
('2013-08-09', 500, 9900000012, 'Did you skip dinner?','true'),
('2013-08-09', 500, 9900000012, 'It is not good to your body.','true'),
('2013-08-09', 500, 9900000012, 'Please keep the food diary up to date.','true'),
('2013-08-09', 500, 9900000012, 'I think you forget to insert the food diary.','true'),
('2013-08-09', 500, 9900000012, 'Hello?','true'),
('2013-08-09', 500, 9900000012, 'I need your inputs inorder to give you suggestions.','true'),
('2013-08-09', 500, 9900000012, 'Are you still want me to give you suggestion?','true'),
('2013-08-09', 500, 9900000012, 'Hello?','true'),
('2013-08-09', 500, 9900000012, 'Remember do not skip any meal.','true'),
('2013-08-09', 500, 9900000012, 'Do not eat after 10pm.','true'),
('2013-08-09', 500, 9900000012, 'Please record your food diary.','true'),
('2013-08-09', 500, 9900000012, 'Do you need help on accessing this interface?','true'),
('2013-08-09', 500, 9900000012, 'Ok...','true'),
('2014-04-10', 500, 9900000012, 'I suggest to eat less fast food.','true'),
('2014-04-10', 500, 9900000012, 'You ate too much salt!','true'),
('2014-04-10', 500, 9900000012, 'Your daily carolies is too high.','true'),
('2014-04-10', 500, 9900000012, 'If you want to keep your shape Eat less fried food.','true'),
('2014-04-10', 500, 9900000012, 'I suggest to eat less fast food.','true'),
('2014-04-12', 500, 9900000012, 'Are you satisfied with your care?','true'),
('2014-04-12', 500, 9900000012, 'Perfect! By the way, you can always monitor your progress by click on View Graph. :)','true')

;
/*End of add suggestions*/

/*Insert declared HCP, only designaed Nutritionist can view the patient's food diary.*/
INSERT INTO declaredhcp
(PatientID,	HCPID)
VALUES
(500,9900000012),
(503,9900000012)
ON DUPLICATE KEY UPDATE PatientID = PatientID;
/*End of insert declared HCP*/

/*Insert weight logs*/
INSERT INTO weightlog(mid, logdate, weight, chest, waist, upperarm, forearm, thigh, calves, neck)
VALUES(500, '2014-04-13', 150, 30, 20.2, 12, 11, 19.2, 14, 20),
(500, '2014-04-20', 151, 30.2, 20.2, 12, 11, 19.4, 14.1, 20),
(500, '2014-04-27', 145, 30.2, 20.3, 12, 11, 19.4, 14.1, 20),
(500, '2014-05-04', 157, 30.3, 20.4, 12, 11, 19.5, 14.2, 20),
(500, '2014-05-11', 160, 30.4, 20.5, 12, 11, 19.5, 14.3, 20);
/*End of insert weight logs*/
INSERT INTO macronutrientplan(rowID, ownerID, protein, fat, carbs, totalCalories)
VALUES(1,500,100,64,337,2332);

INSERT INTO macronutrientprofile(rowID, gender, age, weight, height, goals, activity, macroplanID)
VALUES(NULL, 'male', 20, 55, 168, 'maintain_weight', 'moderately_active', 1);

/*Insert food diary labels.*/
INSERT INTO fooddiarylabels (rowid, mid, label) 
VALUES 
(1,500, 'Diet'),
(2,500, 'FastFood'),
(NULL, 500, 'OldDiaries'),
(NULL, 500, 'BeforeDiet');

INSERT INTO fooddiarysetlabels (mid, diarydate, label, labelrowID)
VALUES
(500, '2014-04-11', 'Diet',1),
(500, '2014-04-12', 'Diet',1),
(500, '2014-04-10', 'FastFood',2)
;
/*End of insert food diary labels.*/