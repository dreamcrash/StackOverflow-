/** Original Question : https://stackoverflow.com/questions/65234492/

How to add a table to dictionary from a given text file using Python?

Say we have two text files containing each a table like:

table1.txt

a: 1
b: 2
c: 3
b: 4
table2.txt

a: 1
b: 2
c: 3
b: 5
I would like to add those tables into Python dictionaries and then 
compare the entries so that I dump the mismatching result in output file. In the example above

output.txt

b:4 | b:5
Appreciate your inputs!

**/
# For each file, and for each of their lines add them as entries in a dictionary. 
# In the end, compare the entries of both dictionaries, and write to the file those entries that mismatch.

def count_words(file, dict):
    for line in file:
        key_value = line.split(":")
        key = key_value[0]
        dict[key] = key_value[1].strip()


dict1 = {}
dict2 = {}
with open("table1.txt", "r") as file1:
    with open("table2.txt", "r") as file2:
        count_words(file2, dict2)
    count_words(file1, dict1)


with open("output.txt", "w") as f:
    for k in dict1:
        if dict1[k] != dict2[k]:
            f.write("{0}:{1} | {0}:{2}\n".format(k, dict1[k], dict2[k]))
