# JobTrack Lite

A beginner-friendly Java console application for tracking job applications.

## Features
- Add a job application with company and role
- View all saved applications
- Search applications by company name
- Update status: Applied, Interview, Selected, or Rejected
- Save and load records from a local `jobs.txt` text file
- Validate required input and application IDs

## Technologies
- Java
- Object-oriented programming
- `ArrayList`
- File handling (`java.nio.file` and readers/writers)

## Run on a computer
Requires a JDK (Java 8 or newer).

```bash
javac Main.java
java Main
```

The program creates or reads `jobs.txt` in its current working directory.

## Run on Android
Open `Main.java` in a Java IDE/compiler app that supports Java file I/O. Make sure the app permits the program to write `jobs.txt`; some online compilers use temporary storage that may not persist between runs.

## Test checklist
1. Add a TCS / Java Developer application.
2. View applications.
3. Search for `TCS`.
4. Update that application's status to `Interview`.
5. Exit.
6. Run again and confirm the record and status were loaded.

## Project scope
This is a console-based learning project, not a deployed web application. Verify file persistence in your chosen environment before relying on it.

## Author
Aditya Pattem 
Screenshots

Screenshot 1

"JobTrack Lite Screenshot 1" (file_000000008b248211a30722061c58fc01.png)

Screenshot 2

"JobTrack Lite Screenshot 2" (file_000000008b248211a30722061c58fc01%281%29.png)

Screenshot 3

"JobTrack Lite Screenshot 3" (file_000000008b248211a30722061c58fc01%282%29.png)

Screenshot 4

"JobTrack Lite Screenshot 4" (file_000000008b248211a30722061c58fc01%283%29.png)

Screenshot 5

"JobTrack Lite Screenshot 5" (file_000000008b248211a30722061c58fc01%284%29.png)

Screenshot 6

"JobTrack Lite Screenshot 6" (file_000000008b248211a30722061c58fc01%285%29.png)
