Java program that will ask user to perform search term/word in excel files found in "INPUT" folder via console.
Output search results will be displayed in console in format that indicates file name, sheet name, row and column where the term exist.
Program also gives option to user to print out search results in PDF file to be put in "OUTPUT" folder.

with JUNIT, jacoco report, concurrency, itextpdf

Java batch program that will do/have the ff:
•	Configuration file (config.properties) with the ff variables
o	Input_directory
o	Output_directory
o	PDF_name
o	version
•	Read all spreadsheet (xls) file located in the input directory
•	Parse all the data in each cell of every sheet in each excel file
•	Do a map-reduce-like algo (see diagram below) on the words parsed in each cell and store in memory
•	Display 1 time to the console to “Output to file? (Y/N)”
o	If N, output the results to the console
o	If Y, output the results to the console and a PDF file
	Prefix the PDF output file with the name from properties file (PDF_name), append with _<keyword>_<datetimestamp>
•	Where <keyword> is the word used for searching
•	<datetimestamp> is the date and time the search was performed
•	Using the console input, find the word, return the file(s), sheet(s) and cell(s) containing the word. Ignore cases, print the total time spent searching and the total words found
•	If no word found, display message. Redisplay search again.
•	Display in console to “Do You want to search again (Y/N)”
o	If N, exit the program
o	If Y, continue the program
o	Re display the original question if user selects Yes. Exit the program when user selects No.
•	Use POI/iTextPDF/PDFBox library
•	Performance metrics:
o	Search result < 1s

Input files
•	Excel file will be provided
•	Data in the sheets for the input files will be limited to the columns A-H and rows 1-30

Sample Output

![Search_Results_2of2](https://github.com/user-attachments/assets/26741643-af88-45e1-95be-295e1e53caa8)
![Search_Results_1of2](https://github.com/user-attachments/assets/af684f38-4492-4bb2-9d31-f8b281693976)

![Create_PDF_1of2](https://github.com/user-attachments/assets/3f9df2e4-05b7-406d-8b24-1125f49eb72c)
![Create_PDF_2of2](https://github.com/user-attachments/assets/cfa29169-7d2a-475a-878f-34393b614fa4)

![Junit_Result_1](https://github.com/user-attachments/assets/14b8051d-f568-49da-a547-f44103eec39e)
![Junit_Result_2](https://github.com/user-attachments/assets/428561ce-0af5-4e1b-ab8b-c1fa963e435a)
![Sample_Jacoco_report](https://github.com/user-attachments/assets/72c05eb2-b33a-4119-a69c-2aff81c4c0ad)





