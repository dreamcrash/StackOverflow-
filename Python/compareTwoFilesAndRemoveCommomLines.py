/** Original Question : https://stackoverflow.com/questions/65028698/
I have two files. File1 with data

DF2SVT-(.CD(),.CP(clk),.D(),.SDN(),.Q(na));

OAI3DSVT-(.A1(na),.A2(),.A3(),.B(),.ZN(y));

GLHSVT-(.D(v),.E(),.Q(y));

DCCDSVT-(.I(w),.ZN(y));

and file2 with data

GLHSVT-(.D(v),.E(),.Q(y));
If the line in file2 is present in file1 then remove that line from file1 and print rest of the lines of file1. So I want output file fout as

DF2SVT-(.CD(),.CP(clk),.D(),.SDN(),.Q(na));

OAI3DSVT-(.A1(na),.A2(),.A3(),.B(),.ZN(y));

DCCDSVT-(.I(w),.ZN(y));
I know how to print common lines between two files using

for line in file1 & file2:
    if line:
       print line
But I am not getting how to remove that common line from file if match is there.

*/


with open(file1, "r") as file1:
     lines_file1 = file1.readlines()
with open(file, "r") as file2:
     lines_file2 = file2.readlines()
     with open(file1, "w") as f_w:
           for line in lines_file1:
               if line not in lines_file2
                  f_w.write(line)
