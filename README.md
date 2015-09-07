# bullshtml 1.1
Html Reporter for BullseyeCoverage tool. 
This project has been forked from google code as the original author seems to no longer maintain the project.
This project has been enhanced to support newer version of Bullseye and properly show branch coverage.

## Build instructions

Windows: "ant packagewindow"

Linux: "ant packagelinux"

## Usage

java -jar /path1/bullshtml.jar -c /path2/covxml /path3/bullshtml 

This will use covxml in a given path2 and store html files and coverage xml in /path3/bullshtml

## Example output

<img src="bullshtml_output.png" />
