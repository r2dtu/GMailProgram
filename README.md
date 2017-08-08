# GMailProgram
SummerSAT E-mail Progress Report Program

Weekly Progress Report E-mail Program

Instructions: Please read the following before using the program.

This program was designed to be used with SummerSAT 2016 Class 101, and is used for sending out individual e-mails to students with their online SAT test submission program credentials and/or their weekly progress reports which included information on their test, quiz, and homework scores.

The Excel sheet records must be downloaded in a .csv (comma-splitted values) format. Only data for students may be in these .csv files (extraneous rows of data will inhibit functionality for the program). Any extraneous data in cells may corrupt or prevent the program from functioning as required.

Before running the program, go to GMailConstants and ensure the top 5 constants are what you want the program to have. It's always good to preview the output first with a few rows of data (and email them to yourself) before you run the program in its entirety. Then be sure to add in your username and password for your GMail account. It may be helpful to test the program before running it with your classroom by setting GMailConstants.TEST_PREVIEW to 'true', and editing / utilizing the files in "csvfiles.test". The GMailConstants provides a skeleton bone of what is needed to get the program running. You may need experience with formatted Strings to edit the text displayed in the e-mail.

The Student class has some unimplemented data (stored, but unused). This will be implemented in the future.

Currently, the program is in development. Hopefully in the future, it may become a more user-friendly application with graphical interfaces so that regular non computer programmers can utilize it. But for now, go learn some CS! =)

Sample Google Sheet: https://goo.gl/abWb4n

WARNINGS:

Make sure the top 5 constants in GMailConstants are EXACTLY what you want them to be! Only run ONE program feature at a time, not both!
When you run the program, you may encounter a GMail server error. It's not their fault, it's yours. Log onto GMail, click on your account (top right), click 'My Account', then click 'Sign-in & security', and scroll all the way to the bottom and flip the "Allow less secure apps" to ON.
Any extraneous data in cells may corrupt or prevent the program from functioning as required. Please be sure only student data (no keys, notes, etc.) is in the cells, and not anything more.
